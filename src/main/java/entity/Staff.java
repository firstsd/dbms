package entity;

public class Staff {
    private Integer staffID;
    private String staffName;

    public Staff(Integer staffID, String staffName) {
        this.staffID = staffID;
        this.staffName = staffName;
    }

    public Integer getStaffID() {
        return staffID;
    }

    public void setStaffID(Integer staffID) {
        this.staffID = staffID;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }
}
