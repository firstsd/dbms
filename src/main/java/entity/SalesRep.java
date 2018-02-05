package entity;

public class SalesRep {
    private Staff saleRepID;
    private Customer customerID;
    private Double Commission;

    public SalesRep(Staff saleRepID, Customer customerID, Double commission) {
        this.saleRepID = saleRepID;
        this.customerID = customerID;
        Commission = commission;
    }

    public Staff getSaleRepID() {
        return saleRepID;
    }

    public void setSaleRepID(Staff saleRepID) {
        this.saleRepID = saleRepID;
    }

    public Customer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Customer customerID) {
        this.customerID = customerID;
    }

    public Double getCommission() {
        return Commission;
    }

    public void setCommission(Double commission) {
        Commission = commission;
    }
}
