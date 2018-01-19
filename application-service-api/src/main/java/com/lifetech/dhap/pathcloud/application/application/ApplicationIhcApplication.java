package com.lifetech.dhap.pathcloud.application.application;

import com.lifetech.dhap.pathcloud.application.application.condition.ApplicationIhcCondition;
import com.lifetech.dhap.pathcloud.application.application.condition.IhcBlockCondition;
import com.lifetech.dhap.pathcloud.application.application.condition.PrintIhcCondition;
import com.lifetech.dhap.pathcloud.application.application.dto.IhcApplicationDto;
import com.lifetech.dhap.pathcloud.application.application.dto.IhcApplicationQueryDto;
import com.lifetech.dhap.pathcloud.application.application.dto.IhcBlockDto;
import com.lifetech.dhap.pathcloud.application.application.dto.PrintIhcsDetailDto;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;

import java.util.List;

/**
 * Created by LiuMei on 2017-04-01.
 */
public interface ApplicationIhcApplication {

    /**
     * 创建特染申请
     * @param ihcApplicationDto
     * @return
     * @throws BuzException
     */
    void createApplication(IhcApplicationDto ihcApplicationDto);

    /**
     * 更新特染申请
     * 补充蜡块
     * @param ihcApplicationDto
     * @throws BuzException
     */
    void updateApplication(IhcApplicationDto ihcApplicationDto) throws BuzException;

    IhcApplicationDto getApplicationIhcByPathNoAndType(String pathNo,Integer type);

    List<IhcApplicationQueryDto> getApplicationIHCs(ApplicationIhcCondition con);

    Long getApplicationIHCsTotal(ApplicationIhcCondition con);
    /**
     * 撤销蜡块染色申请
     * @param ihcBlockDto
     * @throws BuzException
     */
    void cancelApplication(IhcBlockDto ihcBlockDto);

    /**
     *  撤销整个蜡块申请
     * @param ihcId
     */
    void cancelApplication(long ihcId);

    /**
     * 根据ID查询蜡块特染申请内容
     * @param ihcBlockId
     * @return
     */
    IhcBlockDto getIhcBlockById(long ihcBlockId);

    /**
     * 根据特殊申请号查询特染申请内容
     * @param number
     * @return
     */
    List<IhcBlockDto> getIhcBlocksByNumber(String number);

    /**
     * 染色确认
     * @param ihcBlockDTOs
     */
    void confirmApplication(List<IhcBlockDto> ihcBlockDTOs);

    /**
     * 延迟执行
     * @param ihcBlockIds
     */
    void delayApplication(List<Long> ihcBlockIds);

    /**
     * 根据条件查询
     * @param condition
     * @return
     */
    List<IhcBlockDto> getIhcBlockByCondition(IhcBlockCondition condition);

    /**
     * 根据蜡块ID查询当前染色申请ID列表
     * @param blockId
     * @return
     */
    List<Long> getCurrentIhcIdsByBlockId(long blockId);

    /**
     *免疫组化查询
     * @param con
     * @return
     */
    List<IhcApplicationQueryDto> getIHCs(ApplicationIhcCondition con);

    /**
     *免疫组化查询总数
     * @param con
     * @return
     */
    Long getIHCsTotal(ApplicationIhcCondition con);

    /**
     * 更新蜡块申请信息
     * @param ihcBlockDto
     */
    void updateIhcBlock(IhcBlockDto ihcBlockDto);

    List<PrintIhcsDetailDto> getPrintIhcs(PrintIhcCondition con);

    Long getPrintIhcsTotal(PrintIhcCondition con);

    /**
     * 根据蜡块特检申请ID查询的生成玻片最小状态
     * @param blockIhcId
     * @return
     */
    Integer getMinSlideStatusByTrackingId(long blockIhcId);

    /**
     * 检查蜡块标记物是否重复
     * @param blockId
     * @param marker
     * @return
     */
    Boolean checkRepeatMarkerByBlockId(long blockId,String marker);
}
