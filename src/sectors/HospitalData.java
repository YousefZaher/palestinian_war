package sectors;

import impact.HealthcareImpact;

public class HospitalData {
    private String name;
    private String status; 
    private int patients;
    public HospitalData(String n, String s, int p){
        name = n;
        status = s;
        patients = p;
    }
    public HospitalData(String n, String s, int p, HealthcareImpact region) {
        this(n,s,p);
        region.addHospital(this);
    }
    public String getName(){
        return name;
    }
    public String getStatus(){
        return status;
    }
    public int getPatients(){
        return patients;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public void setPatients(int patients) {
        this.patients = patients;
    }
    
}