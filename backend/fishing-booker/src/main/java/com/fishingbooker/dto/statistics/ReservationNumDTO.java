package com.fishingbooker.dto.statistics;

public class ReservationNumDTO {
    private String rentableName;
    private int reservationNumWeek;
    private int reservationNumMonth;
    private int reservationNumYear;

    public ReservationNumDTO(){}

    public ReservationNumDTO(String rentableName, int reservationNumWeek, int reservationNumMonth, int reservationNumYear) {
        this.rentableName = rentableName;
        this.reservationNumWeek = reservationNumWeek;
        this.reservationNumMonth = reservationNumMonth;
        this.reservationNumYear = reservationNumYear;
    }

    public String getRentableName() {
        return rentableName;
    }

    public void setRentableName(String rentableName) {
        this.rentableName = rentableName;
    }

    public int getReservationNumWeek() {
        return reservationNumWeek;
    }

    public void setReservationNumWeek(int reservationNumWeek) {
        this.reservationNumWeek = reservationNumWeek;
    }

    public int getReservationNumMonth() {
        return reservationNumMonth;
    }

    public void setReservationNumMonth(int reservationNumMonth) {
        this.reservationNumMonth = reservationNumMonth;
    }

    public int getReservationNumYear() {
        return reservationNumYear;
    }

    public void setReservationNumYear(int reservationNumYear) {
        this.reservationNumYear = reservationNumYear;
    }
}
