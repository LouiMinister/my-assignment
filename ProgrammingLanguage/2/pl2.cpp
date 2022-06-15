#include <ctype.h>
#include <iostream>
#include <stdlib.h>

using namespace std;

char token;

void command(void);
int expr(void);
int term(void);
int factor(void);
int number(void);
int digit(void);

void error(void) {
  cout << "syntax error!" << endl;
  exit(1);
}

void getToken(void) {
  while (true) {
    token = getchar();
    if (token != ' ')
      return;
  }
}

void match(char c) {
  if (token == c)
    getToken();
  else
    error();
}

void command(void) {
  int result = expr();
  if (token == '\n') {
    cout << result << endl;
  } else {
    error();
  }
}

// <expr> -> <term> (+ <term>, -<term>)
int expr(void) {
  int result;
  result = term();
  while (token == '+' || token == '-') {
    if (token == '+') {
      match('+');
      result += term();
    } else if (token == '-') {
      match('-');
      result -= term();
    }
  }
  return result;
}

// <term> -> <factor> (* <factor> | / <factor>) */
int term(void) {
  int result = factor();
  while (token == '*' || token == '/') {
    if (token == '*') {
      match('*');
      result *= factor();
    } else if (token == '/') {
      match('/');
      result /= factor();
    }
  }
  return result;
}

// <factor> -> [-](<number> | (<expr>) )
int factor(void) {
  int result = 1;
  if (token == '-') {
    match('-');
    result = -1;
  }
  if (token == '(') {
    match('(');
    result *= expr();
    if (token == ')') {
      match(')');
    } else {
      error();
      exit(1);
    }
  } else {
    result *= number();
  }
  return result;
}

// <number> -> <digit>{<digit>}
int number(void) {
  int result = digit();
  while (isdigit(token))
    result = 10 * result + digit();
  return result;
}

// digit -> 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9
int digit(void) {
  int result;
  if (isdigit(token)) {
    result = token - '0';
    match(token);
  } else {
    error();
  }
  return result;
}

void parse(void) {
  getToken();
  command();
}

int main() {
  while (true) {
    cout << ">> ";
    parse();
  }
  return 0;
}
