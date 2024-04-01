// lets use this as an example program to analyze

public class Sample {
    public static void main(String[] args) {
        if (args.length != 4) {
            System.err.println("Usage: java Sample <number> <number> <number> <number>");
            System.exit(1);
        }

        int userInput = Integer.parseInt(args[0]);
        int userInput2 = Integer.parseInt(args[1]);
        short userInput3 = Integer.parseInt(args[2]);
        long userInput4 = Integer.parseInt(args[3]);

        int[] array = new int[1000000];

        if (userInput > 5) {
            int[] anotherArray = new int[500000];
            if (userInput2 < 15) {
                int[] yetAnotherArray = new int[1000];
            } else {
                long[] evenMoreDataWhat = new long[8];
            }
        } else {
            if (userInput3 < 0) {
                int[] whatAnotherArray = new int[6969];
            }
        }

        int[][] arrayOfArrays;
        if(userInput4 > 0 && userInput4 < Integer.MAX_VALUE) {
            arrayOfArrays = new int[userInput4][];
            for(int i = 0; i < userInput4; i++) {
                arrayOfArrays[i] = new int[12345];
            }
        }

    }
}