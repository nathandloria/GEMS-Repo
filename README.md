# GEMS - Gator Error Management System
#### Professor Mohan, Ryan Hilty, Nathan Loria
---
### Goal:
Mastering the art of programming is one of the core ideas behind undergraduate computer science education. Studying the syntax and semantics of different programming languages is an important step to learn to program. A huge challenge in this learning process is finding ways to tackle the errors in programs and incorporating debugging strategies in the program to produce a meaningful result. One challenge is for students to deal with the frustration of better understanding the errors generated during the program compilation and execution process. The second challenge is identifying ways to resolve errors by browsing through online sources such as Stackoverflow. These frustrations add negativity to the learning experience and hence restricts the students from joyfully ascending through the different levels of computer science education. In order to provide a better learning experience to the students, we propose a new tool called Gator Error Management System, a.k.a GEMS. In this project, we had adapted the idea of a translator in natural language communication. The communication between a speaker and listener in natural languages is driven through the correctness of the language validated and processed by the listener. If the speaker and the listener do not speak the same language, then a translator is required to translate the information provided by the speaker to the listener.  In GEMS, we take a similar approach to provide a translated version of error messages to students. In this paper, we propose 1) to implement a translator that translates the raw compiler-generated error messages into an enhanced error message; 2) identify and display the most helpful resources from Stackoverflow to resolve the errors produced during the compilation and execution process; 3) conduct preliminary experiments to showcase the correctness and efficiency of our tool.
### Instructions:
In order to run the source code, enter the gems-src folder and open a terminal in that directory. To build and compile new changes to the code, type ./build into the terminal. To run the program, type ./run in the same terminal and follow the prompts of the program. The input test file is located in gems-src/classes along with any output file. To test the program, introduce new errors into the java file test and enter ./run. This should attempt to compile the program which you have entered and create an error summary if errors are present.