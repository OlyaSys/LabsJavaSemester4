package main.web;

import main.entity.Cars;
import main.entity.Masters;
import main.entity.Services;
import main.entity.Works;
import main.exception.FieldNotFoundException;
import main.service.CarsService;
import main.service.MastersService;
import main.service.ServicesService;
import main.service.WorksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/bt")
public class BugTrackerController {
    private CarsService carsService;
    private ServicesService servicesService;
    private MastersService mastersService;
    private WorksService worksService;

    @GetMapping("/cars")
    public ResponseEntity<List<Cars>> getCars() {
        List<Cars> list = carsService.listCars();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/cars/byId/{id}")
    public ResponseEntity<Cars> getCarById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(carsService.findById(id), HttpStatus.OK);
        }
        catch (FieldNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cars not found");
        }
    }

    @GetMapping("/cars/byNum/{number}")
    public ResponseEntity<Cars> getCarByNum(@PathVariable String number) {
        try {
            return new ResponseEntity<>(carsService.findByNumber(number), HttpStatus.OK);
        }
        catch (FieldNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cars not found");
        }
    }

    @GetMapping("/cars/filterByColor/{color}")
    public ResponseEntity<List<Cars>> getFilteredListByColor(@PathVariable String color) {
        try {
            return new ResponseEntity<>(carsService.filterByColor(color), HttpStatus.OK);
        }
        catch (FieldNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cars not found");
        }
    }

    @GetMapping("/cars/filterByMark/{mark}")
    public ResponseEntity<List<Cars>> getFilteredListByMark(@PathVariable String mark) {
        try {
            return new ResponseEntity<>(carsService.filterByMark(mark), HttpStatus.OK);
        }
        catch (FieldNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cars not found");
        }
    }

    @PostMapping(value = "/addCar", consumes = "application/json", produces = "application/json")
    public Cars addCar(@RequestBody Cars newCar) {
        return carsService.addCar(newCar);
    }

    @PostMapping(value = "/setForeignFlag/{id}", consumes = "application/json", produces = "application/json")
    public Cars setForeignFlag(@PathVariable Long id, @RequestBody String flag) {
        try {
            Integer intFlag = Integer.parseInt(flag);
            return carsService.setForeignCar(id, intFlag);
        }
        catch (FieldNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cars not found");
        }
    }

    @GetMapping("/masters")
    public ResponseEntity<List<Masters>> getMasters() {
        List<Masters> listMasters = mastersService.listMasters();
        return new ResponseEntity<>(listMasters, HttpStatus.OK);
    }

    @PostMapping(value = "/addMaster", consumes = "application/json", produces = "application/json")
    public Masters addMaster(@RequestBody Masters newMaster) {
        return mastersService.addMaster(newMaster);
    }

    @GetMapping("/masters/byId/{id}")
    public ResponseEntity<Masters> getMasterById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(mastersService.findById(id), HttpStatus.OK);
        }
        catch (FieldNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Masters not found");
        }
    }

    @GetMapping("/masters/byName/{name}")
    public ResponseEntity<Masters> getMasterByName(@PathVariable String name) {
        try {
            return new ResponseEntity<>(mastersService.findByName(name), HttpStatus.OK);
        } catch (FieldNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Masters not found");
        }
    }

    @GetMapping("/services")
    public ResponseEntity<List<Services>> getServices() {
        List<Services> listServices = servicesService.listServices();
        return new ResponseEntity<>(listServices, HttpStatus.OK);
    }

    @PostMapping(value = "/addService", consumes = "application/json", produces = "application/json")
    public Services addService(@RequestBody Services newService) {
        return servicesService.addService(newService);
    }

    @GetMapping("/services/byId/{id}")
    public ResponseEntity<Services> getServiceById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(servicesService.findById(id), HttpStatus.OK);
        }
        catch (FieldNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Services not found");
        }
    }

    @GetMapping("/services/byName/{name}")
    public ResponseEntity<Services> getServiceByName(@PathVariable String name) {
        try {
            return new ResponseEntity<>(servicesService.findByName(name), HttpStatus.OK);
        } catch (FieldNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Services not found");
        }
    }

    @GetMapping("/services/byCostOur/{costOur}")
    public ResponseEntity<Services> getServiceByCostOur(@PathVariable String costOur) {
        try {
            Integer intCostOur = Integer.valueOf(costOur);
            return new ResponseEntity<>(servicesService.findByCostOur(intCostOur), HttpStatus.OK);
        } catch (FieldNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Services not found");
        }
    }

    @GetMapping("/services/byCostForeign/{costForeign}")
    public ResponseEntity<Services> getServiceByCostForeign(@PathVariable String costForeign) {
        try {
            Integer intCostForeign = Integer.valueOf(costForeign);
            return new ResponseEntity<>(servicesService.findByCostForeign(intCostForeign), HttpStatus.OK);
        } catch (FieldNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Services not found");
        }
    }

    @GetMapping("/works")
    public ResponseEntity<List<Works>> getWorks() {
        List<Works> listWorks = worksService.listWorks();
        return new ResponseEntity<>(listWorks, HttpStatus.OK);
    }

    @PostMapping(value = "/addWork", consumes = "application/json", produces = "application/json")
    public Works addWork(@RequestBody Works work) {
        return worksService.addWork(work);
    }

    @GetMapping("/works/byId/{id}")
    public ResponseEntity<Works> getWorkById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(worksService.findById(id), HttpStatus.OK);
        }
        catch (FieldNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cars not found");
        }
    }

    @GetMapping("/works/byDate/{date}")
    public ResponseEntity<Works> getWorkByDate(@PathVariable String date) {
        try {
            return new ResponseEntity<>(worksService.findByDate(date), HttpStatus.OK);
        }
        catch (FieldNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cars not found");
        }
    }

    /*@PostMapping(value = "/setDate/{id}", consumes = "application/json", produces = "application/json")
    public Works setMaster(@PathVariable Long id, @RequestBody String date) {
        try {
            return worksService.setDate(id, date);
        }
        catch (FieldNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Work not found");
        }
    }*/

    @PostMapping(value = "/setMaster/{id}", consumes = "application/json", produces = "application/json")
    public Masters setMaster(@PathVariable Long id, @RequestBody Masters master) {
        try {
            return mastersService.setMaster(id, master);
        }
        catch (FieldNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Master not found");
        }
    }

    @PostMapping(value = "/setCar/{id}", consumes = "application/json", produces = "application/json")
    public Cars setCar(@PathVariable Long id, @RequestBody Cars car) {
        try {
            return carsService.setCar(id, car);
        }
        catch (FieldNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car not found");
        }
    }

    @PostMapping(value = "/setService/{id}", consumes = "application/json", produces = "application/json")
    public Services setService(@PathVariable Long id, @RequestBody Services service) {
        try {
            return servicesService.setService(id, service);
        }
        catch (FieldNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Service not found");
        }
    }

    @DeleteMapping(value = "/deleteWork/{id}")
    public void deleteWorks(@PathVariable Long id) {
        try {
            worksService.deleteFromDB(id);
        }
        catch (FieldNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Work does not exist");
        }
    }

    @Autowired
    public void setCarsService(CarsService carsService) {
        this.carsService = carsService;
    }

    @Autowired
    public void setMastersService(MastersService mastersService) {
        this.mastersService = mastersService;
    }

    @Autowired
    public void setServicesService(ServicesService servicesService) {
        this.servicesService = servicesService;
    }

    @Autowired
    public void setWorksService(WorksService worksService) {
        this.worksService = worksService;
    }
}
