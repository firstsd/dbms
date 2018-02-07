package entity;

public class CallingCode {
    private Integer countryCode;
    private String countryName;

    public CallingCode(Integer countryCode, String countryName) {
        this.countryCode = countryCode;
        this.countryName = countryName;
    }

    public Integer getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Integer countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
