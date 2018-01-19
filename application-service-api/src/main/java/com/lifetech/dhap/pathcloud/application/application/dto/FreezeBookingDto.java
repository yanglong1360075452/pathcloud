package com.lifetech.dhap.pathcloud.application.application.dto;

import java.util.Date;
import java.util.List;

/**
 * Created by HP on 2017/3/29.
 */
public class FreezeBookingDto {

    private Date date;

    private Integer instrumentId;

    private List<BookingDto> bookingDto;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<BookingDto> getBookingDto() {
        return bookingDto;
    }

    public void setBookingDto(List<BookingDto> bookingDto) {
        this.bookingDto = bookingDto;
    }

    public Integer getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(Integer instrumentId) {
        this.instrumentId = instrumentId;
    }
}
