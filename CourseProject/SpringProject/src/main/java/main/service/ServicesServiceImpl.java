package main.service;

import main.entity.Masters;
import main.entity.Services;
import main.exception.FieldNotFoundException;
import main.repository.ServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicesServiceImpl implements ServicesService {
    @Autowired
    private ServicesRepository servicesRepository;

    @Override
    public List<Services> listServices() {
        return (List<Services>) servicesRepository.findAll();
    }

    @Override
    public Services findById(Long id) {
        Optional<Services> optionalServices = servicesRepository.findById(id);
        if (optionalServices.isPresent()){
            return optionalServices.get();
        } else {
            throw new FieldNotFoundException("Services do not contain such id = " + id);
        }
    }

    @Override
    public Services findByName(String name) {
        List<Services> servicesList = listServices();
        return servicesList.stream()
                .filter(service -> (service.getName().equalsIgnoreCase(name)))
                .findFirst()
                .orElseThrow(() -> new FieldNotFoundException("Works do not contain such name = " + name));
    }

    @Override
    public Services findByCostOur(Integer costOur) {
        List<Services> servicesList = listServices();
        return servicesList.stream()
                .filter(service -> (service.getCostOur().equals(costOur)))
                .findFirst()
                .orElseThrow(() -> new FieldNotFoundException("Works do not contain such cost_our = " + costOur));
    }

    @Override
    public Services findByCostForeign(Integer costForeign) {
        List<Services> servicesList = listServices();
        return servicesList.stream()
                .filter(service -> (service.getCostForeign().equals(costForeign)))
                .findFirst()
                .orElseThrow(() -> new FieldNotFoundException("Works do not contain such cost_foreign = " + costForeign));
    }

    @Override
    public Services addService(Services service) {
        return servicesRepository.save(service);
    }

    @Override
    public Services setService(Long id, Services service) {
        Optional<Services> optionalServices = servicesRepository.findById(id);
        if (optionalServices.isPresent()){
            if (service != null) {
                if (!service.getName().isEmpty()) {
                    optionalServices.get().setName(service.getName());
                }
                if (service.getCostOur() != null) {
                    optionalServices.get().setCostOur(service.getCostOur());
                }
                if (service.getCostForeign() != null) {
                    optionalServices.get().setCostForeign(service.getCostForeign());
                }
                return servicesRepository.save(optionalServices.get());
            } else {
                throw new FieldNotFoundException("Service is empty");
            }
        }
        throw new FieldNotFoundException("Services do not contain such id = " + id);
    }
}
