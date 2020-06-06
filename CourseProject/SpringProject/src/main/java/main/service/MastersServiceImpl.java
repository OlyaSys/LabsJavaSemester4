package main.service;

import main.entity.Cars;
import main.entity.Masters;
import main.entity.Works;
import main.exception.FieldNotFoundException;
import main.repository.MastersRepository;
import main.repository.WorksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MastersServiceImpl implements MastersService {

    @Autowired
    private MastersRepository mastersRepository;

    @Autowired
    private WorksRepository worksRepository;

    @Override
    public List<Masters> listMasters() {
        return (List<Masters>) mastersRepository.findAll();
    }

    @Override
    public Masters findById(Long id) {
        Optional<Masters> optionalMasters = mastersRepository.findById(id);
        if (optionalMasters.isPresent()){
            return optionalMasters.get();
        } else {
            throw new FieldNotFoundException("Masters do not contain such id = " + id);
        }
    }

    @Override
    public Masters findByName(String name) {
        List<Masters> mastersList = listMasters();
        return mastersList.stream()
                .filter(master -> master.getNameMaster()
                                        .equalsIgnoreCase(name))
                                        .findFirst()
                                        .orElseThrow(() -> new FieldNotFoundException("Masters do not contain such name = " + name));
    }

    @Override
    public Masters addMaster(Masters master) {
        return mastersRepository.save(master);
    }

    @Override
    public Masters setMaster(Long id, Masters master) {
        Optional<Masters> optionalMasters = mastersRepository.findById(id);
        if (optionalMasters.isPresent()){
            if (master != null) {
                if (!master.getNameMaster().isEmpty()) {
                    optionalMasters.get().setNameMaster(master.getNameMaster());
                }
                return mastersRepository.save(optionalMasters.get());
            } else {
                throw new FieldNotFoundException("Master is empty");
            }
        }
        throw new FieldNotFoundException("Masters do not contain such id = " + id);
    }
}
