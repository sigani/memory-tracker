Use this file to commit information clearly documenting your check-ins' content. If you want to store more information/details besides what's required for the check-ins that's fine too. Make sure that your TA has had a chance to sign off on your check-in each week (before the deadline); typically you should discuss your material with them before finalizing it here.

# Check-in 5
1. Status of final user study; any feedback and changes planned.

We finished writing up the user study that shows different ways to display the data collected by our anlaysis and are in the process of gathering feedback from other CS Students. 

2. Plans for final video (possible draft version).

No concrete plans yet, but it will probably be a slide presentation followed by a quick demonstration of our project.

3. Planned timeline for the remaining days.

Have the user study complete by the end of the week. Hopefully have a control flow senstive analysis by sometime next week and finish the implementation before april 8th.


# Check-in 3

## Mockup

**Memory Usage Analyzer for Java**
1. Upload Source Code:
  - There will be an upload button 
2. Analyze Source Code
  - There will be an analyze button
  - Inputs will be automatically generated to collect enough data
3. View Analysis Results
  - Total memory usage and peak memory usage of a program for each input size
    - Will be text-based; probably table
    - If time permits, a line graph showing memory usage for each input
  - Method-level analysis (for each method)
    - Total memory usage and peak memory usage for each input size
    - What percentage of total memory usage of the program it takes 
        - Helps determine which method is heavy on memory usage and optimizes the program
  - Branch-level analysis (for each branch)
    - Total memory usage and peak memory usage for each input size
    - What percentage of total memory usage of the program it takes 
        - Helps determine which branch is heavy on memory usage and optimizes the program

## First User Study
- UBC student CS major
  - Didn’t really see the point of branch-level analysis while he thought method-level analysis will be helpful
  - Instead of branch-level analysis, he suggested including which code statement is taking the most memory, which helps optimize the program, in the method-level analysis

## Any changes to the original design
- Probably remove branch-level analysis and include which code statement is the most heavy on memory usage for each method.


# Check-in 2

## Ideas
For our project, our program analysis will focus on memory usage where we keep track of memory usage by checking the variable types of a given source code. We will keep track of memory usage of different control flow which will allow the use to determine the range/maximum amount of memory used. This will allow the user to determine the amount of memory the program it can use.

## TA discussion
During our last meeting, we discussed with the TA various project ideas, but ultimately settled on a memory tracker tool.

## Planned division of main responsibilities
Specific details about this have yet to be decided, however there will probably be a division of 1 or 2 members working on the UI (visualization or not, the component the users will interact with), a number of members working on the static analysis portion of the project, and the remainder will help out on the components that require the most attention (or perhaps even implement the dynamic analysis portion).

## Summary of progress
We have decided that we will do the static analysis. However, we have not decided on whether we will do the dynamic analysis or do the visualization as part of our project. 

We are still deciding whether to target JavaScript, TypeScript, or Java.

We are also still deciding on the components of the program analysis project.

## Roadmap

By the end of this week
- We should be able to determine the target language
- We should be able to determine whether we will do a visualization or the dynamic analysis
- We should have a rough idea for the components of our program analysis project

By the end of next week(Check-in 3)
- We should be able to complete a mock-up for our project
- We should be able to complete our first user study
- We should be able to complete our design of the project, allowing group members to start implementation of their components

By the end of Check-in 4
- The implementation should be at a point where we should be able to analyze memory usage of simple control-flow
- We will start to plan out how to do the final user study(TBD)

By the end of Check-in 5
- Create a video(TBD)
- We should make final adjustments for our project(TBD)
- We should start to plan what to do, which depends on the state of the project at this point

# Check-in 1
During our first meeting, we discussed ideas on what our project two could be.  We have yet to think about a finalized idea but we are currently considering a program that tracks and visualizes memory usage in a C/C++ program.  A finalized project idea will be made by Tuesday Evening, March 5th.
