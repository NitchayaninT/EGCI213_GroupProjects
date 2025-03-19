package Project3;

import javax.swing.*;

public class MapMenu extends JFrame {
    //methods
    String                  playerName;
    int                     chosenCharacterID;
    String                  musicName;
    Weapon []               weapons;
    private JLabel          drawCharbox;
    private JLabel          contentpane; //for background image of frame


    //this frame
    private MapMenu currentFrame;

    //constructor
    public MapMenu()
    {
        setSize(MyConstants.FRAME_WIDTH,MyConstants.FRAME_HEIGHT);
        setLocationRelativeTo(null); // Centers the frame
        setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
        setContentPane(contentpane = new JLabel());
        contentpane.setLayout(null);
        setVisible(true);
        setTitle("Choosing a Map");
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
        drawCharbox.setBounds(600,200,background.getIconWidth(),background.getIconHeight());


        contentpane.add(drawCharbox);

        validate();
    }

    //methods
    public void setPlayerName(String name)  { playerName = name;}
    public void setCharacterID(int ID)      { chosenCharacterID = ID;}
    public void setMusicName(String name)   { musicName = name;}
    public void setWeapons(Weapon []w)      { weapons = w;}
}
