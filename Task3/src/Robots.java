public class Robots implements Runnable {
    private Cabinet cabinet;
    private Subject subject;

    public Robots(Cabinet cabinet, Subject subject) {
        this.cabinet = cabinet;
        this.subject = subject;
    }

    @Override
    public void run() {
        int countHandled = 0;
        while (StudentGenerator.work || !cabinet.getStudentQueue().isEmpty()) {
            //System.out.println(Thread.currentThread().getName());
            Student student = cabinet.get(subject);
            if (student != null) {
                while (student.labsCount > 0) {
                    student.labsCount -= 5;
                    System.out.println(Thread.currentThread().getName() + " " + student.toString());
                }
                countHandled++;
            }
        }
        System.out.println("Handled: " + subject + " " + countHandled);
    }
}
