package com.lifetech.dhap.pathcloud.application.application.impl;

import com.lifetech.dhap.pathcloud.application.application.BookingApplication;
import com.lifetech.dhap.pathcloud.application.application.dto.BookDto;
import com.lifetech.dhap.pathcloud.application.domain.BookingRepository;
import com.lifetech.dhap.pathcloud.application.domain.model.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuMei on 2017-04-27.
 */
@Service
public class BookingApplicationImpl implements BookingApplication {

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public List<BookDto> getBooksByApplicationId(long applicationId) {
        List<Booking> bookings = bookingRepository.selectBookingByApplicationId(applicationId);
        if(bookings != null && bookings.size() > 0){
            List<BookDto> bookDtos = new ArrayList<>();
            BookDto bookDto = null;
            for(Booking booking : bookings){
                bookDto  = new BookDto();
                bookDto.setId(booking.getId());
                bookDto.setApplicationId(booking.getApplicationId());
                bookDto.setFreezeStartTime(booking.getStartTime());
                bookDto.setFreezeEndTime(booking.getEndTime());
                bookDto.setInstrumentId(booking.getInstrumentId());
                bookDtos.add(bookDto);
            }
            return bookDtos;
        }
        return null;
    }
}
