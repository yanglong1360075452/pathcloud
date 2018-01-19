package com.lifetech.dhap.pathcloud.reagent.application.impl;

import com.lifetech.dhap.pathcloud.reagent.domain.StoreMapper;
import com.lifetech.dhap.pathcloud.reagent.domain.model.ReagentStore;
import com.lifetech.dhap.pathcloud.reagent.domain.model.Store;
import com.lifetech.dhap.pathcloud.security.UserContext;
import com.lifetech.dhap.pathcloud.user.application.condition.UserCondition;
import com.lifetech.dnap.pathcloud.reagent.application.ReagentApplication;
import com.lifetech.dnap.pathcloud.reagent.application.StoreApplication;
import com.lifetech.dnap.pathcloud.reagent.application.condition.StoreCondition;
import com.lifetech.dnap.pathcloud.reagent.application.dto.ReagentRecordDto;
import com.lifetech.dnap.pathcloud.reagent.application.dto.StoreDto;
import com.lifetech.dnap.pathcloud.reagent.application.infrastructure.code.ReagentCategory;
import com.lifetech.dnap.pathcloud.reagent.application.infrastructure.code.ReagentType;
import com.lifetech.dnap.pathcloud.reagent.application.infrastructure.code.StoreStatus;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LiuMei
 * @date 2017-12-01.
 */
@Service
public class StoreApplicationImpl implements StoreApplication {

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private ReagentApplication reagentApplication;

    @Override
    public void inStore(StoreDto storeDto) {
        Store store = storeDtoToPo(storeDto);
        if (store != null) {
            storeMapper.insert(store);
        }
    }

    @Override
    public void updateStore(StoreDto storeDto) {
        Store store = storeDtoToPo(storeDto);
        if (store != null) {
            storeMapper.updateByPrimaryKey(store);
        }
    }

    @Override
    public StoreDto getByUnique(long reagentId, String batchNumber, String orderNumber) {
        StoreDto storeDto = null;
        if (StringUtils.isNotBlank(batchNumber) && StringUtils.isNotBlank(orderNumber)) {
            Store store = storeMapper.selectByUnique(reagentId, batchNumber, orderNumber);
            storeDto = storeToDto(store);
        }
        return storeDto;
    }

    @Override
    public StoreDto getById(long storeId) {
        return storeToDto(storeMapper.selectByPrimaryKey(storeId));
    }

    @Override
    public List<StoreDto> getList(StoreCondition condition) {
        List<ReagentStore> storeList = storeMapper.selectByCondition(condition);
        List<StoreDto> storeDtoList = null;
        if (CollectionUtils.isNotEmpty(storeList)) {
            storeDtoList = new ArrayList<>();
            for (ReagentStore reagentStore : storeList) {
                storeDtoList.add(reagentStoreToDto(reagentStore));
            }
        }
        return storeDtoList;
    }

    @Override
    public Long countByCondition(StoreCondition condition) {
        return storeMapper.countByCondition(condition);
    }

    @Override
    public StoreDto getCurrentUse(StoreCondition condition) {
        return storeToDto(storeMapper.getCurrentUse(condition));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void adjust(StoreDto storeDto) {
        if(storeDto != null){
            Long userId = UserContext.getLoginUserID();
            String note = storeDto.getNote();
            storeDto.setUpdateBy(userId);
            updateStore(storeDto);
            ReagentRecordDto reagentRecordDto = new ReagentRecordDto();
            reagentRecordDto.setStoreId(storeDto.getId());
            reagentRecordDto.setCreateBy(userId);
            reagentRecordDto.setDosage(storeDto.getAdjust());
            reagentRecordDto.setNote(note);
            reagentApplication.addReagentRecord(reagentRecordDto);
        }
    }

    private StoreDto storeToDto(Store store) {
        StoreDto storeDto = null;
        if (store != null) {
            storeDto = new StoreDto();
            BeanUtils.copyProperties(store, storeDto);
        }
        return storeDto;
    }

    private Store storeDtoToPo(StoreDto storeDto) {
        Store store = null;
        if (storeDto != null) {
            store = new Store();
            BeanUtils.copyProperties(storeDto, store);
        }
        return store;
    }

    private StoreDto reagentStoreToDto(ReagentStore reagentStore) {
        StoreDto storeDto = null;
        if (reagentStore != null) {
            storeDto = new StoreDto();
            BeanUtils.copyProperties(reagentStore, storeDto);
            storeDto.setReagentCategoryDesc(ReagentCategory.getNameByCode(reagentStore.getReagentCategory()));
            storeDto.setReagentTypeDesc(ReagentType.getNameByCode(reagentStore.getReagentType()));
            storeDto.setStatusDesc(StoreStatus.getNameByCode(reagentStore.getStatus()));
        }
        return storeDto;
    }
}
