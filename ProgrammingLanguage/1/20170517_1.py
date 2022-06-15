import time
import math

numCnt = None
nums = [0]
isPrime = [0, 0]
startClock = None
endClock = None


def GCD(a, b):
    if (a < b):
        a, b = b, a

    while (True):
        r = a % b
        if (r == 0):
            return b
        a, b = b, r


def eratos():
    global isPrime
    isPrime = [0, 0]
    isPrime = isPrime + [True for i in range(100000-1)]
    for i in range(2, math.ceil((100000+1)**0.5)):
        if (isPrime[i]):
            for j in range(i*i, 100000+1, i):
                isPrime[j] = False


def main():

    print("Input the number of numbers to process: ")
    numCnt = 0
    while(True):
        try:
            numCnt = int(input())
            if(not (2 <= numCnt and numCnt <= 30)):
                raise Exception()
        except Exception as e:
            print("Invalid input. Try again: ")
            continue
        break

    print("Input the numbers to be processed:")
    while(True):
        try:
            nums = list(map(int, input().split()))
            for item in nums:
                if(not(1 <= item and item <= 1000000)):
                    raise Exception()
            if(len(nums) != numCnt):
                raise Exception()
            nums = [0] + nums
        except Exception as a:
            print("Invalid input. Try again: ")
            continue
        break

    startClock = time.time()

    gcd = GCD(nums[1], nums[2])
    for i in range(3, numCnt+1):
        gcd = GCD(nums[i], gcd)
    print("GCD of input numbers is " + str(gcd))

    for i in range(1, numCnt):
        minIdx = i
        tmp = nums[i]
        for j in range(i+1, numCnt+1):
            if(nums[minIdx] > nums[j]):
                minIdx = j

        nums[i] = nums[minIdx]
        nums[minIdx] = tmp

    eratos()
    global isPrime
    for i in range(1, numCnt):
        cnt = 0
        if(nums[i] == nums[i+1]):
            continue

        for j in range(nums[i], nums[i+1]+1):
            if(isPrime[j]):
                cnt += 1
        print("Number of prime numbers between" +
              str(nums[i]) + ", "+str(nums[i+1])+": " + str(cnt))
    endClock = time.time()
    duration = (endClock-startClock)
    print("Total execution time using Python is "+str(duration)+" seconds!")


main()
