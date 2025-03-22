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
public class MapFrame extends JFrame implements KeyListener
{
    //methods
    private Random rand = new Random();
    private Player player;
    private PlayerPanel playerPanel;
    private Monster monster;
    private Boss boss;
    private MyImageIcon backgroundImg;
    String playerName;
    String chosenCharacterName;
    String musicName;
    Weapon [] weapons;
    private MySoundEffect themesound;
    private int framewidth   = MyConstants.FRAME_WIDTH;
    private int frameheight  = MyConstants.FRAME_HEIGHT;
    private int playerWidth = MyConstants.PL_WIDTH;
    private int playerHeight = MyConstants.PL_HEIGHT;
    private Weapon weapon;
    //this frame
    private MapFrame   currentFrame;
    private MapPanel mapPanel;
    public static void main(String args[])
    {
        new MapFrame();
    }
    //constructor
    public MapFrame()
    {
        //set title, frame's size and properties
        setTitle("Welcome "+ playerName);
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
        //create a player
        player = new Player(playerName,100,10,playerWidth,playerHeight,weapon,MyConstants.FILE_CHAR0);
        //create a JPanel for moving background
        mapPanel = new MapPanel(player);
        mapPanel.setLayout(null);
        //set dimension for the JPanel
        mapPanel.setPreferredSize(new Dimension(framewidth, frameheight));
        setContentPane(mapPanel);
        playerPanel = player.getPlayerPanel();
        playerPanel.setBounds(framewidth/2-playerWidth/2,frameheight/2-playerHeight/2,playerWidth,(int)((double)playerHeight*1.2));
        mapPanel.add(playerPanel);
        mapPanel.repaint();
        //theme song
        for(int i=0;i<10;i++)spawnMonster();
        themesound = new MySoundEffect(MyConstants.FILE_THEME1);
        themesound.playLoop(); themesound.setVolume(0.4f);
        //creating player
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
    //for moving
    public void keyPressed(KeyEvent e)
    {
        int keyCode = e.getKeyCode();
        switch(keyCode)
        {
            case KeyEvent.VK_A:
                player.moveLeft();
                mapPanel.moveLeft();
                mapPanel.repaint();
                revalidate();
                repaint();
                break;
            case KeyEvent.VK_D:
                player.moveRight();
                mapPanel.moveRight();
                mapPanel.repaint();
                revalidate();
                repaint();
                break;
            case KeyEvent.VK_W:
                player.moveUp();
                mapPanel.moveUp();
                mapPanel.repaint();
                revalidate();
                repaint();
                break;
            case KeyEvent.VK_S:
                player.moveDown();
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
                Monster monster = new Monster("default",MyConstants.MON1_HP,MyConstants.MON1_WIDTH,MyConstants.MON1_HEIGHT,10,MyConstants.FILE_AJ12,randX,randY,player);
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
    //background image and player obj
    private BufferedImage backgroundImg;
    private Player player;
    public MapPanel(Player player)
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
        this.player = player;
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
        if (srcx2 + player.getSpeedX() <= IMAGE_WIDTH)
        {
            srcx1 += player.getSpeedX();
            srcx2 += player.getSpeedX();
        }
    }

    public void moveLeft()
    {
        if (srcx1 - player.getSpeedX() >= 0)
        {
            srcx1 -= player.getSpeedX();
            srcx2 -= player.getSpeedX();
        }
    }

    public void moveUp()
    {
        if (srcy1 - player.getSpeedY() >= 0)
        {
            srcy1 -= player.getSpeedY();
            srcy2 -= player.getSpeedY();
        }
    }

    public void moveDown()
    {
        if (srcy2 + player.getSpeedY() <= IMAGE_HEIGHT)
        {
            srcy1 += player.getSpeedY();
            srcy2 += player.getSpeedY();
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