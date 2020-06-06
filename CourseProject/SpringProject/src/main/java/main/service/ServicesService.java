package main.service;

import main.entity.Services;

import java.util.List;

public interface ServicesService {
    List<Services> listServices();
    Services findById(Long id);
    Services findByName(String name);
    Services findByCostOur(Integer costOur);
    Services findByCostForeign(Integer costForeign);
    Services addService(Services service);
    Services setService(Long id, Services service);
}
