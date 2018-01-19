package com.lifetech.dhap.pathcloud.statistic.application.impl;

import com.lifetech.dhap.pathcloud.application.domain.BookingRepository;
import com.lifetech.dhap.pathcloud.application.domain.model.Booking;
import com.lifetech.dhap.pathcloud.statistic.application.FreezeBookingApplication;
import com.lifetech.dhap.pathcloud.statistic.application.dto.BookingDto;
import com.lifetech.dhap.pathcloud.statistic.application.dto.BookingPerson;
import com.lifetech.dhap.pathcloud.statistic.application.dto.FreezeBookingDto;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by HP on 2017/3/29.
 */
@Service
public class FreezeBookingApplicationImpl implements FreezeBookingApplication {

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public List<FreezeBookingDto> freezeBookingQuery(Date starttime, Date endtime, Integer instrumentId) {
        List<Booking> bookings = bookingRepository.freezeBookingQuery(starttime, endtime, instrumentId);
        List<FreezeBookingDto> lists = new ArrayList<>();

        Calendar instance = Calendar.getInstance();
        instance.setTime(starttime);
        instance.set(Calendar.HOUR, 0);
        instance.set(Calendar.MINUTE, 0);
        instance.set(Calendar.SECOND, 0);
        instance.set(Calendar.MILLISECOND, 0);
        int lastDay = instance.getActualMaximum(Calendar.DAY_OF_MONTH);

        BookingDto bookingDto;
        FreezeBookingDto dto;
        BookingPerson person;

        for (int firstDay=1;firstDay<=lastDay;firstDay++){
            List<BookingDto> BookingDtos = new ArrayList<>();
            dto = new FreezeBookingDto();
            instance.set(Calendar.DATE, firstDay);
            Date time = instance.getTime();
            dto.setDate(time);
            dto.setInstrumentId(instrumentId);
            for (int i=16;i<=35;i++){
                bookingDto = new BookingDto();
                bookingDto.setBooked(false);
                bookingDto.setTimeflag(i);
                bookingDto.setInstrumentId(instrumentId);
                for (Booking booking:bookings){
                    if (booking.getStartTime().equals(time)){
                        if (booking.getTimeFlag() != null){
                            String timeFlag = booking.getTimeFlag();

                            List<Integer> list = JSONArray.fromObject(timeFlag);
                            for (Integer flag:list){
                                if (flag.equals(i)){
                                    bookingDto.setBooked(true);
                                    person=new BookingPerson();
                                    person.setBookingName(booking.getBookingPerson());
                                    person.setPhone(booking.getBookingPhone());
                                    bookingDto.setBookingPerson(person);
                                }
                            }
                        }

                    }

                }
                BookingDtos.add(bookingDto);
            }
            dto.setBookingDto(BookingDtos);
            lists.add(dto);

        }

        return lists;
    }

    @Override
    public List<Booking> freezeBookingExport(Date starttime, Date endtime) {
        List<Booking> bookings = bookingRepository.freezeBookingExport(starttime, endtime);

        return bookings;
    }
}
