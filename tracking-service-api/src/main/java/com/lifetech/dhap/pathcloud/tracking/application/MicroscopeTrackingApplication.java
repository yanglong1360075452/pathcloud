package com.lifetech.dhap.pathcloud.tracking.application;

import com.lifetech.dhap.pathcloud.tracking.application.condition.MicroscopeTrackingCon;
import com.lifetech.dhap.pathcloud.tracking.application.dto.MicroscopeTrackingDto;

import java.util.List;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2017-06-22-10:06
 */
public interface MicroscopeTrackingApplication {
    /**
     * 结束使用显微镜
     */
    void endMicroscopeUse(MicroscopeTrackingDto dto);

    /**
     * 开始使用显微镜
     * @param dto
     */
    void addMicroscopeTracking(MicroscopeTrackingDto dto);

    List<MicroscopeTrackingDto> getMicroscopeByCondition(MicroscopeTrackingCon con);

    Long microscopeTotal(MicroscopeTrackingCon con);
}
