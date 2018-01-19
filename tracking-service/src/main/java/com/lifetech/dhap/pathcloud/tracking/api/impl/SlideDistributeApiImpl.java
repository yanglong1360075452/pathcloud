package com.lifetech.dhap.pathcloud.tracking.api.impl;

import com.lifetech.dhap.pathcloud.application.application.PathologyApplication;
import com.lifetech.dhap.pathcloud.application.application.dto.PathologyDto;
import com.lifetech.dhap.pathcloud.common.data.PathologyStatus;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.common.response.PageDataVO;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.common.utils.StringUtil;
import com.lifetech.dhap.pathcloud.notification.infrastructure.code.NotificationSource;
import com.lifetech.dhap.pathcloud.security.UserContext;
import com.lifetech.dhap.pathcloud.tracking.api.SlideDistributeApi;
import com.lifetech.dhap.pathcloud.tracking.api.vo.NotificationVO;
import com.lifetech.dhap.pathcloud.tracking.api.vo.SlideDistributeInfo;
import com.lifetech.dhap.pathcloud.tracking.api.vo.SlideDistributeVO;
import com.lifetech.dhap.pathcloud.tracking.application.BlockApplication;
import com.lifetech.dhap.pathcloud.tracking.application.TrackingApplication;
import com.lifetech.dhap.pathcloud.tracking.application.condition.DistributeCondition;
import com.lifetech.dhap.pathcloud.tracking.application.condition.GrossingCon;
import com.lifetech.dhap.pathcloud.tracking.application.condition.SlideCondition;
import com.lifetech.dhap.pathcloud.tracking.application.dto.BlockDetailDto;
import com.lifetech.dhap.pathcloud.tracking.application.dto.DealNotificationDto;
import com.lifetech.dhap.pathcloud.tracking.application.dto.SlideDistributeDto;
import com.lifetech.dhap.pathcloud.tracking.application.dto.SlideDto;
import com.lifetech.dhap.pathcloud.tracking.infrastructure.code.AbnormalHandle;
import com.lifetech.dhap.pathcloud.tracking.infrastructure.code.DistributeHistorySortEnum;
import com.lifetech.dhap.pathcloud.tracking.infrastructure.code.DistributeSortEnum;
import com.lifetech.dhap.pathcloud.user.api.vo.UserSimpleVO;
import com.lifetech.dhap.pathcloud.user.application.dto.UserSimpleDto;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.DefaultValue;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by LiuMei on 2017-01-03.
 */
@Component("slideDistributeApi")
public class SlideDistributeApiImpl implements SlideDistributeApi {

    @Autowired
    private TrackingApplication trackingApplication;

    @Autowired
    private BlockApplication blockApplication;

    @Autowired
    private PathologyApplication pathologyApplication;

    @Override
    public ResponseVO getPrepareDistribute(@DefaultValue("1") Integer page, @DefaultValue("10") Integer length,
                                           Integer departments, Long operator,Integer order,String sort) throws BuzException {
        if (page < 0 || length < 1) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        DistributeCondition pageCondition = new DistributeCondition();
        pageCondition.setSize(length);
        pageCondition.setStart((page - 1) * length);
        pageCondition.setDepartments(departments);
        pageCondition.setOperator(operator);
        if(sort == null){
            sort = "DESC";
        }
        pageCondition.setOrder(DistributeSortEnum.valueOf(order).toString()+" "+sort);
        Long total = trackingApplication.countPrepareDistribute(pageCondition);
        return new PageDataVO(page, length, total, trackingApplication.getPrepareDistribute(pageCondition));
    }

