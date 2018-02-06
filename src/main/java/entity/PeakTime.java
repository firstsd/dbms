package entity;

import java.sql.Time;

public class PeakTime {
    private Integer serviceID;
    private Time peakPeriodStart;
    private Time offPeakPeriodStart;
    private CallingCode countryCode;

    public PeakTime(Integer serviceID, Time peakPeriodStart, Time offPeakPeriodStart, CallingCode countryCode) {
        this.serviceID = serviceID;
        this.peakPeriodStart = peakPeriodStart;
        this.offPeakPeriodStart = offPeakPeriodStart;
        this.countryCode = countryCode;
    }

    public Integer getServiceID() {
        return serviceID;
    }

    public void setServiceID(Integer serviceID) {
        this.serviceID = serviceID;
    }

    public Time getPeakPeriodStart() {
        return peakPeriodStart;
    }

    public void setPeakPeriodStart(Time peakPeriodStart) {
        this.peakPeriodStart = peakPeriodStart;
    }

    public Time getOffPeakPeriodStart() {
        return offPeakPeriodStart;
    }

    public void setOffPeakPeriodStart(Time offPeakPeriodStart) {
        this.offPeakPeriodStart = offPeakPeriodStart;
    }

    public CallingCode getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(CallingCode countryCode) {
        this.countryCode = countryCode;
    }
}
