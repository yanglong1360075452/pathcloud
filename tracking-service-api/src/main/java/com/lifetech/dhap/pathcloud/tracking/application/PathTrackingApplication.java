package com.lifetech.dhap.pathcloud.tracking.application;

import com.lifetech.dhap.pathcloud.tracking.application.condition.PathTrackingCondition;
import com.lifetech.dhap.pathcloud.tracking.application.condition.ReportCondition;
import com.lifetech.dhap.pathcloud.tracking.application.dto.PathTrackingDto;
import com.lifetech.dhap.pathcloud.tracking.application.dto.ReportQueryDto;
import com.lifetech.dhap.pathcloud.tracking.application.dto.SignQueryDto;

import java.util.List;

/**
 * Created by LiuMei on 2017-01-16.
 */
public interface PathTrackingApplication {

    void addPathTracking(PathTrackingDto pathTrackingDto);

    List<PathTrackingDto> getPathTrackingByCondition(PathTrackingCondition con);

    List<ReportQueryDto> reportQuery(ReportCondition con);

    Long reportQueryTotal(ReportCondition con);

    List<ReportQueryDto> printRecordQuery(long id,Long specialApplyId);

    List<SignQueryDto> signQuery(ReportCondition con);

    Long signQueryTotal(ReportCondition con);

    PathTrackingDto getPathTrackingByPathId(Long id);

    List<PathTrackingDto>  getPathTrackingByPathIdAndPrintSign(long pathId, Integer printSign);
}
