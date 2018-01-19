package com.lifetech.dhap.pathcloud.tracking.application;

import com.lifetech.dhap.pathcloud.tracking.application.condition.CollectCondition;
import com.lifetech.dhap.pathcloud.tracking.application.dto.CollectDetailDto;
import com.lifetech.dhap.pathcloud.tracking.application.dto.CollectDto;

import java.util.List;

/**
 * Created by HP on 2017/9/27.
 */
public interface CollectApplication {

    void createCollect(CollectDto collectDto);

    List<CollectDetailDto> getCollects(CollectCondition con);

    Long getCollectsTotal(CollectCondition con);

    CollectDto getCollectById(Long id);

    void deleteCollectById(Long id);

    CollectDto getCollectByCondition(CollectCondition collectCondition);

    CollectDto getCollectByIdAndCreateBy(Long id, Long createById);
}
