import geography.RegionData;
import gui.*;

import javax.swing.*;


import java.util.ArrayList;

public class Main{
    public static void main(String[] args){
        ArrayList<RegionData> regions = new ArrayList<>();
        regions.add(new RegionData("Deir Yassin", "04/1948")); // مجزرة دير ياسين
        regions.add(new RegionData("Lydda", "07/1948")); // مجزرة اللد
        regions.add(new RegionData("Jaffa", "05/1948")); // تهجير يافا
        regions.add(new RegionData("Haifa", "04/1948")); // احتلال حيفا وتهجير سكانها
        regions.add(new RegionData("Gaza", "10/1956")); // مجزرة غزة 1956
        regions.add(new RegionData("Khan Yunis", "11/1956")); // مجزرة خان يونس
        regions.add(new RegionData("Qibya", "10/1953")); // مجزرة قبية
        regions.add(new RegionData("Sabra and Shatila", "09/1982")); // مجزرة صبرا وشاتيلا
        regions.add(new RegionData("Jenin", "04/2002")); // اجتياح جنين ومجزرة المخيم
        regions.add(new RegionData("Nablus", "04/2002")); // اجتياح نابلس
        regions.add(new RegionData("Hebron", "02/1994")); // مجزرة الحرم الإبراهيمي
        regions.add(new RegionData("Beit Hanoun", "11/2006")); // مجزرة بيت حانون
        regions.add(new RegionData("Gaza", "12/2008")); // حرب غزة 2008-2009
        regions.add(new RegionData("Gaza", "07/2014")); // حرب غزة 2014
        regions.add(new RegionData("Gaza", "05/2021")); // العدوان على غزة 2021
        regions.add(new RegionData("Gaza", "10/2023")); // العدوان على غزة 2023
        regions.add(new RegionData("Jenin", "01/2023")); // اقتحام ومجزرة جنين
        regions.add(new RegionData("Gaza", "12/2008")); // قصف جامعة غزة الإسلامية
        regions.add(new RegionData("Beit Lahia", "07/2006")); // مجزرة بيت لاهيا
        regions.add(new RegionData("Shuja'iyya", "07/2014")); // مجزرة حي الشجاعية
        regions.add(new RegionData("Rafah", "08/2014")); // مجزرة رفح "الجمعة السوداء"
        regions.add(new RegionData("Bureij", "12/2008")); // مجزرة البريج
        regions.add(new RegionData("Tulkarm", "03/2002")); // اجتياح طولكرم
        regions.add(new RegionData("Balata", "04/2002")); // اجتياح مخيم بلاطة
        regions.add(new RegionData("Qalqilya", "06/2002")); // اجتياح قلقيلية
        regions.add(new RegionData("East Jerusalem", "10/2015")); // اشتباكات الأقصى
        regions.add(new RegionData("Gaza", "03/2008")); // محرقة غزة
        regions.add(new RegionData("Jabalia", "10/2023")); // مجزرة مخيم جباليا
        regions.add(new RegionData("Beit Hanoun", "07/2014")); // قصف مدارس بيت حانون
        regions.add(new RegionData("Nuseirat", "10/2023")); // مجزرة النصيرات
        regions.add(new RegionData("Maghazi", "11/2023")); // قصف مخيم المغازي
        regions.add(new RegionData("Rimal", "10/2023")); // تدمير حي الرمال بغزة
        regions.add(new RegionData("Bureij", "11/2023")); // قصف سوق البريج
        regions.add(new RegionData("Deir al-Balah", "12/2023")); // مجزرة دير البلح
        regions.add(new RegionData("Gaza", "01/2009")); // قصف مدارس الأونروا
        regions.add(new RegionData("Gaza", "05/2018")); // مسيرات العودة ومجزرة الحدود
        regions.add(new RegionData("Hebron", "10/2015")); // استهداف البلدة القديمة
        regions.add(new RegionData("Nablus", "10/2022")); // حصار ومجزرة نابلس
        regions.add(new RegionData("Jenin", "07/2023")); // قصف مخيم جنين بطائرات الاحتلال
        regions.add(new RegionData("Khan Yunis", "12/2023")); // اجتياح خان يونس
        regions.add(new RegionData("Shuja'iyya", "01/2009")); // قصف حي الشجاعية
        regions.add(new RegionData("Rafah", "01/2009")); // قصف أنفاق رفح
        regions.add(new RegionData("Beit Hanoun", "11/2023")); // قصف مستشفى بيت حانون
        regions.add(new RegionData("Jabalia", "11/2023")); // تدمير المنازل فوق رؤوس ساكنيها
        regions.add(new RegionData("East Jerusalem", "05/2021")); // اقتحام المسجد الأقصى
        regions.add(new RegionData("Jenin", "06/2023")); // عملية عسكرية واسعة في جنين
        regions.add(new RegionData("Gaza", "07/2019")); // قصف مواقع مدنية
        regions.add(new RegionData("Gaza", "11/2012")); // اغتيال أحمد الجعبري وبداية العدوان
        
        

        MainFrameZ frame = new MainFrameZ(regions);
        frame.setSize(800, 600);
        frame.setTitle("Palestinian War Analyzer - Test Run");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
