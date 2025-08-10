
#include "state.h"

#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "snake_utils.h"

/* Helper function definitions */
static void set_board_at(game_state_t* state, unsigned int row, unsigned int col, char ch);
static bool is_tail(char c);
static bool is_head(char c);
static bool is_snake(char c);
static char body_to_tail(char c);
static char head_to_body(char c);
static unsigned int get_next_row(unsigned int cur_row, char c);
static unsigned int get_next_col(unsigned int cur_col, char c);
static void find_head(game_state_t* state, unsigned int snum);
static char next_square(game_state_t* state, unsigned int snum);
static void update_tail(game_state_t* state, unsigned int snum);
static void update_head(game_state_t* state, unsigned int snum);

/* Task 1 */
game_state_t* create_default_state() {
  // TODO: Implement this function.
  game_state_t* retstate = malloc(sizeof(game_state_t));
  retstate -> num_rows = 18;
  retstate -> num_snakes = 1;

  //create snake
  snake_t *sn = malloc(sizeof(snake_t));
  sn->tail_row = 2;
  sn->tail_col=2;
  sn->head_row=2;
  sn->head_col=4;
  sn->live=true;
  retstate -> snakes = sn;

  int columns = 21;
  char** rows = (char**)malloc(retstate->num_rows*sizeof(char*));
  for (int i = 0; i < retstate->num_rows; i++) {
      char *row = (char*)malloc(21*sizeof(char));
      row[0] = '#';
      row[columns-2] = '#';
      if (i == 0 || i == 17) {
          for (int j = 1; j < columns-2; j++) {
              row[j] = '#';
          }
      }
      else if (i == 2) {
          for (int j = 1; j < columns-2; j++) {
              if (j==2) {
                  row[j] = 'd';
              }
              else if (j==3) {
                  row[j] = '>';
              }
              else if (j==4) {
                  row[j] = 'D';
              }
              else if (j==9) {
                  row[j] = '*';
              }
              else {
                  row[j] = ' ';
              }
          }
      }
      else {
          for (int j = 1; j < columns-2; j++) {
              row[j] = ' ';
          }
      }
      row[columns-1]='\0';
      rows[i] = row;
  }
  retstate->board = rows;
  return retstate;
}

/* Task 2 */
void free_state(game_state_t* state) {
  // TODO: Implement this function.
  free(state->snakes);
  for (int i = 0; i < state->num_rows; i++) {
      free(state->board[i]);
  }
  free(state->board);
  free(state);
  return;
}

/* Task 3 */
void print_board(game_state_t* state, FILE* fp) {
  // TODO: Implement this function.
  for (int i = 0; i < state->num_rows; i++) {
      char* arr = state->board[i];
      fprintf(fp, "%s", arr);
      fprintf(fp, "\n");
  }
  return;
}

/*
  Saves the current state into filename. Does not modify the state object.
  (already implemented for you).
*/
void save_board(game_state_t* state, char* filename) {
  FILE* f = fopen(filename, "w");
  print_board(state, f);
  fclose(f);
}

/* Task 4.1 */

/*
  Helper function to get a character from the board
  (already implemented for you).
*/
char get_board_at(game_state_t* state, unsigned int row, unsigned int col) {
  return state->board[row][col];
}

/*
  Helper function to set a character on the board
  (already implemented for you).
*/
static void set_board_at(game_state_t* state, unsigned int row, unsigned int col, char ch) {
  state->board[row][col] = ch;
}

/*
  Returns true if c is part of the snake's tail.
  The snake consists of these characters: "wasd"
  Returns false otherwise.
*/
static bool is_tail(char c) {
  // TODO: Implement this function.
  if (c == 'w') {
      return true;
  }
  else if (c == 'a') {
      return true;
  }
  else if (c == 's') {
      return true;
  }
  else if (c == 'd') {
      return true;
  }
  return false;
}

/*
  Returns true if c is part of the snake's head.
  The snake consists of these characters: "WASDx"
  Returns false otherwise.
*/
static bool is_head(char c) {
  // TODO: Implement this function.
  if (c == 'W') {
      return true;
  }
  else if (c == 'A') {
      return true;
  }
  else if (c == 'S') {
      return true;
  }
  else if (c == 'D') {
      return true;
  }
  else if (c == 'x') {
      return true;
  }
  return false;
}

/*
  Returns true if c is part of the snake.
  The snake consists of these characters: "wasd^<v>WASDx"
*/
static bool is_snake(char c) {
  // TODO: Implement this function.
  if (is_tail(c)) {
      return true;
  }
  else if (is_head(c)) {
      return true;
  }
  else if (c == '^') {
      return true;
  }
  else if (c == '<') {
      return true;
  }
  else if (c == 'v') {
      return true;
  }
  else if (c == '>') {
      return true;
  }
  return false;
}

