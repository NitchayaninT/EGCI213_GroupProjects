package Project3;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

// I just create this class as a place holder for the real one
class LevelUpMenu extends JPanel{
    protected MyCharacter myCharacter;

    // Constructor
    LevelUpMenu(MyCharacter mc){
        myCharacter = mc;
    }
}

abstract class BaseLabel extends JLabel {

    protected String name;
    protected MyImageIcon icon;
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected int speedX, speedY;
    protected MapFrame mapFrame; // for putting the map Panel as reference for characters

    // Constructors
    public BaseLabel() {super();} // default constructor

    public BaseLabel(String n, int s, int w, int h, String file, int x, int y) {
        this.x = x;
        this.y = y;
        name = n;
        speedX = s;
        speedY = s;
        width = w;
        height = h;
        icon = new MyImageIcon(file).resize(width, height);
        setIcon(icon);
        setHorizontalAlignment(JLabel.CENTER);
    }

    // getter setter
    public void setName(String n) {
        name = n;
    }

    public String getName() {
        return name;
    }

    public int getSpeedX() {
        return speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setMap(MapFrame m) {
        mapFrame = m;
    }

    // Methods
    //      might need to modify these last part for character that always
    //      at the center of the map
    public void updateLocation() {
        // ...
        setLocation(x, y);
        repaint();
        mapFrame.validate();
        mapFrame.repaint();
    }

}

abstract class Character extends BaseLabel {

    // Variable
    protected int hp;

    // Constructor
    public Character(String n, int hp, int s, int w, int h, String file, int x, int y) {
        super(n, s, w, h, file, x, y);
        this.hp = hp;
    }

    // setter getter
    void setHp(int hp) { this.hp = hp; }
    int getHp(){return this.hp;}

    // Methods
    void takeDamage(int damage) {
        // override this for player and boss
        this.hp -= damage;
        if (hp <= 0) {
            this.death();
        }
    }

    protected void death() {
        mapFrame.remove(this);
        mapFrame.revalidate();
        mapFrame.repaint();
    }
}

class MyCharacter extends Character {
    // Variable
    protected double dmgMultiplier;
    protected int exp, maxExp;
    protected int maxHp; // Hp get from Character class
    protected int level;
    protected Weapon weapon;
    protected PlayerPanel panel;
    private int framewidth   = MyConstants.FRAME_WIDTH;
    private int frameheight  = MyConstants.FRAME_HEIGHT;
    private int playerWidth = MyConstants.PL_WIDTH;
    private int playerHeight = MyConstants.PL_HEIGHT;
    private int imageWidth = MyConstants.BG_WIDTH;
    private int imageHeight = MyConstants.BG_HEIGHT;
    public PlayerPanel getPlayerPanel() {
        return panel;
    }

    // Constructors
    Player(String n, int hp, int s, int w, int h, Weapon wp, String file) {
        super(n, hp, s, w, h, file,MyConstants.FRAME_WIDTH/2-MyConstants.PL_WIDTH/2,MyConstants.FRAME_WIDTH/2-MyConstants.PL_HEIGHT/2);
        this.weapon = wp;
        this.maxHp = hp;
        this.hp = maxHp;
        dmgMultiplier = 1;
        exp = 0;
        maxExp = 10;
        panel = new PlayerPanel(this);
    }

    void levelUp() {
        level += 1;
        LevelUpMenu lm = new LevelUpMenu();//
    }

    @Override
    void takeDamage(int damage) {
        this.hp -= damage;
        if (hp <= 0) {
            this.death();
        } else {
            panel.healthBar.setValue(this.hp);
            revalidate();
            repaint();
        }
    }
    @Override
    public String toString() {
        return this.name;
    }
    public void moveLeft()
    {
        if(x-speedX>=framewidth/2)x-=speedX;
        else x = framewidth/2;

    }
    public void moveRight()
    {
        if(x+speedX<=imageWidth-(framewidth/2))x+=speedX;
        else x = imageWidth-(framewidth/2);
    }
    public void moveUp()
    {
        if(y-speedY>=frameheight/2)y-=speedY;
        else y = frameheight/2;
    }
    public void moveDown()
    {
        if(y+speedY<=imageHeight-(frameheight/2))y-=speedY;
        else y = imageHeight-(frameheight/2);
    }
}

class PlayerPanel extends JPanel {

    // members
    protected Player player;
    protected JProgressBar healthBar;
    private int width = MyConstants.PL_WIDTH;
    private int height = MyConstants.PL_HEIGHT;
    private JLabel playerLabel;
    //default character
    private MyImageIcon defaultImg;

    // Constructor
    PlayerPanel(Player p) {
        this.player = p;
        this.setLayout(null);
        defaultImg = new MyImageIcon(MyConstants.FILE_CHAR0).resize(width, height);
        playerLabel = new JLabel();
        playerLabel.setIcon(defaultImg);
        playerLabel.setBounds(0, 0, width, height);
        this.add(playerLabel);
        // Creating Health Bar
        healthBar = new JProgressBar(SwingConstants.HORIZONTAL, player.maxHp);
        healthBar.setValue(player.maxHp);
        healthBar.setStringPainted(true);
        healthBar.setForeground(Color.RED);
        healthBar.setBackground(Color.GRAY);
        healthBar.setBounds(0, 50, width, height / 5);
        this.add(healthBar);

    }
}

class Monster extends Character {

    private int IMAGE_WIDTH = MyConstants.BG_WIDTH;
    private int IMAGE_HEIGHT = MyConstants.BG_HEIGHT;
    // Constructor
    private Player player;

    Monster(String n, int hp, int s, int w, int h, String file, int x, int y, Player p) {
        super(n, hp, s, w, h, file, x, y);
        width = MyConstants.MON2_WIDTH+50;
        height = MyConstants.MON2_HEIGHT+50;
        icon = new MyImageIcon(MyConstants.FILE_AJ12).resize(width, height);
        setIcon(icon);
        setBounds(x, y, width, height);
        player = p;
    }

    public void updateLocation() {
        int playerX = player.getPlayerPanel().getX();
        int playerY = player.getPlayerPanel().getY();


        // Compute direction
        int dx = playerX - x;
        int dy = playerY - y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > 0) {
            // Normalize direction
            double unitX = dx / distance;
            double unitY = dy / distance;

            // Move the monster
            x += (int) (unitX * speedX);
            y += (int) (unitY * speedY);
            setLocation(x, y);
        }
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
class Boss extends Monster {

        Boss(String n, int hp, int s, int w, int h, String file, int x, int y, Player p) {
            super(n, hp, s, w, h, file, x, y, p);
        }

        @Override
        protected void death() {
            //win();
        }
    }
