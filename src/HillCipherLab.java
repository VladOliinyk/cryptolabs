import javax.swing.text.MutableAttributeSet;
import java.util.Arrays;

public class HillCipherLab {

    private static char[] ABC = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    private int[][] key;
    private String msg;
    private boolean encode;

    public HillCipherLab(String msg, int[][] key, boolean b) {
        this.msg = msg;
        this.key = key;
        this.encode = b;
    }

    public HillCipherLab(String msg, String key, boolean b) {
        this.msg = msg;
        this.key = getStrCode(key);
        this.encode = b;
    }


    public void perform() {

        System.out.println(ConsoleColors.PERFORM_INFO + "INPUT:" + ConsoleColors.RESET);
        System.out.println("Message: "+ ConsoleColors.DATA + "'" + msg + "'" + ConsoleColors.RESET);
        System.out.println("Key: " + ConsoleColors.DATA + Arrays.deepToString(key) + ConsoleColors.RESET );
        System.out.println("ABC: " + ConsoleColors.DATA + Arrays.toString(ABC) + ConsoleColors.RESET );
        System.out.println();
        System.out.println(ConsoleColors.PERFORM_INFO + "PERFORMING:" + ConsoleColors.RESET);

        System.out.println(ConsoleColors.PERFORM_INFO + "Step 0: " + ConsoleColors.RESET + "Checking key validity.");
        if (!keyIsValid(key)) {
            printError("Key is invalid. Closing program.");
            return;
        } else {
            System.out.println("Key is valid.");
        }

        //step 1. Create array of char-pairs from message
        System.out.println(ConsoleColors.PERFORM_INFO + "Step 1: " + ConsoleColors.RESET + "Creating array of char-pairs from message.");
        String[] msgPairs = getMsgPairs(msg);
        System.out.println("Message pairs is: " + ConsoleColors.DATA + Arrays.toString(msgPairs) + ConsoleColors.RESET + ".");


        //Step 2. Get message code
        System.out.println(ConsoleColors.PERFORM_INFO + "Step 2: " + ConsoleColors.RESET + "Performing array of char-pair codes.");
        int[][] messageCode = getMessagePairsMatrix(msgPairs);
        System.out.println("Message char-pair code is: " + ConsoleColors.DATA + Arrays.deepToString(messageCode) + ConsoleColors.RESET + ".");

        //Step 3. Encoding our code pairs
        System.out.println(ConsoleColors.PERFORM_INFO + "Step 3: " + ConsoleColors.RESET + "Encoding array of char-pair codes.");
        int[][] encodedMessageMatrix = new int[messageCode.length][2];
        for (int i = 0; i < messageCode.length; i++) {
            int[][] currentPairCodeMatrix = Matrix.makeMatrixFromOneDimensionalArray(messageCode[i]);
            int[][] encodedCodeMatrix = Matrix.multiply(key, currentPairCodeMatrix, 26);
            int[] encodedCodeRow = Matrix.makeArrayFromMatrix(encodedCodeMatrix);
            encodedMessageMatrix[i] = encodedCodeRow;
        }
        System.out.println("Encoded char-pairs code is: " + ConsoleColors.DATA + Arrays.deepToString(encodedMessageMatrix) + ConsoleColors.RESET + ".");

        //Step 4. Performing message from codes
        System.out.println(ConsoleColors.PERFORM_INFO + "Step 4: " + ConsoleColors.RESET + "Performing encrypted message from codes.");
        String message = getStrFromCodeArray(Matrix.makeArrayFromMatrix(encodedMessageMatrix));
        System.out.println("Encoded message is: " + ConsoleColors.DATA + message + ConsoleColors.RESET + ".");

    }

    private boolean keyIsValid(int[][] key) {


        if (Matrix.determinant(key, key.length) == 0) {
            System.out.println("Key is not valid. Determinant "
                    + ConsoleColors.DATA
                    + Matrix.determinant(key, key.length)
                    + ConsoleColors.RESET
                    + " != 0. Try another keyword.");
            return false;
        }

        int keyGsd = gcd((int) Matrix.determinant(key, key.length), ABC.length);
        if (keyGsd != 1) {
            System.out.println("Key is not valid. Determinant "
                    + ConsoleColors.DATA
                    + Matrix.determinant(key, key.length)
                    + ConsoleColors.RESET
                    + " have common dividers with the base of the module ("
                    + ConsoleColors.DATA
                    + ABC.length
                    + ConsoleColors.RESET
                    + "): "
                    + ConsoleColors.DATA
                    + keyGsd + ConsoleColors.RESET
                    + ".");
            return false;
        }

        return true;
    }

    public static int gcd(int a, int b) {
        if (b == 0) return a;
        int x = a % b;
        return gcd(b, x);
    }

    private String getStrFromCodeArray(int[] ints) {
        String result = "";
        for (int i = 0; i < ints.length; i++) {
            result += getCodedChar(ints[i]);
        }

        return result;
    }

    private String getCodedChar(int code) {

        for (int i = 0; i < ABC.length; i++) {
            if (code == i) {
                return "" + ABC[i];
            }
        }
        return "-1";

    }

    private int[][] getMessagePairsMatrix(String[] msgPairs) {

//        System.out.println("msgPairs = " + Arrays.toString(msgPairs));

        int[][] msgPairCodes = new int[msgPairs.length][2]; // Х пар
        //msgPairCodes[1] - код первой пары [a, b]

        for (int i = 0; i < msgPairCodes.length; i++) {

            msgPairCodes[i][0] = getCharCode(msgPairs[i].charAt(0));
            msgPairCodes[i][1] = getCharCode(msgPairs[i].charAt(1));
        }

//        System.out.println("msg pair codes = " + Arrays.deepToString(msgPairCodes));

        return msgPairCodes;
    }


    private String[] getMsgPairs(String msg) {
        if (msg.length() % 2 == 1) {
            msg += "" + msg.charAt(0);
            System.out.println("Adding one more character to message to make even number of letters.");
        }
        String[] pairs = new String[msg.length() / 2];
        for (int i = 0; i < msg.length(); i += 2) {
            pairs[i / 2] = msg.substring(i, i + 2);
        }
        return pairs;
    }


    private int[][] getStrCode(String str) {
        int keyCodeMatrixSize = (int) Math.sqrt(str.length());
        if (keyCodeMatrixSize * keyCodeMatrixSize != str.length()) {
            printError("Key length is incorrect.");
            System.exit(-1);
        }
        int[][] keyCode = new int[keyCodeMatrixSize][keyCodeMatrixSize];

        for (int i = 0; i < keyCodeMatrixSize; i++) {
            for (int j = 0; j < keyCodeMatrixSize; j++) {
                keyCode[i][j] = getCharCode(str.charAt(i * keyCodeMatrixSize + j));
            }
        }
        return keyCode;
    }

    private int getCharCode(char currentKeyChar) {
        for (int i = 0; i < ABC.length; i++) {
            if (currentKeyChar == ABC[i]) {
                return i;
            }
        }
        return -1;
    }

    private void printError(String errorMsg) {
        System.out.println(ConsoleColors.ERROR + "Error: " + ConsoleColors.RESET + errorMsg);
    }

    public int[][] getKey() {
        return key;
    }

    public String getMsg() {
        return msg;
    }

    public String getAbc() {
        return String.valueOf(ABC);
    }
}
