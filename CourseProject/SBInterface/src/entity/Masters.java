package entity;

public class Masters {
    private Integer id;
    private String nameMaster;

    public Masters() {
    }

    public Masters(Integer id, String nameMaster) {
        this.id = id;
        this.nameMaster = nameMaster;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameMaster() {
        return nameMaster;
    }

    public void setNameMaster(String nameMaster) {
        this.nameMaster = nameMaster;
    }

    @Override
    public String toString() {
        return "id: " + id
                + "\nName: " + nameMaster
                + "\n--------------------------------";
    }
}
