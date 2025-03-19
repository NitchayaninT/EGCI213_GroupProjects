package Project3;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapMenu extends JFrame {
    //methods
    private String                  playerName;
    private int                     myCharacterID;
    private String                  musicName;
    private JLabel                  drawCharbox;
    private JLabel                  contentpane; //for background image of frame
    private JList<Map>                    list;

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
        drawCharbox.setLayout(new BorderLayout());
        drawCharbox.setBounds(700,150,charBox.getIconWidth(),charBox.getIconHeight());

        //create player
        createPlayer();

        //create panel to hold status elements
        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.Y_AXIS)); //use BoxLayout cuz it can control spacing between each components
        Font labelFont = new Font("Century Gothic", Font.BOLD, 24);

        //each components
        JLabel nameLabel = new JLabel("Name: "+myCharacter.getName(), SwingConstants.CENTER); nameLabel.setFont(labelFont);
        JLabel hpLabel = new JLabel("HP: "+myCharacter.getHp(), SwingConstants.CENTER); hpLabel.setFont(labelFont);
        JLabel weaponLabel = new JLabel("Weapon: "+myCharacter.getWeapon().getName(), SwingConstants.CENTER); weaponLabel.setFont(labelFont);

        //making the image bigger for the display
        MyImageIcon resizedIcon = new MyImageIcon(MyConstants.FILE_CHAR1).resize(200,200);
        JLabel playerImage = new JLabel(resizedIcon);

        //center-align text inside labels
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        hpLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        weaponLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerImage.setAlignmentX(Component.CENTER_ALIGNMENT);

        //adding components to panel
        statusPanel.add(Box.createVerticalStrut(70));
        statusPanel.add(nameLabel);
        statusPanel.add(Box.createVerticalStrut(15));
        statusPanel.add(hpLabel);
        statusPanel.add(Box.createVerticalStrut(15));
        statusPanel.add(weaponLabel);
        statusPanel.add(Box.createVerticalStrut(20));
        statusPanel.add(playerImage); // Image at the bottom
        statusPanel.setOpaque(false);

        //add panel to drawCharBox Label
        drawCharbox.add(statusPanel);

        //create Maps
        createMapList();

        //adding components
        JScrollPane scrollPane = new JScrollPane(list); //allow scrolling
        scrollPane.setBounds(150, 100, 300, 500);

        contentpane.add(drawCharbox);
        contentpane.add(scrollPane);
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
                        new Weapon("ATmega328p",5,30,30,MyConstants.FILE_WEAPON0,1),MyConstants.FILE_CHAR1,MyConstants.CH_WIDTH,MyConstants.CH_HEIGHT);
                break;
            case 2:
                myCharacter = new MyCharacter("Ninny",100,4,
                        new Weapon("Magical Wand",15,15,10,MyConstants.FILE_WEAPON0,1),MyConstants.FILE_CHAR1,MyConstants.CH_WIDTH,MyConstants.CH_HEIGHT);
                break;
            case 3:
                //P
                break;
            case 4:
                //Tony
                break;
        }
    }
    public void createMapList()
    {
        //create list of maps
        DefaultListModel<Map> mapModel = new DefaultListModel<>();
        mapModel.addElement(new Map("Map 1", new MyImageIcon(MyConstants.FILE_FOREST)));
        mapModel.addElement(new Map("Map 2", new MyImageIcon(MyConstants.FILE_FOREST)));
        mapModel.addElement(new Map("Map 3", new MyImageIcon(MyConstants.FILE_FOREST)));
        mapModel.addElement(new Map("Map 4", new MyImageIcon(MyConstants.FILE_FOREST)));
        mapModel.addElement(new Map("Map 5", new MyImageIcon(MyConstants.FILE_FOREST)));

        list = new JList<Map>(mapModel);
        //set ListRenderer
        list.setCellRenderer(new MyListRenderer());
    }
}
//ListRenderer customize how items are displayed inside a JList (create JList first!!)
//custom renderer to show text + image
class MyListRenderer extends JPanel implements ListCellRenderer<Map> {
    private JLabel Icon = new JLabel();
    private JLabel Name = new JLabel();

    public MyListRenderer() {
        setLayout(new BorderLayout(5, 5));
        JPanel panelText = new JPanel(new GridLayout(0, 1));
        panelText.add(Name);

        add(Icon, BorderLayout.WEST);
        add(panelText, BorderLayout.CENTER);

        // ✅ Set font and color for visibility
        Name.setFont(new Font("Century Gothic", Font.BOLD, 16));
        Name.setForeground(Color.BLACK);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Map> list, Map value, int index, boolean isSelected, boolean cellHasFocus) {
        if (value != null) {
            Name.setText(value.getMapName());
            Icon.setIcon(value.getMapIcon());

            Name.setForeground(Color.BLACK);
            Icon.setForeground(Color.BLACK);

            Name.setOpaque(true);
            Icon.setOpaque(true);
        }
        // set Opaque to change background color of JLabel
        Name.setOpaque(true);
        Icon.setOpaque(true);
        //handle selection styling
        if (isSelected) {
            Name.setBackground(list.getSelectionBackground());
            Icon.setBackground(list.getSelectionBackground());
            setBackground(list.getSelectionBackground());
        } else {
            setBackground(Color.WHITE);
            Name.setBackground(Color.WHITE);
            Icon.setBackground(Color.WHITE);
            Name.setForeground(Color.BLACK);
        }
        return this;
    }
}