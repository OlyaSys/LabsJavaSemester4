package main.entity;

import javax.persistence.*;

@Entity
public class Masters {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String nameMaster;

    public Masters() {
    }

    public Masters(String nameMaster) {
        this.nameMaster = nameMaster;
    }

    public Long getId() {
        return id;
    }

    public String getNameMaster() {
        return nameMaster;
    }

    public void setNameMaster(String nameMaster) {
        this.nameMaster = nameMaster;
    }

    @Override
    public String toString() {
        return "Masters{" +
                "id=" + id +
                ", nameMaster='" + nameMaster + '\'' +
                '}';
    }
}
