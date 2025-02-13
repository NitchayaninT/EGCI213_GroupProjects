package Project1;

class Order
{
    private int id;
    private Customer customer;
    private Product product;
    private int unit;
    private Installment installment;
    public Order(){}
    public Order(int i, Customer customer, Product product,int unit, Installment installment)
    {
        this.id = i;
        this.customer = customer;
        this.product = product;
        this.unit = unit;
        this.installment = installment;
    }
    public int getID(){return id;}
    public Customer getCustomer(){return customer;}
    public Product getProduct(){return product;}
    public int getUnit(){return unit;}
    public Installment getInstall(){return installment;}

}