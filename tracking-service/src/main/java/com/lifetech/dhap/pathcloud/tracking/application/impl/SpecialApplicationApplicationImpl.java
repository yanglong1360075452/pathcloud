package com.lifetech.dhap.pathcloud.tracking.application.impl;

import com.lifetech.dhap.pathcloud.application.application.PathologyApplication;
import com.lifetech.dhap.pathcloud.common.data.PathologyStatus;
import com.lifetech.dhap.pathcloud.tracking.application.SpecialApplicationApplication;
import com.lifetech.dhap.pathcloud.tracking.application.condition.SpecialApplicationCondition;
import com.lifetech.dhap.pathcloud.tracking.domain.model.SpecialApplication;
import com.lifetech.dhap.pathcloud.tracking.application.dto.SpecialApplicationDto;
import com.lifetech.dhap.pathcloud.tracking.domain.SpecialApplicationRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuMei on 2017-09-25.
 */
@Service
public class SpecialApplicationApplicationImpl implements SpecialApplicationApplication {

    @Autowired
    private SpecialApplicationRepository specialApplicationRepository;

    @Autowired
    private PathologyApplication pathologyApplication;

    @Override
    public void add(SpecialApplicationDto data) {
        if (data != null) {
            SpecialApplication specialApplication = new SpecialApplication();
            BeanUtils.copyProperties(data, specialApplication);
            specialApplicationRepository.insert(specialApplication);
        }

    }

    @Override
    public SpecialApplicationDto getByNumber(String number) {
        return specialApplicationToDto(specialApplicationRepository.selectByNumber(number));
    }

    @Override
    public SpecialApplicationDto getByPathNoAndType(String pathNo, Integer type) {
        return specialApplicationToDto(specialApplicationRepository.selectByPathNoAndType(pathNo,type));
    }

    @Override
    public List<SpecialApplicationDto> getListByCondition(SpecialApplicationCondition condition) {
        List<SpecialApplicationDto> specialApplicationDtoList = null;
        List<SpecialApplication> specialApplications = specialApplicationRepository.selectListByCondition(condition);
        if (!CollectionUtils.isEmpty(specialApplications)) {
            specialApplicationDtoList = new ArrayList<>();
            for (SpecialApplication specialApplication : specialApplications) {
                specialApplicationDtoList.add(specialApplicationToDto(specialApplication));
            }
        }
        return specialApplicationDtoList;
    }

    @Override
    public Long countByCondition(SpecialApplicationCondition condition) {
        return specialApplicationRepository.countByCondition(condition);
    }

    @Override
    public SpecialApplicationDto getById(long id) {
        return specialApplicationToDto(specialApplicationRepository.selectByPrimaryKey(id));
    }

    @Override
    public void update(SpecialApplicationDto dto) {
        if (dto != null && dto.getId() != null) {
            SpecialApplication specialApplication = new SpecialApplication();
            BeanUtils.copyProperties(dto, specialApplication);
            specialApplicationRepository.updateByPrimaryKey(specialApplication);
        }
    }

    @Override
    public Integer getSlideMinStatusByTrackingId(long trackingId) {
        return specialApplicationRepository.selectSlideMinStatusByTrackingId(trackingId);
    }

    @Override
    public List<String> getFrozenNumbersByPathNo(String pathNo) {
        return specialApplicationRepository.selectFrozenNumbersByPathNo(pathNo);
    }

    @Override
    public List<String> getFrozenResultByPathNo(String pathNo) {
        return specialApplicationRepository.selectFrozenResultByPathNo(pathNo);
    }

    private SpecialApplicationDto specialApplicationToDto(SpecialApplication specialApplication) {
        if (specialApplication == null) {
            return null;
        } else {
            SpecialApplicationDto dto = new SpecialApplicationDto();
            BeanUtils.copyProperties(specialApplication, dto);
            dto.setPatientName(pathologyApplication.getSimplePathByNo(dto.getPathNo()).getPatientName());
            dto.setStatusDesc(PathologyStatus.getNameByCode(specialApplication.getStatus()));
            return dto;
        }
    }
}
