package sectors;

public class SchoolData {
    private String name;
    private String status; 
    private int students;

    public SchoolData(String n, String s, int st) {
        this.name = n; 
        this.status = s; 
        this.students = st;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public int getStudents() {
        return students;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStudents(int students) {
        this.students = students;
    }
}
