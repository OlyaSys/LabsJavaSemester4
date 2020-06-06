package main.entity;

import javax.persistence.*;

@Entity
public class Cars {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NUM", unique = true)
    private String number;

    @Column(name = "COLOR")
    private String color;

    @Column(name = "MARK")
    private String mark;

    @Column(name = "IS_FOREIGN")
    private Integer isForeign;

    public Cars() {
    }

    public Cars(String number, String color, String mark, Integer isForeign) {
        this.number = number;
        this.color = color;
        this.mark = mark;
        this.isForeign = isForeign;
    }


    public Long getId() {
        return id;
    }

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
        return isForeign;
    }

    public void setForeign(Integer isForeign) {
        this.isForeign = isForeign;
    }

    @Override
    public String toString() {
        return "Cars{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", color='" + color + '\'' +
                ", mark='" + mark + '\'' +
                ", isForeign=" + isForeign +
                '}';
    }
}
