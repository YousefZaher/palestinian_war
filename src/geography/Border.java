package geography;

public class Border extends RegionData{
    private String borderName;
    private boolean isClosed;
    public Border(String date,String region, String borderName ,boolean isClosed) {
        super(date,region);
        this.borderName = borderName;
        this.isClosed = isClosed;
    }



    public String getBorderName() {
        return borderName;
    }

    public void setBorderName(String borderName) {
        this.borderName = borderName;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean isClosed) {
        this.isClosed = isClosed;
    }


    public void displayStatus(){
        System.out.println(isClosed ? "Border Closed" : "Border Opened");
    }
}
