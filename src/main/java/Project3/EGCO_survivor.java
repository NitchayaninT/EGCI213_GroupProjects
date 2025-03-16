package Project3;

import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;

public class EGCO_survivor extends JFrame{
    // Main class act as Frame by extending JFrame
    // later set content pain and then put other components in the content pain

    // main variable
    private JLabel          contentPane;
    private JComboBox       comboBox;
    private JRadioButton    radioButton;

    private int framewidth   = MyConstants.FRAME_WIDTH;
    private int frameheight  = MyConstants.FRAME_HEIGHT;

    // main methods
    public static void main(String [] args){

    }

    //charLabels[0].setBorder(BorderFactory.createLineBorder(Color.RED, 2)); // add boarder

    public void mainMenu() {

        setSize(framewidth, frameheight);
        setLocationRelativeTo(null);
        setTitle("Main Menu");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // set background image by using JLabel as contentpane
        setContentPane(contentPane = new JLabel());
        // MyImageIcon background = new MyImageIcon();
    }
}

