from glob import glob

token = ''
line = ''
cIdx = 0


def error():
    print("syntax error!")
    exit(1)


def getchar():
    global cIdx
    c = line[cIdx]
    cIdx += 1
    return c


def getToken():
    global token
    while (True):
        token = getchar()
        if (token != ' '):
            return


def match(c):
    if (token == c):
        getToken()
    else:
        error()


def command():
    result = expr()
    if (token == '\n'):
        print(str(result))
    else:
        error()

# <expr> -> <term> (+ <term>, -<term>)


def expr():
    result = term()
    while (token == '+' or token == '-'):
        if (token == '+'):
            match('+')
            result += term()
        elif (token == '-'):
            match('-')
            result -= term()

    return result

# <term> -> <factor> (* <factor> | / <factor>) */


def term():
    result = factor()
    while (token == '*' or token == '/'):
        if (token == '*'):
            match('*')
            result *= factor()
        elif (token == '/'):
            match('/')
            result /= factor()
    return result

# <factor> -> [-](<number> | (<expr>) )


def factor():
    result = 1
    if (token == '-'):
        match('-')
        result = -1
    if (token == '('):
        match('(')
        result *= expr()
        if (token == ')'):
            match(')')
        else:
            error()
            exit(1)
    else:
        result *= number()
    return result

# <number> -> <digit>:{<digit>}


def number():
    result = digit()
    while (token.isdigit()):
        result = 10 * result + digit()
    return result

# digit -> 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9


def digit():
    result = 1
    if (token.isdigit()):
        result = int(token)
        match(token)
    else:
        error()
    return result


def parse():
    getToken()
    command()


def main():
    global line, token, cIdx
    while(True):
        token = ''
        cIdx = 0
        print(">> ", end='')
        line = input() + '\n'
        parse()


main()
