package main.service;

import main.entity.Cars;

import java.util.List;

public interface CarsService {
    List<Cars> listCars();

    Cars findById(Long id);
    Cars findByNumber(String number);

    List<Cars> filterByColor(String color);
    List<Cars> filterByMark(String mark);

    Cars addCar(Cars car);
    Cars setForeignCar(Long id, Integer isForeign);
    Cars setCar(Long id, Cars car);
}
