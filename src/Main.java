import javax.naming.OperationNotSupportedException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println(calc(reader.readLine()));
        } catch (IOException | OperationNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public static String calc(String input) throws OperationNotSupportedException {
        int aInt, bInt, result;
        String a, operation, b;
        String[] numbersAndOperation = input.split(" ");

        if (numbersAndOperation.length != 3)
            throw new IllegalArgumentException("input string must be like 'a [+-/*] b'");

        a = numbersAndOperation[0];
        operation = numbersAndOperation[1];
        b = numbersAndOperation[2];


        if (isRoman(a)) {
            if (isRoman(b))
                return calcRoman(a, operation, b);
            else throw new IllegalArgumentException("both numbers must be Roman or Arabic");
        } else if (isRoman(b))
            throw new IllegalArgumentException("both numbers must be Roman or Arabic");

        aInt = Integer.parseInt(numbersAndOperation[0]);
        bInt = Integer.parseInt(numbersAndOperation[2]);

        if (aInt < 1 || aInt > 10 || bInt < 1 || bInt > 10)
            throw new IllegalArgumentException("numbers have to be in [1;10]");

        result = doCalc(aInt, operation, bInt);

        return String.valueOf(result);
    }

    public static int doCalc(int a, String operation, int b) throws OperationNotSupportedException {
        int result;
        switch (operation) {
            case "+" -> result = a + b;
            case "-" -> result = a - b;
            case "*" -> result = a * b;
            case "/" -> result = a / b;
            default -> throw new OperationNotSupportedException("available operations : +, -, *, /");
        }
        return result;
    }

    private static Boolean isRoman(String str) {
        try {
            RomanNumerals.valueOf(str);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private static String calcRoman(String a, String operation, String b) throws OperationNotSupportedException {
        int aInt, bInt, resultInt;
        aInt = RomanNumerals.valueOf(a).ordinal();
        bInt = RomanNumerals.valueOf(b).ordinal();
        if (aInt == 0 || aInt > 10 || bInt == 0 || bInt > 10)
            throw new IllegalArgumentException("numbers have to be in [I;X]");
        resultInt = doCalc(aInt, operation, bInt);
        if (resultInt < 0)
            throw new RuntimeException("result <= 0");
        return RomanNumerals.values()[resultInt].toString();
    }
}
