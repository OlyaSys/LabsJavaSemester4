package entity;

public class Works {
    private Integer id;
    private String dateWork;
    private Integer masters_id;
    private Integer cars_id;
    private Integer services_id;

    private Cars cars;
    private Masters masters;
    private Services services;

    public Works() {
    }

    public Works(Integer id, String dateWork, Integer masters_id, Integer cars_id, Integer services_id) {
        this.id = id;
        this.dateWork = dateWork;
        this.masters_id = masters_id;
        this.cars_id = cars_id;
        this.services_id = services_id;
    }

    public Works(Integer id, String dateWork, Masters masters, Cars cars, Services services) {
        this.id = id;
        this.dateWork = dateWork;
        this.masters = masters;
        this.cars = cars;
        this.services = services;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDateWork() {
        return dateWork;
    }

    public void setDateWork(String dateWork) {
        this.dateWork = dateWork;
    }

    public Integer getMasters_id() {
        return masters_id;
    }

    public void setMasters_id(Integer masters_id) {
        this.masters_id = masters_id;
    }

    public Integer getCars_id() {
        return cars_id;
    }

    public void setCars_id(Integer cars_id) {
        this.cars_id = cars_id;
    }

    public Integer getServices_id() {
        return services_id;
    }

    public void setServices_id(Integer services_id) {
        this.services_id = services_id;
    }

    public Cars getCar() {
        return cars;
    }

    public void setCar(Cars car) {
        this.cars = car;
    }

    public Masters getMaster() {
        return masters;
    }

    public void setMaster(Masters master) {
        this.masters = master;
    }

    public Services getService() {
        return services;
    }

    public void setService(Services service) {
        this.services = service;
    }

    @Override
    public String toString() {
        return "Works{" +
                "id=" + id +
                ", dateWork='" + dateWork + '\'' +
                ", masters_id=" + masters_id +
                ", cars_id=" + cars_id +
                ", services_id=" + services_id +
                '}';
    }
}
