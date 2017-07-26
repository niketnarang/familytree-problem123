package com.FamilyTree;

import java.io.IOException;
import java.util.Scanner;

/**
 * Main class
 */
class StartHere {
    /**
     * Main method to start the application
     * @param args arguments
     * @throws IOException IO exception
     */
    public static void main (String[] args) throws IOException {
        InputListener inputListener = new InputListener(new Scanner(System.in), new FamilyTree());
        inputListener.parseInput();
    }
}
