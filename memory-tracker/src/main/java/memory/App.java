// https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html

package memory;

import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.factory.Factory;

import java.util.Scanner;

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

            MemoryScanner scanner = new MemoryScanner();
            final Factory factory = launcher.getFactory();
            CtClass<?> sample = factory.Class().get("Sample");
            sample.accept(scanner);
            MemoryReport report = new MemoryReport(scanner);

            boolean repeat = true;

            while (repeat){
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
