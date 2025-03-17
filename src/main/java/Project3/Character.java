package Project3;

import javax.swing.*;

abstract class Character extends JLabel {
    protected String      name;
    protected int           hp;
    protected int        speed;
    protected int        width;
    protected int       height;
    protected MyImageIcon icon;

    protected void setHp(int h)        {this.hp=h;};
    protected void setSpeed(int s)     {this.speed=s;}
    protected void setWidth(int w)     {this.width = w;}

    // intersect

}

class Monster extends Character{

}

class Boss extends Monster{

}

class Player extends Character{
    Weapon w;

    //constructor
    Player(String name, int hp, int speed, int width, int height, Weapon weapon, String iconFile){
        icon = new MyImageIcon(iconFile).resize(width, height);
        this.name = name; this.hp = hp; this.speed = speed; this.width = width; w = weapon; this.height = height;
    }
    //methods
    protected void setWeapon(Weapon w){this.w=w;}

    //toString
    @Override
    public String toString() {
        return this.name;
    }
}

