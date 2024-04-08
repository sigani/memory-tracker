// https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html

package memory;

import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.factory.Factory;

import java.util.Scanner;
import java.util.Set;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        Scanner CLIScanner = new Scanner(System.in);
        System.out.println("Welcome to CPSC 410 Program Analyzer! ");
        System.out.println("Enter the path to the JAVA file you want to analyze: ");
        System.out.println("Example: memory-tracker/src/toAnalyze/Sample.java");
        String path = CLIScanner.nextLine();
        boolean pathError = true;

        Launcher launcher = new Launcher();

        while (pathError) {
            try {
                final String[] arguments = {
                        "-i", path,
                        "-o", "./target/spooned/",
                };


                launcher.setArgs(arguments);
                launcher.run();
                pathError = false;
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                System.out.println("Please enter the path to the JAVA file you want to analyze: ");
                path = CLIScanner.nextLine();
                launcher = new Launcher();
            }
        }

        try {
            MemoryScanner scanner;
            final Factory factory = launcher.getFactory();

            System.out.println("Enter the class name you want to analyze: ");
            String classname = CLIScanner.nextLine();

            CtClass<?> clas = factory.Class().get(classname);
            boolean repeat = true;

            while (repeat){
                Set<CtMethod<?>> methods = clas.getMethods();
                scanner = new MemoryScanner();

                int i = 1;
                for (CtMethod<?> m : methods) {
                    System.out.println("METHOD [" + i + "]: " + m.getSimpleName());
                    i += 1;
                }

                System.out.println("Please select the number that represents the method you would like to analyze: ");
                int methodnumber = Integer.parseInt(CLIScanner.nextLine());

                if (methodnumber > methods.size() || methodnumber < 1) {
                    System.out.println("Invalid method number. Please try again.");
                    methodnumber = Integer.parseInt(CLIScanner.nextLine());
                }

                CtMethod<?> selectedMethod = (CtMethod<?>) methods.toArray()[methodnumber - 1];

                selectedMethod.accept(scanner);
                MemoryReport report = new MemoryReport(scanner);

                System.out.println("Would you like to see branch condition that used the most memory? (Y/N)");
                String response = CLIScanner.nextLine();
                if (response.equals("Y") || response.equals("y")) {
                    report.getExtremesReport(true);
                }

                System.out.println("Would you like to see branch condition that used the least memory? (Y/N)");
                response = CLIScanner.nextLine();

                if (response.equals("Y") || response.equals("y")) {
                    report.getExtremesReport(false);
                }

                System.out.println("Would you like a comprehensive branch analysis? (Y/N)");
                response = CLIScanner.nextLine();
                if (response.equals("Y") || response.equals("y")) {
                    report.getComprehensiveReport();
                }

                System.out.println("Would you like to leave? (Y/N)");
                response = CLIScanner.nextLine();
                if (response.equals("Y") || response.equals("y")) {
                    repeat = false;
                }
            }

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
