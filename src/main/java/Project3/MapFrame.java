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

    public MapFrame()
    {
        setTitle("Welcome "+ playerName);
        setBounds(200, 200, 400, 400);
        setVisible(true);
        setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );

        contentpane = (JPanel)getContentPane();
        contentpane.setLayout( new BorderLayout() );
    }
    public void addComponents(){
        System.out.println(playerName);
        System.out.println(chosenCharacterName);
        System.out.println(musicName);
        System.out.println(Arrays.toString(weapons));
        validate();
    }

    //methods
    public void setPlayerName(String name){playerName = name;}
    public void setCharacterName(String name){chosenCharacterName = name;}
    public void setMusicName(String name){musicName = name;}
    public void setWeapons(Weapon []w){weapons = w;}
}
