package entity;

import java.sql.Time;
import java.util.Date;

public class ProcessCall {
    private Integer callID;
    private String fromCountry;
    private String toCountry;
    private String fromPhoneNo;
    private String toPhoneNo;
    private Integer duration;
    private Date callDate;
    private Time callTime;

    public ProcessCall(Integer callID, String fromCountry, String toCountry, String fromPhoneNo, String toPhoneNo, Integer duration, Date callDate, Time callTime) {
        this.callID = callID;
        this.fromCountry = fromCountry;
        this.toCountry = toCountry;
        this.fromPhoneNo = fromPhoneNo;
        this.toPhoneNo = toPhoneNo;
        this.duration = duration;
        this.callDate = callDate;
        this.callTime = callTime;
    }

    public Integer getCallID() {
        return callID;
    }

    public void setCallID(Integer callID) {
        this.callID = callID;
    }

    public String getFromCountry() {
        return fromCountry;
    }

    public void setFromCountry(String fromCountry) {
        this.fromCountry = fromCountry;
    }

    public String getToCountry() {
        return toCountry;
    }

    public void setToCountry(String toCountry) {
        this.toCountry = toCountry;
    }

    public String getFromPhoneNo() {
        return fromPhoneNo;
    }

    public void setFromPhoneNo(String fromPhoneNo) {
        this.fromPhoneNo = fromPhoneNo;
    }

    public String getToPhoneNo() {
        return toPhoneNo;
    }

    public void setToPhoneNo(String toPhoneNo) {
        this.toPhoneNo = toPhoneNo;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Date getCallDate() {
        return callDate;
    }

    public void setCallDate(Date callDate) {
        this.callDate = callDate;
    }

    public Time getCallTime() {
        return callTime;
    }

    public void setCallTime(Time callTime) {
        this.callTime = callTime;
    }
}
