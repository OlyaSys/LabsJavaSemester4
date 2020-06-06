package main.service;

import main.entity.Cars;
import main.entity.Masters;
import main.entity.Services;
import main.entity.Works;
import main.exception.FieldNotFoundException;
import main.repository.CarsRepository;
import main.repository.MastersRepository;
import main.repository.ServicesRepository;
import main.repository.WorksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorksServiceImpl implements WorksService {
    @Autowired
    private WorksRepository worksRepository;

    @Autowired
    private MastersRepository mastersRepository;

    @Autowired
    private CarsRepository carsRepository;

    @Autowired
    private ServicesRepository servicesRepository;

    @Override
    public List<Works> listWorks() {
        return (List<Works>) worksRepository.findAll();
    }

    @Override
    public Works findById(Long id) {
        Optional<Works> optionalWorks = worksRepository.findById(id);
        if (optionalWorks.isPresent()){
            return optionalWorks.get();
        } else {
            throw new FieldNotFoundException("Works do not contain such id = " + id);
        }
    }

    @Override
    public Works findByDate(String dateWork) {
        List<Works> worksList = listWorks();
        return worksList.stream()
                .filter(work -> (work.getDateWork().equalsIgnoreCase(dateWork)))
                .findFirst()
                .orElseThrow(() -> new FieldNotFoundException("Works do not contain such date = " + dateWork));
    }

    @Override
    public Works addWork(Works work) {
        return worksRepository.save(work);
    }

    /*@Override
    public Works setDate(Long id, String dateWork) {
        Optional<Works> optionalWorks = worksRepository.findById(id);
        if (optionalWorks.isPresent()) {
            optionalWorks.get().setDateWork(dateWork);
            return worksRepository.save(optionalWorks.get());
        } else {
            throw new FieldNotFoundException("Works do not contain such id = " + id);
        }
    }

    @Override
    public Works setMaster(Long id, Masters master) {
        Long index = null;
        List<Masters> mastersList = (List<Masters>)mastersRepository.findAll();
        for (Masters tmpMaster : mastersList) {
            if (tmpMaster.getId().equals(master.getId())) {
                index = master.getId();
                break;
            }
        }

        if (index == null) {
            mastersRepository.save(master);
            index = master.getId();
        }

        Optional<Works> optionalWorks = worksRepository.findById(id);
        if (optionalWorks.isPresent()) {
            optionalWorks.get().setMasters(mastersRepository.findById(index).get());
            return worksRepository.save(optionalWorks.get());
        } else {
            throw new FieldNotFoundException("Works do not contain such id = " + id);
        }
    }

    @Override
    public Works setCar(Long id, Cars car) {
        Long index = null;
        List<Cars> carsList = (List<Cars>)carsRepository.findAll();
        for (Cars tmpCar : carsList) {
            if (tmpCar.getId().equals(car.getId())) {
                index = car.getId();
                break;
            }
        }

        if (index == null) {
            carsRepository.save(car);
            index = car.getId();
        }

        Optional<Works> optionalWorks = worksRepository.findById(id);
        if (optionalWorks.isPresent()) {
            optionalWorks.get().setCars(carsRepository.findById(index).get());
            return worksRepository.save(optionalWorks.get());
        } else {
            throw new FieldNotFoundException("Works do not contain such id = " + id);
        }
    }

    @Override
    public Works setService(Long id, Services service) {
        Long index = null;
        List<Services> servicesList = (List<Services>)servicesRepository.findAll();
        for (Services tmpService : servicesList) {
            if (tmpService.getId().equals(service.getId())) {
                index = service.getId();
                break;
            }
        }

        if (index == null) {
            servicesRepository.save(service);
            index = service.getId();
        }

        Optional<Works> optionalWorks = worksRepository.findById(id);
        if (optionalWorks.isPresent()) {
            optionalWorks.get().setServices(servicesRepository.findById(index).get());
            return worksRepository.save(optionalWorks.get());
        } else {
            throw new FieldNotFoundException("Works do not contain such id = " + id);
        }
    }*/

    @Override
    public void deleteFromDB(Long id) {
        Optional<Works> optionalWorks = worksRepository.findById(id);
        if (optionalWorks.isPresent()) {
            worksRepository.delete(optionalWorks.get());
        } else {
            throw new FieldNotFoundException("Works do not contain such id = " + id);
        }
    }
}