/*
  Converts a character in the snake's body ("^<v>")
  to the matching character representing the snake's
  tail ("wasd").
*/
static char body_to_tail(char c) {
  // TODO: Implement this function.
  if (c == '^') {
      return 'w';
  }
  else if (c == '<') {
      return 'a';
  }
  else if (c == 'v') {
      return 's';
  }
  else if (c == '>') {
      return 'd';
  }
  return 0;
}

/*
  Converts a character in the snake's head ("WASD")
  to the matching character representing the snake's
  body ("^<v>").
*/
static char head_to_body(char c) {
  // TODO: Implement this function.
  if (c == 'W') {
      return '^';
  }
  else if (c == 'A') {
      return '<';
  }
  else if (c == 'S') {
      return 'v';
  }
  else if (c == 'D') {
      return '>';
  }
  return '0';
}

/*
  Returns cur_row + 1 if c is 'v' or 's' or 'S'.
  Returns cur_row - 1 if c is '^' or 'w' or 'W'.
  Returns cur_row otherwise.
*/
static unsigned int get_next_row(unsigned int cur_row, char c) {
  // TODO: Implement this function.
  if (c == 'v' || c == 's' || c == 'S') {
      return cur_row + 1;
  }
  else if (c == '^' || c == 'w' || c == 'W') {
      return cur_row - 1;
  }
  return cur_row;
}

/*
  Returns cur_col + 1 if c is '>' or 'd' or 'D'.
  Returns cur_col - 1 if c is '<' or 'a' or 'A'.
  Returns cur_col otherwise.
*/
static unsigned int get_next_col(unsigned int cur_col, char c) {
  // TODO: Implement this function.
  if (c == '>' || c == 'd' || c == 'D') {
      return cur_col + 1;
  }
  else if (c == '<' || c == 'a' || c == 'A') {
      return cur_col - 1;
  }
  return cur_col;
}

/*
  Task 4.2

  Helper function for update_state. Return the character in the cell the snake is moving into.

  This function should not modify anything.
*/
static char next_square(game_state_t* state, unsigned int snum) {    
  // TODO: Implement this function.
  int col = state->snakes[snum].head_col;
  int row = state->snakes[snum].head_row;
  char head = state->board[row][col];
  return state->board[get_next_row(row, head)][get_next_col(col, head)];
}

/*
  Task 4.3

  Helper function for update_state. Update the head...

  ...on the board: add a character where the snake is moving

  ...in the snake struct: update the row and col of the head

  Note that this function ignores food, walls, and snake bodies when moving the head.
*/
static void update_head(game_state_t* state, unsigned int snum) {
  // TODO: Implement this function.
  int row_num = state->snakes[snum].head_row;
  int col_num = state->snakes[snum].head_col;
  char ch = state->board[row_num][col_num];
  //updating snake
  state->snakes[snum].head_col = get_next_col(col_num, ch);
  state->snakes[snum].head_row = get_next_row(row_num, ch);
  //updating state;
  if (get_next_row(row_num, ch) == row_num + 1) {
      state->board[row_num][col_num] = 'v';
  }
  else if (get_next_row(row_num, ch) == row_num - 1) {
      state->board[row_num][col_num] = '^';
  }
  else if (get_next_col(col_num, ch) == col_num + 1) {
      state->board[row_num][col_num] = '>';
  }
  else if (get_next_col(col_num, ch) == col_num - 1) {
      state->board[row_num][col_num] = '<'; 
  }
  state->board[get_next_row(row_num, ch)][get_next_col(col_num, ch)] = ch; 
  return;
}

/*
  Task 4.4

  Helper function for update_state. Update the tail...

  ...on the board: blank out the current tail, and change the new
  tail from a body character (^<v>) into a tail character (wasd)

  ...in the snake struct: update the row and col of the tail
*/
static void update_tail(game_state_t* state, unsigned int snum) {
  // TODO: Implement this function.
  int row_num = state->snakes[snum].tail_row;
  int col_num = state->snakes[snum].tail_col;
  char ch = state->board[row_num][col_num];
  //updating snake
  state->snakes[snum].tail_col = get_next_col(col_num, ch);
  state->snakes[snum].tail_row = get_next_row(row_num, ch);
  //updating state
  char dest = state->board[get_next_row(row_num, ch)][get_next_col(col_num, ch)];
  if (dest == 'v') {
      state->board[get_next_row(row_num, ch)][get_next_col(col_num, ch)] = 's';
  }
  else if (dest == '>') {
      state->board[get_next_row(row_num, ch)][get_next_col(col_num, ch)] = 'd';
  }
  else if (dest == '<') {
      state->board[get_next_row(row_num, ch)][get_next_col(col_num, ch)] = 'a';
  }
  else if (dest == '^') {
      state->board[get_next_row(row_num, ch)][get_next_col(col_num, ch)] = 'w';
  }
  state->board[row_num][col_num] = ' ';
  return;
}

