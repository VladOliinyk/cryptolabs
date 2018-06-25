import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private static ArrayList<String> labs = new ArrayList<String>();
    private static String splitter = "=-=-=-=-=-=-=-= =-=-=-=-=-=-=-=  =-=-=-=-=-=-=-= =-=-=-=-=-=-=-=";

    private static HillCipherLab hillCipherLab;


    public static void main(String[] args) {

        setUpLabs();
        startInteractionWithHuman();

    }

    private static void setUpLabs() {

        ///// SETTING UP LAB #1 - HILL CIPHER /////

//        hillCipherLab = new HillCipherLab("HELP", new int[][]{{3, 3}, {2, 5}}, true);
//        hillCipherLab = new HillCipherLab("HELP", new int[][]{{5, 1},{3, 5}}, true);
        hillCipherLab = new HillCipherLab("RATEMYWORKBYFIV", "ZXCV", true);

        labs.add("Lab #1. "
                + ConsoleColors.PERFORM_INFO + "Hill cipher" + ConsoleColors.RESET
                + ". Encrypt word "
                + ConsoleColors.DATA + "'" + hillCipherLab.getMsg() + "'" + ConsoleColors.RESET
                + " using key "
                + ConsoleColors.DATA + Arrays.deepToString(hillCipherLab.getKey()) + ConsoleColors.RESET
                + " and "
                + ConsoleColors.DATA + hillCipherLab.getAbc() + ConsoleColors.RESET
                + ".");


    }

    private static void performLab(int labNumber) {
        if (labNumber == 0) {
            System.out.println("Performing all available labs.");
        } else {
            if (labNumber > labs.size()) {
                System.out.println(ConsoleColors.WARNING + "Wait a minute... There is no lab #" + labNumber + " Is that wrong or just a joke?" + ConsoleColors.RESET);
                System.out.println("Closing the program.");
                return;
            }
        }

        if (labNumber == 0 || labNumber == 1) {

            System.out.println("Performing lab #" + ConsoleColors.DATA + labNumber + ConsoleColors.RESET + ". Please wait...\n");
            hillCipherLab.perform();
        }

    }


    private static int getUserLabChoise() {

        System.out.println("Please select necessary for perform lab by enter lab ID.");
        System.out.println("Here is available labs:");
        for (int i = 0; i < labs.size(); i++) {
            System.out.println("#" + ConsoleColors.CHOISE + (i + 1) + ConsoleColors.RESET + ": " + labs.get(i));
        }
        System.out.println("Enter " + ConsoleColors.CHOISE + "'0'" + ConsoleColors.RESET + " to perform all labs.");
        System.out.println("Any other input will be regarded as incorrect and the program will shut down.");
        System.out.print(ConsoleColors.CHOISE + "> " + ConsoleColors.RESET);

        String s;
        Scanner input = new Scanner(System.in);
        s = input.nextLine().replaceAll("[\\D]", "");
        if (s.length() != 0 && s.length() < 5) {
            return Integer.parseInt(s);
        } else {
//            System.out.println(splitter + "\n");
            System.out.println("Ok! As you wish... Closing the program.");
            return -1;
        }
    }

    private static int getUserGerenalChoise() {

        System.out.println("Here is available options for you:");
        System.out.println("#" + ConsoleColors.CHOISE + "1" + ConsoleColors.RESET + ": Just see pre-maked labs.");
        System.out.println("#" + ConsoleColors.CHOISE + "2" + ConsoleColors.RESET + ": Try to encrypt/decrypt sth by yourself.");
        System.out.println("Any other input will be regarded as incorrect and the program will shut down.");
        System.out.print(ConsoleColors.CHOISE + "> " + ConsoleColors.RESET);

        String s;
        Scanner input = new Scanner(System.in);
        s = input.nextLine().replaceAll("[\\D]", "");

        if (s.length() == 1) {
            if (Integer.parseInt(s) == 1 || Integer.parseInt(s) == 2) {
                return Integer.parseInt(s);
            }
        }
        System.out.println("Ok! As you wish... Closing the program.");
        return -1;
    }

    private static void startInteractionWithHuman() {

//        System.out.println(splitter);
        System.out.println("\nHello!\n");

        int userGeneralChoise = getUserGerenalChoise();

        if (userGeneralChoise == 1) {
            // pre-maked labs
            int userLabChoise = getUserLabChoise();
            if (userLabChoise > 0) {
                performLab(userLabChoise);
            }
        }


        if (userGeneralChoise == 2) {
            System.out.println(ConsoleColors.WARNING + "Sorry. This option unavailable now." + ConsoleColors.RESET);
        }


        System.out.println("\nThanks for using our software.");
        System.out.println("2018 (c) Vladyslav Oliinyk\n");
    }

}
