import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        Cabinet cabinet = new Cabinet();
        StudentGenerator studentGenerator = new StudentGenerator(cabinet,20);

        Robots robot1 = new Robots(cabinet, Subject.OOP);
        Robots robot2 = new Robots(cabinet, Subject.Math);
        Robots robot3 = new Robots(cabinet, Subject.Physics);

        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        service.execute(studentGenerator);
        service.execute(robot1);
        service.execute(robot2);
        service.execute(robot3);

        service.shutdown();
    }
}
