package com.lifetech.dhap.pathcloud.application.application.dto;

/**
 * Created by HP on 2017/3/29.
 */
public class BookingDto {

    private Integer timeflag;

    private Boolean checked;

    private Boolean booked;

    private Integer instrumentId;

    private BookingPerson bookingPerson;

    public Integer getTimeflag() {
        return timeflag;
    }

    public void setTimeflag(Integer timeflag) {
        this.timeflag = timeflag;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Boolean getBooked() {
        return booked;
    }

    public void setBooked(Boolean booked) {
        this.booked = booked;
    }

    public BookingPerson getBookingPerson() {
        return bookingPerson;
    }

    public void setBookingPerson(BookingPerson bookingPerson) {
        this.bookingPerson = bookingPerson;
    }

    public Integer getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(Integer instrumentId) {
        this.instrumentId = instrumentId;
    }
}
