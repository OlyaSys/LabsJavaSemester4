package main.entity;

import javax.persistence.*;

@Entity
public class Services {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    @Column(name = "COST_OUR", nullable = false)
    private Integer costOur;

    @Column(name = "COST_FOREIGN", nullable = false)
    private Integer costForeign;

    public Services() {
    }

    public Services(String name, Integer costOur, Integer costForeign) {
        this.name = name;
        this.costOur = costOur;
        this.costForeign = costForeign;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCostOur() {
        return costOur;
    }

    public void setCostOur(Integer costOur) {
        this.costOur = costOur;
    }

    public Integer getCostForeign() {
        return costForeign;
    }

    public void setCostForeign(Integer costForeign) {
        this.costForeign = costForeign;
    }

    @Override
    public String toString() {
        return "Services{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", costOur=" + costOur +
                ", costForeign=" + costForeign +
                '}';
    }
}
