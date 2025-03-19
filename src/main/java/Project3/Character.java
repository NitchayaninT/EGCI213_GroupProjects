package Project3;

import javax.swing.*;
import java.awt.*;

// I just create this class as a place holder for the real one
class LevelUpMenu extends JPanel{ }

abstract class BaseLabel extends JLabel{
    protected String name;
    protected MyImageIcon icon;
    protected int x , y, width, height;
    protected int speed;
    protected Map mapFrame; // for putting the map Panel as reference for characters

    // Constructors
    public BaseLabel() {super();} // default constructor

    public BaseLabel(String n, int s, String file){
        name = n;
        speed = s;
        icon = new MyImageIcon(file).resize(width, height);
        setIcon(icon);
        setHorizontalAlignment(JLabel.CENTER);
    }

    // getter setter
    public void setName(String n) { name = n;}

    public String getName() { return name;}

    public void setMap(Map m){
        mapFrame = m;
    }

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

// ******************************************************************************

abstract class Character extends BaseLabel{
    // Variable
    protected int       hp;

    // Constructor
    Character(String n, int hp, int s, String file) {
        super(n, s, file);
        this.hp = hp;
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

// ******************************************************************************
class MyCharacter extends Character {
    // Variable
    protected double        dmgMultiplier;
    protected int           exp, maxExp;
    protected int           maxHp; // Hp get from Character class
    protected int           level;
    protected Weapon        weapon;
    protected PlayerPanel   panel;
    // Constructors
    MyCharacter(String n, int hp, int s, Weapon wp, String file){
        super(n, hp, s, file);
        this.height = MyConstants.CH_HEIGHT;
        this.width = MyConstants.CH_WIDTH;
        this.maxHp = hp;
        this.hp = maxHp;
        this.weapon = wp;
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
    @Override
    public String toString(){
        return this.name;
    }
}


class PlayerPanel extends JPanel{
    // members
    protected MyCharacter player;
    protected JProgressBar    healthBar;

    //

    // Constructor
    PlayerPanel(MyCharacter p){
        this.player = p;
        this.setSize(player.width,player.height);
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

// ******************************************************************************
class Monster extends Character{

    // Constructor
    Monster(String n, int hp, int s, int w, int h, String file){
        super(n, hp, s,file);
        width = w;
        height = h;
    }
}

class Boss extends Monster{

    // Constructor
    Boss(String n, int hp, int s, String file){
        super(n,hp,s, MyConstants.BOSS_HEIGHT,MyConstants.BOSS_WIDTH,file);
    }

    @Override
    protected void death(){
        //win();
    }
}