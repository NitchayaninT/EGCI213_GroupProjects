package Project3;

import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;

//main menu
public class EGCO_survivor extends JFrame{

    //components
    private JPanel          contentPane;
    private JButton         startButton, creditsButton;
    private ButtonGroup      bgroup;
    private JComboBox       chooseCharacterBox; //keep characters
    private JToggleButton   [] tb; //keep radio buttons (songs)
    private JTextField      enterNameText;
    private MySoundEffect   [] sounds;
    private JTextArea       creditsText;

    //this frame
    private EGCO_survivor   currentFrame;

    //next frame
    MapFrame                mapFrame;

    //objects
    private Player       []players; //contains characters object

    //frame width and height
    private int framewidth   = MyConstants.FRAME_WIDTH;
    private int frameheight  = MyConstants.FRAME_HEIGHT;
    private int score;

    // main methods
    public static void main(String [] args){
        new EGCO_survivor();
    }
    //charLabels[0].setBorder(BorderFactory.createLineBorder(Color.RED, 2)); // add boarder

    //CONSTRUCTOR
    public EGCO_survivor() {
        setSize(framewidth, frameheight);
        setLocationRelativeTo(null);
        setTitle("EGCO_Survivors");
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        currentFrame = this;

        setContentPane(contentPane = new JPanel());
        AddComponents();
    }

    public void AddComponents()
    {
        //set background
        MyImageIcon background = new MyImageIcon(MyConstants.FILE_BG).resize(framewidth, frameheight);

        //for sound effect
        sounds[0] = new MySoundEffect(MyConstants.FILE_THEME1);
        sounds[0].playLoop(); sounds[0].setVolume(0.4f);

        //for start button
        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //if clicked, go to next frame
                mapFrame = new MapFrame();

                //and pass messages to the next frame
            }
        });

        //for credits button
        creditsButton = new JButton("Credits");
        creditsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //if clicked, Dialog will appear
                creditsText = new JTextArea("Akkhrawin Nair 6580013\n" +
                        "Pakin Panawattanakul 6580043\n" +
                        "Nitchayanin Thamkunanon 6580081\n" +
                        "Pibhu Chitburanachart 6580195\n" +
                        "Panupong Sangaphunchai 6580587");
                JOptionPane.showMessageDialog(currentFrame,creditsText,"Credits",JOptionPane.PLAIN_MESSAGE);
            }
        });

        //enter name textfield with initial text "enter name"
        enterNameText = new JTextField("Enter name", 16);

        // set font
        Font F = new Font("Century Gothic", Font.PLAIN, 20);
        enterNameText.setFont(F);

        //for radio buttons (songs)
        tb = new JToggleButton[5];
        tb[0] = new JRadioButton("Song 1");
        tb[1] = new JRadioButton("Song 2");
        tb[2] = new JRadioButton("Song 3");
        tb[3] = new JRadioButton("Song 4");
        tb[4] = new JRadioButton("Song 5");
        bgroup = new ButtonGroup();
        for(int i=0;i<5;i++) {bgroup.add(tb[i]);}

        //for combo box (choose character)
        setCharacters();
        chooseCharacterBox = new JComboBox<>(players);

        //adding to contentpane
        contentPane.add(chooseCharacterBox);
        validate();
    }
    private void setCharacters()
    {
        players = new Player[5];
        //setting characters skills and name ...
        players[0].setName("Ninny");

    }
}

