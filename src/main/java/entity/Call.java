package entity;

import java.sql.Time;
import java.util.Date;

public class Call {
    private Integer callID;
    private CallingCode fromCode;
    private CallingCode toCode;
    private Customer fromPhonel;
    private String toPhoneNo;
    private Double duration;
    private Date callDate;
    private Time callTime;

    public Call(Integer callID, CallingCode fromCode, CallingCode toCode, Customer fromPhonel, String toPhoneNo, Double duration, Date callDate, Time callTime) {
        this.callID = callID;
        this.fromCode = fromCode;
        this.toCode = toCode;
        this.fromPhonel = fromPhonel;
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

    public CallingCode getFromCode() {
        return fromCode;
    }

    public void setFromCode(CallingCode fromCode) {
        this.fromCode = fromCode;
    }

    public CallingCode getToCode() {
        return toCode;
    }

    public void setToCode(CallingCode toCode) {
        this.toCode = toCode;
    }

    public Customer getFromPhonel() {
        return fromPhonel;
    }

    public void setFromPhonel(Customer fromPhonel) {
        this.fromPhonel = fromPhonel;
    }

    public String getToPhoneNo() {
        return toPhoneNo;
    }

    public void setToPhoneNo(String toPhoneNo) {
        this.toPhoneNo = toPhoneNo;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
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
