package com.example;

import java.util.List;
import java.util.Scanner;

public class Rover {
    private int xCoord;
    private int yCoord;
    private char heading;
    private String instructions;

    public int getxCoord() {
        return xCoord;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    public char getHeading() {
        return heading;
    }

    public void setHeading(char heading) {
        this.heading = heading;
    }

    public void setPosition(int xCoord, int yCoord, char heading) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.heading = heading;
    }

    public String getPosition() {
        return String.format("%d %d %c", this.xCoord, this.yCoord, this.heading);
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(Scanner scanner) {
        boolean validInstructions = false;
        String instructions = "";
        while(!validInstructions) {
            validInstructions = true;
            System.out.print("Enter instructions: ");
            instructions = scanner.nextLine();
            for (int i = 0; i < instructions.length(); i++) {
                char currentChar = instructions.charAt(i);
                if (currentChar != 'L' && currentChar != 'R' && currentChar != 'M') {
                    validInstructions = false;
                    System.out.println("Encountered invalid instruction. Please try again.");
                }
            }
        }
        this.instructions = instructions;
    }

    public void setPosition(Scanner scanner, Grid grid, List<Rover> rovers) {
        boolean validStartingPosition = false;
        while (!validStartingPosition) {
            System.out.print("Enter starting position: ");
            String startingPosition = scanner.nextLine();
            String[] startingPositionData = startingPosition.split(" ");
            if (startingPositionData.length < 3) {
                System.out.println("Starting position is missing a value. Please try again.");
            } else if (startingPositionData.length > 3) {
                System.out.println("Starting position has too many values. Please try again.");
            }
            try {
                int xCoord = Integer.parseInt(startingPositionData[0]);
                int yCoord = Integer.parseInt(startingPositionData[1]);
                char heading = Character.toUpperCase(startingPositionData[2].charAt(0));
                if (heading != ('N') && heading != 'S' && heading != 'E' && heading != 'W') {
                    System.out.println("Invalid heading.");
                } else if (xCoord < 0 || yCoord < 0) {
                    System.out.println("Both coordinates must be positive integers.");
                } else {
                    boolean outsideGrid = xCoord > grid.getWidth() || yCoord > grid.getHeight();
                    boolean onOccupiedSpace = false;
                    for (Rover rover: rovers) {
                        if (xCoord == rover.getxCoord() && yCoord == rover.getyCoord()) {
                            onOccupiedSpace = true;
                        }
                    }
                    if (outsideGrid) {
                        System.out.println("Starting position is outside grid. Please try again.");
                    } else if (onOccupiedSpace) {
                        System.out.println("Starting position already occupied. Please try again.");
                    } else {
                        validStartingPosition = true;
                        this.setxCoord(xCoord);
                        this.setyCoord(yCoord);
                        this.setHeading(heading);
                    }
                }
            } catch(NumberFormatException e) {
                System.out.println("X and Y coordinates must be positive integers.");
            }
        }
    }

    public void executeInstructions(Grid grid, List<Rover> rovers, List<Rover> destroyedRovers) {
        if (destroyedRovers.contains(this)) {
            return;
        }
        for (int i = 0; i < this.instructions.length(); i++) {
            if (this.instructions.charAt(i) == 'L' ) {
                this.turnLeft();
            } else if (this.instructions.charAt(i) == 'R') {
                this.turnRight();
            } else if (this.instructions.charAt(i) == 'M') {
                if (!this.move(grid, rovers, destroyedRovers)) {
                    return;
                }
            }
        }
    }

    private void turnLeft() {
        if (this.heading == 'W') {
            this.heading = 'S';
        } else if (this.heading == 'E') {
            this.heading = 'N';
        } else if (this.heading == 'N') {
            this.heading = 'W';
        } else if (this.heading == 'S') {
            this.heading = 'E';
        }
    }

    private void turnRight() {
        if (this.heading == 'W') {
            this.heading = 'N';
        } else if (this.heading == 'E') {
            this.heading = 'S';
        } else if (this.heading == 'N') {
            this.heading = 'E';
        } else if (this.heading == 'S') {
            this.heading = 'W';
        }
    }

    private boolean move(Grid grid, List<Rover> rovers, List<Rover> destroyedRovers) {
        if (destroyedRovers.contains(this)) {
            return false;
        }
        if (this.heading == 'W') {
            if (this.xCoord > 0) {
                this.xCoord -= 1;
            } else {
                System.out.println("ERROR: ROVER HAS LEFT THE GRID AND BEEN DESTROYED");
                destroyedRovers.add(this);
                return false;
            }
        } else if (this.heading == 'E') {
            if (this.xCoord < grid.getWidth()) {
                this.xCoord += 1;
            } else {
                System.out.println("ERROR: ROVER HAS LEFT THE GRID AND BEEN DESTROYED");
                destroyedRovers.add(this);
                return false;
            }
        } else if (this.heading == 'N') {
            if (this.yCoord < grid.getHeight()) {
                this.yCoord += 1;
            } else {
                System.out.println("ERROR: ROVER HAS LEFT THE GRID AND BEEN DESTROYED");
                destroyedRovers.add(this);
                return false;
            }
        } else if (this.heading == 'S') {
            if (this.yCoord > 0) {
                this.yCoord -= 1;
            } else {
                System.out.println("ERROR: ROVER HAS LEFT THE GRID AND BEEN DESTROYED");
                destroyedRovers.add(this);
                return false;
            }
        }
        for (Rover rover: rovers) {
            if (rover == this) {
                continue;
            } else if (destroyedRovers.contains(rover)) {
                continue;
            } else {
                if (rover.getxCoord() == this.xCoord && rover.getyCoord() == this.yCoord) {
                    System.out.println("ERROR: ROVER HAS COLLIDED WITH ANOTHER ROVER AND BOTH HAVE BEEN DESTROYED");
                    destroyedRovers.add(this);
                    destroyedRovers.add(rover);
                    return false;
                }
            }
        }
        return true;
    }
}
