package com.fishingbooker.dto.statistics;

public class FinanceDTO {
    private String rentableName;
    private double earning;

    public FinanceDTO(){}

    public FinanceDTO(String rentableName, double earning) {
        this.rentableName = rentableName;
        this.earning = earning;
    }

    public String getRentableName() {
        return rentableName;
    }

    public void setRentableName(String rentableName) {
        this.rentableName = rentableName;
    }

    public double getEarning() {
        return earning;
    }

    public void setEarning(double earning) {
        this.earning = earning;
    }
}
