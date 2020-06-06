package entity;

public class Cars {
    private Integer id;
    private String number;
    private String color;
    private String mark;
    private Integer foreign;

    public Cars() {
    }

    public Cars(Integer id, String number, String color, String mark, Integer foreign) {
        this.id = id;
        this.number = number;
        this.color = color;
        this.mark = mark;
        this.foreign = foreign;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {this.id = id;}

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public Integer getForeign() {
        return foreign;
    }

    public void setForeign(Integer isForeign) {
        this.foreign = isForeign;
    }

    @Override
    public String toString() {
        return "id: " + id
                + "\nNumber: " + number
                + "\nColor: " + color
                + "\nMark: " + mark
                + "\nForeign: " + foreign
                + "\n--------------------------------";
    }
}
