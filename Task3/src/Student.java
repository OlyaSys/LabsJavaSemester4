enum Subject {
    OOP,
    Math,
    Physics
}

public class Student {
    public String name;
    public int labsCount;
    public Subject subjectName;

    public Student(String name, int labsCount, Subject subjectName) {
        this.name = name;
        this.labsCount = labsCount;
        this.subjectName = subjectName;
    }

    public String getName() {
        return name;
    }

    public int getLabsCount() {
        return labsCount;
    }

    Subject getSubjectName() {
        return subjectName;
    }

    @Override
    public String toString() {
        return name + " " + subjectName + " " + labsCount;
    }
}
