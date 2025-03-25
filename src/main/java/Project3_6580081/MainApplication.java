/*Akkhrawin Nair 6580013
Pakin Panawattanakul 6580043
Nitchayanin Thamkunanon 6580081
Pibhu Chitburanachart 6580195
Panupong Sangaphunchai 6580587*/

package Project3_6580081;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.List;
import java.util.*;
import java.awt.*;

//main menu
public class MainApplication extends JFrame{

    //components
    private JPanel              contentPane;
    private JLabel              drawPane;
    private JButton             startButton, creditsButton, howToPlayButton;
    private ButtonGroup         bg;
    private JComboBox<String>   chooseCharacterBox; //keep characters
    private JToggleButton []    tb; //keep radio buttons
    private JTextField          enterNameText;
    private JTextArea           creditsText, howToPlayText;
    private Font                F_Plain;
    private Font                F_Bold;
    private String              [] players; //contains players name (will pass the name user chooses to next frame)
    private String              [] songs; //contains all songs (will pass the song name to next frame)
    private boolean             running = true; //keep track of the stars' status. if false, that means we move to another frame
    private MySoundEffect       main_theme;
    private JSlider             volumeSlider;

    //frame width and height
    private int framewidth   = MyConstants.FRAME_WIDTH;
    private int frameheight  = MyConstants.FRAME_HEIGHT;

    //this frame
    private MainApplication currentFrame;
    //next frame ***
    MapMenu mapFrame;

    //messages to be passed to next frame*** IMPORTANT
    private String          playerName;
    private int             characterID;
    private int             songID;

    // main methods
    public static void main(String [] args){
        new MainApplication();
    }
    //charLabels[0].setBorder(BorderFactory.createLineBorder(Color.RED, 2)); // add boarder

