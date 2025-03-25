/*Akkhrawin Nair 6580013
Pakin Panawattanakul 6580043
Nitchayanin Thamkunanon 6580081
Pibhu Chitburanachart 6580195
Panupong Sangaphunchai 6580587*/
package Project3_6580081;

import javax.swing.*;
import java.awt.*;

abstract class BaseLabel extends JLabel {
    protected String name;
    protected MyImageIcon icon;
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected int speedX, speedY;
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

    public MyImageIcon getIcon(){return icon;}

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
    void setSpeed(int s){this.speedX = s; this.speedY = s;}
    int getHp(){return this.hp;}
    // Methods
    void takeDamage(int damage) {
        // override this for myCharacter and boss
        this.hp -= damage;
    }
}

class MyCharacter extends Character {
    // Variable
    protected int maxHp; // Hp get from Character class
    protected Weapon weapon;
    protected MyCharacterPanel panel;
    private int framewidth   = MyConstants.FRAME_WIDTH;
    private int frameheight  = MyConstants.FRAME_HEIGHT;
    private int imageWidth = MyConstants.BG_WIDTH;
    private int imageHeight = MyConstants.BG_HEIGHT;
    public MyCharacterPanel getMyCharacterPanel() {
        return panel;
    }
    public Weapon getWeapon(){return this.weapon;}
    // Constructors
    MyCharacter(String n, int hp, int s,Weapon wp, String file) {
        super(n, hp, s, MyConstants.CH_WIDTH, MyConstants.CH_HEIGHT, file,MyConstants.FRAME_WIDTH/2-MyConstants.CH_WIDTH/2,MyConstants.FRAME_WIDTH/2-MyConstants.CH_HEIGHT/2);
        this.weapon = wp;
        this.maxHp = hp;
        this.hp = maxHp;
        panel = new MyCharacterPanel(this);
    }
    int getMaxHp(){return this.maxHp;}
    @Override
    void takeDamage(int damage) {
        this.hp -= damage;
        if (hp > 0) {
            panel.healthBar.setValue(this.hp);
            revalidate();
            repaint();
        }
    }
    protected void heal()
    {
        if(this.hp+50>this.maxHp) this.hp = this.maxHp;
        else{this.hp += 50;}
        panel.healthBar.setValue(hp);
        revalidate();
        repaint();
    }
    @Override
    public String toString() {
        return this.name;
    }
}

class MyCharacterPanel extends JPanel {

    // members
    protected MyCharacter myCharacter;
    protected JProgressBar healthBar;
    private int width = MyConstants.CH_HEIGHT;
    private int height = MyConstants.CH_HEIGHT;
    private JLabel MyCharacterLabel;
    //default character
    private MyImageIcon Img;

    // Constructor
    MyCharacterPanel(MyCharacter p) {
        this.myCharacter = p;
        this.setLayout(null);
        MyCharacterLabel = new JLabel();
        this.setOpaque(false);
        MyCharacterLabel.setIcon(myCharacter.icon);
        MyCharacterLabel.setBounds(0, 0, width, height);
        this.add(MyCharacterLabel);
        // Creating Health Bar
        healthBar = new JProgressBar(SwingConstants.HORIZONTAL, myCharacter.maxHp);
        healthBar.setValue(myCharacter.maxHp);
        healthBar.setStringPainted(true);
        healthBar.setForeground(Color.RED);
        healthBar.setBackground(Color.GRAY);
        healthBar.setBounds(0, 50, width, height / 5);
        this.add(healthBar);

    }
}

class Monster extends Character {

    private boolean alive = true;
    private int IMAGE_WIDTH = MyConstants.BG_WIDTH;
    private int IMAGE_HEIGHT = MyConstants.BG_HEIGHT;
    // Constructor
    private MyCharacter MyCharacter;

    Monster(String n, int hp, int s, int w, int h, String file, int x, int y, MyCharacter p) {
        super(n, hp, s, w, h, file, x, y);
        width = MyConstants.MON_WIDTH;
        height = MyConstants.MON_HEIGHT;
        icon = new MyImageIcon(file).resize(width, height);
        setIcon(icon);
        setBounds(x, y, width, height);
        MyCharacter = p;
    }

    public void setAlive(boolean a) {alive = a;}
    public boolean getAlive() {return alive;}
    public void setX(int no){x=no;}
    public void setY(int no){y=no;}

    public void updateLocation() {
        int MyCharacterX = MyCharacter.getMyCharacterPanel().getX();
        int MyCharacterY = MyCharacter.getMyCharacterPanel().getY();
        // Compute direction
        int dx = MyCharacterX - x;
        int dy = MyCharacterY - y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        if (distance > 0) {
            // Normalize direction
            double unitX = dx / distance;
            double unitY = dy / distance;

            // Move the monster
            x += (int) (unitX * speedX);
            y += (int) (unitY * speedY);
            setLocation(x, y);
            revalidate();
            repaint();
        }
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
class Boss extends Monster {
        private MyCharacter MyCharacter;
        private boolean alive = true;
        Boss(String n, int hp, int s, int w, int h, String file, int x, int y, MyCharacter p) {
            super(n, hp, s, w, h, file, x, y, p);
            icon = new MyImageIcon(file).resize(w, h);
            setIcon(icon);
            setBounds(x, y, w, h);
            MyCharacter = p;
        }

        public boolean isAlive(){return alive;}
        public void setAlive(boolean bool){alive = bool;}
    }
