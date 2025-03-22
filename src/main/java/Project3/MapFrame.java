package Project3;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.util.*;
import javax.swing.Timer;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Random;

class Map{
    private MyImageIcon mapIcon;
    private String mapName;
    Map(String name, MyImageIcon icon){
        this.mapName = name;
        this.mapIcon = icon;
    }

    //setter getter
    public String getMapName(){return mapName;}
    public MyImageIcon getMapIcon(){return mapIcon;}
}
public class MapFrame extends JFrame implements KeyListener
{
    //methods
    private String mapName;
    private MyImageIcon mapIcon;
    private Random rand = new Random();
    private MyCharacter MyCharacter;
    private MyCharacterPanel MyCharacterPanel;
    private Monster monster;
    private Boss boss;
    private MyImageIcon backgroundImg;
    String  MyCharacterName;
    String  chosenCharacterName;
    String  musicName;
    Weapon [] weapons;
    private MySoundEffect themesound;
    private int framewidth   = MyConstants.FRAME_WIDTH;
    private int frameheight  = MyConstants.FRAME_HEIGHT;
    private int MyCharacterWidth = MyConstants.PL_WIDTH;
    private int MyCharacterHeight = MyConstants.PL_HEIGHT;
    private Weapon weapon;
    //this frame
    private MapFrame   currentFrame;
    private MapPanel mapPanel;
    //constructor
    public MapFrame(String name)
    {
        this.mapName = name;
        //set title, frame's size and properties
        setTitle("Welcome "+ MyCharacterName);
        setSize(framewidth,frameheight);
        setLocationRelativeTo(null); // Centers the frame
        setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        currentFrame = this;
        //add keylistener
        addKeyListener(this);
        addComponents();
    }
    //add components
    public void addComponents(){
        //create a MyCharacter
        MyCharacter = new MyCharacter(MyCharacterName,100,10,MyCharacterWidth,MyCharacterHeight,weapon,MyConstants.FILE_CHAR0);
        //create a JPanel for moving background
        mapPanel = new MapPanel(MyCharacter);
        mapPanel.setLayout(null);
        //set dimension for the JPanel
        mapPanel.setPreferredSize(new Dimension(framewidth, frameheight));
        setContentPane(mapPanel);
        MyCharacterPanel = MyCharacter.getMyCharacterPanel();
        MyCharacterPanel.setBounds(framewidth/2-MyCharacterWidth/2,frameheight/2-MyCharacterHeight/2,MyCharacterWidth,(int)((double)MyCharacterHeight*1.2));
        mapPanel.add(MyCharacterPanel);
        mapPanel.repaint();
        //theme song
        for(int i=0;i<10;i++){
            spawnMonster();
            System.out.println("Spawn monster");
        }
        themesound = new MySoundEffect(MyConstants.FILE_THEME1);
        themesound.playLoop(); themesound.setVolume(0.4f);
        //creating MyCharacter
        System.out.println(MyCharacterName);
        System.out.println(chosenCharacterName);
        System.out.println(musicName);
        System.out.println(Arrays.toString(weapons));
        setVisible(true);
        validate();
    }

    //methods
    public void setMyCharacterName(String name){MyCharacterName = name;}
    public void setCharacterName(String name){chosenCharacterName = name;}
    public void setMusicName(String name){musicName = name;}
    public void setWeapons(Weapon []w){weapons = w;}

