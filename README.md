# Group22Project2
## Demo video
https://media.github.students.cs.ubc.ca/user/16124/files/bf0535c1-59b6-4a7d-a2b5-685c1e0e3577

## How to setup project
0) project is using version 21 of java (open sdk) and latest version of maven
1) download and install  [maven](https://maven.apache.org/download.cgi)
    - the readme will help you, but tldr is like unzip, put in a good place (e.g home directory), setup path variable to target the bin (unsure how this differs from mac, hopefully its similar)
2) if using intellij, it should be fine afterwards
    - otherwise, might need to run `mvn clean install` and then `mvn clean test` to ensure everything works
3) it would be helpful to move the file you want to analyze into the root of memorytracker folder, but this isn't technically necessary
4) if using intellij, going into memory-tracker/src/main/java/memory/App and run the main
    - if you want to use the command line, you would have to build everything inside memory and utils folder and run the main method of App

## Example

For this piece of code:
```java
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
```
This program will tell you which branch takes up the most amount of memory  
and will tell you which arguments will lead to this branch and the value of the variables as they change  
![image](https://media.github.students.cs.ubc.ca/user/16124/files/56596efa-67e8-4e22-ab3c-00f3516a03f4)

It can also tell you other things, such as the branch that takes up the least amount of memory (with respect to user parameters)  
![image](https://media.github.students.cs.ubc.ca/user/16124/files/d5dd3524-8401-4d65-a98a-ea572124fe9d)

It can also give you a comprehensive report of each branch, the amount of memory used, and the variables needed to enter the branch and how they are influenced by the parameters  
![image](https://media.github.students.cs.ubc.ca/user/16124/files/ff459e8c-c911-4327-ba2b-5fff998885ca)

