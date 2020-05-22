package javaFX;

public class Table {

    private int id;
    private int prodid;
    private String title;
    private int cost;

    public Table(int id, int prodid, String title, int cost) {
        this.id = id;
        this.prodid = prodid;
        this.title = title;
        this.cost = cost;
    }

    public Table() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProdId(int prodid) {
        this.prodid = prodid;
    }

    public void setTitle(String title) {
        this.title =  title;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public int getProdid() {
        return prodid;
    }

    public String getTitle() { return title; }

    public int getCost() {
        return cost;
    }
}
