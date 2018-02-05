package entity;

import java.util.Date;

public class Call {
    private Integer callID;
    private CallingCode fromCode;
    private CallingCode toCode;
    private Customer fromPhonel;
    private Customer toPhoneNo;
    private Double duration;
    private Date callDate;

    public Call(Integer callID, CallingCode fromCode, CallingCode toCode, Customer fromPhonel, Customer toPhoneNo, Double duration, Date callDate) {
        this.callID = callID;
        this.fromCode = fromCode;
        this.toCode = toCode;
        this.fromPhonel = fromPhonel;
        this.toPhoneNo = toPhoneNo;
        this.duration = duration;
        this.callDate = callDate;
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

    public Customer getToPhoneNo() {
        return toPhoneNo;
    }

    public void setToPhoneNo(Customer toPhoneNo) {
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
}
