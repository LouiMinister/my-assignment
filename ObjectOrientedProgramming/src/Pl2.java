import java.util.Scanner;
import static java.lang.System.exit;

public class Pl2 {
    public static char token;
    public static int cIdx;
    public static String line;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            token = ' ';
            cIdx = 0;
            System.out.print(">> ");
            line = scanner.nextLine() + '\n';
            parse();
        }
    }

    static void error() {
        System.out.println("syntax error!");
        exit(1);
    }

    static char getchar(){
        char c = line.charAt(cIdx++);
        return c;
    }

    static void getToken() {
        while (true) {
            token = getchar();
            if (token != ' ')
                return;
        }
    }

    static void match(char c) {
        if (token == c)
            getToken();
        else
            error();
    }

    static void command() {
        int result = expr();
        if (token == '\n') {
            System.out.println(result);
        } else {
            error();
        }
    }

    // <expr> -> <term> (+ <term>, -<term>)
    static int expr() {
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
    static int term() {
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
    static int factor() {
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
    static int number() {
        int result = digit();
        while (Character.isDigit(token))
            result = 10 * result + digit();
        return result;
    }

    // digit -> 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9
    static int digit() {
        int result = 0;
        if (Character.isDigit(token)) {
            result = token - '0';
            match(token);
        } else {
            error();
        }
        return result;
    }

    static void parse() {
        getToken();
        command();
    }
}
