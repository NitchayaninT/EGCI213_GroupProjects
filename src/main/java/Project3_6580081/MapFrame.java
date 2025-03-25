/*Akkhrawin Nair 6580013
Pakin Panawattanakul 6580043
Nitchayanin Thamkunanon 6580081
Pibhu Chitburanachart 6580195
Panupong Sangaphunchai 6580587*/
package Project3_6580081;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;
import java.util.*;
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
    private MySoundEffect main_theme;
    private ArrayList<Thread> monsterThreadList = new ArrayList<>();
    private String mapName;
    private MyImageIcon mapIcon;
    private Random rand = new Random();
    private MyCharacter MyCharacter;
    private MyCharacterPanel MyCharacterPanel;
    private JLabel myWeaponLabel;
    private Monster monster;
    private Boss boss;
    private MyImageIcon backgroundImg;
    private String  MyCharacterName;
    private String  chosenCharacterName;
    private Weapon [] weapons;
    private int framewidth   = MyConstants.FRAME_WIDTH;
    private int frameheight  = MyConstants.FRAME_HEIGHT;
    private int MyCharacterWidth = MyConstants.CH_WIDTH;
    private int MyCharacterHeight = MyConstants.CH_HEIGHT;
    private Weapon weapon;
    protected ArrayList <Monster> monsterArrayList = new ArrayList<>();
    private long differenceTime; //to keep track of different time
    private JLabel hpLabel;
    private JLabel speedLabel;
    private Timer monsterTimer;

    //booleans
    private boolean selectedW = false;
    private boolean selectedA = false;
    private boolean selectedS = false;
    private boolean selectedD = false;
    //this frame
    private MapFrame   currentFrame;
    private MapPanel mapPanel;
    //constructor
    public void setMusic(MySoundEffect theme){this.main_theme = theme;}
    public String getMapName(){return mapName;}
    public MapFrame(String mapName,MyCharacter mc,String playerName)
    {
        this.mapName = mapName;
        MyCharacter = mc;
        weapon = MyCharacter.getWeapon();
        MyCharacterName = playerName;
        //set title, frame's size and properties
        if(MyCharacterName!=null) setTitle("Welcome "+ MyCharacterName);
        else setTitle("Welcome");

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
        //create a JPanel for moving background
        mapPanel = new MapPanel(MyCharacter,this);
        mapPanel.setLayout(null);
        //set dimension for the JPanel
        mapPanel.setPreferredSize(new Dimension(framewidth, frameheight));
        mapPanel.setFocusable(true);
        mapPanel.requestFocusInWindow();
        mapPanel.addKeyListener(this);
        setContentPane(mapPanel);

        //for character panel
        MyCharacterPanel = MyCharacter.getMyCharacterPanel();
        MyCharacterPanel.setBounds(framewidth/2-MyCharacterWidth/2,frameheight/2-MyCharacterHeight/2,MyCharacterWidth,(int)((double)MyCharacterHeight*1.2));
        mapPanel.add(MyCharacterPanel);
        //my weapon label
        myWeaponLabel = new JLabel(weapon.getIcon()); //INITIAL!!!
        myWeaponLabel.setBounds(framewidth/2-MyCharacterWidth/2,frameheight/2-MyCharacterHeight/2,weapon.getWidth(),weapon.getHeight());

        //for hp and speed labels
        hpLabel = new JLabel("HP: " + MyCharacter.getHp() + "/" + MyCharacter.getMaxHp());
        speedLabel = new JLabel("Speed: " + MyCharacter.getSpeedX());
        hpLabel.setFont(new Font("Century Gothic", Font.BOLD, 16));
        hpLabel.setForeground(Color.RED);
        hpLabel.setBounds(50,40,150,20);
        hpLabel.setOpaque(true);
        hpLabel.setBackground(mapPanel.getBackground());

        speedLabel.setFont(new Font("Century Gothic", Font.BOLD, 16));
        speedLabel.setForeground(Color.BLUE);
        speedLabel.setBounds(210, 40, 150, 20);
        speedLabel.setOpaque(true);
        speedLabel.setBackground(mapPanel.getBackground());

        mapPanel.add(hpLabel);
        mapPanel.add(speedLabel);
        mapPanel.repaint();
        //for timer
        addTimer();
        startMonsterSpawner();
        //themesound = new MySoundEffect(MyConstants.FILE_THEME1);
        //themesound.playLoop(); themesound.setVolume(0.4f);
        //creating myCharacter
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
            //monster has to get hit 3 times
            public void run()
            {
                ArrayList<Integer> randomVar = randomXY();
                int randX = randomVar.get(0);
                int randY = randomVar.get(1);
                int type = rand.nextInt(13);//0-12
                int speed = 0;
                int hp = 0;
                String monsterType = "";
                switch(type)
                {
                    case 0:
                        monsterType = MyConstants.FILE_AJ0;
                        speed = 2;
                        hp = 2;
                        break;
                    case 1:
                        monsterType = MyConstants.FILE_AJ1;
                        speed = 2;
                        hp = 2;
                        break;
                    case 2:
                        monsterType = MyConstants.FILE_AJ2;
                        speed = 4;
                        hp = 3;
                        break;
                    case 3:
                        monsterType = MyConstants.FILE_AJ3;
                        speed = 5;
                        hp = 3;
                        break;
                    case 4:
                        monsterType = MyConstants.FILE_AJ4;
                        speed = 3;
                        hp = 1;
                        break;
                    case 5:
                        monsterType = MyConstants.FILE_AJ5;
                        speed = 4;
                        hp = 2;
                        break;
                    case 6:
                        monsterType = MyConstants.FILE_AJ6;
                        speed = 1;
                        hp = 3;
                        break;
                    case 7:
                        monsterType = MyConstants.FILE_AJ7;
                        speed = 2;
                        hp = 2;
                        break;
                    case 8:
                        monsterType = MyConstants.FILE_AJ8;
                        speed = 3;
                        hp = 1;
                        break;
                    case 9:
                        monsterType = MyConstants.FILE_AJ9;
                        speed = 3;
                        hp = 3;
                        break;
                    case 10:
                        monsterType = MyConstants.FILE_AJ10;
                        speed = 4;
                        hp = 2;
                        break;
                    case 11:
                        monsterType = MyConstants.FILE_AJ11;
                        speed = 2;
                        hp = 1;
                        break;
                    case 12:
                        monsterType = MyConstants.FILE_AJ12;
                        speed = 10;
                        hp = 3;
                        break;
                }
                Monster monster = new Monster("default",hp,speed,MyConstants.MON_WIDTH,MyConstants.MON_HEIGHT,monsterType,randX,randY,MyCharacter);
                monsterArrayList.add(monster);
                System.out.println("Spawn monster");
                mapPanel.add(monster);
                while(monster.getAlive())
                {
                    monster.updateLocation();
                    checkCollision(monster);
                }
            }
        };
        monsterThreadList.add(monsterThread);
        monsterThread.start();
    }

    public void spawnBoss()
    {
        Thread bossThread = new Thread()
        {
            public void run()
            {
                ArrayList<Integer>randomVar = randomXY();
                int randX = randomVar.get(0);
                int randY = randomVar.get(1);
                boss = new Boss("AJRangsipan",10,10,MyConstants.BOSS_WIDTH,MyConstants.BOSS_WIDTH,MyConstants.FILE_AJ13,randX,randY,MyCharacter);
                mapPanel.add(boss);
                monsterArrayList.add(boss);
                while(boss.isAlive())
                {
                    boss.updateLocation();
                    checkCollision(boss);
                }
            }
        };
        monsterThreadList.add(bossThread);
        bossThread.start();
    }
    public ArrayList<Integer> randomXY()
    {
        ArrayList<Integer>randomVar = new ArrayList<>();
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
        randomVar.add(randX);
        randomVar.add(randY);
        return randomVar;
    }
    private void addTimer()
    {
        //start time
        long startTime = System.currentTimeMillis();
        Thread timerThread = new Thread()
        {
            public void run()
            {
                long ps = -1;
                JLabel timer = new JLabel(String.valueOf(System.currentTimeMillis()-System.currentTimeMillis()));
                mapPanel.add(timer);
                boolean running = true;
                boolean BossSpawn = false;
                while(running)
                {
                    long currentTime = System.currentTimeMillis();
                    differenceTime = currentTime - startTime;

                    //total seconds
                    long totalSeconds = differenceTime / 1000;
                    //minutes and actual seconds
                    long minutes = totalSeconds / 60;
                    long seconds = totalSeconds % 60;

                    //two digits format
                    String timer_text = String.format("%02d:%02d", minutes, seconds);

                    //set timer text
                    timer.setText(timer_text);
                    timer.setForeground(Color.WHITE);

                    if(Objects.equals(mapName, "Galaxy")) timer.setForeground(Color.WHITE); //if galaxy, text is white
                    timer.setFont(new Font("Century Gothic", Font.BOLD, 24));
                    timer.setBounds((mapPanel.getWidth()-timer.getWidth())/2+20,30,100,60);

                    //level up every 30 secs
                    if(totalSeconds%10 == 0 && totalSeconds != ps && totalSeconds>0)
                    {
                        createLevelUpMenu();
                        ps = totalSeconds;
                    }
                    if(totalSeconds==60&&!BossSpawn)
                    {
                        BossSpawn = true;
                        spawnBoss();
                    }
                    if(MyCharacter.getHp()<=0)
                    {
                        //System.out.println(monsterThreadList.size());
                        for (Monster m:monsterArrayList)
                        {
                            m.setAlive(false);
                        }
                        for(Thread m:monsterThreadList)
                        {
                            try{
                                m.join();
                            }catch(Exception e){}
                        }
                        running = false;
                        String s = "    You die \nGAME OVER!!";
                        JOptionPane.showMessageDialog(new JFrame(),s,"EGCO Survivor",JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                        main_theme.stop();
                        new MainApplication();
                    }
                    else //if different time exceeds
                    {
                        if(differenceTime >= 90*1000)
                        {
                            for (Monster m:monsterArrayList)
                            {
                                m.setAlive(false);
                            }
                            for(Thread m:monsterThreadList)
                            {
                                try{
                                    m.join();
                                }catch(Exception e){}
                            }
                            running = false;
                            String s = "You Survived EGCO! Congrats!";
                            JOptionPane.showMessageDialog(new JFrame(),s,"EGCO Survivor",JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                            main_theme.stop();
                            new MainApplication();
                        }
                    }
                }
            }
        };
        timerThread.start();
    }
    public void startMonsterSpawner() {
        monsterTimer = new Timer(2000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(MyCharacter.getHp()>0) {
                    if(differenceTime >= 88*1000)
                    {
                        System.out.println("stop timer");
                        monsterTimer.stop();
                    }
                    for (int i = 0; i < 2; i++) {
                        spawnMonster();
                    }
                }else {
                    System.out.println("stop timer");
                    monsterTimer.stop();
                }

            }
        });
        monsterTimer.start();
    }

    // Weapon and Monster
    public boolean checkCollision(JLabel weapon) {

        Rectangle weaponBounds = weapon.getBounds();
        for (Monster m : monsterArrayList) {
            Rectangle monsterBounds = m.getBounds();
            if(weaponBounds.intersects(monsterBounds)) //if intersects weapon
            {
                m.takeDamage(MyCharacter.getWeapon().getDamage());
                System.out.println("DAMAGE!");
                mapPanel.remove(weapon);
                if(m.getHp()<=0){
                    mapPanel.remove(m);
                    m.setAlive(false);
                    monsterArrayList.remove(m);
                }
                revalidate();
                repaint();
                return false;
            }
        }
        return true;
    }
    // For check monster and player
    public void checkCollision(Monster monster) {
            // check collision with each monster on the panel
        Rectangle monsterBounds = monster.getBounds();
        Rectangle playerBounds = MyCharacterPanel.getBounds();
        //Rectangle weaponBounds = myWeaponLabel.getBounds();

        //if intersects player
        if (monsterBounds.intersects(playerBounds)) {
            //System.out.println("Monster hit player!");
            if(Objects.equals(monster.getName(), "AJRangsipan"))
            {
                MyCharacter.takeDamage(10);
            }
            else MyCharacter.takeDamage(2);
            updateHPandSpeedLabel();
        }
    }

    public void spawnWeapon()
    {
        Thread weaponThread = new Thread()
        {
            private boolean spawnWeapon = false;
            private final JLabel weaponLabel = new JLabel(weapon.getIcon());
            public JLabel getWeaponLabel(){return weaponLabel;}
            public void run()
            {
                Point characterPos = MyCharacterPanel.getLocation();
                int weaponX = characterPos.x;
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
                    weaponLabel.setLocation(weaponX,weaponY);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {throw new RuntimeException(e);}
                    revalidate();
                    repaint();
                    spawnWeapon = checkCollision(weaponLabel);
                }
                spawnWeapon = false; //if out of frame
            }
        };
        weaponThread.start();
    }
    public void createLevelUpMenu()
    {
        JPanel levelUpPanel = new JPanel();
        levelUpPanel.setLayout(new BoxLayout(levelUpPanel, BoxLayout.Y_AXIS));
        levelUpPanel.setBackground(Color.WHITE);
        levelUpPanel.setBounds(framewidth/2-105,frameheight/2-200,200,150);
        levelUpPanel.setVisible(true); // hidden by default

        JLabel title = new JLabel("LEVEL UP!");
        title.setFont(new Font("Century Gothic", Font.BOLD, 24));
        title.setForeground(Color.RED);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel speedLabel = new JLabel("Speed increased!");
        MyCharacter.setSpeed(MyCharacter.getSpeedX()+2); //player's speed +1
        System.out.println("Speed"+MyCharacter.getSpeedX());
        JLabel hpLabel = new JLabel("HP increased by 50!");
        MyCharacter.heal();
        //update panel too
        updateHPandSpeedLabel();
        speedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        hpLabel.setAlignmentX(Component.CENTER_ALIGNMENT);


        levelUpPanel.add(Box.createVerticalStrut(10));
        levelUpPanel.add(title);
        levelUpPanel.add(Box.createVerticalStrut(15));
        levelUpPanel.add(speedLabel);
        levelUpPanel.add(Box.createVerticalStrut(15));
        levelUpPanel.add(hpLabel);
        if(differenceTime == 60*1000)
        {
            JLabel BossLabel = new JLabel("BOSS IS HERE!");
            BossLabel.setForeground(Color.RED);
            BossLabel.setFont(new Font("Century Gothic", Font.BOLD, 24));
            BossLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            levelUpPanel.add(Box.createVerticalStrut(10));
            levelUpPanel.add(BossLabel);
        }
        //levelUpPanel.add(okButton);
        levelUpPanel.setOpaque(true);
        mapPanel.add(levelUpPanel);
        revalidate();
        repaint();

        //hide panel after 2.5 seconds using anonymous class
        new Timer(2500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                levelUpPanel.setVisible(false);
                mapPanel.remove(levelUpPanel);
                MapFrame.this.requestFocus();
                ((Timer) e.getSource()).stop(); //stop the timer
            }
        }) {{
            setRepeats(false);
            start();
        }};

    }
    public void updateHPandSpeedLabel()
    {
        int hp = MyCharacter.getHp();
        if(MyCharacter.getHp()<0)hp=0;
        hpLabel.setText("HP: " + hp + "/" + MyCharacter.getMaxHp());
        speedLabel.setText("Speed: " + MyCharacter.getSpeedX());
        revalidate();
        repaint();
    }
}

