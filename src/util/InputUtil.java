package util;

import java.util.Scanner;

public class InputUtil {
    private static InputUtil instance;
    private InputUtil(){}

    public static InputUtil getInstance() {
        return instance==null? instance=new InputUtil() : instance;
    }

    public byte inputByte (String title){
        Scanner scanner = new Scanner(System.in);
        System.out.print(title);
        return scanner.nextByte();
    }
    public int inputInt (String title){
        int input= 0;
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print(title);
            input = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Please write correct parameter !!!");
            return InputUtil.getInstance().inputInt(title);
        }

        return input;
    }
    public String inputString (String title){
        Scanner scanner = new Scanner(System.in);
        System.out.print(title);
        return scanner.nextLine();
    }
    public Double inputDouble (String title){
        Scanner scanner = new Scanner(System.in);
        System.out.print(title);
        return scanner.nextDouble();
    }
}
