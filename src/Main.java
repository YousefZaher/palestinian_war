import geography.RegionData;
import gui.*;

import javax.swing.*;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import geography.RegionData;

public class Main{
    public static void main(String[] args){
        ArrayList<RegionData> regions = new ArrayList<>();
        regions.add(new RegionData("Gaza", "09/2023"));
        regions.add(new RegionData("Gaza", "08/2023")); // same region, different date
        regions.add(new RegionData("Gaza", "07/2023")); // same region, different date
        regions.add(new RegionData("West Bank", "12/2023"));
        regions.add(new RegionData("West Bank", "11/2023")); // same region, different date
        regions.add(new RegionData("West Bank", "10/2023")); // same region, different date
        regions.add(new RegionData("East Jerusalem", "10/2023"));
        regions.add(new RegionData("East Jerusalem", "09/2023")); // same region, different date
        regions.add(new RegionData("Jenin", "11/2023"));
        regions.add(new RegionData("Tulkarm", "12/2023"));

        MainFrameZ frame = new MainFrameZ(regions);
        frame.setSize(800, 600);
        frame.setTitle("Palestinian War Analyzer - Test Run");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