    @Override
    public ResponseVO distribute(SlideDistributeVO slideDistributeVO) throws BuzException {
        if (slideDistributeVO == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        String distributeDoctor = slideDistributeVO.getDistributeDoctor();
        if (StringUtils.isBlank(distributeDoctor) || slideDistributeVO.getDistributeRecords() == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        SlideDistributeDto slideDistributeDto = new SlideDistributeDto();
        BeanUtils.copyProperties(slideDistributeVO, slideDistributeDto);
        blockApplication.slideDistribute(slideDistributeDto);
        return new ResponseVO();
    }

    @Override
    public ResponseVO distributeHistory(@DefaultValue("1") Integer page, @DefaultValue("10") Integer length,
            String filter, Integer departments, Long operator, Long timeStart, Long timeEnd,Integer order,String sort) throws BuzException {
        if (page < 0 || length < 1) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        DistributeCondition con = new DistributeCondition();
        con.setSize(length);
        con.setStart((page - 1) * length);
        con.setDepartments(departments);
        con.setOperator(operator);
        if(sort == null){
            sort = "DESC";
        }
        con.setOrder(DistributeHistorySortEnum.valueOf(order).toString()+" "+sort);
        if (filter != null && filter.contains("-") && filter.length() > 18) {
            String[] pathNos = filter.split("-");
            if (pathNos.length != 2) {
                throw new BuzException(BuzExceptionCode.ErrorParam);
            }
            con.setPathNoStart(pathNos[0].trim());
            con.setPathNoEnd(pathNos[1].trim());
        } else if (filter != null) {
            con.setFilter(StringUtil.escapeExprSpecialWord(filter.trim()));
        }
        if (timeStart != null) {
            con.setTimeStart(new Date(timeStart));
        }
        if (timeEnd != null) {
            con.setTimeEnd(new Date(timeEnd));
        }
        Long total = trackingApplication.countDistributeHistory(con);
        return new PageDataVO(page, length, total, trackingApplication.getDistributeHistory(con));
    }

    @Override
    public ResponseVO getDistributeByPathNo(String pathNo, @DefaultValue("1") Integer page, @DefaultValue("10") Integer length) throws BuzException {
        if (pathNo == null || "".equals(pathNo.trim())) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        if (page < 0 || length < 1) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        SlideCondition condition = new SlideCondition();
        condition.setSize(length);
        condition.setStart((page - 1) * length);
        if (pathNo.indexOf("-") != -1) {
            String[] pathNos = pathNo.split("-");
            if (pathNos.length != 2) {
                throw new BuzException(BuzExceptionCode.ErrorParam);
            }
            condition.setStartPathNo(pathNos[0]);
            condition.setEndPathNo(pathNos[1]);
        } else {
            condition.setPathNo(pathNo);
        }
        Long total = blockApplication.countSlideDistributesByCondition(condition);
        List<SlideDto> slideDtos = blockApplication.getSlideDistributesByCondition(condition);
        List<SlideDistributeInfo> slideDistributeInfos = new ArrayList<>();
        SlideDistributeInfo slideDistributeInfo = null;
        for(SlideDto slideDto : slideDtos){
            slideDistributeInfo = new SlideDistributeInfo();
            BeanUtils.copyProperties(slideDto,slideDistributeInfo);
            UserSimpleDto simpleDto = slideDto.getConfirmUser();
            UserSimpleVO user = new UserSimpleVO();
            BeanUtils.copyProperties(simpleDto,user);
            slideDistributeInfo.setConfirmUser(user);
            slideDistributeInfos.add(slideDistributeInfo);
        }
        return new PageDataVO(page, length, total, slideDistributeInfos);
    }

    @Override
    public ResponseVO blockStatusStatistic() {
        return new ResponseVO(blockApplication.sampleStatusStatistic());
    }

    @Override
    public ResponseVO abnormalBlock(@DefaultValue("1") Integer page, @DefaultValue("10") Integer length, Integer status,
                                    Long totalError) {
        if (page < 0 || length < 1) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        GrossingCon con = new GrossingCon();
        con.setSize(length);
        con.setStart((page - 1) * length);
        con.setBlockStatus(status);
        List<BlockDetailDto> data = blockApplication.getAbnormalBlock(con);
        return new PageDataVO(page, length, totalError, data);
    }

    @Override
    public ResponseVO abnormalBlockDeal(NotificationVO notification) throws BuzException {
        if((notification.getBlockId() == null && notification.getPathId() == null ) || notification.getDealType() == null){
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }else if(AbnormalHandle.valueOf(notification.getDealType()) == null){
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        DealNotificationDto notificationDto = new DealNotificationDto();
        BeanUtils.copyProperties(notification, notificationDto);
        notificationDto.setCreateBy(UserContext.getLoginUserID());
        notificationDto.setSource(NotificationSource.completionConfirm.toCode());

        if(notification.getBlockId() == null && notification.getDealType().equals(3)){//异常终止，待取材的病理
            PathologyDto pathology = new PathologyDto();
            pathology.setId(notificationDto.getPathId());
            pathology.setStatus(PathologyStatus.ErrorEnding.toCode());
            pathology.setUpdateBy(notificationDto.getCreateBy());
            pathologyApplication.updatePathology(pathology);

            return new ResponseVO();
        }

        blockApplication.abnormalBlockDeal(notificationDto);
        return new ResponseVO();
    }
}
