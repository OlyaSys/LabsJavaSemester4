enum Subject {
    OOP,
    Math,
    Physics
}

public class Student {
    public int labsCount;
    public Subject subjectName;

    public Student(int labsCount, Subject subjectName) {
        this.labsCount = labsCount;
        this.subjectName = subjectName;
    }

    public int getLabsCount() {
        return labsCount;
    }

    Subject getSubjectName() {
        return subjectName;
    }

    @Override
    public String toString() {
        return "Subject = " + subjectName +
                ", labs = " + labsCount
                ;
    }
}
