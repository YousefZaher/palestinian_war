package people;
import sectors.WarStats;
import geography.*;

public class Martyr extends Person{
    private String dateOfDeath;
    private String cause;
    private RegionData region;
    private WarStats warStats;
    public Martyr(String name, int age,String dateOfDeath, String cause ,WarStats warStats){
        super(name, age);
        this.dateOfDeath= dateOfDeath;
        this.cause= cause;
        this.warStats= warStats;
        warStats.add(this);
    }

    public String getDateOfDeath() {
        return dateOfDeath;
    }
    
    public void setDateOfDeath(String dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }
    
    public String getCauseOfDeath() {
        return cause;
    }
    
    public void setCause(String cause) {
        this.cause = cause;
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
    

    //override
    public void displayInfo(){
        super.displayInfo();
        System.out.println("Date Of Death: " + dateOfDeath);
        System.out.println("Cause of Death: " + cause);
        System.out.println("Region: " + region);
        System.out.println("War Stats: " + warStats);
    }
}
