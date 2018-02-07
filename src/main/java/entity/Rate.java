package entity;

import java.sql.Time;
import java.util.Date;

public class Rate {
    private Integer RateID;
    private CallingCode fromCountryCode;
    private CallingCode toCountryCode;
    private Double peak;
    private Double offPeak;
    private Date changeDate;
    private Service serviceID;

    public Rate(Integer RateID, CallingCode fromCountryCode, CallingCode toCountryCode, Double peak, Double offPeak, Date changeDate, Service serviceID) {
        this.RateID = RateID;
        this.fromCountryCode = fromCountryCode;
        this.toCountryCode = toCountryCode;
        this.peak = peak;
        this.offPeak = offPeak;
        this.changeDate = changeDate;
        this.serviceID = serviceID;
    }

    public Integer getPeakRateID() {
        return RateID;
    }

    public void setPeakRateID(Integer RateID) {
        this.RateID = RateID;
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

    public Service getServiceID() {
        return serviceID;
    }

    public void setServiceID(Service serviceID) {
        this.serviceID = serviceID;
    }

    public Integer getRateID() {
        return RateID;
    }

    public void setRateID(Integer rateID) {
        RateID = rateID;
    }

    public CallingCode getFromCountryCode() {
        return fromCountryCode;
    }

    public void setFromCountryCode(CallingCode fromCountryCode) {
        this.fromCountryCode = fromCountryCode;
    }

    public CallingCode getToCountryCode() {
        return toCountryCode;
    }

    public void setToCountryCode(CallingCode toCountryCode) {
        this.toCountryCode = toCountryCode;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }
}
