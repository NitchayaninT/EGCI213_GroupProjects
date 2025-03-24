package Project3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MapMenu extends JFrame {
    //variables
    private String                  playerName;
    private int                     myCharacterID;
    private MySoundEffect           main_theme;
    private JLabel                  drawCharbox;
    private JLabel                  contentpane; //for background image of frame
    private JList<Map>              list;
    private Font                    F_Plain;
    private Font                    F_Bold;
    private Font                    F_large;


    //this frame
    private MapMenu currentFrame;

    //pass to next frame
    MyCharacter                 myCharacter; //for the chosen character
    Weapon[]                    myWeapons; //optional if you want to switch or not. if not, no need to pass this
    private Map                 chosenMap;
    private MySoundEffect       myMusic;
    private MapFrame mf;

    //constructor
    public MapMenu()
    {
        setSize(MyConstants.FRAME_WIDTH,MyConstants.FRAME_HEIGHT);
        setLocationRelativeTo(null); // Centers the frame
        setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
        setTitle("Choosing a MapFrame");
        setContentPane(contentpane = new JLabel());
        setVisible(true);

    }
    //add components
    public void addComponents(){

        //set font name
        F_Plain = new Font("Century Gothic", Font.PLAIN, 16);
        F_Bold = new Font("Century Gothic", Font.BOLD, 16);

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

        //create song
        //createSong();

        //create panel to hold status elements
        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.Y_AXIS)); //use BoxLayout cuz it can control spacing between each components
        F_large = new Font("Century Gothic", Font.BOLD, 24);

        //each components
        JLabel nameLabel = new JLabel("Chosen Character: "+myCharacter.getName(), SwingConstants.CENTER); nameLabel.setFont(F_large);
        JLabel hpLabel = new JLabel("HP: "+myCharacter.getHp(), SwingConstants.CENTER); hpLabel.setFont(F_large);
        JLabel weaponLabel = new JLabel("Weapon: "+myCharacter.getWeapon().getName(), SwingConstants.CENTER); weaponLabel.setFont(F_large);

        //making the image bigger for the display
        MyImageIcon resizedIcon = switch (myCharacterID) {
            case 0 -> //Hope's pic
                    new MyImageIcon(MyConstants.FILE_CHAR0).resize(200, 200);
            case 1 -> //Phil's pic
                    new MyImageIcon(MyConstants.FILE_CHAR1).resize(200, 200);
            case 2 -> //Ninny's pic
                    new MyImageIcon(MyConstants.FILE_CHAR2).resize(200, 200);
            case 3 -> //P's pic
                    new MyImageIcon(MyConstants.FILE_CHAR3).resize(200, 200);
            case 4 -> //Tony' pic
                    new MyImageIcon(MyConstants.FILE_CHAR4).resize(200, 200);
            default -> null;
        };
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

        //adding components
        JScrollPane scrollPane = new JScrollPane(createMapList()); //allow scrolling

        //create button for choosing the MapFrame, and pass items to GameFrame if action performed
        JButton chooseMapButton = new JButton("Choose");
        chooseMapButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chosenMap = list.getSelectedValue();
                System.out.println("chosen map = "+ chosenMap.getMapName());

                //music continues
                mf = new MapFrame(chosenMap.getMapName(),myCharacter,playerName);
                mf.setMusic(main_theme);
                dispose();
            }
        });
        chooseMapButton.setFont(F_Bold);

        //setting bounds
        scrollPane.setBounds(180, 230, 300, 300);
        chooseMapButton.setBounds(250,550,120,40);

        contentpane.add(chooseMapButton);
        contentpane.add(drawCharbox);
        contentpane.add(scrollPane);
        validate();
        repaint();
    }
    //methods
    public void setPlayerName(String name){playerName = name;}
    public void setCharacterID(int ID){myCharacterID = ID;}
    public void setMusic(MySoundEffect theme){this.main_theme = theme;}
    private void createPlayer()
    {
        switch(myCharacterID)
        {
            case 0:
                myCharacter = new MyCharacter("Hope",1320,10,
                        new Weapon("Mat",1,30,30,MyConstants.FILE_WEAPON0,1),MyConstants.FILE_CHAR0);
                break;
            case 1:
                myCharacter = new MyCharacter("Phil",1250,9,
                        new Weapon("ATmega328p",1,30,30,MyConstants.FILE_WEAPON1,1),MyConstants.FILE_CHAR1);
                break;
            case 2:
                myCharacter = new MyCharacter("Ninny",1000,9,
                        new Weapon("Magical Wand",1,40,30,MyConstants.FILE_WEAPON2,1),MyConstants.FILE_CHAR2);
                break;
            case 3:
                myCharacter = new MyCharacter("P",2220,10,
                        new Weapon("2Heart",1,32,32,MyConstants.FILE_WEAPON3,1),MyConstants.FILE_CHAR3);
                break;
            case 4:
                myCharacter = new MyCharacter("Tony",1200,11,
                        new Weapon("Cat",1,32,32,MyConstants.FILE_WEAPON4,1),MyConstants.FILE_CHAR4);
                break;
        }
    }
    private JList<Map> createMapList()
    {
        //create list of maps model
        DefaultListModel<Map> mapModel = new DefaultListModel<>();
        mapModel.addElement(new Map("Desert", new MyImageIcon(MyConstants.FILE_MAP0).resize(75,75)));
        mapModel.addElement(new Map("Galaxy", new MyImageIcon(MyConstants.FILE_MAP1).resize(75,75)));
        mapModel.addElement(new Map("Sky", new MyImageIcon(MyConstants.FILE_MAP2).resize(75,75)));
        mapModel.addElement(new Map("Hell", new MyImageIcon(MyConstants.FILE_MAP3).resize(75,75)));
        mapModel.addElement(new Map("Grass", new MyImageIcon(MyConstants.FILE_MAP4).resize(75,75)));

        list = new JList<Map>(mapModel);
        list.setFixedCellHeight(75);

        //set ListRenderer
        list.setCellRenderer(new MyListRenderer());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0); //to make sure 1 map is chosen intially
        return list;
    }
}
//ListRenderer customize how items are displayed inside a JList (create JList first!!)
//custom renderer to show text + image as a list
class MyListRenderer extends JPanel implements ListCellRenderer<Map>
{
    private JLabel Icon = new JLabel();
    private JLabel Name = new JLabel();

    public MyListRenderer() {
        //set up list renderer (contains Icon and Name)
        setLayout(new BorderLayout(50, 30));
        setSize(200, 75);

        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setOpaque(false);
        textPanel.add(Name);

        add(Icon, BorderLayout.WEST);
        add(textPanel, BorderLayout.CENTER);

        Name.setFont(new Font("Century Gothic", Font.BOLD, 16));
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Map> list, Map value, int index, boolean isSelected, boolean cellHasFocus)
    {
        if (value != null) {
            Name.setText(value.getMapName());
            Icon.setIcon(value.getMapIcon());

            //to make sure both appears
            Name.setOpaque(true);
            Icon.setOpaque(true);
        }
        Color grey = new Color(200, 200, 200);
        //handle selection styling (if user chooses, it turns grey but default is white)
        if (isSelected)
        {
            setBackground(grey);
            Name.setBackground(grey);
            Icon.setBackground(grey);
        }
        else
        {
            setBackground(Color.WHITE);
            Name.setBackground(Color.WHITE);
        }
        return this;
    }
}