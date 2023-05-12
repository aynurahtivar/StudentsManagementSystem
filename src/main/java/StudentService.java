import java.util.List;
import java.util.Scanner;

public class StudentService {

    Scanner scan = new Scanner(System.in);
    StudentRepository repository = new StudentRepository();


    public void createStudentTable() {
        repository.createTable();
    }

    public void saveStudent() {
        System.out.print("\t\tNAME : ");
        String name = scan.nextLine().trim();
        System.out.print("\t\tLASTNAME : ");
        String lastname = scan.nextLine().trim();
        System.out.print("\t\tCITY : ");
        String city = scan.nextLine().trim();
        System.out.print("\t\tAGE : ");
        int age = scan.nextInt();
        scan.nextLine();
        Student newStudent = new Student(name, lastname, city, age);
        repository.save(newStudent);
    }

    public void getAllStudents() {
        repository.findAll();
    }


    public Student getStudentyId(int id) {

        Student student = repository.findById(id);
        return student;
    }

    public void displayStudent(int id) {
        Student student = getStudentyId(id);
        if (student == null) {
            System.out.println("\t\tStudent does not exist by id" + id);
        } else {
            System.out.println("\n\t\tStudent Info : " + student);
        }
    }

    public void updateStudent(int id) {
        Student foundStudent = getStudentyId(id);
        if (foundStudent == null) {
            System.out.println("\t\tStudent does not exist by id" + id);
        } else {
            System.out.print("\t\tNAME : ");
            String name = scan.nextLine().trim();
            System.out.print("\t\tLASTNAME : ");
            String lastname = scan.nextLine().trim();
            System.out.print("\t\tCITY : ");
            String city = scan.nextLine().trim();
            System.out.print("\t\tAGE : ");
            int age = scan.nextInt();
            scan.nextLine();
            foundStudent.setName(name);
            foundStudent.setLastname(lastname);
            foundStudent.setCity(city);
            foundStudent.setAge(age);
            repository.update(foundStudent);


        }
    }

    public void deleteStudent(int id) {
        Student foundStudent;
        foundStudent = getStudentyId(id);
        repository.delete(id);
        System.out.println("\n\t\tDeleted " + foundStudent);

    }


    public void filterNameOrLastname() {
        System.out.print("\t\tEnter name or lastname : ");
        String nameOrLastname = scan.nextLine();

        List<Student> studentList = repository.filter(nameOrLastname);
        if (studentList.size() == 0) {
            System.out.println("\t\tStudent not found\n");
        } else {
            System.out.println("\n\t\tStudent / Students....");
            System.out.printf("\t\t%-5s %-15s %-15s %-10s %-4s\n", "ID", "NAME", "LASTNAME", "CITY", "AGE");
            for (Student w : studentList) {
                System.out.printf("\t\t%-5s %-15s %-15s %-10s %-4s\n", w.getId(), w.getName(), w.getLastname(), w.getCity(), w.getAge());
            }


        }
    }
}