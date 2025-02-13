package Project1;

class Installment
{
    private int months;
    private double interest;

    public Installment(int month, double interest){this.months = month; this.interest = interest;}
    public void setmonths(int m){months = m;}
    public void setInterest(double i){interest = i;}
    public int getMonths(){return this.months;}
    public double getInterest(){return this.interest;}

    public void print(){System.out.printf("%2d-month plan %4s monthly interest = %.2f%% \n",this.months," ",this.interest);}
}