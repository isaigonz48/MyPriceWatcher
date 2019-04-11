package edu.utep.cs.cs4330.mypricewatcher;

public class DatabaseItem extends Item{
    private long id;
    private String name;
    private double currentPrice;
    private double initialPrice;
    private String URL;
    private double percentageOff;

    /**
     * Default Constructor
     */
    public DatabaseItem(){
        //this.name = "Broom";
        //this.currentPrice = 29.97;
        //this.initialPrice = 29.97;
        //this.URL = "https://www.amazon.com/Cedar-Heavy-Commercial-Broom-Handle/dp/B0106FW42U/ref=sr_1_8_a_it?ie=UTF8&qid=1550354047&sr=8-8&keywords=broom&th=1";
        super();
    }

    /**
     * Constructor that receives the name, initial price, and url of the item. Automatically calculates percentage off.
     * @param n name of the item
     * @param i initial price of the item
     * @param url url of the item
     */
    public DatabaseItem(long id, String n, double i, String url){
        super(n, i, url);
        this.id = id;
        /*this.name = n;
        this.initialPrice = i;
        this.currentPrice = this.initialPrice;
        this.URL = url;
        this.percentageOff = calcPercentageOff();
    */
    }

    public DatabaseItem(long id, String n, double i, double c, String url){
        super(n, i, c, url);
        this.id = id;
    }

    public String getName(){
        return this.name;
    }
    public double getCurPrice(){
        return this.currentPrice;
    }
    public double getInitPrice(){
        return this.initialPrice;
    }
    public String getUrl(){
        return this.URL;
    }
    public double getPercentageOff(){
        return this.percentageOff;
    }

    public void setName(String newName){
        this.name = newName;
    }
    public void setCurPrice(double newPrice){
        this.currentPrice = newPrice;
    }
    public void setURL(String newUrl){
        this.URL = newUrl;
    }
    public void setPercentageOff(){
        this.percentageOff = calcPercentageOff();
    }


    /**
     * Calculates the percentage difference between the initial price and the current price.
     * @return the percentage off
     */
    public double calcPercentageOff(){
        return super.calcPercentageOff();///return (100.00 - (this.currentPrice/this.initialPrice) * 100);
    }

    /**
     * Find the current price of the item. For this version of the app, it is only a simulated
     * price.
     */
    public void findNewPrice(){
        super.findNewPrice();//currentPrice = PriceFinder.simulatePrice(this.initialPrice);
    }

}
