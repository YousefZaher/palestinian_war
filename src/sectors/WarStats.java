package sectors;
import java.util.ArrayList;
import people.*;

public class WarStats {
    private int martyrs;
    private int wounded;
    private int prisoner;

    private ArrayList<Martyr> martyrsList;
    private ArrayList<Wounded> woundedList;
    private ArrayList<Prisoner> prisonersList;

    private WarStats(int martyrs, int wounded, int prisoner) {
        this.martyrs = martyrs;
        this.wounded = wounded;
        this.prisoner = prisoner;
    }

    public WarStats(){
        this(0,0,0);
        martyrsList = new ArrayList<>();
        woundedList = new ArrayList<>();
        prisonersList = new ArrayList<>();
    }

    public void add(Martyr martyr) {
        martyrsList.add(martyr);
        this.martyrs++;
    }

    public void add(Wounded wounded) {
        woundedList.add(wounded);
        this.wounded++;
    }

    public void add(Prisoner prisoner) {
        prisonersList.add(prisoner);
        this.prisoner++;
    }

    public void update(int newMartyrs, int newWounded, int newPrisoner) {
        this.martyrs = newMartyrs;
        this.wounded = newWounded;
        this.prisoner = newPrisoner;
    }

    public void display(){
        System.out.println("Martyrs: " + martyrs);
        System.out.println("Wounded: " + wounded);
        System.out.println("Prisoner: " + prisoner);
    }

    public int getMartyrs(){
        return martyrs;
    }
    public int getWounded(){
        return wounded;
    }
    public int getPrisoners(){
        return prisoner;
    }


    public ArrayList<Martyr> getMartyrsList(){
        return martyrsList;
    }
    public ArrayList<Wounded> getWoundedList(){
        return woundedList;
    }
    public ArrayList<Prisoner> getPrisonersList(){
        return prisonersList;
    }

}