    //CONSTRUCTOR
    public MainApplication() {
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

        //start time
        long startTime = System.currentTimeMillis();

        //keep track of RUNNING threads
        List<Thread> starThreads = new ArrayList<>();

        while (running) {//spawning stars
            long currentTime = System.currentTimeMillis();
            long differenceTime = currentTime - startTime;
            if(differenceTime < 15000)
            {
                starThreads.addAll(starThread());//spawning stars thread and add to array
            }
            else
            {
                //just dont create more threads, to save resources and to not make main menu look messy!
                //join if the frame disposes (!running), else, waits until it disposes and then join threads
                if(!running)
                {
                    for (Thread t : starThreads) {
                        try {
                            t.join();
                        } catch (InterruptedException e) {}
                    }
                    System.out.println("All star threads have finished.");
                    break;
                }
                else continue;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {}
        }
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

        //for sound effect (FILE_THEME is initial song)
        main_theme = new MySoundEffect(MyConstants.FILE_THEME0);
        main_theme.playLoop(); main_theme.setVolume(0.5f);

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
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //if clicked, go to next frame
                mapFrame = new MapMenu();
                //and pass messages to the next frame ****
                mapFrame.setPlayerName(playerName);
                mapFrame.setMusic(main_theme);
                mapFrame.setCharacterID(characterID);
                mapFrame.addComponents();
                running = false;
                //but music continues
                dispose();
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

            //store index as action command (to be set inside the anonymous class)
            tb[i].setActionCommand(String.valueOf(i));

            //anonymous class for choosing songs
            tb[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JRadioButton selectedButton = (JRadioButton) e.getSource();

                    //songID now gets index from action command
                    songID = Integer.parseInt(e.getActionCommand());

                    //this keeps songFilename so that it will run correct music file
                    String songfileName = MyConstants.PATH+"Music/theme"+songID+".wav";

                    //stops the old song, then open selected song
                    main_theme.stop();
                    main_theme = new MySoundEffect(songfileName); //FILE_THEME 0 - 5
                    main_theme.playLoop(); main_theme.setVolume(0.4f);
                }
            });
        }
        tb[0].setSelected(true);
        songID = 0; //set initial
        JPanel radioButtonGroup = new JPanel();
        radioButtonGroup.setLayout( new GridLayout(5, 1) );
        for (int i=0; i < 5; i++) radioButtonGroup.add( tb[i] );

        //for combo box (choose character)
        setCharacters();
        chooseCharacterBox = new JComboBox<>(players);
        chooseCharacterBox.setFont(F_Bold);
        chooseCharacterBox.setSelectedIndex(0);
        characterID = chooseCharacterBox.getSelectedIndex();//set initial

        //for user to choose 1 character
        chooseCharacterBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                characterID = chooseCharacterBox.getSelectedIndex();
            }
        });

        //add volume slider
        volumeSlider();

        //how to play button
        howToPlay();

        //setting bounds for all components
        enterNameText.setBounds(450,450,295,40);
        creditsButton.setBounds(625,500,120,40);
        startButton.setBounds(450,500,120,40);
        chooseCharacterBox.setBounds(850,450,180,40);
        radioButtonGroup.setBounds(150,450,200,200);
        volumeSlider.setBounds(framewidth/2-100,frameheight-180,180,50);
        howToPlayButton.setBounds(framewidth-335,500,150,40);

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
        songs[0] = "No role modelz";
        songs[1] = "Rick Roll";
        songs[2] = "Mingle Game";
        songs[3] = "Long Pan";
        songs[4] = "At Doom's Gate";
    }
    //method to make stars fall
    public List<Thread> starThread()
    {
        //keep track of alive threads
        List<Thread> starThreads = new ArrayList<>();
        Thread starThread= new Thread() {
            public void run()
            {
                MyImageIcon icon = new MyImageIcon(MyConstants.PATH + "/star.png").resize(10, 10);
                JLabel star = new JLabel();
                star.setLayout(null);
                star.setIcon(icon);
                star.setHorizontalAlignment(JLabel.CENTER);

                //use to random x and Y axis
                Random rand = new Random();
                int yAxis = 0; //initial

                //add to draw pane
                drawPane.add(star);

                //looping
                while(running){
                    try {
                        Thread.sleep(40);
                    } catch (InterruptedException e) {throw new RuntimeException(e);}

                    int xAxis = rand.nextInt(MyConstants.FRAME_WIDTH - 10);
                    yAxis = rand.nextInt(MyConstants.FRAME_HEIGHT/2);

                    //set stars
                    star.setBounds(xAxis, yAxis, 5, 5);
                }
            }
        };
        starThread.start();
        starThreads.add(starThread);
        return starThreads;
    }
    public void volumeSlider()
    {
        JLabel volumeLabel = new JLabel("Volume");
        volumeLabel.setFont(F_Bold);
        volumeLabel.setForeground(Color.WHITE);
        volumeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        volumeLabel.setVisible(true);

        volumeSlider = new JSlider(0, 100, 10); //min, max, initial volume
        volumeSlider.setMajorTickSpacing(25);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(true);
        volumeSlider.setOpaque(true);
        volumeLabel.setBounds(framewidth/2-60,frameheight-220,100,50);

        drawPane.add(volumeSlider);
        drawPane.add(volumeLabel);

        volumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int volume = volumeSlider.getValue();
                System.out.println("Volume set to: " + volume);
                main_theme.setVolume(volume/100.0f);
            }
        });
    }
    public void howToPlay()
    {
        //for how to play button
        howToPlayButton = new JButton("How to play");
        howToPlayButton.setFont(F_Bold);

        howToPlayButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                //if clicked, go to JOptionPane
                howToPlayText = new JTextArea("Welcome to EGCO survivors!\n" +
                        "We want to know if you can actually survive from EGCO battlefield \n" +
                        "Are you good enough to survive?\n\n" +
                        "Press W,A,S,D keys to control the character\n" +
                        "Press Space bar to shoot\n"+
                        "Be prepared...for the EGCO war\n");
                howToPlayText.setFont(F_Bold);
                JOptionPane.showMessageDialog(currentFrame,howToPlayText,"How to play",JOptionPane.PLAIN_MESSAGE);
            }
        });
        drawPane.add(howToPlayButton);

    }
}


