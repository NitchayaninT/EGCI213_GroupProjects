package Project3;

class Weapon
{
    private String name;
    private int damage =  10;
    private int height;
    private int width;
    private MyImageIcon icon;
    private int level;

    //constructor
    protected Weapon(String name, int dmg, int height, int width, String iconFile, int level){
        icon = new MyImageIcon(iconFile).resize(width, height);
        this.name = name; this.damage = dmg; this.height = height; this.width = width; this.level = level;
    }
    //getters
    String getName(){return name;}
    int getDamage(){return damage;}
    int getHeight(){return height;}
    int getWidth(){return width;}
    int getLevel(){return level;}
    MyImageIcon getIcon(){return icon;}

    //methods
    protected void increaseLevel(){this.level++;}
    protected void changeWeapon(Weapon w){this.name = w.name; this.damage = w.damage;
        this.height = w.height; this.width = w.width;
        this.icon = w.getIcon(); this.level = w.getLevel();
    }
}
