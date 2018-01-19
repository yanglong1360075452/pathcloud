package com.lifetech.dhap.pathcloud.application.application;

import com.lifetech.dhap.pathcloud.application.application.dto.SampleDto;

import java.util.List;

/**
 * Created by LiuMei on 2017-02-06.
 */
public interface SampleApplication {

    /**
     * 根据病理Id获取样本基本信息
     * @param pathId
     * @return
     */
    List<SampleDto> getSampleInfo(long pathId);

    /**
     * 按照样本类别计数
     * @param category
     * @return
     */
    Long countByCategory(int category);
}
