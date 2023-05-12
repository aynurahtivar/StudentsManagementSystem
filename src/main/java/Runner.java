import java.util.Scanner;

public class Runner {
    /*
Proje:Student Management System
     -1-Herhangi bir eğitim kurumu için öğrenci yönetim uygulaması geliştiriniz.
     -2-Kullanıcı
               -C:öğrenci kayıt
               -R:öğrenci veya öğrencileri görüntüleme
               -U:id ile öğrenci güncelleme
               -D:id ile öğrenci silme
       işlemlerini yapabilmeli.
     -3-öğrenci:id,name,lastname,city,age özelliklerine sahiptir.

     ÖDEVV:öğrenci silme işleminden sonra silinen öğrencinin bilgilerini gösterelim.
     ÖDEVV:öğrenci ekleme işleminden sonra eklenen öğrencinin bilgilerini gösterelim.
     ÖDEVV: -R: ad-soyad ile öğrenci filtreleme işlemlerini yapabilmeli.
 */
    public static void main(String[] args) {
        start();
    }


    public static void start() {
        Scanner scan = new Scanner(System.in);

        StudentService service = new StudentService();
        service.createStudentTable();

        int select;
        int id;
        do {
            System.out.println("\n\n\t-------------> WELCOME TO STUDENT MANAGEMENT SYSTEM <-------------\n");
            System.out.println("\t\t1 : Save student");
            System.out.println("\t\t2 : Show a student");
            System.out.println("\t\t3 : Show all students");
            System.out.println("\t\t4 : Update student");
            System.out.println("\t\t5 : Delete student");
            System.out.println("\t\t6 : Filter name or lastname");
            System.out.println("\t\t0 : EXIT");
            System.out.print("\t\tSELECT : ");

            select = scan.nextInt();
            switch (select) {
                case 1://save student
                    service.saveStudent();
                    service.getAllStudents();
                    break;
                case 2://Show a student
                    id = getId(scan);
                    service.displayStudent(id);
                    break;
                case 3://Show all students
                    service.getAllStudents();
                    break;
                case 4://Update student
                    id = getId(scan);
                    service.updateStudent(id);
                    break;
                case 5://Delete student
                    id = getId(scan);
                    service.deleteStudent(id);
                    break;
                case 6://Student filter by name-surname
                    service.filterNameOrLastname();
                    break;
                case 0:
                    System.out.println("\t\tSee you..");
                    break;
                default:
                    System.out.println("\t\tInvalid Selection! Try Again");
            }
        } while (select != 0);
    }

    public static int getId(Scanner scan) {
        System.out.print("\t\tSTUDENT ID : ");
        return scan.nextInt();
    }


}
