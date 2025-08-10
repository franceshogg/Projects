#include "compute.h"

// Computes the dot product of vec1 and vec2, both of size n
int32_t dot(uint32_t n, int32_t *vec1, int32_t *vec2) {
  // TODO: implement dot product of vec1 and vec2, both of size n

  return -1;
}

// Computes the convolution of two matrices
int convolve(matrix_t *a_matrix, matrix_t *b_matrix, matrix_t **output_matrix) {
  // TODO: convolve matrix a and matrix b, and store the resulting matrix in
  // output_matrix

  //load values
  uint32_t rows_a = a_matrix->rows;
  uint32_t cols_a = a_matrix->cols;
  int32_t *data_a = a_matrix->data;
  uint32_t rows_b = b_matrix->rows;
  uint32_t cols_b = b_matrix->cols;
  int32_t *data_b = b_matrix->data;

  //get size of and malloc output matrix
  uint32_t rows_c = rows_a - rows_b + 1;
  uint32_t cols_c = cols_a - cols_b + 1;
  //int *data_c = calloc(sizeof(int) * rows_c * cols_c);
  int32_t *data_c = (int32_t*)calloc(rows_c * cols_c, sizeof(int32_t));
  *output_matrix = malloc(sizeof(matrix_t));

  //for loop
  //int sum = 0;
  for (int i = 0; i < rows_c; i++) {
      for (int j = 0; j < cols_c; j++) {
          for (int k = 0; k < rows_b; k++) {
              for (int m = 0; m < cols_b; m++) {
                  //data[i][j] += data_a[i+k][j+m] * data_b[k][m]
                  //data_c[i * rows_c + j] += data_a[(i + k) * rows_a + j + m] * data_b[k * rows_b + m];
                  //data_c[i * cols_c + j] += data_a[(i + k) * cols_a + j + m] * data_b[k * cols_b + m];
                  data_c[i * cols_c + j] += data_a[(i + k) * cols_a + j + m] * data_b[(rows_b - 1 - k) * cols_b + (cols_b - 1 - m)];
                  //sum += data_c[i * cols_c + j];
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
