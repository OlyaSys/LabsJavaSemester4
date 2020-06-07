package main.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Works {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DATE_WORK", nullable = false)
    private String dateWork;

    @ManyToOne
    @JoinColumn(name = "MASTER_ID", nullable = false)
    private Masters masters;

    @ManyToOne
    @JoinColumn(name = "CAR_ID", nullable = false)
    private Cars cars;

    @ManyToOne
    @JoinColumn(name = "SERVICE_ID", nullable = false)
    private Services services;

    public Works() {
    }

    public Works(String dateWork, Masters masters, Cars cars, Services services) {
        this.dateWork = dateWork;
        this.masters = masters;
        this.cars = cars;
        this.services = services;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDateWork() {
        return dateWork;
    }

    public void setDateWork(String dateWork) {
        this.dateWork = dateWork;
    }

    public Masters getMasters() {
        return masters;
    }

    public void setMasters(Masters masters) {
        this.masters = masters;
    }

    public Cars getCars() {
        return cars;
    }

    public void setCars(Cars cars) {
        this.cars = cars;
    }

    public Services getServices() {
        return services;
    }

    public void setServices(Services services) {
        this.services = services;
    }

    @Override
    public String toString() {
        return "Works{" +
                "id=" + id +
                ", dateWork=" + dateWork +
                ", masters=" + masters +
                ", cars=" + cars +
                ", services=" + services +
                '}';
    }
}
