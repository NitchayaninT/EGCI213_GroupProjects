package Project3;

import javax.swing.*;
import java.awt.*;

// I just create this class as a place holder for the real one
class Map extends JPanel { }
class LevelUpMenu extends JPanel{ }

abstract class BaseLabel extends JLabel{
    protected String name;
    protected MyImageIcon icon;
    protected int x , y, width, height;
    protected int speed;
    protected Map mapFrame; // for putting the map Panel as reference for characters

    // Constructors
    public BaseLabel() {super();} // default constructor

    public BaseLabel(String file, int w, int h, Map mf){
        width = w;
        height = h;
        icon = new MyImageIcon(file).resize(width, height);
        setIcon(icon);
        setHorizontalAlignment(JLabel.CENTER);
        mapFrame = mf;
    }

    // getter setter
    public void setName(String n) { name = n;}

    public String getName() { return name;}

    // Methods
    //      might need to modify these last part for character that always
    //      at the center of the map
    public void updateLocation() {
        // ...
        mapFrame.validate();
        mapFrame.repaint();
    }

    public void moveUp() { this.y -= speed;}

    public void moveDown() { this.y += speed;}

    public void moveLeft() { this.x += speed;}

    public void moveRight() { this.y -= speed;}
}

abstract class Character extends BaseLabel{
    // Variable
    protected int       hp;

    // Constructor
    Character(String file, int w, int h, Map mf) {
        super(file, w, h, mf);
    }

    // setter getter
    void setHp(int hp) { this.hp = hp; }

    // Methods
    void takeDamage(int damage){
        // override this for player and boss
        this.hp -= damage;
        if (hp <= 0) {this.death() ;}
    }

    protected void death(){
        mapFrame.remove(this);
        mapFrame.revalidate();
        mapFrame.repaint();
    }
}

class Player extends Character {
    // Variable
    protected double        dmgMultiplier;
    protected int           exp, maxExp;
    protected int           maxHp; // Hp get from Character class
    protected int           level;
    protected Weapon        weapon;
    protected PlayerPanel   panel;
    // Constructors
    Player(String file, int w, int h, Map mf, int maxHp){
        super(file, w, h, mf);
        maxHp = maxHp;
        hp = maxHp;
        dmgMultiplier = 1;
        exp = 0;
        maxExp = 10;
        panel = new PlayerPanel(this);
    }

    void levelUp(){
        level += 1;
        LevelUpMenu lm = new LevelUpMenu();//
    }

    @Override
    void takeDamage( int damage){
        this.hp -= damage;
        if (hp <= 0) {this.death() ;}
        else{
            panel.healthBar.setValue(this.hp);
            revalidate();
            repaint();
        }
    }
}

class PlayerPanel extends JPanel{
    // members
    protected Player player;
    protected JProgressBar    healthBar;

    // Constructor
    PlayerPanel(Player p){
        this.player = p;

        // Creating Health Bar
        healthBar = new JProgressBar(SwingConstants.HORIZONTAL, player.maxHp);
        healthBar.setValue(player.maxHp);
        healthBar.setStringPainted(true);
        healthBar.setForeground(Color.RED);
        healthBar.setBackground(Color.GRAY);

        this.add(player, BorderLayout.CENTER);
        this.add(healthBar, BorderLayout.SOUTH);
    }
}

class Monster extends Character{

    // Constructor
    Monster(String file, int w, int h, Map mf){
        super(file, w, h, mf);
    }
}

class Boss extends Monster{
    Boss(String file, int w, int h, Map mf){
        super(file, w, h, mf);
    }
}