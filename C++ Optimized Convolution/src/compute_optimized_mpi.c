#include <omp.h>
#include <x86intrin.h>

#include "compute.h"

// Computes the dot product of vec1 and vec2, both of size n
int32_t dot(uint32_t n, int32_t *vec1, int32_t *vec2) {
  // TODO: implement dot product of vec1 and vec2, both of size n

  return -1;
}

matrix_t* flip_b(matrix_t *b) {
    matrix_t *ret = malloc(sizeof(matrix_t));
    ret->data = malloc(sizeof(int32_t) * b->rows * b->cols);
    ret->rows = b->rows;
    ret->cols = b->cols;
    #pragma omp parallel for
    for (int i = 0; i < b->rows; i++) {
        for (int j = 0; j < b->cols; j++) {
            ret->data[(ret->rows-1-i)*ret->cols + ret->cols-1-j] = b->data[i*b->cols+j];
        }
    }
    return ret;
}

// Computes the convolution of two matrices
int convolve(matrix_t *a_matrix, matrix_t *b_matrix, matrix_t **output_matrix) {
  // TODO: convolve matrix a and matrix b, and store the resulting matrix in
  // output_matrix
  uint32_t rows_a = a_matrix->rows;
  uint32_t cols_a = a_matrix->cols;
  int32_t *data_a = a_matrix->data;
  uint32_t rows_b = b_matrix->rows;
  uint32_t cols_b = b_matrix->cols;
  matrix_t* f_b = flip_b(b_matrix);
  int32_t *data_b = f_b->data;
  uint32_t rows_c = rows_a - rows_b + 1;
  uint32_t cols_c = cols_a - cols_b + 1;
  int32_t *data_c = (int32_t*)calloc(rows_c * cols_c, sizeof(int32_t));
  *output_matrix = malloc(sizeof(matrix_t));
  #pragma omp parallel for
  for (int i = 0; i < rows_c; i++) {
      for (int j = 0; j < cols_c; j++) {
          for (int k = 0; k < rows_b; k++) {
              __m256i sum_vec = _mm256_setzero_si256();
              for (int b = 0; b < (cols_b/32)*32; b+=32) {
                  __m256i vec_a = _mm256_loadu_si256((__m256i *)(data_a + ((i + k) * cols_a + j + b)));
                  __m256i vec_b = _mm256_loadu_si256((__m256i *)(data_b + (k * cols_b + b)));
                  __m256i mult = _mm256_mullo_epi32(vec_a, vec_b);
                  sum_vec = _mm256_add_epi32(sum_vec, mult);

                  vec_a = _mm256_loadu_si256((__m256i *)(data_a + ((i + k) * cols_a + j + b + 8)));
                  vec_b = _mm256_loadu_si256((__m256i *)(data_b + (k * cols_b + b + 8)));
                  mult = _mm256_mullo_epi32(vec_a, vec_b);
                  sum_vec = _mm256_add_epi32(sum_vec, mult);

                  vec_a = _mm256_loadu_si256((__m256i *)(data_a + ((i + k) * cols_a + j + b + 16)));
                  vec_b = _mm256_loadu_si256((__m256i *)(data_b + (k * cols_b + b + 16)));
                  mult = _mm256_mullo_epi32(vec_a, vec_b);
                  sum_vec = _mm256_add_epi32(sum_vec, mult);

                  vec_a = _mm256_loadu_si256((__m256i *)(data_a + ((i + k) * cols_a + j + b + 24)));
                  vec_b = _mm256_loadu_si256((__m256i *)(data_b + (k * cols_b + b + 24)));
                  mult = _mm256_mullo_epi32(vec_a, vec_b);
                  sum_vec = _mm256_add_epi32(sum_vec, mult);
              }
              for (int m = (cols_b/32)*32; m < (cols_b/8)*8; m += 8) {
                  __m256i vec_a2 = _mm256_loadu_si256((__m256i *)(data_a + ((i + k) * cols_a + j + m)));
                  __m256i vec_b2 = _mm256_loadu_si256((__m256i *)(data_b + (k * cols_b + m)));
                  __m256i mult2 = _mm256_mullo_epi32(vec_a2, vec_b2);
                  sum_vec = _mm256_add_epi32(sum_vec, mult2);
              }
              int32_t tmp_arr[8];
              _mm256_storeu_si256((__m256i *) tmp_arr, sum_vec);
              for (int s = 0; s < 8; s++) {
                  data_c[i * cols_c + j] += tmp_arr[s];
              }
              for (int v = (cols_b/8)*8; v < cols_b; v++) {
                  data_c[i * cols_c + j] += data_a[(i + k) * cols_a + j + v] * data_b[k * cols_b + v];
              }
          }
      }
  }
  (*output_matrix)->data = data_c;
  (*output_matrix)->cols = cols_c;
  (*output_matrix)->rows = rows_c;

  return 0;
}

// Executes a task
int execute_task(task_t *task) {
  matrix_t *a_matrix, *b_matrix, *output_matrix;

  if (read_matrix(get_a_matrix_path(task), &a_matrix))
    return -1;
  if (read_matrix(get_b_matrix_path(task), &b_matrix))
    return -1;

  if (convolve(a_matrix, b_matrix, &output_matrix))
    return -1;

  if (write_matrix(get_output_matrix_path(task), output_matrix))
    return -1;

  free(a_matrix->data);
  free(b_matrix->data);
  free(output_matrix->data);
  free(a_matrix);
  free(b_matrix);
  free(output_matrix);
  return 0;
}
