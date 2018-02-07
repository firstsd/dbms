package entity;

public class Customer {
    private Integer custID;
    private String fName;
    private String lName;
    private String phoneNo;
    private Service service;
    private String address;
    private CallingCode countryCode;

    public Customer(Integer custID, String fName, String lName, String phoneNo, Service service, String address, CallingCode countryCode) {
        this.custID = custID;
        this.fName = fName;
        this.lName = lName;
        this.phoneNo = phoneNo;
        this.service = service;
        this.address = address;
        this.countryCode = countryCode;
    }
    public Customer(String fName, String lName, String phoneNo, Service service, String address, CallingCode countryCode) {
        this.fName = fName;
        this.lName = lName;
        this.phoneNo = phoneNo;
        this.service = service;
        this.address = address;
        this.countryCode = countryCode;
    }

    public Integer getCustID() {
        return custID;
    }

    public void setCustID(Integer custID) {
        this.custID = custID;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public CallingCode getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(CallingCode countryCode) {
        this.countryCode = countryCode;
    }
}
