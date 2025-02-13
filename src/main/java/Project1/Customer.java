package Project1;

class Customer
{
    private String name;
    private int point;
    private boolean firsttime;
    public Customer(String name)
    {
        this.name = name;
        this.firsttime = true;
        this.point = 0;
    }
    public void setName(String n){name =n;}
    public void setPoints(int pt){point = pt;}//calculate from purchased order
    public boolean getFirstTime(){return this.firsttime;}
    public String getName(){return name;}
    public int getPoints(){return point;}
    public void setFirsttime(boolean firsttime){this.firsttime = firsttime;}
    }
