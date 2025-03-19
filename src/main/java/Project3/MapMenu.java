package Project3;

import javax.swing.*;

public class MapMenu extends JFrame {
    //methods
    String                  playerName;
    int                     myCharacterID;
    String                  musicName;
    private JLabel          drawCharbox;
    private JLabel          contentpane; //for background image of frame

    //this frame
    private MapMenu currentFrame;

    //pass to next frame
    MyCharacter             myCharacter; //for the chosen character
    Weapon                  myWeapons;

    //constructor
    public MapMenu()
    {
        setSize(MyConstants.FRAME_WIDTH,MyConstants.FRAME_HEIGHT);
        setLocationRelativeTo(null); // Centers the frame
        setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
        setTitle("Choosing a Map");
        setContentPane(contentpane = new JLabel());
        setVisible(true);

    }
    //add components
    public void addComponents(){

        //set actual background image  (contentPane)
        MyImageIcon background = new MyImageIcon(MyConstants.FILE_FOREST).resize(MyConstants.FRAME_WIDTH, MyConstants.FRAME_HEIGHT);
        contentpane.setIcon(background);

       // set background image for the character display (at the right)
        MyImageIcon charBox = new MyImageIcon(MyConstants.FILE_YOURCHARBOX);
        drawCharbox = new JLabel();
        drawCharbox.setIcon(charBox);
        drawCharbox.setLayout(null);
        drawCharbox.setBounds(700,25,background.getIconWidth(),background.getIconHeight());
        createPlayer();

        //textfield to show player name
        /*JPanel myCharPanel = new JPanel();
        myCharPanel.setBounds(700,25,background.getIconWidth(),background.getIconHeight());
        JLabel Namelabel = new JLabel("Your name = "+playerName);*/
        /*
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(Color.RED); // Text color
        label.setBounds(100, 100, 200, 50);*/


        //adding components
        contentpane.add(drawCharbox);

        validate();
        repaint();
    }

    //methods
    public void setPlayerName(String name){playerName = name;}
    public void setCharacterID(int ID){myCharacterID = ID;}
    public void setMusicName(String name){musicName = name;}
    public void createPlayer()
    {

        switch(myCharacterID)
        {
            case 0:
                //Hope
                break;
            case 1:
                myCharacter = new MyCharacter("Phil",125,3,
                        new Weapon("ATmega328p",5,30,30,MyConstants.FILE_WEAPON0,1),MyConstants.FILE_CHAR1);
                break;
            case 2:
                myCharacter = new MyCharacter("Ninny",100,4,
                        new Weapon("Magical Wand",15,15,10,MyConstants.FILE_WEAPON0,1),MyConstants.FILE_CHAR1);
                break;
            case 3:
                //P
                break;
            case 4:
                //Tony
                break;
        }

    }

}
