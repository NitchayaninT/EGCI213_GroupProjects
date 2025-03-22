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
    private JLabel myWeaponLabel;
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
    //booleans
    private boolean spawnWeapon = false;
    private boolean selectedW = false;
    private boolean selectedA = false;
    private boolean selectedS = false;
    private boolean selectedD = false;
    //this frame
    private MapFrame   currentFrame;
    private MapPanel mapPanel;
    //constructor
    public String getMapName(){return mapName;}
    public MapFrame(String name,MyCharacter mc,String playerName)
    {
        this.mapName = name;
        MyCharacter = mc;
        weapon = MyCharacter.getWeapon();
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
        MyCharacter = new MyCharacter(MyCharacterName,100,10,MyCharacterWidth,MyCharacterHeight,weapon,MyConstants.FILE_CHAR0);
        //create a JPanel for moving background
        mapPanel = new MapPanel(MyCharacter,this);
        mapPanel.setLayout(null);
        //set dimension for the JPanel
        mapPanel.setPreferredSize(new Dimension(framewidth, frameheight));
        setContentPane(mapPanel);
        //for character panel
        MyCharacterPanel = MyCharacter.getMyCharacterPanel();
        MyCharacterPanel.setBounds(framewidth/2-MyCharacterWidth/2,frameheight/2-MyCharacterHeight/2,MyCharacterWidth,(int)((double)MyCharacterHeight*1.2));
        mapPanel.add(MyCharacterPanel);
        //my weapon label
        myWeaponLabel = new JLabel(weapon.getIcon()); //INITIAL!!!
        myWeaponLabel.setBounds(framewidth/2-MyCharacterWidth/2,frameheight/2-MyCharacterHeight/2,weapon.getWidth(),weapon.getHeight());
        mapPanel.repaint();

        for(int i=0;i<10;i++) {
            spawnMonster();
            System.out.println("Spawn monster");
        }
        //for timer
        addTimer();

        //themesound = new MySoundEffect(MyConstants.FILE_THEME1);
        //themesound.playLoop(); themesound.setVolume(0.4f);
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

                selectedA = true;
                selectedD = false;
                selectedW = false;
                selectedS = false;

                MyCharacter.moveLeft();
                mapPanel.moveLeft();
                mapPanel.repaint();
                revalidate();
                repaint();
                break;
            case KeyEvent.VK_D:

                selectedA = false;
                selectedD = true;
                selectedW = false;
                selectedS = false;

                MyCharacter.moveRight();
                mapPanel.moveRight();
                mapPanel.repaint();
                revalidate();
                repaint();
                break;
            case KeyEvent.VK_W:

                selectedA = false;
                selectedD = false;
                selectedW = true;
                selectedS = false;

                MyCharacter.moveUp();
                mapPanel.moveUp();
                mapPanel.repaint();
                revalidate();
                repaint();
                break;
            case KeyEvent.VK_S:

                selectedA = false;
                selectedD = false;
                selectedW = false;
                selectedS = true;

                MyCharacter.moveDown();
                mapPanel.moveDown();
                mapPanel.repaint();
                revalidate();
                repaint();
                break;
            case KeyEvent.VK_SPACE:
                spawnWeapon();
                break;
        }
    }
    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}
    public void spawnMonster()
    {
        Thread monsterThread = new Thread()
        {
            private boolean running = true; //monster has to get hit 3 times
            public void run()
            {
                int randX = rand.nextInt(MyConstants.BG_WIDTH-MyConstants.MON1_WIDTH);
                int randY = rand.nextInt(MyConstants.BG_HEIGHT-MyConstants.MON1_HEIGHT);
                Monster monster = new Monster("default",MyConstants.MON1_HP,50,MyConstants.MON1_WIDTH,MyConstants.MON1_HEIGHT,MyConstants.FILE_AJ12,randX,randY,MyCharacter);
                mapPanel.add(monster);
                while(running)
                {
                    monster.updateLocation();
                    boolean collide = checkCollision(monster);
                    if(collide) {
                        if(monster.getHp()==0) {
                            mapPanel.remove(monster);
                            revalidate();
                            repaint();
                            running = false; //destroys monster thread
                        }
                    }
                }
            }
        };
        monsterThread.start();
    }
    private void addTimer()
    {
        //start time
        long startTime = System.currentTimeMillis();
        Thread timerThread = new Thread()
        {
            public void run()
            {
                JLabel timer = new JLabel(String.valueOf(System.currentTimeMillis()-System.currentTimeMillis()));
                mapPanel.add(timer);
                while(true) //until level up (progress bar 100%) -> reset thread
                {
                    long currentTime = System.currentTimeMillis();
                    long differenceTime = currentTime - startTime;

                    //total seconds
                    long totalSeconds = differenceTime / 1000;

                    //minutes and actual seconds
                    long minutes = totalSeconds / 60;
                    long seconds = totalSeconds % 60;

                    //two digits format
                    String timer_text = String.format("%02d:%02d", minutes, seconds);

                    //set timer text
                    timer.setText(timer_text);
                    timer.setBackground(Color.WHITE);
                    timer.setFont(new Font("Century Gothic", Font.BOLD, 24));
                    timer.setBounds((mapPanel.getWidth()-timer.getWidth())/2,10,100,60);
                }
            }
        };
        timerThread.start();
    }
    public boolean checkCollision(Monster monster) {
            // check collision with each monster on the panel
        Rectangle monsterBounds = monster.getBounds();
        Rectangle playerBounds = MyCharacterPanel.getBounds();
        //Rectangle weaponBounds = myWeaponLabel.getBounds();

        //if intersects player
        if (monsterBounds.intersects(playerBounds)) {
            System.out.println("Monster hit player!");
            MyCharacter.takeDamage(10); //if intersects and dont spawn weapon, character loses blood.
            //monster.takeDamage(MyCharacter.getWeapon().getDamage());
            // e.g. player.takeDamage(), monster.stop(), etc.
            /*if(spawnWeapon){
                monster.takeDamage(MyCharacter.getWeapon().getDamage());
                spawnWeapon = false;
            }*/
            return true;//stop monster thread
        }
        /*else if(monsterBounds.intersects(weaponBounds)) //if intersects weapon
        {
            monster.takeDamage(MyCharacter.getWeapon().getDamage());
            System.out.println("DAMAGE!");
            mapPanel.remove(myWeaponLabel);
            revalidate();
            repaint();
            spawnWeapon = false;
            return false;
        }*/
        else return false;
    }

    public void spawnWeapon()
    {
        Thread weaponThread = new Thread()
        {
            private final JLabel weaponLabel = new JLabel(weapon.getIcon());
            public JLabel getWeaponLabel(){return weaponLabel;}
            public void run()
            {
                Point characterPos = MyCharacterPanel.getLocation();
                int weaponX = characterPos.x+10;
                int weaponY = characterPos.y;
                //set new bounds
                weaponLabel.setBounds(weaponX,weaponY,weapon.getWidth(),weapon.getHeight());
                mapPanel.add(weaponLabel);
                spawnWeapon = true;
                //to detect key press from initial
                boolean A = false;
                boolean D = false;
                boolean S = false;
                boolean Y = false;
                //selected which key? (from initial press)
                if(selectedA) //left
                {A = true;}
                else if(selectedD) //right
                {D = true;}
                else if(selectedS) //south
                {S = true;}
                else //north
                {Y = true;}
                while(spawnWeapon && (weaponLabel.getX()<framewidth||weaponLabel.getY()<frameheight)) //if spawn weapon and still in frame (and not collide with monster yet)
                {
                    if(A) //left
                    {weaponX -= 10;}
                    else if(D) //right
                    {weaponX += 10;}
                    else if(S) //south
                    {weaponY += 10;}
                    else //north
                    {weaponY -= 10;}
                    weaponLabel.setBounds(weaponX,weaponY,weapon.getWidth(),weapon.getHeight());
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {throw new RuntimeException(e);}
                    revalidate();
                    repaint();
                }
                spawnWeapon = false; //if out of frame
            }
        };
        weaponThread.start();
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
