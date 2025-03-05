package Project2;

public class Place {
    private String name;
    private int visitor;
    Place(String name){this.name=name; visitor = 0;}
    
    //getter
    public int getVisitor(){return visitor;}
    public String getName(){return name;}
    //setter 
    public void setVisitor(int v){visitor = v;}
    
    //update
    public void updateVisitor(int v){visitor +=v;}
    @Override
    public String toString() {
        return name;
    }
}
