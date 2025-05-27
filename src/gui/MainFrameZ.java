package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import geography.*;
import impact.*;
import people.*;
import sectors.*;
import static utils.Utils.*;

public class MainFrameZ extends JFrame {

    private final JComboBox<String> regionComboBox;
    private final JComboBox<String> dateComboBox;
    private final DefaultTableModel regionsTableModel;
    private final JTable regionsTable;
    private final JButton addRegionButton, deleteRegionButton, editRegionButton;


    private final JButton healthImpactButton, educationImpactButton;
    private final JButton martyrButton, woundedButton, prisonerButton;
    private final JButton borderButton, statsButton;

    private final ArrayList<RegionData> regionsList;
    private final Map<RegionData, HealthcareImpact> healthcareImpactsMap= new HashMap<>();
    private final Map<RegionData, EducationImpact> educationalImpactsMap= new HashMap<>();


    public MainFrameZ(final ArrayList<RegionData> initialRegions){
        
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        super("Palestinian War Analyzer");
        JPanel header= new JPanel();
        header.setBackground(Color.RED);
        JLabel headerTitle= new JLabel("Palestinian War Analyzer");
        headerTitle.setFont(new Font("Arial", Font.BOLD, 24));
        headerTitle.setForeground(Color.WHITE);
        header.add(headerTitle);
        add(header,BorderLayout.NORTH);

        regionsList= initialRegions;


        JPanel leftSidebar = new JPanel();
        leftSidebar.setLayout(new GridLayout(0, 1));

        JPanel regionSelectPanel = new JPanel(new GridLayout(0, 1, 5,5));
        leftSidebar.setBorder(BorderFactory.createTitledBorder("Region Selection"));
        regionSelectPanel.add(new JLabel("Region:"));
        regionComboBox = new JComboBox<>();
        regionComboBox.addItem("Select a region");
        regionSelectPanel.add(regionComboBox);
        updateRegionComboBox();

        regionSelectPanel.add(new JLabel("Date:"));
        dateComboBox = new JComboBox<>();
        dateComboBox.addItem("mm/yy");
        regionSelectPanel.add(dateComboBox);

        regionComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e){
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedRegion = (String) regionComboBox.getSelectedItem();
                    if (selectedRegion != null)
                        changeDateComboBoxState(selectedRegion);
                }
            }
        });

        leftSidebar.add(regionSelectPanel);


        JPanel regionManagePanel = new JPanel(new GridLayout(1, 3, 5, 5));
        addRegionButton = new JButton("Add");
        editRegionButton = new JButton("Edit");
        deleteRegionButton = new JButton("Delete");
        regionManagePanel.add(addRegionButton);
        regionManagePanel.add(editRegionButton);
        regionManagePanel.add(deleteRegionButton);
        leftSidebar.add(Box.createVerticalStrut(0));
        leftSidebar.add(regionManagePanel);

        add(leftSidebar, BorderLayout.WEST);

        String[] columns = {"Region", "Siege Date"};
        regionsTableModel = new DefaultTableModel(columns, 0){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        regionsTable = new JTable(regionsTableModel);
        refreshRegionsTable();
        JScrollPane tableScroll = new JScrollPane(regionsTable);
        add(tableScroll, BorderLayout.CENTER);



        JPanel rightSidebar = new JPanel();
        rightSidebar.setLayout(new GridLayout(0, 1, 10, 10));
        rightSidebar.setBorder(BorderFactory.createTitledBorder("Actions"));

        healthImpactButton = new JButton("Health Care Impact");
        educationImpactButton = new JButton("Educational Impact");
        martyrButton = new JButton("Martyr Details");
        woundedButton = new JButton("Wounded Details");
        prisonerButton = new JButton("Prisoner Details");
        borderButton = new JButton("Border Status");
        statsButton = new JButton("Show Statistics");

        rightSidebar.add(healthImpactButton);
        rightSidebar.add(educationImpactButton);
        rightSidebar.add(martyrButton);
        rightSidebar.add(woundedButton);
        rightSidebar.add(prisonerButton);
        rightSidebar.add(borderButton);
        rightSidebar.add(statsButton);

        add(rightSidebar,BorderLayout.EAST);

        RegionManagementButtonsHandler regionBtnsHandler= new RegionManagementButtonsHandler();
        addRegionButton.addActionListener(regionBtnsHandler);
        deleteRegionButton.addActionListener(regionBtnsHandler);
        editRegionButton.addActionListener(regionBtnsHandler);

        ActionButtonsHandler actionBtnsHandler= new ActionButtonsHandler();
        healthImpactButton.addActionListener(actionBtnsHandler);
        educationImpactButton.addActionListener(actionBtnsHandler);
        martyrButton.addActionListener(actionBtnsHandler);
        woundedButton.addActionListener(actionBtnsHandler);
        prisonerButton.addActionListener(actionBtnsHandler);
        borderButton.addActionListener(actionBtnsHandler);
        statsButton.addActionListener(actionBtnsHandler);
    }

    private void showHealthCare(){
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        RegionData region = getSelectedRegionObj();
        if(region == null){
            JOptionPane.showMessageDialog(this, "Please select a region!");
            return;
        }
        HealthcareImpact healthcareImpact= healthcareImpactsMap.computeIfAbsent(region, k -> new HealthcareImpact(k.getRegionName(),k.getDate()));
        ArrayList<HospitalData> hospitalsList= healthcareImpact.getHospitalsDataList();


        String[] cols = {"Hospital Name", "Destruction Status", "Patients"};
        DefaultTableModel model = new DefaultTableModel(cols, 0){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (HospitalData h : hospitalsList) {
            model.addRow(new Object[]{h.getName(), h.getStatus(), h.getPatients()});
        }

        JTable table = new JTable(model);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);


        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField nameField = new JTextField(10);
        JComboBox<String> statusField = new JComboBox<>(new String[]{"Partially", "Completely"});
        JTextField patientsField = new JTextField(5);
        JButton addBtn = new JButton("Add");

        addBtn.addActionListener(e -> {
            System.out.println("Add button clicked");

            String name = nameField.getText().trim();
            String status = (String) statusField.getSelectedItem();
            int patients;

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter hospital name.");
                return;
            }

            try {
                patients = Integer.parseInt(patientsField.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Enter valid patient count.");
                return;
            }

            HospitalData newHospital = new HospitalData(name, status, patients);
            hospitalsList.add(newHospital);
            model.addRow(new Object[]{name, status, patients});


                healthcareImpact.update(1, patients);
                healthcareImpact.analyzeImpact();



            nameField.setText("");
            patientsField.setText("");
        });

        controls.add(new JLabel("Name:"));
        controls.add(nameField);
        controls.add(new JLabel("Status:"));
        controls.add(statusField);
        controls.add(new JLabel("Patients:"));
        controls.add(patientsField);
        controls.add(addBtn);

        JButton removeBtn = new JButton("Remove Selected");
        removeBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                String name = (String) model.getValueAt(row, 0);
                String status = (String) model.getValueAt(row, 1);
                int patients = (Integer) model.getValueAt(row, 2);

                hospitalsList.removeIf(h ->
                        h.getName().equals(name) &&
                                h.getStatus().equals(status) &&
                                h.getPatients() == patients
                );

                model.removeRow(row);


                healthcareImpact.analyzeImpact();

            }
        });

        controls.add(removeBtn);
        panel.add(controls, BorderLayout.SOUTH);

        JFrame subFrame = new JFrame("Health Care Impact | " + region);
        subFrame.add(panel);
        subFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        subFrame.setSize(600, 400);
        subFrame.setLocationRelativeTo(this);
        subFrame.setVisible(true);

    }

    private void showEducationImpact(){
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        RegionData region = getSelectedRegionObj();
        if(region == null){
            JOptionPane.showMessageDialog(this, "Please select a region!");
            return;
        }
        EducationImpact educationImpact= educationalImpactsMap.computeIfAbsent(region, k -> new EducationImpact(k.getRegionName(),k.getDate()));
        ArrayList<SchoolData> schoolsList= educationImpact.getSchoolsList();


        String[] cols = {"School Name", "Destruction Status", "Students"};
        DefaultTableModel model = new DefaultTableModel(cols, 0){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (SchoolData school : schoolsList) {
            model.addRow(new Object[]{school.getName(), school.getStatus(), school.getStudents()});
        }

        JTable table = new JTable(model);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);


        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField nameField = new JTextField(10);
        JComboBox<String> statusField = new JComboBox<>(new String[]{"Partially", "Completely"});
        JTextField studentsField = new JTextField(5);
        JButton addBtn = new JButton("Add");

        addBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String status = (String) statusField.getSelectedItem();
            int students;

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter school name.");
                return;
            }

            try {
                students = Integer.parseInt(studentsField.getText().trim());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Enter valid patient count.");
                return;
            }
            SchoolData newSchool = new SchoolData(name, status, students);
            schoolsList.add(newSchool);
            model.addRow(new Object[]{name, status, students});


                educationImpact.update(1, students);
                educationImpact.analyzeImpact();



            nameField.setText("");
            studentsField.setText("");
        });

        controls.add(new JLabel("Name:"));
        controls.add(nameField);
        controls.add(new JLabel("Status:"));
        controls.add(statusField);
        controls.add(new JLabel("Students:"));
        controls.add(studentsField);
        controls.add(addBtn);

        JButton removeBtn = new JButton("Remove Selected");
        removeBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                String name = (String) model.getValueAt(row, 0);
                String status = (String) model.getValueAt(row, 1);
                int students = (Integer) model.getValueAt(row, 2);

                schoolsList.removeIf(school ->
                                school.getName().equals(name) &&
                                school.getStatus().equals(status) &&
                                school.getStudents() == students
                );

                model.removeRow(row);


                educationImpact.analyzeImpact();

            }
        });

        controls.add(removeBtn);
        panel.add(controls, BorderLayout.SOUTH);

        JFrame subFrame = new JFrame("Eductaional Impact | " + region);
        subFrame.add(panel);
        subFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        subFrame.setSize(650, 450);
        subFrame.setLocationRelativeTo(this);
        subFrame.setVisible(true);
}

