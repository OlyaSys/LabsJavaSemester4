package main.service;
import main.entity.Cars;
import main.entity.Masters;
import main.entity.Services;
import main.entity.Works;

import java.util.List;

public interface WorksService {
    List<Works> listWorks();
    Works findById(Long id);
    Works findByDate(String dateWork);
    Works addWork(Works work);
    Works setDate(Long id, String dateWork);
    Works setMaster(Long id, Masters master);
    Works setCar(Long id, Cars car);
    Works setService(Long id, Services service);
    void deleteFromDB(Long id);
}
