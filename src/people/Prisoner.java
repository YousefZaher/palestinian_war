package people;
import sectors.WarStats;
import geography.*;

public class Prisoner extends Person{
    private int yearsInPrison;
    private boolean released;
    private RegionData region;
    private WarStats warStats;
    public Prisoner(String name, int age, int yearsInPrison ,boolean released ,RegionData region, WarStats warStats){
        super(name, age);
        this.yearsInPrison= yearsInPrison;
        this.released = released;
        this.region= region;
        this.warStats= warStats;
        warStats.add(this);
    }

    public void updateState(boolean released){
        this.released= released;
    }

    //override
    public void displayInfo(){
        super.displayInfo();
        System.out.println("Years Of Prisoning: " + yearsInPrison);
        System.out.println("Has been Released: " + hasReleased());
        System.out.println("Region: " + region);
        System.out.println("War Stats: " + warStats);
    }


    public int getYearsInPrison() {
        return yearsInPrison;
    }

    public void setYearsInPrison(int yearsInPrison) {
        this.yearsInPrison = yearsInPrison;
    }

    public boolean isReleased() {
        return released;
    }

    public void setReleased(boolean released) {
        this.released = released;
    }


    //helpers
    private String hasReleased() {
        return released? "Yes" : "No";
    }
}
