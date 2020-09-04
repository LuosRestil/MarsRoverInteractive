package com.example;

import java.util.Scanner;

public class Grid {
    private int width;
    private int height;

    public int getWidth() {
        return width;
    }

    private void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    private void setHeight(int height) {
        this.height = height;
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
        this.setWidth(dimensions[0]);
        this.setHeight(dimensions[1]);
    }
}
