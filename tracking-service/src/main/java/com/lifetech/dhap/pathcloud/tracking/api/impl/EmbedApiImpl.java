package com.lifetech.dhap.pathcloud.tracking.api.impl;

import com.lifetech.dhap.pathcloud.common.data.PathologyStatus;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.common.response.PageDataVO;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.common.utils.StringUtil;
import com.lifetech.dhap.pathcloud.notification.infrastructure.code.NotificationSource;
import com.lifetech.dhap.pathcloud.security.UserContext;
import com.lifetech.dhap.pathcloud.tracking.api.EmbedApi;
import com.lifetech.dhap.pathcloud.tracking.api.vo.BlockCountVO;
import com.lifetech.dhap.pathcloud.tracking.api.vo.EmbedVO;
import com.lifetech.dhap.pathcloud.tracking.api.vo.NoteVO;
import com.lifetech.dhap.pathcloud.tracking.application.BlockApplication;
import com.lifetech.dhap.pathcloud.tracking.application.TrackingApplication;
import com.lifetech.dhap.pathcloud.tracking.application.condition.BlockCondition;
import com.lifetech.dhap.pathcloud.tracking.application.condition.EmbedsCon;
import com.lifetech.dhap.pathcloud.tracking.application.condition.HadOperatedCondition;
import com.lifetech.dhap.pathcloud.tracking.application.condition.TrackingCondition;
import com.lifetech.dhap.pathcloud.tracking.application.dto.*;
import com.lifetech.dhap.pathcloud.tracking.application.infrastructure.code.TrackingOperation;
import com.lifetech.dhap.pathcloud.tracking.infrastructure.code.AbnormalHandle;
import com.lifetech.dhap.pathcloud.tracking.infrastructure.code.EmbedSortEnum;
import com.lifetech.dhap.pathcloud.user.api.vo.UserSimpleVO;
import com.lifetech.dhap.pathcloud.user.application.dto.UserDto;
import com.lifetech.dhap.pathcloud.user.application.dto.UserSimpleDto;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.DefaultValue;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by LiuMei on 2016-12-20.
 */
@Component("embedApi")
public class EmbedApiImpl implements EmbedApi {

    @Autowired
    private BlockApplication blockApplication;

    @Autowired
    private TrackingApplication trackingApplication;

