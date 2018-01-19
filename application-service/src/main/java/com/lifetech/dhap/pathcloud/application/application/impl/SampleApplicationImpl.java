package com.lifetech.dhap.pathcloud.application.application.impl;

import com.lifetech.dhap.pathcloud.application.application.PathologyApplication;
import com.lifetech.dhap.pathcloud.application.application.SampleApplication;
import com.lifetech.dhap.pathcloud.application.application.condition.SampleCondition;
import com.lifetech.dhap.pathcloud.application.application.dto.PathologyDto;
import com.lifetech.dhap.pathcloud.application.application.dto.SampleDto;
import com.lifetech.dhap.pathcloud.application.domain.SampleRepository;
import com.lifetech.dhap.pathcloud.application.domain.model.Sample;
import com.lifetech.dhap.pathcloud.setting.application.ParamSettingApplication;
import com.lifetech.dhap.pathcloud.setting.application.infrastructure.code.SystemKey;
import com.lifetech.dhap.pathcloud.user.application.UserApplication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuMei on 2017-02-06.
 */
@Service
public class SampleApplicationImpl implements SampleApplication {

    @Autowired
    private SampleRepository sampleRepository;

    @Autowired
    private PathologyApplication pathologyApplication;

    @Autowired
    private UserApplication userApplication;

    @Autowired
    private ParamSettingApplication paramSettingApplication;

    @Override
    public List<SampleDto> getSampleInfo(long pathId) {
        PathologyDto pathologyDto = pathologyApplication.getSimplePathById(pathId);
        List<Sample> samples = sampleRepository.selectByPathId(pathId);
        List<SampleDto> sampleDtoList = new ArrayList<>();
        for(Sample sample : samples){
            SampleDto sampleDto = new SampleDto();
            BeanUtils.copyProperties(sample,sampleDto);
            sampleDto.setCategoryName(paramSettingApplication.getNameByKeyAndCode(SystemKey.SampleCategory.toString(),sample.getCategory()));
            sampleDto.setRegisterTime(pathologyDto.getCreateTime());
            sampleDto.setRegisterUser(userApplication.getUserSimpleInfoById(pathologyDto.getCreateBy()));
            sampleDto.setCreateBy(userApplication.getUserSimpleInfoById(sample.getCreateBy()));
            sampleDto.setUpdateBy(userApplication.getUserSimpleInfoById(sample.getUpdateBy()));
            sampleDtoList.add(sampleDto);
        }
        return sampleDtoList;
    }

    @Override
    public Long countByCategory(int category) {
        SampleCondition sampleCondition = new SampleCondition();
        sampleCondition.setCategory(category);
        sampleCondition.setDelete(false);
        return sampleRepository.countByCondition(sampleCondition);
    }

}
