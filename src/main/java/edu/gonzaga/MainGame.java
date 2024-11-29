/*
 * Final project main driver class
 * 
 * 
 * Project Description:
 * 
 * 
 * Contributors:
 * 
 * 
 * Copyright: 2023
 */
package edu.gonzaga;
import javax.swing.*;



/** Main program class for launching your team's program. */
public class MainGame {
    public static void main(String[] args) {
        //System.out.println("Hello Team Game");

        JFrame frame = new JFrame("minesweeper");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton button = new JButton("Click");
        frame.add(button);

        frame.setVisible(true);


        // Your code here. Good luck!
    }
}
