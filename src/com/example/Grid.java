package com.example;

import java.util.Scanner;

public class Grid {
    private int xLimit;
    private int yLimit;

    public int getWidth() {
        return xLimit;
    }

    private void setxLimit(int xLimit) {
        this.xLimit = xLimit;
    }

    public int getHeight() {
        return yLimit;
    }

    private void setyLimit(int yLimit) {
        this.yLimit = yLimit;
    }

    public void setGridDimensions(Scanner scanner) {
        boolean validDimensions = false;
        int[] dimensions = new int[2];
        while (!validDimensions) {
            System.out.print("Please enter the maximum coordinates of the simulation grid (space-separated): ");
            String dimensionsString = scanner.nextLine();
            String[] dimensionsArray = dimensionsString.split(" ");
            if (dimensionsArray.length != 2) {
                System.out.println("Coordinates must be space-separated integers.");
                continue;
            }
            try {
                dimensions[0] = Integer.parseInt(dimensionsArray[0]);
                dimensions[1] = Integer.parseInt(dimensionsArray[1]);
                if (dimensions[0] < 0 || dimensions[1] < 0) {
                    System.out.println("Coordinates must be positive integers.");
                } else {
                    validDimensions = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Coordinates must be positive integers.");
            }
        }
        this.setxLimit(dimensions[0]);
        this.setyLimit(dimensions[1]);
    }
}
