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
import java.awt.*;


/** Main program class for launching your team's program. */
public class MainGame {
    public static void main(String[] args) {

        /*
        JFrame frame = new JFrame("minesweeper");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton button = new JButton("Click");
        frame.add(button);

        frame.setVisible(true);
         */


        //set up frame
        JFrame frame = new JFrame("Minesweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

        //set up pannel
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(10, 10)); // 10x10 grid


        //add buttons for grid cells
        //hard coded grid number
        JButton[][] buttons = new JButton[10][10];
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                buttons[row][col] = new JButton();
                gridPanel.add(buttons[row][col]);
            }
        }

        //make visible
        frame.add(gridPanel);
        frame.setVisible(true);


    }
}
