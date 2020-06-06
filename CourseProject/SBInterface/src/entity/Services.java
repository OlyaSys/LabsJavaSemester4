package entity;

public class Services {
    private Integer id;
    private String name;
    private Integer costOur;
    private Integer costForeign;

    public Services() {
    }

    public Services(Integer id, String name, Integer costOur, Integer costForeign) {
        this.id = id;
        this.name = name;
        this.costOur = costOur;
        this.costForeign = costForeign;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        return "id: " + id
                + "\nName: '" + name
                + "\nOur cost: " + costOur
                + "\nForeign cost: " + costForeign
                + "\n--------------------------------";
    }
}
