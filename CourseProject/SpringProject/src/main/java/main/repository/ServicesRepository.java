package main.repository;

import main.entity.Services;
import org.springframework.data.repository.CrudRepository;

public interface ServicesRepository extends CrudRepository<Services, Long> {
}
