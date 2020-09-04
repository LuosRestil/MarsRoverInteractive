package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MarsRover {
    public static Scanner scanner = new Scanner(System.in);
    public static Grid grid = new Grid();
    public static List<Rover> rovers = new ArrayList<>();
    public static List<Rover> destroyedRovers = new ArrayList<>();

    public static void main(String[] args) {
        for (String arg: args) {
            System.out.println(arg);
        }
        System.out.println("***** Welcome to the Mars Rover Simulator! *****");
        System.out.println("First, let's establish the simulation grid dimensions.");

        grid.setGridDimensions(scanner);
        Rover firstRover = new Rover();

        System.out.println("Now let's set the first rover's starting position on the grid.");
        System.out.println("You will need to enter an x coordinate, a y coordinate, and a heading.");
        System.out.println("Valid directions are N, S, E or W.");
        System.out.println("Example: 1 2 N");

        firstRover.setPosition(scanner, grid, rovers);

        System.out.println("Now it's time to give the rover movement instructions.");
        System.out.println("You can choose from three instructions...");
        System.out.println("\"L\" will turn the rover 90 degrees to the left.");
        System.out.println("\"R\" will turn the rover 90 degrees to the right.");
        System.out.println("\"M\" will move the rover one space in the heading direction.");
        System.out.println("You can chain commands together without spaces.");
        System.out.println("Example: LLMMMRM");

        firstRover.setInstructions(scanner);
        rovers.add(firstRover);

        boolean finished = false;
        while(!finished) {
            System.out.print("Would you like to add another rover? (Y/N): ");
            String answer = scanner.nextLine();
            if (answer.toUpperCase().equals("Y")) {
                Rover newRover = new Rover();
                newRover.setPosition(scanner, grid, rovers);
                newRover.setInstructions(scanner);
                rovers.add(newRover);
            } else if (answer.toUpperCase().equals("N")) {
                finished = true;
            } else {
                System.out.println("Invalid answer. Please try again.");
            }
        }

        System.out.println("Moving rovers...");
        for (Rover rover: rovers) {
            if (!destroyedRovers.contains(rover)) {
                rover.executeInstructions(grid, rovers, destroyedRovers);
            }
        }

        for (Rover rover : destroyedRovers) {
            if (rovers.contains(rover)) {
                rovers.remove(rover);
            }
        }

        if (rovers.size() == 0) {
            System.out.println("No rovers survived the simulation.");
        } else {
            System.out.println("Final positions:");
            for (Rover rover: rovers) {
                System.out.println(rover.getPosition());
            }
        }

    }
}