private void showMartyrsFrame(){
    JPanel panel = new JPanel(new BorderLayout(5, 5));
    RegionData region = getSelectedRegionObj();
    if(region == null){
        JOptionPane.showMessageDialog(this, "Please select a region!");
        return;
    }
    WarStats warStats = region.getWarStats();
    ArrayList<Martyr> martyrsList= warStats.getMartyrsList();


    String[] cols = {"Martyr Name", "Martyr Age" ,"date of death", "cause of death"};
    DefaultTableModel model = new DefaultTableModel(cols, 0){
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    for (Martyr m : martyrsList) {
        model.addRow(new Object[]{m.getName(), m.getAge(), m.getDateOfDeath(), m.getCauseOfDeath()});
    }

    JTable table = new JTable(model);
    panel.add(new JScrollPane(table), BorderLayout.CENTER);


    JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JTextField nameField = new JTextField(10);
    JTextField ageField = new JTextField(2);
    JTextField monthField = new JTextField(2);
    JTextField yearField = new JTextField(4);
    JTextField causeOfDeathField = new JTextField(15);
    JButton addBtn = new JButton("Add");


    addBtn.addActionListener(e -> {
        String name = nameField.getText().trim();
        String causeOfDeath = causeOfDeathField.getText().trim();
        int age,mth,year;

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter Martyr name.");
            return;
        }

        try {
            age= Integer.parseInt(ageField.getText().trim());
            mth= Integer.parseInt(monthField.getText().trim());
            year= Integer.parseInt(yearField.getText().trim());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid count.");
            return;
        }
        String dateOfDeath = mth+"/"+year;
        Martyr newMartyr = new Martyr(name, age, dateOfDeath, causeOfDeath, warStats);
        martyrsList.add(newMartyr);
        model.addRow(new Object[]{name, age, dateOfDeath, causeOfDeath});


        nameField.setText("");
        ageField.setText("");
        monthField.setText("");
        yearField.setText("");
        causeOfDeathField.setText("");
    });

    controls.add(new JLabel("Name:"));
    controls.add(nameField);
    controls.add(new JLabel("Age:"));
    controls.add(ageField);
    controls.add(new JLabel("Date of Death:"));
    controls.add(monthField);
    controls.add(new JLabel("/"));
    controls.add(yearField);
    controls.add(new JLabel("Cause of Death:"));
    controls.add(causeOfDeathField);
    controls.add(addBtn);

    panel.add(controls, BorderLayout.SOUTH);

    JFrame subFrame = new JFrame("Martyrs List | " + region);
    subFrame.add(panel);
    subFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    subFrame.setSize(750, 450);
    subFrame.setLocationRelativeTo(this);
    subFrame.setVisible(true);
}

    private void showWoundedFrame() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        RegionData region = getSelectedRegionObj();
        if (region == null) {
            JOptionPane.showMessageDialog(this, "Please select a region!");
            return;
        }
        WarStats warStats = region.getWarStats();
        ArrayList<Wounded> woundedList = warStats.getWoundedList();

        String[] cols = {"Name", "Age", "Permanent Disability", "Hospitalized"};
        DefaultTableModel model = new DefaultTableModel(cols, 0){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (Wounded w : woundedList) {
            model.addRow(new Object[]{
                    w.getName(),
                    w.getAge(),
                    w.hasPermanentDisability(),
                    w.isHospitalized()
            });
        }

        JTable table = new JTable(model);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);


        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField nameField = new JTextField(10);
        JTextField ageField = new JTextField(2);
        JCheckBox permDisabilityCheck = new JCheckBox("Permanent Disability");
        JCheckBox hospitalizedCheck = new JCheckBox("Hospitalized");
        JButton addBtn = new JButton("Add");

        addBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            int age;
            boolean permDisab = permDisabilityCheck.isSelected();
            boolean hospitalized = hospitalizedCheck.isSelected();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter wounded person's name.");
                return;
            }
            try {
                age = Integer.parseInt(ageField.getText().trim());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid age.");
                return;
            }
            Wounded newWounded = new Wounded(name, age, permDisab, hospitalized, region, warStats);
            woundedList.add(newWounded);
            model.addRow(new Object[]{
                    name,
                    age,
                    newWounded.hasPermanentDisability(),
                    newWounded.isHospitalized()
            });

            nameField.setText("");
            ageField.setText("");
            permDisabilityCheck.setSelected(false);
            hospitalizedCheck.setSelected(false);
        });

        controls.add(new JLabel("Name:"));
        controls.add(nameField);
        controls.add(new JLabel("Age:"));
        controls.add(ageField);
        controls.add(permDisabilityCheck);
        controls.add(hospitalizedCheck);
        controls.add(addBtn);

        panel.add(controls, BorderLayout.SOUTH);

        JFrame subFrame = new JFrame("Wounded List | " + region);
        subFrame.add(panel);
        subFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        subFrame.setSize(650, 450);
        subFrame.setLocationRelativeTo(this);
        subFrame.setVisible(true);
    }

    private void showPrisonerSortedFrame() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        RegionData region = getSelectedRegionObj();
        if (region == null) {
            JOptionPane.showMessageDialog(this, "Please select a region!");
            return;
        }
        WarStats warStats = region.getWarStats();
        ArrayList<Prisoner> prisonerList = new ArrayList<>(warStats.getPrisonersList());


        prisonerList.sort((a, b) -> Integer.compare(b.getYearsInPrison(), a.getYearsInPrison()));

        String[] cols = {"Name", "Age", "Years in Prison", "Released"};
        DefaultTableModel model = new DefaultTableModel(cols, 0){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (Prisoner p : prisonerList) {
            model.addRow(new Object[]{
                    p.getName(),
                    p.getAge(),
                    p.getYearsInPrison(),
                    p.isReleased() ? "Yes" : "No"
            });
        }

        JTable table = new JTable(model);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);


        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField nameField = new JTextField(10);
        JTextField ageField = new JTextField(2);
        JTextField yearsInPrisonField = new JTextField(3);
        JCheckBox releasedCheck = new JCheckBox("Released");
        JButton addBtn = new JButton("Add");

        addBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            int age, yearsInPrison;
            boolean released = releasedCheck.isSelected();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter prisoner's name.");
                return;
            }
            try {
                age = Integer.parseInt(ageField.getText().trim());
                yearsInPrison = Integer.parseInt(yearsInPrisonField.getText().trim());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for age and years in prison.");
                return;
            }
            Prisoner newPrisoner = new Prisoner(name, age, yearsInPrison, released, region, warStats);
            prisonerList.add(newPrisoner);


            prisonerList.sort((a, b) -> Integer.compare(b.getYearsInPrison(), a.getYearsInPrison()));


            model.setRowCount(0);
            for (Prisoner p2 : prisonerList) {
                model.addRow(new Object[]{
                        p2.getName(),
                        p2.getAge(),
                        p2.getYearsInPrison(),
                        p2.isReleased() ? "Yes" : "No"
                });
            }

            nameField.setText("");
            ageField.setText("");
            yearsInPrisonField.setText("");
            releasedCheck.setSelected(false);
        });

        controls.add(new JLabel("Name:"));
        controls.add(nameField);
        controls.add(new JLabel("Age:"));
        controls.add(ageField);
        controls.add(new JLabel("Years in Prison:"));
        controls.add(yearsInPrisonField);
        controls.add(releasedCheck);
        controls.add(addBtn);

        panel.add(controls, BorderLayout.SOUTH);

        JFrame subFrame = new JFrame("Prisoners List | " + region);
        subFrame.add(panel);
        subFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        subFrame.setSize(650, 450);
        subFrame.setLocationRelativeTo(this);
        subFrame.setVisible(true);
    }

    private void showBorderStatusFrame() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        RegionData region = getSelectedRegionObj();
        if (region == null) {
            JOptionPane.showMessageDialog(this, "Please select a region!");
            return;
        }

        ArrayList<Border> borderList = new ArrayList<>();
        for (RegionData r : regionsList) {
            if (r instanceof Border && r.getRegionName().equals(region.getRegionName())) {
                borderList.add((Border) r);
            }
        }

        String[] cols = {"Border Name", "Status"};
        DefaultTableModel model = new DefaultTableModel(cols, 0){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (Border b : borderList) {
            model.addRow(new Object[]{
                    b.getBorderName(),
                    b.isClosed()? "Closed" : "Open"
            });
        }

        JTable table = new JTable(model);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);


        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField borderNameField = new JTextField(10);
        JCheckBox closedCheck = new JCheckBox("Closed");
        JButton addBtn = new JButton("Add");

        addBtn.addActionListener(e -> {
            String borderName = borderNameField.getText().trim();
            boolean closed = closedCheck.isSelected();

            if (borderName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter border name.");
                return;
            }

            Border newBorder = new Border("", region.getRegionName(), borderName, closed);
            regionsList.add(newBorder);
            borderList.add(newBorder);

            model.setRowCount(0);
            for (Border br : borderList) {
                model.addRow(new Object[]{
                        br.getBorderName(),
                        br.isClosed()? "Closed" : "Open"
                });
            }

            borderNameField.setText("");
            closedCheck.setSelected(false);
        });

        controls.add(new JLabel("Border Name:"));
        controls.add(borderNameField);
        controls.add(closedCheck);
        controls.add(addBtn);

        panel.add(controls, BorderLayout.SOUTH);

        JFrame subFrame = new JFrame("Border Status | " + region.getRegionName());
        subFrame.add(panel);
        subFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        subFrame.setSize(650, 450);
        subFrame.setLocationRelativeTo(this);
        subFrame.setVisible(true);
    }

    private void showStatisticsFrame() {
        JPanel panel = new JPanel(new BorderLayout());

        class Totals {
            int martyrs = 0, wounded = 0, prisoners = 0, healthImpact = 0, eduImpact = 0;
            int totalImpact() {
                return martyrs + wounded + prisoners + healthImpact + eduImpact;
            }
        }

        Map<String, Totals> regionTotals = new HashMap<>();

        for (RegionData region : regionsList) {
            String name = region.getRegionName();
            Totals totals = regionTotals.getOrDefault(name, new Totals());

            HealthcareImpact healthcareImpact = healthcareImpactsMap.get(region);
            EducationImpact educationImpact = educationalImpactsMap.get(region);

            if (healthcareImpact != null)
                totals.healthImpact += healthcareImpact.getHospitalDestroyed() + healthcareImpact.getUntreatedPatients();
            if (educationImpact != null)
                totals.eduImpact += educationImpact.getSchoolDestroyed() + educationImpact.getStudentDisplaced();

            totals.martyrs += getMartyrForRegion(region);
            totals.wounded += getWoundedForRegion(region);
            totals.prisoners += getPrisonerForRegion(region);

            regionTotals.put(name, totals);
        }

        String mostAffectedRegion = "<Empty Region>";
        int maxImpact = Integer.MIN_VALUE;
        String maxMartyrsRegion = "<Empty Region>", minMartyrsRegion = "<Empty Region>";
        String maxWoundedRegion = "<Empty Region>", minWoundedRegion = "<Empty Region>";
        String maxPrisonersRegion = "<Empty Region>", minPrisonersRegion = "<Empty Region>";
        int maxMartyrs = Integer.MIN_VALUE, minMartyrs = Integer.MAX_VALUE;
        int maxWounded = Integer.MIN_VALUE, minWounded = Integer.MAX_VALUE;
        int maxPrisoners = Integer.MIN_VALUE, minPrisoners = Integer.MAX_VALUE;

        for (Map.Entry<String, Totals> entry : regionTotals.entrySet()) {
            String name = entry.getKey();
            Totals totals = entry.getValue();
            int totalImpact = totals.totalImpact();

            if (totalImpact > maxImpact) {
                maxImpact = totalImpact;
                mostAffectedRegion = name;
            }

            if (totals.martyrs > maxMartyrs) {
                maxMartyrs = totals.martyrs;
                maxMartyrsRegion = name;
            }
            if (totals.martyrs < minMartyrs) {
                minMartyrs = totals.martyrs;
                minMartyrsRegion = name;
            }

            if (totals.wounded > maxWounded) {
                maxWounded = totals.wounded;
                maxWoundedRegion = name;
            }
            if (totals.wounded < minWounded) {
                minWounded = totals.wounded;
                minWoundedRegion = name;
            }

            if (totals.prisoners > maxPrisoners) {
                maxPrisoners = totals.prisoners;
                maxPrisonersRegion = name;
            }
            if (totals.prisoners < minPrisoners) {
                minPrisoners = totals.prisoners;
                minPrisonersRegion = name;
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Most Affected Region: ").append(mostAffectedRegion).append("\n");
        sb.append("Most Martyrs in: ").append(maxMartyrsRegion).append(" (").append(maxMartyrs).append(")\n");
        sb.append("Least Martyrs in: ").append(minMartyrsRegion).append(" (").append(minMartyrs).append(")\n");
        sb.append("Most Wounded in: ").append(maxWoundedRegion).append(" (").append(maxWounded).append(")\n");
        sb.append("Least Wounded in: ").append(minWoundedRegion).append(" (").append(minWounded).append(")\n");
        sb.append("Most Prisoners in: ").append(maxPrisonersRegion).append(" (").append(maxPrisoners).append(")\n");
        sb.append("Least Prisoners in: ").append(minPrisonersRegion).append(" (").append(minPrisoners).append(")\n");

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        textArea.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JFrame subFrame = new JFrame("Statistics Board");
        subFrame.add(panel);
        subFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        subFrame.setSize(650, 450);
        subFrame.setLocationRelativeTo(this);
        subFrame.setVisible(true);
    }




    private class RegionManagementButtonsHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == addRegionButton) {
                String newRegion = JOptionPane.showInputDialog(MainFrameZ.this, "Enter Region Name:");
                String newDate = JOptionPane.showInputDialog(MainFrameZ.this, "Enter Siege Date mm/yyyy:");

                if (newRegion == null || newRegion.isEmpty() || newDate == null || newDate.isEmpty()) {
                    JOptionPane.showMessageDialog(MainFrameZ.this, "Invalid input. Please try again.");
                    return;
                }

                if(!newDate.matches("\\d{2}/\\d{4}")){
                    JOptionPane.showMessageDialog(MainFrameZ.this, "Invalid date format. Please use mm/yyyy format.");
                    return;
                }

                RegionData r = new RegionData(newRegion, newDate);
                regionsList.add(r);
                updateRegionComboBox();
                regionsTableModel.addRow(new String[]{newRegion, newDate});
                JOptionPane.showMessageDialog(MainFrameZ.this, "Region added successfully!");
            }

            else if (e.getSource() == editRegionButton) {
                int rowIdx = regionsTable.getSelectedRow();
                if (rowIdx == -1) {
                    JOptionPane.showMessageDialog(MainFrameZ.this, "Please select a region from the table to edit.");
                    return;
                }
                RegionData selectedRegion = regionsList.get(rowIdx);
                String editRegion = JOptionPane.showInputDialog(MainFrameZ.this, "Edit Region Name:", selectedRegion.getRegionName());
                String editDate = JOptionPane.showInputDialog(MainFrameZ.this, "Edit Siege Date:", selectedRegion.getDate());

                if (editRegion == null || editRegion.isEmpty() || editDate == null || editDate.isEmpty()) {
                    JOptionPane.showMessageDialog(MainFrameZ.this, "Invalid input. Please try again.");
                    return;
                }

                if (!editDate.matches("\\d{2}/\\d{4}")) {
                    JOptionPane.showMessageDialog(MainFrameZ.this, "Invalid date format. Please use mm/yyyy format.");
                    return;
                }

                selectedRegion.setRegion(editRegion);
                selectedRegion.setDate(editDate);
                updateRegionComboBox();
                regionsTableModel.setValueAt(editRegion, rowIdx, 0);
                regionsTableModel.setValueAt(editDate, rowIdx, 1);
                regionsTable.setRowSelectionInterval(rowIdx, rowIdx);
                JOptionPane.showMessageDialog(MainFrameZ.this, "Region edited successfully!");
            }
            else if (e.getSource() == deleteRegionButton) {
                int rowIdx = regionsTable.getSelectedRow();
                if (rowIdx == -1) {
                    JOptionPane.showMessageDialog(MainFrameZ.this, "Please select a region from the table to delete.");
                    return;
                }
                regionsList.remove(rowIdx);
                updateRegionComboBox();
                refreshRegionsTable();
                JOptionPane.showMessageDialog(MainFrameZ.this, "Region deleted successfully!");
            }
        }
    }


    private class ActionButtonsHandler implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == healthImpactButton){
                showHealthCare();
            }
            else if(e.getSource() == educationImpactButton){
                showEducationImpact();
            }
            else if(e.getSource() == martyrButton){
                showMartyrsFrame();
            }
            else if(e.getSource() == woundedButton){
                showWoundedFrame();
            }
            else if(e.getSource() == prisonerButton){
                showPrisonerSortedFrame();
            }
            else if(e.getSource() == borderButton){
                showBorderStatusFrame();
            }
            else if(e.getSource() == statsButton){
                showStatisticsFrame();
            }
        }
    }



    private String generateKey(String regionName, String regionDate){
        return regionName+"-"+regionDate;
    }

    private void refreshRegionsTable() {
        regionsTableModel.setRowCount(0);
        for (RegionData region : regionsList) {
            regionsTableModel.addRow(new String[]{region.getRegionName(), region.getDate()});
        }
    }
    private void changeDateComboBoxState(String regionName){
        dateComboBox.removeAllItems();
        if(regionName.equals("Select a region")){
            dateComboBox.addItem("mm/yyyy");
        }
        for(RegionData r: regionsList){
            if(r.getRegionName().equals(regionName))
                dateComboBox.addItem(r.getDate());
        }
    }
    private void updateRegionComboBox() {
        regionComboBox.removeAllItems();
        regionComboBox.addItem("Select a region");

        Set<String> regionsSet = new HashSet<>();

        for (RegionData r : regionsList) {
            String regionName = r.getRegionName();
            if (!regionsSet.contains(regionName)) {
                regionComboBox.addItem(regionName);
                regionsSet.add(regionName);
            }
        }
    }


    private RegionData getSelectedRegionObj() {
        int idx = regionComboBox.getSelectedIndex() - 1;
        if (idx >= 0 && idx < regionsList.size()){
            String selectedRegionName= (String) regionComboBox.getSelectedItem();
            String selectedDate= (String) dateComboBox.getSelectedItem();
            String key= generateKey(selectedRegionName, selectedDate);
            for(RegionData r: regionsList){
                if(r.equalsRegionData(key))
                    return r;
            }
        }
        return null;
    }




}
