# CS5004 2024 Spring Final Project - Elevator Simulator
**This project is a simulation of an elevator system, it is developed in Java and uses the JavaFX library for the GUI. The project is developed as the final project of the CS5004 course in the 2024 Spring semester.**

- [Overview](#overview)
- [Features](#features)
- [How to Run the Project](#how-to-run-the-project)
- [How to Use the Project](#how-to-use-the-project)
- [Design Changes and Improvements](#design-changes-and-improvements)
- [Assumptions](#assumptions)
- [Limitations](#limitations)
- [Citations](#citations)

## Overview
The system is designed to simulate the operation of an elevator in a building, while able to take multiple inputs as parameters to customize the simulation. The system is able to simulate the operation of multiple elevators in a building, and the user can customize the number of floors, the number of elevators and the capacity of an elevator.

## Features
The system is able to create and simulate the operation of multiple elevators in a building via a set of buttons and JComboBox in the top control panel. Corresponding 
information and graphic representations are shown in the two main panels below the control panel.
1. `Start Elevator System` button: to start the elevator system.
2. `Stop Elevator System` button: to stop the elevator system.
3. `Step` button: to move the elevator system one step (one time unit) forward. A JComboBox is provided to select the number of steps to move forward.
4. `Add Request` button: to add a request to the system. A JComboBox is provided to select the from floor and to floor.
5. `Random Request` button: to add a random request to the system.
6. `Up Request` label: to show the up requests in the system.
7. `Down Request` label: to show the down requests in the system.
8. `Quit` button: to quit the system.

## How to Run the Project
Suppose the user has by far obtained the jar file of the project, and have directed to the directory where the jar file is located, 
the user can run the project by executing the following command in the terminal:
```
java -jar ElevatorSimulation.jar
```

## How to Use the Project
Having successfully run the jar file, user will then be directed to the start screen of the project, 
where the user can customize the number of floors, the number of elevators and the capacity of an elevator, prompts like 
`Please enter the number of floors:`, `Please enter the number of elevators:`, `Please enter the capacity of an elevator:`will be shown on the screen,
and the user can now initiate the simulation by clicking the `Start Smilation` button.

After the project is successfully run, as listed in the Features section, the user can interact with the system 
via the buttons and JComboBox in the top control panel. 

The bottom left panel will be primarily used to show the positions of the elevators in regard to the floors,
in 2D graphic representation, while the
bottom right panel will be showing the status of each elevator, including the current floor, the direction of the elevator,
door waiting time etc. in a textual and ASCII graphic representation.

## Design Changes and Improvements
In comparison to the initial design, the final design has made the following changes and improvements:
1. `List<ElevatorInterface> getElevators()` method is added to the `BuildingInterface` and `Building` classes as well as 
relevant tests are added to test suits. The purpose of this method is to return a list of elevators in the building, therefore made information
of each elevator more accessible from the controller `SwingController` class. 


2. `Request generateRandomRequest()` method is added to the `BuildingInterface` and `Building` classes as well as relevant
test cases. The purpose of this method is to generate a random request in the building, 
therefore made the simulation more dynamic and easier to test when running the project.


3. **_Error & Exception Handling_**. In the first console based design, every exception and error were handled or somewhat presented in the
console, despite the fact that the system was able to run smoothly, the user experience was not ideal. In the final design, situations where might
trigger exceptions or errors were to the maximum extent avoided by providing a set of JComboBox for the user to select from, instead of
directly inputting the number of floors, the number of elevators and the capacity of an elevator. Also, the system was designed to be able to
handle exceptions and errors more gracefully, for example, when the user tries to add a request to the system while the system is not running, it will
not be possible to do so as the corresponding button will be disabled.


4. Comparing with the first UML diagram where it mostly focused on the model design phase, the `Building` class was at the very heart and central
of the system, and the `Elevator` class was the core of the `Building` class to ensure all functionalities of the elevator system. However,
in the final design, the `SwingController` and `SwingControllerInterface` classes was added to the system, which is the controller class 
that connects the model and the view, therefore increased the modularity and flexibility of the system, also solidified the overall logic flow while closely
following the MVC design pattern.

## Assumptions
Assumptions made in the project were mostly based on the requirements of the project itself, the source code (exceptions) of the elevator 
system, and certain common sense.
1. The number of floors of a building should between 2 and 30, but because the elevator's minimum number of floors is 3, so
user are offered to select the number of floors from 3 to 30 when creating the building.


2. The number of elevators in a building should between 1 and 10, and the capacity of an elevator should between 3 and 20. Such assumptions
were all made based on the requirements of the project, and the source code from the elevator classes.


3. Overall the project assumes that the user has not any prior knowledge of the elevator system, and certainly knows not in what situation
exceptions or errors might be triggered, therefore the system is designed to be user-friendly and error-proof.


## Limitations
Despite the fact that the project was designed with the developer's best effort, there are obviously certain limitations in the project.
1. The project is designed to be a simulation of an elevator system, but overall the GUI design is not very user-friendly, elevators
are represented in a 2D graphic representation - JButtons - to be more specific, which is not very intuitive and might be confusing to the user.


2. The current GUI design is not able and not suitable for large-scale simulation, for example, when the number of floors or elevators is large, the
GUI layout will be very crowded and not very user-friendly.


3. The elevator's info is presented fairly comprehensive but missing one key information (if possible to implement in the future) - the
current number of passengers in the elevator, which is quite a current and important information in a real-world elevator system.


## Citations
1. AlexanderFeltser. (n.d.). Elevator-Swing. GitHub. Retrieved April 11, 2024, from https://github.com/alexanderFeltser/Elevator-Swing
2. JavaTpoint. (n.d.-a). Graphics in swing. Retrieved April 11, 2024, from https://www.javatpoint.com/Graphics-in-swing
3. JavaTpoint. (n.d.-b). Java Swing. Retrieved April 11, 2024, from https://www.javatpoint.com/java-swing
4. LinoNEU. (n.d.). GitHub. Retrieved April 11, 2024, from https://github.com/linoNEU
5. MarvinJason. (n.d.). Elevator-simulator. GitHub. Retrieved April 11, 2024, from https://github.com/marvinjason/elevator-simulator
6. Matia6170. (n.d.). Elevator. GitHub. Retrieved April 11, 2024, from https://github.com/matia6170/elevator?ocid=AIDcmmli8vlwie_SEM__k_EAIaIQobChMIp6iZ_pW7hQMV5DCtBh0O7QxSEAAYASAAEgK_R_D_BwE_k_
7. Oracle. (n.d.-a). Graphics (Java Platform SE 8). Retrieved April 11, 2024, from https://docs.oracle.com/javase/8/docs/api/java/awt/Graphics.html
8. Oracle. (n.d.-b). The Java Tutorials. Retrieved April 11, 2024, from https://docs.oracle.com/javase/tutorial/uiswing/
9. SimplyMisfit. (n.d.). Elevator-simulator-java-swing. GitHub. Retrieved April 11, 2024, from https://github.com/simplymisfit/Elevator-simulator-java-swing?ocid=AIDcmmli8vlwie_SEM__k_EAIaIQobChMIp6iZ_pW7hQMV5DCtBh0O7QxSEAAYASAAEgK_R_D_BwE_k_
10. Sillyfunnypedro. (n.d.). GitHub. Retrieved April 11, 2024, from https://github.com/sillyfunnypedro
