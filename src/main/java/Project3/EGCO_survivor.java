package Project3;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.StyledEditorKit;
import java.util.*;
import java.awt.*;

//main menu
public class EGCO_survivor extends JFrame{

    //components
    private JPanel              contentPane;
    private JLabel              drawPane;
    private JButton             startButton, creditsButton;
    private ButtonGroup         bg;
    private JComboBox<String>   chooseCharacterBox; //keep characters
    private JToggleButton []    tb; //keep radio buttons
    private JTextField          enterNameText;
    private JTextArea           creditsText;
    private Font                F_Plain;
    private Font                F_Bold;
    private String              [] players; //contains players name (will pass the name user chooses to next frame)
    private String              [] songs; //contains all songs (will pass the song name to next frame)

    //frame width and height
    private int framewidth   = MyConstants.FRAME_WIDTH;
    private int frameheight  = MyConstants.FRAME_HEIGHT;

    //this frame
    private EGCO_survivor   currentFrame;
    //next frame ***
    MapFrame                mapFrame;

    //messages to be passed to next frame*** IMPORTANT
    private String          playerName;
    private String          chosenCharacterName;
    private String          chosenSong;
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
        MySoundEffect main_theme = new MySoundEffect(MyConstants.FILE_THEME1);
        main_theme.playLoop(); main_theme.setVolume(0.4f);

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
                }
                else {
                    playerName = enterNameText.getText(); //store player name
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
                //and pass messages to the next frame ****
                mapFrame.setPlayerName(playerName);
                mapFrame.setMusicName(chosenSong);
                mapFrame.setCharacterName(chosenCharacterName);
                mapFrame.setWeapons(weapons);
                mapFrame.addComponents();
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
        setSongs();
        tb = new JRadioButton[5];
        bg = new ButtonGroup();
        for(int i=0;i<5;i++)
        {
            tb[i] = new JRadioButton(songs[i]); tb[i].setFont(F_Plain);
            bg.add(tb[i]);
            //anonymous class for choosing songs
            tb[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JRadioButton selectedButton = (JRadioButton) e.getSource();
                    chosenSong = selectedButton.getText();
                }
            });
        }
        tb[0].setSelected(true);
        chosenSong = tb[0].getText(); //set initial
        JPanel radioButtonGroup = new JPanel();
        radioButtonGroup.setLayout( new GridLayout(5, 1) );
        for (int i=0; i < 5; i++) radioButtonGroup.add( tb[i] );


        //set weapons
        setWeapons();

        //for combo box (choose character)
        setCharacters();
        chooseCharacterBox = new JComboBox<>(players);
        chooseCharacterBox.setFont(F_Bold);
        chooseCharacterBox.setSelectedIndex(0);
        chosenCharacterName = (String) chooseCharacterBox.getSelectedItem(); //set initial

        //for user to choose 1 character
        chooseCharacterBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chosenCharacterName = (String) chooseCharacterBox.getSelectedItem();
            }
        });

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
        players = new String[5];
        players[0] = "Hope";
        players[1] = "Phil";
        players[2] = "Ninny";
        players[3] = "P";
        players[4] = "Tony";
    }
    private void setSongs()
    {
        songs = new String[5];
        songs[0] = "Song 0";
        songs[1] = "Song 1";
        songs[2] = "Song 2";
        songs[3] = "Song 3";
        songs[4] = "Song 4";
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
    }


}

