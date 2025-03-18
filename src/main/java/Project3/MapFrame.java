package Project3;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class MapFrame extends JFrame {
    //methods
    private JPanel          contentpane;
    String playerName;
    String chosenCharacterName;
    String musicName;
    Weapon [] weapons;

    //this frame
    private MapFrame   currentFrame;

    //constructor
    public MapFrame()
    {
        setSize(800,600);
        setLocationRelativeTo(null); // Centers the frame
        setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
        contentpane = (JPanel)getContentPane();
        contentpane.setLayout( new BorderLayout() );

    }
    //add components
    public void addComponents(){
        setTitle("Welcome "+ playerName);
        System.out.println(playerName);
        System.out.println(chosenCharacterName);
        System.out.println(musicName);
        System.out.println(Arrays.toString(weapons));
        setVisible(true);
        validate();
    }

    //methods
    public void setPlayerName(String name){playerName = name;}
    public void setCharacterName(String name){chosenCharacterName = name;}
    public void setMusicName(String name){musicName = name;}
    public void setWeapons(Weapon []w){weapons = w;}
}
