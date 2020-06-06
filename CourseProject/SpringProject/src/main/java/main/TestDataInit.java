package main;

import main.entity.*;
import main.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;

@Component
public class TestDataInit implements CommandLineRunner {

    @Autowired
    CarsRepository carsRep;

    @Autowired
    UserRepository userRep;

    @Autowired
    MastersRepository mastersRep;

    @Autowired
    ServicesRepository servicesRep;

    @Autowired
    WorksRepository worksRep;

    @Autowired
    PasswordEncoder pwdEncoder;

    @Override
    public void run(String... args) throws Exception {
        Masters master = new Masters("Sysoeva Olya");
        Masters master1 = new Masters("Sysoev Ivan");
        Services service = new Services("Change tire", 1500, 5000);

        Cars car = new Cars("Number1", "Black", "Volvo", 1);
        Cars car2 = new Cars("Number2", "Green", "Nissan", 1);
        Cars car3 = new Cars("Number3", "Black", "Lada", 0);

        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String year = String.valueOf(localDate.getYear());
        String month = String.valueOf(localDate.getMonthValue());
        String day = String.valueOf(localDate.getDayOfMonth());

        String dateWork = day + "_" + month + "_" + year;
        Works work = new Works(dateWork, master, car, service);

        carsRep.save(car);
        mastersRep.save(master);
        servicesRep.save(service);
        worksRep.save(work);

        carsRep.save(car2);
        carsRep.save(car3);
        mastersRep.save(master1);

        userRep.save(new User("user", pwdEncoder.encode("pwd"), Collections.singletonList("ROLE_USER")));
        userRep.save(new User("admin", pwdEncoder.encode("apwd"), Collections.singletonList("ROLE_ADMIN")));
    }
}