int snake_count(game_state_t* state) {
    int count = 0;
    for (int i = 0; i < state->num_rows; i++) {
        char* row = state->board[i];
        for (int j = 0; j != '\n'; j++) {
            if (is_head(row[j])) {
                count++;
            }
        }
    }
    return count;
}

/* Task 4.5 */
void update_state(game_state_t* state, int (*add_food)(game_state_t* state)) {
  // TODO: Implement this function.
  for (int i = 0; i < state->num_snakes; i++) {
      int tail_c = state->snakes[i].tail_col;
      int tail_r = state->snakes[i].tail_row;
      int head_c = state->snakes[i].head_col;
      int head_r = state->snakes[i].head_row;
      char head = state->board[head_r][head_c];
      char next_ch = state->board[get_next_row(head_r, head)][get_next_col(head_c, head)];
      if (next_ch == ' ') {
          update_head(state, i);
          update_tail(state, i);
      }
      else if (next_ch == '*') {
          update_head(state, i);
          add_food(state);
      }
      else if (next_ch == '#' || is_snake(next_ch)){
          state->board[head_r][head_c] = 'x';
          state->snakes[i].live = false;
      }
  }
  return;
}

/* Task 5 */
game_state_t* load_board(FILE* fp) {
  // TODO: Implement this function.
  game_state_t* retboard = malloc(sizeof(game_state_t));
  retboard->num_snakes=0;
  retboard->snakes=NULL;
  if (fp == NULL) {
      return NULL;
  }
  char** rows = NULL;
  char* row = NULL;
  size_t row_size = 0;
  size_t num_rows = 0;
  while (getline(&row, &row_size, fp) != -1) {
      size_t length = strcspn(row, "\n");
      row[length] = '\0';
      rows = realloc(rows, (num_rows + 1) * sizeof(char*)); //prev num_rows + 1
      rows[num_rows] = row;
      row = NULL;
      row_size = 0;
      num_rows++;
      retboard->num_rows = num_rows;
  }
  retboard->board=rows;
  return retboard;
}

/*
  Task 6.1

  Helper function for initialize_snakes.
  Given a snake struct with the tail row and col filled in,
  trace through the board to find the head row and col, and
  fill in the head row and col in the struct.
*/
int count_snakes(game_state_t* state) {
    int ret = 0;
    for (int i = 0; i < state->num_rows; i++) {
        char* row = state->board[i];
        for (int j = 1; j < strlen(row); j++) { //prev 0, '\n'
            if (is_head(row[j])) {
                ret++;
            }
        }
    }
    return ret;
}

static void find_head(game_state_t* state, unsigned int snum) {
  // TODO: Implement this function.
  //find_tail(state, snum);
  int tail_c = state->snakes[snum].tail_col;
  int tail_r = state->snakes[snum].tail_row;
  char ch = state->board[tail_r][tail_c];
  while(!is_head(ch)) {
      tail_r = get_next_row(tail_r, ch);
      tail_c = get_next_col(tail_c, ch);
      ch = state->board[tail_r][tail_c];
  }
  state->snakes[snum].head_col = tail_c;
  state->snakes[snum].head_row = tail_r;
  return;
}

void find_tail(game_state_t* state, int snum) {
    int count = 0;
    int tail_c;
    int tail_r;
    bool got_tail = false;
    for (int i = 0; i < state->num_rows; i++) {
        if (got_tail) {
            break;
        }
        char* row = state->board[i];
        for (int j = 1; j < strlen(row); j++) { //prev 0, '\n'
            if (is_tail(row[j])) {
                if (snum == count) {
                    tail_c = j;
                    tail_r = i;
                    got_tail = true;
                    break;
                }
                else {
                    count++;
                }
            }
        }
    }
    state->snakes[snum].tail_col = tail_c;
    state->snakes[snum].tail_row = tail_r;
    return;
}

/* Task 6.2 */
game_state_t* initialize_snakes(game_state_t* state) {
  // TODO: Implement this function.
  int c_snakes = count_snakes(state);
  state->snakes = malloc(sizeof(snake_t)*c_snakes);
  for (int k = 0; k < c_snakes; k++) {
      find_tail(state, k);
      find_head(state, k);
      state->snakes[k].live=true;
  }
  state->num_snakes=c_snakes;
  return state;
}
