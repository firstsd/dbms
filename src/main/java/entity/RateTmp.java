package entity;

import java.util.Date;

public class RateTmp {
    private Integer rateID;
    private String serviceName;
    private String sourceName;
    private String destName;
    private Double peak;
    private Double offPeak;
    private Date changeDate;

    public RateTmp(Integer rateID, String serviceName, String sourceName, String destName, Double peak, Double offPeak, Date changeDate) {
        this.rateID = rateID;
        this.serviceName = serviceName;
        this.sourceName = sourceName;
        this.destName = destName;
        this.peak = peak;
        this.offPeak = offPeak;
        this.changeDate = changeDate;
    }

    public Integer getRateID() {
        return rateID;
    }

    public void setRateID(Integer rateID) {
        this.rateID = rateID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getDestName() {
        return destName;
    }

    public void setDestName(String destName) {
        this.destName = destName;
    }

    public Double getPeak() {
        return peak;
    }

    public void setPeak(Double peak) {
        this.peak = peak;
    }

    public Double getOffPeak() {
        return offPeak;
    }

    public void setOffPeak(Double offPeak) {
        this.offPeak = offPeak;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }
}
