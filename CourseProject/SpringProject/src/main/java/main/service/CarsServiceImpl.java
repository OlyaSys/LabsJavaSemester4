package main.service;

import main.entity.Cars;
import main.exception.FieldNotFoundException;
import main.repository.CarsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarsServiceImpl implements CarsService {

    @Autowired
    private CarsRepository carsRepository;

    @Override
    public List<Cars> listCars() {
        return (List<Cars>) carsRepository.findAll();
    }

    @Override
    public Cars findById(Long id) {
        Optional<Cars> optionalCars = carsRepository.findById(id);
        if (optionalCars.isPresent()){
            return optionalCars.get();
        } else {
            throw new FieldNotFoundException("Cars do not contain such id = " + id);
        }
    }

    @Override
    public Cars findByNumber(String number) {
        List<Cars> carsList = listCars();
        return carsList.stream()
                .filter(car -> car.getNumber()
                                  .equalsIgnoreCase(number))
                                  .findFirst()
                                  .orElseThrow(() -> new FieldNotFoundException("Cars do not contain such number = " + number));
    }

    @Override
    public List<Cars> filterByColor(String color) {
        List<Cars> carsList = listCars();
        List<Cars> filteredList = carsList.stream()
                .filter(cars -> cars.getColor().equalsIgnoreCase(color)).collect(Collectors.toList());
        if (!filteredList.isEmpty()) {
            return filteredList;
        }
        throw new FieldNotFoundException("Cars do not contain such color = " + color);
    }

    @Override
    public List<Cars> filterByMark(String mark) {
        List<Cars> carsList = listCars();
        List<Cars> filteredList = carsList.stream()
                .filter(cars -> cars.getMark().equalsIgnoreCase(mark)).collect(Collectors.toList());
        if (!filteredList.isEmpty()) {
            return filteredList;
        }
        throw new FieldNotFoundException("Cars do not contain such mark = " + mark);
    }

    @Override
    public Cars addCar(Cars car) {
        List<Cars> carsList = listCars();
        for (Cars tmpCar : carsList) {
            if (tmpCar.getNumber().equalsIgnoreCase(car.getNumber())) {
                throw new FieldNotFoundException("Car already exists with such number = " + tmpCar.getNumber());
            }
        }
        return carsRepository.save(car);
    }

    @Override
    public Cars setForeignCar(Long id, Integer isForeign) {
        Optional<Cars> optionalCars = carsRepository.findById(id);
        if (optionalCars.isPresent()){
            if (isForeign.equals(0) || isForeign.equals(1)) {
                optionalCars.get().setForeign(isForeign);
                return carsRepository.save(optionalCars.get());
            } else {
                throw new FieldNotFoundException("Foreign can be '1' or '0'");
            }
        }
        throw new FieldNotFoundException("Cars do not contain such id = " + id);
    }

    @Override
    public Cars setCar(Long id, Cars car) {
        Optional<Cars> optionalCars = carsRepository.findById(id);
        System.out.println(car);
        if (optionalCars.isPresent()){
            if (car != null) {
                if (!car.getNumber().isEmpty()) {
                    optionalCars.get().setNumber(car.getNumber());
                }
                if (!car.getColor().isEmpty()) {
                    optionalCars.get().setColor(car.getColor());
                }
                if (!car.getMark().isEmpty()) {
                    optionalCars.get().setMark(car.getMark());
                }
                if (car.getForeign() != null) {
                    optionalCars.get().setForeign(car.getForeign());
                }
                return carsRepository.save(optionalCars.get());
            } else {
                throw new FieldNotFoundException("Car is empty");
            }
        }
        throw new FieldNotFoundException("Cars do not contain such id = " + id);
    }
}
