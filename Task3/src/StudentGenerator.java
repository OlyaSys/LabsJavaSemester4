import java.util.*;

public class StudentGenerator implements Runnable {
    private Cabinet queue;
    private int countGenerator;
    static boolean work = true;

    public StudentGenerator(Cabinet queue, int countGenerator) {
        this.queue = queue;
        this.countGenerator = countGenerator;
    }

    private int createLabsCount() {
        List<Integer> labs = Arrays.asList(10, 20, 100);
        Random rand = new Random();
        return (labs.get(rand.nextInt(labs.size())));
    }

    private Subject createSubjectName() {
        //Subject subject;
        Random rand = new Random();
        return (Subject.values()[rand.nextInt(Subject.values().length)]);
    }

    @Override
    public void run() {
        int count = 0;
        while (count < countGenerator) {
            Thread.currentThread().setName("Generator: ");
            Student newStudent = new Student(createLabsCount(), createSubjectName());
            if (queue.add(newStudent))
                count++;
        }
        work = false;
        System.out.println("End of generate");
    }
}
