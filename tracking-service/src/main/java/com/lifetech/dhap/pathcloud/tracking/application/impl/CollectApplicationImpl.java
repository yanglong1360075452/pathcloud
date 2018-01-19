package com.lifetech.dhap.pathcloud.tracking.application.impl;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.tracking.application.CollectApplication;
import com.lifetech.dhap.pathcloud.tracking.application.condition.CollectCondition;
import com.lifetech.dhap.pathcloud.tracking.application.dto.CollectDetailDto;
import com.lifetech.dhap.pathcloud.tracking.application.dto.CollectDto;
import com.lifetech.dhap.pathcloud.tracking.application.infrastructure.code.LabelCategory;
import com.lifetech.dhap.pathcloud.tracking.application.infrastructure.code.PermissionCol;
import com.lifetech.dhap.pathcloud.tracking.domain.CollectMapper;
import com.lifetech.dhap.pathcloud.tracking.domain.model.Collect;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 2017/9/27.
 */

@Service
public class CollectApplicationImpl implements CollectApplication {

    @Autowired
    private CollectMapper collectMapper;

    @Override
    public void createCollect(CollectDto collectDto) {
        String label = collectDto.getLabels().toString();
        Collect collect = new Collect();
        BeanUtils.copyProperties(collectDto, collect);
        collect.setLabel(label);
        collectMapper.insert(collect);
    }

    @Override
    public List<CollectDetailDto> getCollects(CollectCondition con) {
        List<CollectDetailDto> dtoList = collectMapper.getCollects(con);
        for (CollectDetailDto dto : dtoList) {
            dto.setPermissionDesc(PermissionCol.getNameByCode(dto.getPermission()));
            String able = dto.getLabel();
            String b = able.replaceAll("[\\[\\]]", "");
            String[] split = b.split(",");

            List<String> arrayList = new ArrayList();
            for (String label : split) {
                Integer labelInt = Integer.valueOf(label.trim());
                arrayList.add(LabelCategory.getNameByCode(labelInt));
            }
            dto.setLabelsDesc(arrayList);
        }
        return dtoList;
    }

    @Override
    public Long getCollectsTotal(CollectCondition con) {
        return collectMapper.getCollectsTotal(con);
    }

    @Override
    public CollectDto getCollectById(Long id) {
        Collect collect = collectMapper.selectByPrimaryKey(id);
        if (collect == null) {
            throw new BuzException(BuzExceptionCode.CollectNotExists);
        }
        CollectDto collectDto = new CollectDto();
        BeanUtils.copyProperties(collect, collectDto);
        return collectDto;
    }

    @Override
    public void deleteCollectById(Long id) {
        Collect collect = collectMapper.selectByPrimaryKey(id);
        if (collect == null) {
            throw new BuzException(BuzExceptionCode.CollectNotExists);
        }
        collectMapper.deleteByPrimaryKey(id);
    }

    @Override
    public CollectDto getCollectByCondition(CollectCondition collectCondition) {
        Collect collect = collectMapper.selectCollectByCondition(collectCondition);
        CollectDto collectDto = null;
        if (collect != null) {
            collectDto = new CollectDto();
            BeanUtils.copyProperties(collect, collectDto);
        }
        return collectDto;
    }

    @Override
    public CollectDto getCollectByIdAndCreateBy(Long id, Long createById) {
        Collect collect = collectMapper.getCollectByIdAndCreateBy(id, createById);
        CollectDto collectDto = null;
        if (collect != null) {
            collectDto = new CollectDto();
            BeanUtils.copyProperties(collect, collectDto);
        }
        return collectDto;
    }
}
