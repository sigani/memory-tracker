# Group22Project2
## How to setup project
0) project is using version 21 of java (open sdk) and latest version of maven
1) download and install  [maven](https://maven.apache.org/download.cgi)
    - the readme will help you, but tldr is like unzip, put in a good place (e.g home directory), setup path variable to target the bin (unsure how this differs from mac, hopefully its similar)
2) if using intellij, it should be fine afterwards
    - otherwise, might need to run `mvn clean install` and then `mvn clean test` to ensure everything works
3) it would be helpful to move the file you want to analyze into the root of memorytracker folder, but this isn't technically necessary
4) if using intellij, going into memory-tracker/src/main/java/memory/App and run the main
    - if you want to use the command line, you would have to build everything inside memory and utils folder and run the main method of App
