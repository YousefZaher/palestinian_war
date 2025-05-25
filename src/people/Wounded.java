package people;
import sectors.WarStats;
import geography.*;

public class Wounded extends Person{
    private String injuryType;
    private boolean permanentDisability;
    private boolean hospitalized;
    private RegionData region;
    private WarStats warStats;
    public Wounded(String name, int age, boolean permanentDisability,boolean hospitalized ,RegionData region, WarStats warStats){
        super(name, age);
        this.permanentDisability = permanentDisability;
        this.hospitalized = hospitalized;
        this.region= region;
        this.warStats= warStats;
        warStats.add(this);
    }

    public void updateState(boolean hospitalized){
        this.hospitalized= hospitalized;
    }

    //override
    public void displayInfo(){
        super.displayInfo();
        System.out.println("Injury Type: " + injuryType);
        System.out.println("Has a Permanent Disablitiy: " + hasPermanentDisability());
        System.out.println("In the Hospital: " + isHospitalized());
        System.out.println("Region: " + region);
        System.out.println("War Stats: " + warStats);
    }

    public boolean getPermanentDisability() {
        return permanentDisability;
    }

    public void setPermanentDisability(boolean permanentDisability) {
        this.permanentDisability = permanentDisability;
    }

    public boolean getHospitalized() {
        return hospitalized;
    }

    public void setHospitalized(boolean hospitalized) {
        this.hospitalized = hospitalized;
    }

    public RegionData getRegion() {
        return region;
    }

    public void setRegion(RegionData region) {
        this.region = region;
    }

    public WarStats getWarStats() {
        return warStats;
    }

    public void setWarStats(WarStats warStats) {
        this.warStats = warStats;
    }


    //helpers
    public String isHospitalized() {
        return hospitalized? "Yes" : "No";
    }
    public String hasPermanentDisability() {
        return permanentDisability? "Yes" : "No";
    }
}