    //for moving
    public void keyPressed(KeyEvent e)
    {
        int keyCode = e.getKeyCode();
        switch(keyCode)
        {
            case KeyEvent.VK_A:
                MyCharacter.moveLeft();
                mapPanel.moveLeft();
                mapPanel.repaint();
                revalidate();
                repaint();
                break;
            case KeyEvent.VK_D:
                MyCharacter.moveRight();
                mapPanel.moveRight();
                mapPanel.repaint();
                revalidate();
                repaint();
                break;
            case KeyEvent.VK_W:
                MyCharacter.moveUp();
                mapPanel.moveUp();
                mapPanel.repaint();
                revalidate();
                repaint();
                break;
            case KeyEvent.VK_S:
                MyCharacter.moveDown();
                mapPanel.moveDown();
                mapPanel.repaint();
                revalidate();
                repaint();
                break;
        }
    }
    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}
    public void spawnMonster()
    {
        Thread monsterThread = new Thread()
        {
            public void run()
            {
                int randX = rand.nextInt(MyConstants.BG_WIDTH-MyConstants.MON1_WIDTH);
                int randY = rand.nextInt(MyConstants.BG_HEIGHT-MyConstants.MON1_HEIGHT);
                Monster monster = new Monster("default",MyConstants.MON1_HP,10,MyConstants.MON1_WIDTH,MyConstants.MON1_HEIGHT,MyConstants.FILE_AJ12,randX,randY,MyCharacter);
                mapPanel.add(monster);
                while(true)
                {
                    monster.updateLocation();
                }
            }
        };
        monsterThread.start();
    }
}
class MapPanel extends JPanel{
    //size of panel that we are going to map to frame
    private static final int DIM_W = MyConstants.FRAME_WIDTH;
    private static final int DIM_H = MyConstants.FRAME_HEIGHT;
    //where background will be drawn on the panel, basically where you want to image to be drawn
    private int dx1,dx2,dy1,dy2;
    //the part of the bg image to be drawn
    private int srcx1,srcx2,srcy1,srcy2;
    //actual size of map
    private int IMAGE_WIDTH = MyConstants.BG_WIDTH;
    private int IMAGE_HEIGHT = MyConstants.BG_HEIGHT;
    //background image and MyCharacter obj
    private BufferedImage backgroundImg;
    private MyCharacter MyCharacter;
    public MapPanel(MyCharacter MyCharacter)
    {
        //import background image
        try{
        backgroundImg = ImageIO.read(new File(MyConstants.MAP_BG));
        repaint();
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        initImagePoints();  
        this.MyCharacter = MyCharacter;
    }
    private void initImagePoints()
    {
        dx1 =0;
        dy1 =0;
        dx2 = DIM_W;
        dy2 = DIM_H;
        srcx1 = (IMAGE_WIDTH/2)-(DIM_W/2);
        srcy1 = (IMAGE_HEIGHT/2)-(DIM_H/2);
        srcx2 = (IMAGE_WIDTH/2)+(DIM_W/2);
        srcy2 = (IMAGE_HEIGHT/2)+(DIM_H/2);
    }
    public void moveRight()
    {
        if (srcx2 + MyCharacter.getSpeedX() <= IMAGE_WIDTH)
        {
            srcx1 += MyCharacter.getSpeedX();
            srcx2 += MyCharacter.getSpeedX();
        }
    }

    public void moveLeft()
    {
        if (srcx1 - MyCharacter.getSpeedX() >= 0)
        {
            srcx1 -= MyCharacter.getSpeedX();
            srcx2 -= MyCharacter.getSpeedX();
        }
    }

    public void moveUp()
    {
        if (srcy1 - MyCharacter.getSpeedY() >= 0)
        {
            srcy1 -= MyCharacter.getSpeedY();
            srcy2 -= MyCharacter.getSpeedY();
        }
    }

    public void moveDown()
    {
        if (srcy2 + MyCharacter.getSpeedY() <= IMAGE_HEIGHT)
        {
            srcy1 += MyCharacter.getSpeedY();
            srcy2 += MyCharacter.getSpeedY();
        }
    }
    @Override
    protected void paintComponent(Graphics g)
    {
        //clear background by calling default implementation
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        //draw a filled white rectangle on the panel
        g.fillRect(0,0,getWidth(),getHeight());
        //draw the image
        g.drawImage(backgroundImg, dx1, dy1, dx2, dy2, srcx1, srcy1, srcx2, srcy2, this);
    }
    
}