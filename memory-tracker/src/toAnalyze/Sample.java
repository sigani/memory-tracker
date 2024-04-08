// lets use this as an example program to analyze

public class Sample {
    private int testField = 5;

    public int test() {
        return 5 + 5;
    }
    public static void main(String[] args) {
        if (args.length != 4) {
            System.err.println("Usage: java Sample <number> <number> <number> <number>");
            System.exit(1);
        }

        test();

        Integer[] integerTest = new Integer[5];

        int userInput = Integer.parseInt(args[0]);
        int userInput2 = Integer.parseInt(args[1]);
        short userInput3 = Integer.parseInt(args[2]);
        long userInput4 = Integer.parseInt(args[3]);
        int test = userInput;
        int[] test2 = new int[userInput2];
        String[] test3 = new String[4];
        String test4 = "args";
        int notUserInput = 88;

        int notUserInputCopy = notUserInput;

        int[] variableDependentArr = new int[notUserInputCopy];

        int[] array = new int[10];

        if(notUserInput > 0) {
            int one = 1;
            int two = 2;
            int three = 3;
            int four = 4;
            long whaat = 200;
            int[] lotsOfMemory = new int[9999999];
            if(notUserInput > 80) {
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

//        int[][] arrayOfArrays;
//        if(userInput4 > 0 && userInput4 < Integer.MAX_VALUE) {
//            arrayOfArrays = new int[userInput4][];
//            for(int i = 0; i < userInput4; i++) {
//                arrayOfArrays[i] = new int[12345];
//            }
//        }
    }
}