    @Override
    public ResponseVO getBlockBySerialNumber(String blockSerialNumber,Long prePathId) throws BuzException {
        if (StringUtils.isBlank(blockSerialNumber)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        BlockDto blockDto = blockApplication.getBlockBySerialNumber(blockSerialNumber);
        if (blockDto == null) {
            throw new BuzException(BuzExceptionCode.RecordNotExists);
        }

        if (prePathId != null) {
            List<Long> blockIds = blockApplication.getNormalBlocksIdByPathId(prePathId);
            List<Long> hadEmbedBlockIds = blockApplication.getHadEmbedBlocksIdByPathId(prePathId);
            if (blockIds.size() > hadEmbedBlockIds.size()) {//上一个病理号有蜡块号未切片
                blockIds.removeAll(hadEmbedBlockIds);
                List<String> blockNos = new ArrayList<>();
                for (long blockId : blockIds) {
                    blockNos.add(blockApplication.getBlockById(blockId).getSubId());
                }
                return new ResponseVO(blockNos);
            }
        }

        BlockExtendDto dto = blockApplication.getBlockExtendBySerialNumber(blockSerialNumber);
        EmbedVO embedVO = new EmbedVO();
        if (dto != null) {
            BeanUtils.copyProperties(dto, embedVO);
            UserSimpleDto grossingDoctor = dto.getGrossingDoctor();
            if (grossingDoctor != null) {
                UserSimpleVO userSimpleVO = new UserSimpleVO();
                BeanUtils.copyProperties(grossingDoctor, userSimpleVO);
                embedVO.setGrossingDoctor(userSimpleVO);
            }
            long pathId = blockDto.getPathId();
            embedVO.setPrepareEmbedCount(blockApplication.countBlockByStatus(PathologyStatus.PrepareEmbed.toCode()));
            embedVO.setPathBlockCount(blockApplication.getNormalBlocksIdByPathId(pathId).size());
            HadOperatedCondition condition = new HadOperatedCondition();
            condition.setPathId(pathId);
            condition.setStatus(PathologyStatus.PrepareEmbed.toCode());
            embedVO.setPathEmbedCount(blockApplication.countHadOperatedBlocks(condition));

            BlockCondition blockCondition = new BlockCondition();
            blockCondition.setPathId(pathId);
            blockCondition.setStatus(PathologyStatus.PrepareEmbed.toCode());
            List<BlockDto> blockDtoList = blockApplication.getBlockByCondition(blockCondition);
            if(CollectionUtils.isNotEmpty(blockDtoList)){
                List<String> otherEmbed = new ArrayList<>();
                for(BlockDto blockDto1 : blockDtoList){
                    otherEmbed.add(blockDto1.getSubId());
                }
                embedVO.setOtherEmbed(otherEmbed);
            }
        }
        return new ResponseVO(embedVO);
    }

    @Override
    public ResponseVO embedConfirm(Long blockId, NoteVO noteVO) throws BuzException {
        if (blockId == null || blockId <= 0) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }

        BlockDto blockDto = blockApplication.getBlockById(blockId);
        if (blockDto == null) {
            throw new BuzException(BuzExceptionCode.RecordNotExists);
        }
        if (!blockDto.getStatus().equals(PathologyStatus.PrepareEmbed.toCode())) {
            throw new BuzException(BuzExceptionCode.StatusNotMatch);
        }

        Boolean embedPause = blockDto.getEmbedPause();
        if(embedPause != null && embedPause){
            throw new BuzException(BuzExceptionCode.EmbedPause);
        }

        BlockExtendDto extendDto = new BlockExtendDto();
        extendDto.setBlockId(blockId);

        if(noteVO != null){
            String note = noteVO.getNote();
            String noteType = noteVO.getNoteType();
            if (noteType != null && !"".equals(noteType.trim())) {
                extendDto.setEmbedRemarkType(noteType);
            }
            if(note != null && !"".equals(note)){
                if(noteType == null || "".equals(noteType.trim())){
                    extendDto.setEmbedRemarkType("其他");
                }
                extendDto.setEmbedRemark(note);
            }
        }

        blockApplication.embedConfirm(extendDto);
        return new ResponseVO();
    }

    @Override
    public ResponseVO getBlocksCount() throws BuzException {
        Date date = new Date(System.currentTimeMillis() / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset());
        BlockCountVO blockCount = new BlockCountVO();
        HadOperatedCondition condition = new HadOperatedCondition();
        blockCount.setBlockTotal(blockApplication.countHadOperatedBlocks(condition)); //蜡块总数
        condition.setStatus(PathologyStatus.PrepareEmbed.toCode());
        blockCount.setHadOperated(blockApplication.countHadOperatedBlocks(condition)); //已包埋蜡块数
        HadOperatedCondition toCondition = new HadOperatedCondition();
        toCondition.setStartTime(date);
        blockCount.setTodayTotal(blockApplication.countHadOperatedBlocks(toCondition));//今日蜡块总数
        TrackingCondition con = new TrackingCondition();
        con.setOperation(TrackingOperation.embed.toCode());
        con.setOperationTime(date);
        blockCount.setTodayOperated(trackingApplication.countTrackingByCondition(con)); //今日已包埋蜡块数
        blockCount.setPrepareOperate(blockApplication.countBlockByStatus(PathologyStatus.PrepareEmbed.toCode()));
        return new ResponseVO(blockCount);
    }

