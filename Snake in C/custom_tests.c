#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "asserts.h"
// Necessary due to static functions in state.c
#include "state.c"

/* Look at asserts.c for some helpful assert functions */

int greater_than_forty_two(int x) {
  return x > 42;
}

bool is_vowel(char c) {
  char* vowels = "aeiouAEIOU";
  for (int i = 0; i < strlen(vowels); i++) {
    if (c == vowels[i]) {
      return true;
    }
  }
  return false;
}

/*
  Example 1: Returns true if all test cases pass. False otherwise.
    The function greater_than_forty_two(int x) will return true if x > 42. False otherwise.
    Note: This test is NOT comprehensive
*/
bool test_greater_than_forty_two() {
  int testcase_1 = 42;
  bool output_1 = greater_than_forty_two(testcase_1);
  if (!assert_false("output_1", output_1)) {
    return false;
  }

  int testcase_2 = -42;
  bool output_2 = greater_than_forty_two(testcase_2);
  if (!assert_false("output_2", output_2)) {
    return false;
  }

  int testcase_3 = 4242;
  bool output_3 = greater_than_forty_two(testcase_3);
  if (!assert_true("output_3", output_3)) {
    return false;
  }

  return true;
}

/*
  Example 2: Returns true if all test cases pass. False otherwise.
    The function is_vowel(char c) will return true if c is a vowel (i.e. c is a,e,i,o,u)
    and returns false otherwise
    Note: This test is NOT comprehensive
*/
bool test_is_vowel() {
  char testcase_1 = 'a';
  bool output_1 = is_vowel(testcase_1);
  if (!assert_true("output_1", output_1)) {
    return false;
  }

  char testcase_2 = 'e';
  bool output_2 = is_vowel(testcase_2);
  if (!assert_true("output_2", output_2)) {
    return false;
  }

  char testcase_3 = 'i';
  bool output_3 = is_vowel(testcase_3);
  if (!assert_true("output_3", output_3)) {
    return false;
  }

  char testcase_4 = 'o';
  bool output_4 = is_vowel(testcase_4);
  if (!assert_true("output_4", output_4)) {
    return false;
  }

  char testcase_5 = 'u';
  bool output_5 = is_vowel(testcase_5);
  if (!assert_true("output_5", output_5)) {
    return false;
  }

  char testcase_6 = 'k';
  bool output_6 = is_vowel(testcase_6);
  if (!assert_false("output_6", output_6)) {
    return false;
  }

  return true;
}

/* Task 4.1 */

bool test_is_tail() {
  char first = 'w';
  bool output_1 = is_tail(first);
  if (!assert_true("output_1", output_1)){
      return false;
  }
  char second = 'a';
  bool output_2 = is_tail(second);
  if (!assert_true("output_2", output_2)){
      return false;
  }
  char third = 's';
  bool output_3 = is_tail(third);
  if (!assert_true("output_3", output_3)) {
      return false;
  }
  char fourth = 'd';
  bool output_4 = is_tail(fourth);
  if (!assert_true("output_4", output_4)) {
      return false;
  }
  char fifth = 'p';
  bool output_5 = is_tail(fifth);
  if (assert_true("output_5", output_5)) {
      return false;
  }
  return true;
}

bool test_is_head() {
  char first = 'W';
  bool output1 = is_head(first);
  if (!assert_true("output1", output1)) {
      return false;
  }
  if (!assert_true("output", is_head('A'))) {
      return false;
  }
  if (!assert_true("output", is_head('S'))) {
      return false;
  }
  if (!assert_true("output", is_head('D'))) {
      return false;
  }
  if (assert_true("output2", is_head('\0'))) {
      return false;
  }
  if (assert_true("output", is_head('\n'))) {
      return false;
  }
  if (assert_true("output", is_head('w'))) {
      return false;
  }
  return true;
}

bool test_is_snake() {
  if (!assert_true("output1", is_snake('W'))) {
      return false;
  }
  if (assert_true("output2", is_snake('\n'))) {
      return false;
  }
  return true;
}

bool test_body_to_tail() {
  bool equals = body_to_tail('>') == 'd';
  if (!assert_true("output", equals)) {
      return false;
  }
  bool not_equals = body_to_tail('>') != 'd';
  if (assert_true("output", not_equals)) {
      return false;
  }
  return true;
}

bool test_head_to_body() {
  bool equals = head_to_body('W') == '^';
  if (!assert_true("output", equals)) {
      return false;
  }
  bool other = head_to_body('D') != '>';
  if (assert_true("output", other)) {
      return false;
  }
  return true;
}

bool test_get_next_row() {
  bool f = get_next_row(1, 'v') == 2;
  if (!assert_true("output", f)){
      return false;
  }
  bool g = get_next_row(1, 'q') == 2;
  if (assert_true("output", g)) {
      return false;
  }
  return true;
}

bool test_get_next_col() {
  bool f = get_next_col(1, '>') == 2;
  if (!assert_true("output", f)) {
      return false;
  }
  bool g = get_next_col(1, 'd') != 2;
  if (assert_true("output", g)) {
      return false;
  }
  return true;
}

bool test_customs() {
  if (!test_greater_than_forty_two()) {
    printf("%s\n", "test_greater_than_forty_two failed.");
    return false;
  }
  if (!test_is_vowel()) {
    printf("%s\n", "test_is_vowel failed.");
    return false;
  }
  if (!test_is_tail()) {
    printf("%s\n", "test_is_tail failed");
    return false;
  }
  if (!test_is_head()) {
    printf("%s\n", "test_is_head failed");
    return false;
  }
  if (!test_is_snake()) {
    printf("%s\n", "test_is_snake failed");
    return false;
  }
  if (!test_body_to_tail()) {
    printf("%s\n", "test_body_to_tail failed");
    return false;
  }
  if (!test_head_to_body()) {
    printf("%s\n", "test_head_to_body failed");
    return false;
  }
  if (!test_get_next_row()) {
    printf("%s\n", "test_get_next_row failed");
    return false;
  }
  if (!test_get_next_col()) {
    printf("%s\n", "test_get_next_col failed");
    return false;
  }
  return true;
}

int main(int argc, char* argv[]) {
  init_colors();

  if (!test_and_print("custom", test_customs)) {
    return 0;
  }

  return 0;
}
