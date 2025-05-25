package geography;

import java.util.ArrayList;

import sectors.WarStats;

public class RegionData {
    private String date;
    private String region;
    private WarStats warStats;

    public RegionData(String region, String date, WarStats warStats) {
        this.region = region.trim().toLowerCase();
        this.date = date;
        this.warStats = warStats;
    }
    public RegionData(String region, String date) {
        this(region, date , new WarStats());
    }

    public RegionData(String region){
        this("","mm/yy",new WarStats());
    }

    public void setRegion(String region) {
        if (region == null || region.trim().isEmpty()) {
            throw new IllegalArgumentException("Region cannot be null or empty!");
        }
        this.region = region.trim().toLowerCase();
    }

    public void setDate(String date) {
        if (date == null || !date.matches("\\d{2}/\\d{4}")) { // Example pattern: mm/yy
            throw new IllegalArgumentException("Date must be in format mm/yy");
        }
        this.date = date;
    }

    public WarStats getWarStats(){
        return warStats;
    }

    public String getDate() {
        return date;
    }
    public String getRegionName() {
        return region;
    }

    public void updateState( String region, String date, WarStats warStats, ArrayList<RegionData> regions) {
        this.date = date;
        this.region = region;
        this.warStats = warStats;
    }

    public String toString() {
        return region + "-" + date;
    }
    public boolean equalsRegionData(String regionDateKey){
        return this.toString().equals(regionDateKey);
    }
}