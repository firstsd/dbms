package entity;

public class CallingCode {
    private Integer countryCode;
    private String CountryName;

    public CallingCode(Integer countryCode, String countryName) {
        this.countryCode = countryCode;
        CountryName = countryName;
    }

    public Integer getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Integer countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return CountryName;
    }

    public void setCountryName(String countryName) {
        CountryName = countryName;
    }
}
