package com.lifetech.dhap.pathcloud.application.domain;

import com.lifetech.dhap.pathcloud.application.application.condition.ApplicationIhcCondition;
import com.lifetech.dhap.pathcloud.application.application.condition.PrintIhcCondition;
import com.lifetech.dhap.pathcloud.application.application.dto.IhcApplicationQueryDto;
import com.lifetech.dhap.pathcloud.application.application.condition.IhcBlockCondition;
import com.lifetech.dhap.pathcloud.application.application.dto.PrintIhcsDetailDto;
import com.lifetech.dhap.pathcloud.application.domain.model.BlockIhc;
import com.lifetech.dhap.pathcloud.application.domain.model.BlockIhcExtend;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 特染申请腊块
 */
public interface BlockIhcRepository {

    int deleteByPrimaryKey(Long id);

    int insert(BlockIhc record);

    BlockIhc selectByPrimaryKey(Long id);

    List<BlockIhc> selectByNumber(String number);

    List<BlockIhc> selectAll();

    int updateByPrimaryKey(BlockIhc record);

    List<BlockIhcExtend> selectByCondition(IhcBlockCondition condition);

    List<IhcApplicationQueryDto> getApplicationIHCs(ApplicationIhcCondition con);

    Long getApplicationIHCsTotal(ApplicationIhcCondition con);

    List<IhcApplicationQueryDto> getIHCs(ApplicationIhcCondition con);

    Long getIHCsTotal(ApplicationIhcCondition con);

    List<Long> selectCurrentIhcIdsByBlockId(long blockId);

    long last();

    void updateStatus(@Param("blockIhcIds") List<Long> blockIhcIds,@Param("updateBy") Long updateBy,@Param("status") int status);

    List<PrintIhcsDetailDto> getPrintIhcs(PrintIhcCondition con);

    /**
     * 根据特检申请TrackingID查询生成的玻片最小状态
     * @param trackingId
     * @return
     */
    Integer selectMinSlideStatusByTracingId(long trackingId);

    Long getPrintIhcsTotal(PrintIhcCondition con);

    /**
     * 根据蜡块ID和标记物查询申请ID
     * @param blockId
     * @param marker
     * @return
     */
    Long selectIdByMarkerAndBlockId(@Param("blockId") long blockId,@Param("marker") String marker);
}