package impact;

import java.util.ArrayList;

import geography.RegionData;
import sectors.HospitalData;

public class HealthcareImpact extends RegionData implements ImpactAnalyzer {

    private int hospitalDestroyed;
    private int untreatedPatients;
    private boolean status;
    private final ArrayList<HospitalData> hospitalsList;

    public HealthcareImpact(String region, String date) {
        super(region, date);
        this.hospitalDestroyed = 0;
        this.untreatedPatients = 0;
        this.status = true;
        hospitalsList= new ArrayList<>();
    }

    public void analyzeImpact() {
        if(hospitalDestroyed >= 10 || untreatedPatients >= 100)
            status = false;
        else
            status = true;
    }

    public void addHospital(HospitalData hospital){
        hospitalsList.add(hospital);
        hospitalDestroyed++;
    }

    public void add(int hospitals, int patients) {
        hospitalDestroyed += hospitals;
        untreatedPatients += patients;
    }

    public void update(int newHospitalState, int newUntreatedPatientsState) {
        hospitalDestroyed += newHospitalState;
        untreatedPatients += newUntreatedPatientsState;
    }

    public void displayStats() {
        System.out.println("War's Impact in the Educational Sector of "+ this.getRegionName() + ": ");
        System.out.println("Hospitals Destroyed: "+ this.hospitalDestroyed);
        System.out.println("Patients That aren't treated: "+ this.untreatedPatients);
        System.out.println("Hospitals' Status in this Region: " + this.destructionType());

    }

    public int getHospitalDestroyed() {
        return hospitalDestroyed;
    }
    public int getUntreatedPatients() {
        return untreatedPatients;
    }
    public boolean getStatus() {
        return status;
    }
    public ArrayList<HospitalData> getHospitalsDataList(){
        return hospitalsList;
    }

    //helper method
    private String destructionType() {
        return !this.status? "Completely Destroyed" : "Partially Destroyed";
    }
}
