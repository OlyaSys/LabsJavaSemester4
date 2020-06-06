package main.repository;

import main.entity.Works;
import org.springframework.data.repository.CrudRepository;

public interface WorksRepository extends CrudRepository<Works, Long> {
}
