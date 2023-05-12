import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StudentRepository {


    private Connection connection;
    private Statement st;
    private PreparedStatement pst;

    private void setConnection() {
        try {
            this.connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Recap", "myRecap", "aynur");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void setStatement() {

        try {
            this.st = connection.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void setPreparedStatement(String query) {
        try {
            this.pst = connection.prepareStatement(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void createTable() {
        setConnection();
        setStatement();

        try {
            st.execute("CREATE TABLE IF NOT EXISTS t_student(" +
                    "id SERIAL UNIQUE," +
                    "name VARCHAR(50) NOT NULL CHECK(LENGTH(name)>0)," +//empty ""
                    "lastname VARCHAR(50) NOT NULL CHECK(LENGTH(lastname)>0)," +
                    "city VARCHAR(50) NOT NULL CHECK(LENGTH(city)>0)," +
                    "age INT NOT NULL CHECK(age>0)" +
                    ")");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                st.close();
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }


    }

    public void save(Student student) {
        String query = " INSERT INTO t_student(name,lastname,city,age) VALUES(?,?,?,?)";
        setConnection();
        setPreparedStatement(query);

        try {
            pst.setString(1, student.getName());
            pst.setString(2, student.getLastname());
            pst.setString(3, student.getCity());
            pst.setInt(4, student.getAge());
            pst.executeUpdate();
            System.out.println("\t\tSaved succesfully...");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                st.close();
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }


    }

    public void findAll() {
        setConnection();
        setStatement();
        String query = "SELECT * FROM t_student";
        System.out.println("\n\n\t-------------> ALL STUDENTS <-------------\n");
        try {
            ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                System.out.printf("\t\t%-6s", resultSet.getInt("id") + " ");
                System.out.printf("\t\t%-20s", resultSet.getString("name") + " ");
                System.out.printf("\t\t%-20s", resultSet.getString("lastname") + " ");
                System.out.printf("\t\t%-20s", resultSet.getString("city") + " ");
                System.out.printf("\t\t%-6s", resultSet.getInt("age") + " ");
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                st.close();
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }


    }

    public Student findById(int id) {
        Student student = null;
        setConnection();
        String query = "SELECT * FROM t_student WHERE id=?";
        setPreparedStatement(query);

        try {
            pst.setInt(1, id);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()) {
                student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setName(resultSet.getString("name"));
                student.setLastname(resultSet.getString("lastname"));
                student.setCity(resultSet.getString("city"));
                student.setAge(resultSet.getInt("age"));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                pst.close();
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return student;
    }


    public void update(Student foundStudent) {
        setConnection();
        String query = "UPDATE t_student SET name=?,lastname=?,city=?,age=? WHERE id=?";
        setPreparedStatement(query);

        try {
            pst.setString(1, foundStudent.getName());
            pst.setString(2, foundStudent.getLastname());
            pst.setString(3, foundStudent.getCity());
            pst.setInt(4, foundStudent.getAge());
            pst.setInt(5, foundStudent.getId());
            pst.executeUpdate();
            if (pst.executeUpdate() > 0) {
                System.out.println("\t\tUpdated Successfully");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                pst.close();
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }
    }


    public void delete(int id) {
        setConnection();
        String query = "DELETE FROM t_student WHERE id=?";
        setPreparedStatement(query);
        try {
            pst.setInt(1, id);
            int deleted = pst.executeUpdate();
            if (deleted > 0) {
                System.out.println("\t\tStudent is deleted successfully by id " + id);
            } else {
                System.out.println("\t\tStudent could not found by id " + id);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                pst.close();
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    public List<Student> filter(String nameOrLastname) {
        setConnection();

        List<Student> studentList = new ArrayList<>();
        String query = "SELECT * FROM t_student WHERE name ILIKE ? OR lastname ILIKE ?";
        setPreparedStatement(query);

        try {
            pst.setString(1, "%" + nameOrLastname + "%");
            pst.setString(2, "%" + nameOrLastname + "%");
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setName(resultSet.getString("name"));
                student.setLastname(resultSet.getString("lastname"));
                student.setCity(resultSet.getString("city"));
                student.setAge(resultSet.getInt("age"));
                studentList.add(student);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                pst.close();
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return studentList;
    }
}
