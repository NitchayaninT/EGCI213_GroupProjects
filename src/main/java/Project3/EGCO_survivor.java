package Project3;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.StyledEditorKit;
import java.util.*;
import java.awt.*;

//main menu
public class EGCO_survivor extends JFrame{

    //components
    private JPanel          contentPane;
    private JLabel          drawPane;
    private JButton         startButton, creditsButton;
    private ButtonGroup      bgroup;
    private JComboBox<Player>      chooseCharacterBox; //keep characters
    private JToggleButton   [] tb; //keep radio buttons
    private JTextField      enterNameText;
    private JTextArea       creditsText;
    private Font            F_Plain;
    private Font            F_Bold;

    //frame width and height
    private int framewidth   = MyConstants.FRAME_WIDTH;
    private int frameheight  = MyConstants.FRAME_HEIGHT;

    //this frame
    private EGCO_survivor   currentFrame;
    //next frame
    MapFrame                mapFrame;

    //messages to be passed to next frame
    private String          playerName;
    private Player          []players; //contains characters object
    private MySoundEffect   [] sounds;
    private Weapon          [] weapons;

    // main methods
    public static void main(String [] args){
        new EGCO_survivor();
    }
    //charLabels[0].setBorder(BorderFactory.createLineBorder(Color.RED, 2)); // add boarder

    //CONSTRUCTOR
    public EGCO_survivor() {
        //set frame's properties
        setSize(framewidth, frameheight);
        setLocationRelativeTo(null);
        setTitle("EGCO_Survivors");
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        currentFrame = this;

        //MAIN contentpane
        setContentPane(contentPane = new JPanel());
        contentPane.setLayout(new BorderLayout());
        AddComponents();
    }

    public void AddComponents()
    {
        //set fonts
        F_Plain = new Font("Century Gothic", Font.PLAIN, 16);
        F_Bold = new Font("Century Gothic", Font.BOLD, 16);

        // set background image by using JLabel as contentpane
        MyImageIcon background = new MyImageIcon(MyConstants.FILE_BG).resize(framewidth, frameheight);
        drawPane = new JLabel();
        drawPane.setIcon(background);
        drawPane.setLayout(null);

        //for sound effect
        sounds = new MySoundEffect[5];
        sounds[0] = new MySoundEffect(MyConstants.FILE_THEME1);
        sounds[0].playLoop(); sounds[0].setVolume(0.4f);

        //enter name textfield with initial text "enter name", saves player name to a variable that needs to be passed to next frame
        enterNameText = new JTextField("Enter name", 16);
        enterNameText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (enterNameText.getText().equals("Enter name")) {
                    enterNameText.setText(""); //clear text when clicked
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (enterNameText.getText().isEmpty()) {
                    enterNameText.setText("Enter name"); //restore text if left empty
                    playerName = enterNameText.getText();//keep player's name until user presses start
                }
            }
        });

        // set font when user enter name
        enterNameText.setFont(F_Plain);

        //for start button
        startButton = new JButton("Start");
        startButton.setFont(F_Bold);
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
        creditsButton.setFont(F_Bold);
        creditsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //if clicked, Dialog will appear
                creditsText = new JTextArea("Akkhrawin Nair 6580013\n" +
                        "Pakin Panawattanakul 6580043\n" +
                        "Nitchayanin Thamkunanon 6580081\n" +
                        "Pibhu Chitburanachart 6580195\n" +
                        "Panupong Sangaphunchai 6580587");
                creditsText.setFont(F_Plain);
                JOptionPane.showMessageDialog(currentFrame,creditsText,"Credits",JOptionPane.PLAIN_MESSAGE);
            }
        });

        //for radio buttons (songs)
        tb = new JToggleButton[5];
        tb[0] = new JRadioButton("Song 1"); tb[0].setFont(F_Plain);
        tb[1] = new JRadioButton("Song 2"); tb[1].setFont(F_Plain);
        tb[2] = new JRadioButton("Song 3"); tb[2].setFont(F_Plain);
        tb[3] = new JRadioButton("Song 4"); tb[3].setFont(F_Plain);
        tb[4] = new JRadioButton("Song 5"); tb[4].setFont(F_Plain);
        bgroup = new ButtonGroup();
        for(int i=0;i<5;i++) {bgroup.add(tb[i]);}
        JPanel radioButtonGroup = new JPanel();
        radioButtonGroup.setLayout( new GridLayout(5, 1) );
        for (int i=0; i < 5; i++) radioButtonGroup.add( tb[i] );

        //set weapons
        setWeapons();

        //for combo box (choose character)
        setCharacters();
        chooseCharacterBox = new JComboBox<>(players);
        chooseCharacterBox.setFont(F_Bold);

        //setting bounds for all components
        enterNameText.setBounds(450,450,295,40);
        creditsButton.setBounds(625,500,120,40);
        startButton.setBounds(450,500,120,40);
        chooseCharacterBox.setBounds(850,450,180,40);
        radioButtonGroup.setBounds(150,450,200,200);

        //adding them to drawPane (JLabel,which represents background)
        drawPane.add(enterNameText);
        drawPane.add(creditsButton);
        drawPane.add(startButton);
        drawPane.add(chooseCharacterBox);
        drawPane.add(radioButtonGroup);

        //adding background + components to contentPane
        contentPane.add(drawPane);
        validate();
    }
    private void setCharacters()
    {
        players = new Player[5];
        //setting characters skills,weapon, pic and name ...
        players[0] = new Player("Ninny",15,4,30,80,weapons[0],MyConstants.FILE_CHAR0);
        //pls set your characters by yourself!!!
        players[1] = new Player("Phil",15,4,30,80,weapons[1],MyConstants.FILE_CHAR0);
        players[2] = new Player("Hope",15,4,30,80,weapons[2],MyConstants.FILE_CHAR0);
        players[3] = new Player("Tony",15,4,30,80,weapons[3],MyConstants.FILE_CHAR0);
        players[4] = new Player("P",15,4,30,80,weapons[4],MyConstants.FILE_CHAR0);
    }
    private void setWeapons()
    {
        weapons = new Weapon[5];
        weapons[0] = new Weapon("Magical Wand",30,120,50,MyConstants.FILE_WEAPON0,1);
        //pls add your weapons by yourself !!!!
        weapons[1] = new Weapon("Weapon1",30,120,50,MyConstants.FILE_WEAPON0,1);
        weapons[2] = new Weapon("Weapon2",30,120,50,MyConstants.FILE_WEAPON0,1);
        weapons[3] = new Weapon("Weapon3",30,120,50,MyConstants.FILE_WEAPON0,1);
        weapons[4] = new Weapon("Weapon4",30,120,50,MyConstants.FILE_WEAPON0,1);
        setContentPane(contentPane = new JLabel());
        //MyImageIcon background = new MyImageIcon();
    }
}

