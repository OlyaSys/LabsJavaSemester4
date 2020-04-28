import java.util.*;

class Cabinet {
    private List<Student> studentQueue;
    public int maxStudentsInCabinet = 10;

    Cabinet() {
        studentQueue = new ArrayList<>();
    }

    List<Student> getStudentQueue() {
        return studentQueue;
    }

    synchronized boolean add(Student student) {
        try {
            if (studentQueue.size() < maxStudentsInCabinet) {
                notifyAll();
                studentQueue.add(student);
                System.out.println(Thread.currentThread().getName() + student.toString());
                return true;
            } else {
                //System.out.println(studentQueue.size() + " > no place for a student in the cabinet: ");
                wait();
            }
        } catch (InterruptedException e) {
              e.printStackTrace();
        }
        return false;
    }

    synchronized Student get(Subject subjectName) {
        try {
            if (studentQueue.size() > 0) {
                notifyAll();
                for (Student student : studentQueue) {
                    if (student.getSubjectName() == subjectName) {
                        System.out.println(studentQueue.size() + " - 1 student left cabinet ");
                        studentQueue.remove(student);
                        return student;
                    }
                }
            } else if (StudentGenerator.work) {
                wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
