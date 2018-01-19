package com.lifetech.dhap.pathcloud.application.application;

import com.lifetech.dhap.pathcloud.application.application.dto.BookDto;

import java.util.List;

/**
 * Created by LiuMei on 2017-04-27.
 */
public interface BookingApplication {

    List<BookDto> getBooksByApplicationId(long applicationId);
}
