package com.lifetech.dhap.pathcloud.statistic.application;

import com.lifetech.dhap.pathcloud.application.domain.model.Booking;
import com.lifetech.dhap.pathcloud.statistic.application.dto.FreezeBookingDto;

import java.util.Date;
import java.util.List;

/**
 * Created by HP on 2017/3/29.
 */
public interface FreezeBookingApplication {

    List<FreezeBookingDto> freezeBookingQuery(Date starttime, Date endtime, Integer instrumentId);

    List<Booking> freezeBookingExport(Date starttime, Date endtime);
}
