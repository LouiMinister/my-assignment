#include <ctime>
#include <iostream>
#include <limits.h>

using namespace std;

int numCnt;
int nums[31];
bool isPrime[100001] = {0, 0};
clock_t startClock;
clock_t endClock;

int GCD(int a, int b) {
  if (a < b) {
    int tmp = a;
    a = b;
    b = tmp;
  }
  while (true) {
    int r = a % b;
    if (r == 0)
      return b;
    a = b;
    b = r;
  }
}

void eratos() {
  for (int i = 2; i <= 100000; i++) {
    isPrime[i] = true;
  }
  for (int i = 2; i * i <= 100000; i++) {
    if (isPrime[i]) {
      for (int j = i * i; j <= 100000; j += i) {
        isPrime[j] = false;
      }
    }
  }
}

int main() {
  cout << "Input the number of numbers to process: ";
  numCnt = 0;

  while (!(cin >> numCnt) || !(2 <= numCnt && numCnt <= 30)) {
    cin.clear();
    cin.ignore(INT_MAX, '\n');
    cout << "Invalid input.  Try again: ";
  }

  cout << "Input the numbers to be processed:" << endl;
  bool isInputOk = true;
  int i = 1;
  while (i <= numCnt) {
    isInputOk = true;
    while (!(cin >> nums[i]) || !(1 <= nums[i] && nums[i] <= 100000)) {
      cin.clear();
      cin.ignore(INT_MAX, '\n');
      cout << "Invalid input.  Try again: ";
      isInputOk = false;
      break;
    }
    i++;
    if (!isInputOk) {
      i = 1;
    }
  }

  startClock = clock();

  int gcd = GCD(nums[1], nums[2]);
  for (int i = 3; i <= numCnt; i++) {
    gcd = GCD(nums[i], gcd);
  }
  cout << "GCD of input numbers is " << gcd << endl;

  for (int i = 1; i < numCnt; i++) { // selection sort
    int minIdx = i;
    int tmp = nums[i];
    for (int j = i + 1; j <= numCnt; j++) {
      if (nums[minIdx] > nums[j]) {
        minIdx = j;
      }
    }
    nums[i] = nums[minIdx];
    nums[minIdx] = tmp;
  }

  eratos();
  for (int i = 1; i < numCnt; i++) {
    int cnt = 0;
    if (nums[i] == nums[i + 1]) {
      continue;
    }
    for (int j = nums[i]; j <= nums[i + 1]; j++) {
      if (isPrime[j]) {
        cnt++;
      }
    }
    cout << "Number of prime numbers between " << nums[i] << ", " << nums[i + 1]
         << ": " << cnt << endl;
  }
  endClock = clock();
  double duration = (double)(endClock - startClock) / CLOCKS_PER_SEC;
  cout << "Total execution time using C++ is " << duration << " seconds!"
       << endl;
}
