package entity;

import java.util.Date;

public class Bill {
    private Integer billID;
    private Date billDate;
    private Integer billAmnt;
    private Integer billStatus;
    private Customer customerID;
    private String serviceName;

    public Bill(Integer billID, Date billDate, Integer billAmnt, Integer billStatus, Customer customerID, String serviceName) {
        this.billID = billID;
        this.billDate = billDate;
        this.billAmnt = billAmnt;
        this.billStatus = billStatus;
        this.customerID = customerID;
        this.serviceName = serviceName;
    }

    public Integer getBillID() {
        return billID;
    }

    public void setBillID(Integer billID) {
        this.billID = billID;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public Integer getBillAmnt() {
        return billAmnt;
    }

    public void setBillAmnt(Integer billAmnt) {
        this.billAmnt = billAmnt;
    }

    public Integer getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(Integer billStatus) {
        this.billStatus = billStatus;
    }

    public Customer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Customer customerID) {
        this.customerID = customerID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
