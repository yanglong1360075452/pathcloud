package com.lifetech.dhap.pathcloud.tracking.application.impl;

import com.lifetech.dhap.pathcloud.application.application.PathologyApplication;
import com.lifetech.dhap.pathcloud.common.data.PathologyStatus;
import com.lifetech.dhap.pathcloud.common.utils.MapUtil;
import com.lifetech.dhap.pathcloud.tracking.application.BlockApplication;
import com.lifetech.dhap.pathcloud.tracking.application.SlideApplication;
import com.lifetech.dhap.pathcloud.tracking.application.condition.SlideCondition;
import com.lifetech.dhap.pathcloud.tracking.application.dto.BlockDto;
import com.lifetech.dhap.pathcloud.tracking.application.dto.SlideDto;
import com.lifetech.dhap.pathcloud.tracking.application.dto.SlideLostDto;
import com.lifetech.dhap.pathcloud.user.application.UserApplication;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by LiuMei on 2017-08-31.
 */
@Service("slideApplication")
public class SlideApplicationImpl implements SlideApplication {

    @Autowired
    private BlockApplication blockApplication;

    @Autowired
    private PathologyApplication pathologyApplication;

    @Autowired
    private UserApplication userApplication;

    @Override
    public Map slideGroupByPathNo(List<Long> slideIds, Integer exclude) {
        Map<Long, HashSet<Long>> pathSlide = new HashMap<>();
        for (Long slideId : slideIds) {
            BlockDto slide = blockApplication.getSimpleBlockById(slideId);
            Long pathId = slide.getPathId();
            Integer biaoshi = slide.getBiaoshi();
            if (!biaoshi.equals(exclude)) {
                pathSlide = MapUtil.mapGroup(pathSlide, pathId, slideId);
            }
        }
        return pathSlide;
    }

    @Override
    public List<SlideLostDto> slideErrorCheck(SlideCondition slideCondition, Map<Long, HashSet<Long>> compareMap) {
        List<SlideLostDto> slideLostDtoList = null;
        if (MapUtils.isNotEmpty(compareMap)) {
            slideLostDtoList = new ArrayList<>();
            SlideLostDto slideLostDto;
            for (Map.Entry<Long, HashSet<Long>> entry : compareMap.entrySet()) {
                slideCondition.setPathId(entry.getKey());
                long slides = blockApplication.countSlideByCondition(slideCondition);
                HashSet<Long> value = entry.getValue();
                int size = value.size();
                if (slides != size) {//有异常
                    List<SlideDto> slideDtoList = blockApplication.getSlideByCondition(slideCondition);
                    List<SlideLostDto> slideError1 = new ArrayList<>();
                    if (!CollectionUtils.isEmpty(slideDtoList)) {
                        for (SlideDto slideDto : slideDtoList) {
                            long slideId = slideDto.getId();
                            boolean realError = true;
                            for (Long id : value) {
                                if (id.equals(slideId)) {
                                    realError = false;
                                }
                            }
                            if (realError) {
                                slideLostDto = new SlideLostDto();
                                slideLostDto.setId(slideId);
                                slideLostDto.setSerialNumber(slideDto.getSerialNumber());
                                slideLostDto.setSubId(slideDto.getSubId());
                                slideLostDto.setBlockSerialNumber(slideDto.getBlockSerialNumber());
                                slideLostDto.setBlockSubId(slideDto.getBlockSubId());
                                Integer status = slideDto.getStatus();
                                slideLostDto.setStatus(status);
                                slideLostDto.setStatusDesc(PathologyStatus.getNameByCode(status));
                                slideLostDto.setPathNo(pathologyApplication.getSimplePathById(slideDto.getPathId()).getSerialNumber());
                                slideLostDto.setLastOperator(userApplication.getUserSimpleInfoById(slideDto.getUpdateBy()));
                                slideLostDto.setLastOperateTime(slideDto.getUpdateTime());
                                slideError1.add(slideLostDto);
                            }
                        }
                    }
                    slideLostDtoList.addAll(slideError1);
                }
            }
        }
        return slideLostDtoList;
    }
}
