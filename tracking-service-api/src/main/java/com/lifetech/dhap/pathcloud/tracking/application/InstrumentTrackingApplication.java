package com.lifetech.dhap.pathcloud.tracking.application;

import com.lifetech.dhap.pathcloud.tracking.application.condition.InstrumentTrackingCon;
import com.lifetech.dhap.pathcloud.tracking.application.dto.InstrumentTrackingDto;

import java.util.List;

/**
 * Created by HP on 2017/9/7.
 */
public interface InstrumentTrackingApplication {
    List<InstrumentTrackingDto> InstrumentTracking(InstrumentTrackingCon con);

    Long InstrumentTrackingTotal(InstrumentTrackingCon con);
}
