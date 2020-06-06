package main.repository;

import main.entity.Cars;
import org.springframework.data.repository.CrudRepository;

public interface CarsRepository extends CrudRepository<Cars, Long> {
}
