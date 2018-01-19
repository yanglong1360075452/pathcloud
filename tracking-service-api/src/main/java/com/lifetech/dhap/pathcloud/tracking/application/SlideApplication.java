package com.lifetech.dhap.pathcloud.tracking.application;

import com.lifetech.dhap.pathcloud.tracking.application.condition.SlideCondition;
import com.lifetech.dhap.pathcloud.tracking.application.dto.SlideLostDto;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by LiuMei on 2017-08-31.
 */
public interface SlideApplication {

    /**
     * 玻片分组
     * @param slideIds 分组ID列表
     * @param exclude 标识不包含
     * @return 分组后结果
     */
    Map slideGroupByPathNo(List<Long> slideIds, Integer exclude);

    /**
     * 玻片缺失检查
     * @param condition 查询玻片条件
     * @param compareMap 传入比较的列表(已按病理号分好组)
     * @return 缺失玻片信息
     */
    List<SlideLostDto> slideErrorCheck(SlideCondition condition, Map<Long, HashSet<Long>> compareMap);

}
