/* lets use this as an example program to analyze */
public class Sample {
    public static void main(java.lang.String[] args) {
        if (args.length != 4) {
            java.lang.System.err.println("Usage: java Sample <number> <number> <number> <number>");
            java.lang.System.exit(1);
        }
        int userInput = java.lang.Integer.parseInt(args[0]);
        int userInput2 = java.lang.Integer.parseInt(args[1]);
        short userInput3 = java.lang.Integer.parseInt(args[2]);
        long userInput4 = java.lang.Integer.parseInt(args[3]);
        int test = userInput;
        int[] test2 = new int[userInput2];
        java.lang.String test3 = "args";
        int notUserInput = 88;
        int[] array = new int[10];
        if (notUserInput > 0) {
            int one = 1;
            int two = 2;
            int three = 3;
            int four = 4;
            long whaat = 200;
            int[] lotsOfMemory = new int[9999999];
            if (notUserInput > 80) {
                notUserInput = userInput;
            }
        }
        if (userInput > 5) {
            int[] anotherArray = new int[5];
            if (userInput2 < 15) {
                int[] yetAnotherArray = new int[2000];
            } else {
                long[] evenMoreDataWhat = new long[8];
            }
        } else {
            if (userInput3 < 0) {
                int[] whatAnotherArray = new int[6969];
            } else {
                short[] rickAndMorty = new short[1234];
            }
            boolean newjeansIsSoGood = true;
        }
        int[][] arrayOfArrays;
        if ((userInput4 > 0) && (userInput4 < java.lang.Integer.MAX_VALUE)) {
            arrayOfArrays = new int[userInput4][];
            for (int i = 0; i < userInput4; i++) {
                arrayOfArrays[i] = new int[12345];
            }
        }
    }
}