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
    Weapon [] weapons;
    private MySoundEffect themesound;
    private int framewidth   = MyConstants.FRAME_WIDTH;
    private int frameheight  = MyConstants.FRAME_HEIGHT;
    private int MyCharacterWidth = MyConstants.PL_WIDTH;
    private int MyCharacterHeight = MyConstants.PL_HEIGHT;
    private Weapon weapon;
    ArrayList <Monster> monsterArrayList = new ArrayList<>();
    //this frame
    private MapFrame   currentFrame;
    private MapPanel mapPanel;
    //constructor
    public String getMapName(){return mapName;}
    public MapFrame(String name,MyCharacter mc,String playerName)
    {
        this.mapName = name;
        MyCharacter = mc;
        MyCharacterName = playerName;
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
        MyCharacter = new MyCharacter(MyCharacterName,100,30,MyCharacterWidth,MyCharacterHeight,weapon,MyConstants.FILE_CHAR0);
        //create a JPanel for moving background
        mapPanel = new MapPanel(MyCharacter,this);
        mapPanel.setLayout(null);
        //set dimension for the JPanel
        mapPanel.setPreferredSize(new Dimension(framewidth, frameheight));
        setContentPane(mapPanel);
        MyCharacterPanel = MyCharacter.getMyCharacterPanel();
        MyCharacterPanel.setBounds(framewidth/2-MyCharacterWidth/2,frameheight/2-MyCharacterHeight/2,MyCharacterWidth,(int)((double)MyCharacterHeight*1.2));
        mapPanel.add(MyCharacterPanel);
        mapPanel.repaint();

        for(int i=0;i<20;i++) {
            spawnMonster();
            System.out.println("Spawn monster");
        }
        //creating MyCharacter
        System.out.println(MyCharacterName);
        System.out.println(chosenCharacterName);
        System.out.println(Arrays.toString(weapons));
        setVisible(true);
        validate();
    }

    //methods
    public void setMyCharacterName(String name){MyCharacterName = name;}
    public void setCharacterName(String name){chosenCharacterName = name;}
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

                int srcx1 = mapPanel.getsrcx1();
                int srcx2 = mapPanel.getsrcx2();
                int srcy1 = mapPanel.getsrcy1();
                int srcy2 = mapPanel.getsrcy2();
                int randY;
                int randX = rand.nextInt(MyConstants.BG_WIDTH-MyConstants.MON_WIDTH);
                if(randX<=srcx1||randX>=srcx2)
                {
                    randY = rand.nextInt(MyConstants.BG_HEIGHT-MyConstants.MON_HEIGHT);
                }else
                {
                    randY = rand.nextInt(MyConstants.BG_HEIGHT-MyConstants.FRAME_HEIGHT-MyConstants.MON_HEIGHT);
                    if(randY>srcy1)
                    {
                        randY+=MyConstants.FRAME_HEIGHT;
                    }
                }
                int type = rand.nextInt(13);//0-12
                int speed = 0;
                String monsterType = "";
                switch(type)
                {
                    case 0:
                        monsterType = MyConstants.FILE_AJ0;
                        speed = 2;
                        break;
                    case 1:
                        monsterType = MyConstants.FILE_AJ1;
                        speed = 2;
                        break;
                    case 2:
                        monsterType = MyConstants.FILE_AJ2;
                        speed = 4;
                        break;
                    case 3:
                        monsterType = MyConstants.FILE_AJ3;
                        speed = 5;
                        break;
                    case 4:
                        monsterType = MyConstants.FILE_AJ4;
                        speed = 3;
                        break;
                    case 5:
                        monsterType = MyConstants.FILE_AJ5;
                        speed = 6;
                        break;
                    case 6:
                        monsterType = MyConstants.FILE_AJ6;
                        speed = 1;
                        break;
                    case 7:
                        monsterType = MyConstants.FILE_AJ7;
                        speed = 2;
                        break;
                    case 8:
                        monsterType = MyConstants.FILE_AJ8;
                        speed = 3;
                        break;
                    case 9:
                        monsterType = MyConstants.FILE_AJ9;
                        speed = 7;
                        break;
                    case 10:
                        monsterType = MyConstants.FILE_AJ10;
                        speed = 4;
                        break;
                    case 11:
                        monsterType = MyConstants.FILE_AJ11;
                        speed = 2;
                        break;
                    case 12:
                        monsterType = MyConstants.FILE_AJ12;
                        speed = 10;
                        break;
                }
                Monster monster = new Monster("default",MyConstants.MON1_HP,speed,MyConstants.MON_WIDTH,MyConstants.MON_HEIGHT,MyConstants.FILE_AJ12,randX,randY,MyCharacter,monsterType);
                monsterArrayList.add(monster);
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
    private MapFrame mf;
    public int getsrcx1(){return srcx1;}
    public int getsrcx2(){return srcx2;}
    public int getsrcy1(){return srcy1;}
    public int getsrcy2(){return srcy2;};
    public MapPanel(MyCharacter MyCharacter,MapFrame map)
    {
        //import background image
        mf = map;
        String mapName = mf.getMapName();
        String background = "";
        switch(mapName)
        {
            case "MapFrame 1":
                background = MyConstants.MAP_BG1;
                break;
            case "MapFrame 2":
                background = MyConstants.MAP_BG2;
                break;
            case "MapFrame 3":
                background = MyConstants.MAP_BG3;
                break;
            case "MapFrame 4":
                background = MyConstants.MAP_BG4;
                break;
            case "MapFrame 5":
                background = MyConstants.MAP_BG5;
                break;
        }
        try{
        backgroundImg = ImageIO.read(new File(background));
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
            for (Monster monster : mf.monsterArrayList) {
                monster.setX(monster.getX() - MyCharacter.getSpeedX());
                monster.setY(monster.getY());
            }
        }
    }
    public void moveLeft()
    {
        if (srcx1 - MyCharacter.getSpeedX() >= 0)
        {
            srcx1 -= MyCharacter.getSpeedX();
            srcx2 -= MyCharacter.getSpeedX();
            for (Monster monster : mf.monsterArrayList) {
                monster.setX(monster.getX() + MyCharacter.getSpeedX());
                monster.setY(monster.getY());
            }
        }
    }

    public void moveUp()
    {
        if (srcy1 - MyCharacter.getSpeedY() >= 0)
        {
            srcy1 -= MyCharacter.getSpeedY();
            srcy2 -= MyCharacter.getSpeedY();
            for (Monster monster : mf.monsterArrayList) {
                monster.setX(monster.getX());
                monster.setY(monster.getY()+MyCharacter.getSpeedY());
            }
        }
    }

    public void moveDown()
    {
        if (srcy2 + MyCharacter.getSpeedY() <= IMAGE_HEIGHT)
        {
            srcy1 += MyCharacter.getSpeedY();
            srcy2 += MyCharacter.getSpeedY();
            for (Monster monster : mf.monsterArrayList) {
                monster.setX(monster.getX());
                monster.setY(monster.getY()-MyCharacter.getSpeedY());
            }
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