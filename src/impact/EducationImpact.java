package impact;

import java.util.ArrayList;
import sectors.SchoolData;
import geography.RegionData;

public class EducationImpact extends RegionData implements ImpactAnalyzer {

    private int schoolDestroyed;
    private int studentDislaced;
    private boolean status;
    public ArrayList<SchoolData> schoolsList;

    public EducationImpact(String region, String date) {
        super(region,date);
        this.schoolDestroyed = 0;
        this.studentDislaced = 0;
        this.status = true;
        schoolsList= new ArrayList<>();
    }

    public EducationImpact(String regionDate) {
        String[] parameters= regionDate.split("-");
        super(parameters[0],parameters[1]);
        this.schoolDestroyed = 0;
        this.studentDislaced = 0;
        this.status = true;
        schoolsList= new ArrayList<>();
    }

    public void analyzeImpact() {
        if(schoolDestroyed >= 10 || studentDislaced >= 100)
            status = false;
        else
            status = true;
    }

    public void add(int hospitals, int patients) {
        schoolDestroyed += hospitals;
        studentDislaced += patients;
    }

    public void update(int newHospitalState, int newstudentDislacedState) {
        schoolDestroyed += newHospitalState;
        studentDislaced += newstudentDislacedState;
    }

    public void display() {
        System.out.println("War's Impact in the Educational Sector of "+ this.getRegionName() + ": ");
        System.out.println("Schools Destroyed: "+ this.schoolDestroyed);
        System.out.println("Student Dislaced: "+ this.studentDislaced);
        System.out.println("Schools' Status in this Region: " + this.destructionType());
    }

    public boolean getStatus() {
        return status;
    }
    public int getSchoolDestroyed() {
        return schoolDestroyed;
    }
    public int getStudentDisplaced() {
        return studentDislaced;
    }
    public ArrayList<SchoolData> getSchoolsList() {
        return schoolsList;
    }
    //helper method
    private String destructionType() {
        return !this.status? "Completely Destroyed" : "Partially Destroyed";
    }
}
