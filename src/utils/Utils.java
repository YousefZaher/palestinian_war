package utils;

import geography.RegionData;
import impact.HealthcareImpact;
import sectors.WarStats;

public class Utils {
    public static int getMartyrForRegion(RegionData region) {
        WarStats warStats = region.getWarStats();
        return warStats.getMartyrs();
    }
    public static int getWoundedForRegion(RegionData region) {
        WarStats warStats = region.getWarStats();
        return warStats.getWounded();
    }
    public static int getPrisonerForRegion(RegionData region) {
        WarStats warStats = region.getWarStats();
        return warStats.getPrisoners();
    }
}
