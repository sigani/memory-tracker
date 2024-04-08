// lets use this as an example program to analyze

public class Test {
    private int testField = 5;
    public static void main(String[] args) {
        if (args.length != 4) {
            System.err.println("Usage: java Sample <number> <number> <number> <number>");
            System.exit(1);
        }

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

    }

    public static void method1(int a) {
        int x = 0;
        int y = 1;
        int z = 1;

        if(x > 1) {
            int[] lotsOfMemory = new int[20];
        } else {
            if (a < 9) {
                long[] moreMemory = new long[8];
            } else {
                short teehee = 0;
            }
        }
    }

    public void method2(int a, int b) {
        if(2 > 1) {
            int what = 3;
        }
        int x = 1;
        int y = 2;
        int z = 3;

        if (z > y) {
            long[] newjeansForever = new long[2000];
        } else {
            if(a > y) {
                short[] isThisBigger = new short[4000];
            } else {
                int[] orThisOne = new int[500];
            }
        }

        if (b > a) {
            int[] array = new int[200];
            long aVariable = 696969;
        }
    }

    public void method3(int a, int b, int c) {
        int x = 1;
        short y = 2;
        long z = a + b + c;
        byte[] what = new byte[200];

        if(what.length > 199) {
            if (x < z) {
               int pleaseHireMe = 200;
               String hello = "I am free to work full-time";
            } else {
                int[] youAreSeeingThis = new int[2000];
                if (youAreSeeingThis[0] > 0) {
                    String then = "<3";
                }
            }
        } else {
            long memory = 2000000;
            if (x > z) {
                long[] howManyBytesIsThis = new long[5];
                if(z > y) {
                    short imOutOfNames = 1;
                }
                String anotherMessage = "";
            } else {
                if (a > b) {
                    int[] lastArray = new int[3];
                } else {
                    long[] okILied = new long[2];
                }
            }
        }

    }
}