    @Override
    public ResponseVO getEmbedsQuery(@DefaultValue("1") Integer page, @DefaultValue("10") Integer length, String filter,
                                     Long startTime, Long endTime, Integer status, Long operatorId,Integer order,String sort) throws BuzException {
        if (page < 0 || length < 1){
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        EmbedsCon con = new EmbedsCon();
        con.setStart((page - 1) * length);
        con.setSize(length);
        if (filter != null) {
            if(filter.indexOf("-") != -1){
                String[] pathNos = filter.split("-");
                if(pathNos.length != 2){
                    throw new BuzException(BuzExceptionCode.ErrorParam);
                }
                con.setStartPathNo(pathNos[0].trim());
                con.setEndPathNo(pathNos[1].trim());
            }else{
                con.setFilter(StringUtil.escapeExprSpecialWord(filter.trim()));
            }

        }
        if (startTime != null){
            con.setStartTime(new Date(startTime));
        }
        if (endTime != null){
            con.setEndTime(new Date(endTime));
        }
        con.setStatus(status);
        con.setOperatorId(operatorId);
        if(sort == null){
            sort = "ASC";
        }
        con.setOrder(EmbedSortEnum.valueOf(order).toString()+" "+sort);
        List<InfoQueryDto> embedsQuery = blockApplication.getEmbedsQuery(con);
        Long total=blockApplication.getEmbedsQueryTotal(con);

        return new PageDataVO(page,length,total,embedsQuery);
    }

    @Override
    public ResponseVO getEmbedPerson() throws BuzException {
        List<UserDto> user = blockApplication.getEmbedPerson();
        return new ResponseVO(user);
    }

    @Override
    public ResponseVO applyReGrossing(NoteVO noteVO) throws BuzException {
        long blockId = noteVO.getBlockId();
        String note = noteVO.getNote();
        BlockDto blockDto = blockApplication.getBlockById(blockId);
        if(blockDto == null){
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        Integer status = blockDto.getStatus();
        if(!status.equals(PathologyStatus.PrepareEmbed.toCode())){
            throw new BuzException(BuzExceptionCode.StatusNotMatch);
        }
        DealNotificationDto notificationDto = new DealNotificationDto();
        notificationDto.setBlockId(blockId);
        notificationDto.setDealType(AbnormalHandle.ReGrossing.toCode());
        notificationDto.setCreateBy(UserContext.getLoginUserID());
        notificationDto.setNote(note);
        notificationDto.setSource(NotificationSource.embed.toCode());
        blockApplication.abnormalBlockDeal(notificationDto);
        return new ResponseVO();
    }

    @Override
    public ResponseVO pause(NoteVO noteVO) throws BuzException {
        if (noteVO != null){
            long blockId = noteVO.getBlockId();
            String noteType = noteVO.getNoteType();
            String note = noteVO.getNote();
            BlockDto blockDto = blockApplication.getBlockById(blockId);
            if(blockDto == null){
                throw new BuzException(BuzExceptionCode.ErrorParam);
            }
            if (blockDto.getStatus() != null){
                if(!blockDto.getStatus().equals(PathologyStatus.PrepareEmbed.toCode())){
                    throw new BuzException(BuzExceptionCode.StatusNotMatch);
                }
            }

            TrackingDto trackingDto = new TrackingDto();
            trackingDto.setBlockId(blockId);
            trackingDto.setNote(note);
            trackingDto.setNoteType(noteType);
            trackingDto.setOperation(TrackingOperation.embedPause.toCode());
            blockApplication.embedPause(trackingDto);
            return new ResponseVO();
        }else {
            return new ResponseVO();
        }

    }

    @Override
    public ResponseVO cancelPause(NoteVO noteVO) throws BuzException {
        long blockId = noteVO.getBlockId();
        String noteType = noteVO.getNoteType();
        String note = noteVO.getNote();
        BlockDto blockDto = blockApplication.getBlockById(blockId);
        if(blockDto == null){
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        if(!blockDto.getStatus().equals(PathologyStatus.PrepareEmbed.toCode()) && blockDto.getEmbedPause()){
            throw new BuzException(BuzExceptionCode.StatusNotMatch);
        }
        TrackingDto trackingDto = new TrackingDto();
        trackingDto.setBlockId(blockId);
        trackingDto.setNote(note);
        trackingDto.setNoteType(noteType);
        trackingDto.setOperation(TrackingOperation.embedPauseCancel.toCode());
        blockApplication.embedPause(trackingDto);
        return new ResponseVO();
    }
}
