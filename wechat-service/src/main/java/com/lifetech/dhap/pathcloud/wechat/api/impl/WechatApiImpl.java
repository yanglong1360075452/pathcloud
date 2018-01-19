package com.lifetech.dhap.pathcloud.wechat.api.impl;

import com.lifetech.dhap.pathcloud.application.application.ApplicationApplication;
import com.lifetech.dhap.pathcloud.application.application.ApplicationIhcApplication;
import com.lifetech.dhap.pathcloud.application.application.BookingApplication;
import com.lifetech.dhap.pathcloud.application.application.PathologyApplication;
import com.lifetech.dhap.pathcloud.application.application.condition.IhcBlockCondition;
import com.lifetech.dhap.pathcloud.application.application.condition.WechatCondition;
import com.lifetech.dhap.pathcloud.application.application.dto.*;
import com.lifetech.dhap.pathcloud.application.application.infrastructure.code.ApplicationStatus;
import com.lifetech.dhap.pathcloud.application.application.infrastructure.code.ApplyType;
import com.lifetech.dhap.pathcloud.application.application.infrastructure.code.IhcApplicationStatus;
import com.lifetech.dhap.pathcloud.application.application.infrastructure.code.ResearchType;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.common.response.PageDataVO;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.common.utils.StringUtil;
import com.lifetech.dhap.pathcloud.common.utils.ValidUtil;
import com.lifetech.dhap.pathcloud.security.UserContext;
import com.lifetech.dhap.pathcloud.setting.application.ParamSettingApplication;
import com.lifetech.dhap.pathcloud.setting.application.dto.ParamSettingDto;
import com.lifetech.dhap.pathcloud.setting.application.infrastructure.code.ParamKey;
import com.lifetech.dhap.pathcloud.setting.application.infrastructure.code.SystemKey;
import com.lifetech.dhap.pathcloud.tracking.application.BlockApplication;
import com.lifetech.dhap.pathcloud.tracking.application.TrackingApplication;
import com.lifetech.dhap.pathcloud.tracking.application.condition.ApplicationCondition;
import com.lifetech.dhap.pathcloud.tracking.application.dto.BlockDto;
import com.lifetech.dhap.pathcloud.tracking.application.dto.BlockExtendDto;
import com.lifetech.dhap.pathcloud.tracking.application.dto.SlideDto;
import com.lifetech.dhap.pathcloud.tracking.application.dto.TrackingDto;
import com.lifetech.dhap.pathcloud.user.application.UserApplication;
import com.lifetech.dhap.pathcloud.user.application.dto.UserDto;
import com.lifetech.dhap.pathcloud.user.application.dto.UserSimpleDto;
import com.lifetech.dhap.pathcloud.wechat.api.WechatApi;
import com.lifetech.dhap.pathcloud.wechat.api.vo.*;
import com.lifetech.dhap.pathcloud.wechat.data.WechatApplyType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.DefaultValue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by HP on 2017/5/24.
 */
@Component("wechatApi")
public class WechatApiImpl implements WechatApi {

    @Autowired
    private ApplicationApplication applicationApplication;

    @Autowired
    private BookingApplication bookingApplication;

    @Autowired
    private PathologyApplication pathologyApplication;


    @Autowired
    private BlockApplication blockApplication;

    @Autowired
    private ApplicationIhcApplication applicationIhcApplication;

    @Autowired
    private ParamSettingApplication paramSettingApplication;

    @Autowired
    private UserApplication userApplication;

    @Autowired
    private TrackingApplication trackingApplication;

    @Override
    public ResponseVO createResearch(ResearchVO researchVO) throws BuzException {
        String applicant = researchVO.getApplicant();
        Integer departments = researchVO.getDepartments();
        Integer identity = researchVO.getIdentity();
        String funds = researchVO.getFunds();
        String phone = researchVO.getPhone();
        Integer researchType = researchVO.getResearchType();
        if (departments == null || identity == null || applicant == null || "".equals(applicant.trim()) || funds == null || "".equals(funds.trim()) || phone == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }

        Integer status;
        if (researchType.equals(ResearchType.Freeze.toCode())) {
            status = ApplicationStatus.Ending.toCode();
            if (researchVO.getBooks() == null || researchVO.getBooks().isEmpty()) {
                throw new BuzException(BuzExceptionCode.ErrorParam);
            }
        } else {
            status = ApplicationStatus.PrepareRegister.toCode();
        }


        List<SampleVO> samples = researchVO.getSamples();
        if (samples == null || samples.size() <= 0) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }

        List<SampleDto> sampleDtos = sampleVOsToDtos(samples);
        ApplicationDto applicationDto = new ApplicationDto();
        BeanUtils.copyProperties(researchVO, applicationDto);
        applicationDto.setCreateBy(UserContext.getLoginUserID());
        applicationDto.setStatus(status);
        applicationDto.setSamples(sampleDtos);
        ResearchDto researchDto = new ResearchDto();
        List<BookVO> books = researchVO.getBooks();
        BeanUtils.copyProperties(researchVO, researchDto);
        if (books != null && books.size() > 0) {
            List<BookDto> bookDtos = new ArrayList<>();
            BookDto bookDto = null;
            for (BookVO bookVO : books) {
                bookDto = new BookDto();
                BeanUtils.copyProperties(bookVO, bookDto);
                bookDtos.add(bookDto);
            }
            researchDto.setBooks(bookDtos);
        }
        applicationDto.setResearch(researchDto);
        applicationDto.setDepartments(departments);
        applicationDto.setDoctor(UserContext.getUserDetails().getFirstName());
        applicationDto.setDoctorTel(phone);
        applicationDto.setApplyType(ApplyType.Research.toCode());
        applicationApplication.createApplication(applicationDto);
        return new ResponseVO();
    }

    @Override
    public ResponseVO getApplicationBooking(Long timeStart, Long timeEnd) throws BuzException {
        return new ResponseVO(applicationApplication.freezeBookingQuery(new Date(timeStart), new Date(timeEnd)));
    }

    @Override
    public ResponseVO create(IhcApplicationVO ihcApplicationVO) throws BuzException {
        if (ihcApplicationVO == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        String applyUser = ihcApplicationVO.getApplyUser();
        String applyTel = ihcApplicationVO.getApplyTel();
        List<IhcBlockVO> ihcBlocks = ihcApplicationVO.getIhcBlocks();
        if (applyUser == null || "".equals(applyUser.trim()) || applyTel == null || "".equals(applyTel.trim()) || ihcBlocks == null || ihcBlocks.size() <= 0) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        List<IhcBlockDto> ihcBlockDtos = new ArrayList<>();
        IhcBlockDto ihcBlockDto = null;
        for (IhcBlockVO ihcBlockVO : ihcBlocks) {
            String pathNo = ihcBlockVO.getSerialNumber();
            String subId = ihcBlockVO.getSubId();
            Integer specialDye = ihcBlockVO.getSpecialDye();
            List<String> ihcMarker = ihcBlockVO.getIhcMarker();
            if (pathNo == null || "".equals(pathNo) || subId == null || "".equals(subId)) {
                throw new BuzException(BuzExceptionCode.ErrorParam);
            }
            if (specialDye != null) {
                if (ihcMarker == null || ihcMarker.size() <= 0) {
                    throw new BuzException(BuzExceptionCode.ErrorParam);
                }
            }
            BlockDto blockDto = blockApplication.getBlockBySerialNumber(pathNo + subId);
            if (blockDto == null || blockDto.getId() == null) {
                throw new BuzException(BuzExceptionCode.RecordNotExists);
            }
            if (blockDto.getMarker() != null) {
                throw new BuzException(BuzExceptionCode.RecordExists);
            }
            IhcBlockCondition condition = new IhcBlockCondition();
            condition.setBlockId(blockDto.getId());
            condition.setStatus(IhcApplicationStatus.PrepareConfirm.toCode());
            List<IhcBlockDto> ihcBlockDtos1 = applicationIhcApplication.getIhcBlockByCondition(condition);
            if (ihcBlockDtos1 != null && ihcBlockDtos1.size() > 0) {
                throw new BuzException(BuzExceptionCode.RecordExists);
            }

            ihcBlockDto = new IhcBlockDto();
            BeanUtils.copyProperties(ihcBlockVO, ihcBlockDto);
            ihcBlockDto.setBlockId(blockDto.getId());
            ihcBlockDto.setPathId(blockDto.getPathId());
            ihcBlockDtos.add(ihcBlockDto);
        }
        IhcApplicationDto ihcApplicationDto = new IhcApplicationDto();
        BeanUtils.copyProperties(ihcApplicationVO, ihcApplicationDto);
        ihcApplicationDto.setIhcBlocks(ihcBlockDtos);
        applicationIhcApplication.createApplication(ihcApplicationDto);
        return new ResponseVO();
    }

    @Override
    public ResponseVO getBlocks(@DefaultValue("1") Integer page,
                                @DefaultValue("10") Integer length, Long createTimeStart,
                                Long createTimeEnd, String pathNo) {
        if (page < 0 || length < 1) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        ApplicationCondition con = new ApplicationCondition();
        con.setSize(length);
        con.setStart((page - 1) * length);
        con.setCreateBy(UserContext.getLoginUserID());
        con.setPathNo(pathNo);
        if (createTimeStart != null) {
            con.setCreateTimeStart(new Date(createTimeStart));
        }
        if (createTimeEnd != null) {
            con.setCreateTimeEnd(new Date(createTimeEnd));
        }
        con.setCreateBy(UserContext.getLoginUserID());
        List<BlockExtendDto> blockExtendDtos = blockApplication.getNotSpecialBlocksByCondition(con);
        List<BlockVO> blockVOS = new ArrayList<>();
        BlockVO blockVO = null;
        if (blockExtendDtos != null && blockExtendDtos.size() > 0) {
            for (BlockExtendDto blockExtendDto : blockExtendDtos) {
                blockVO = new BlockVO();
                BeanUtils.copyProperties(blockExtendDto, blockVO);
                blockVOS.add(blockVO);
            }
        }
        return new PageDataVO(page, length, blockApplication.countNotSpecialBlocksByCondition(con), blockVOS);
    }

    @Override
    public ResponseVO getDepartments() throws BuzException {
        List<ParamSettingDto> data = paramSettingApplication.getContentToListByKey(ParamKey.Departments.toString());
        List<ParamSettingDto> lists = new ArrayList<>();
        for (ParamSettingDto psd : data) {
            if (psd.getName() != null) {
                lists.add(psd);
            }
        }
        return new ResponseVO(lists);
    }

    @Override
    public ResponseVO getDepartmentDesc(Integer code) {
        return new ResponseVO(paramSettingApplication.getNameByKeyAndCode(ParamKey.Departments.toString(), code));
    }

    @Override
    public ResponseVO getUserInfo() {
        long id = UserContext.getLoginUserID();
        UserDto userDto = userApplication.getUserByUserID(id);
        if (userDto == null) {
            throw new BuzException(BuzExceptionCode.UserNotExists);
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userDto, userVO);
        return new ResponseVO(userVO);
    }

    @Override
    public ResponseVO updateUserInfo(UserUpdateReq user) throws BuzException {
        String firstName = user.getFirstName();
        String username = user.getUsername();
        if (username == null || "".equals(username) || firstName == null || "".equals(firstName) || user.getDepartments() == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        UserDto userDto1 = userApplication.getUserByUsername(username);
        if (userDto1 == null) {
            throw new BuzException(BuzExceptionCode.UserNotExists);
        }
        String email = user.getEmail();
        String phone = user.getPhone();
        if (email != null && !"".equals(email) && !ValidUtil.email(email)) {
            throw new BuzException(BuzExceptionCode.WrongEmail);
        }
        if (phone != null && !"".equals(phone) && !ValidUtil.phoneNumber(phone)) {
            throw new BuzException(BuzExceptionCode.WrongPhoneNumber);
        }
        firstName = firstName.trim();
        if (email != null) {
            user.setEmail(email.trim());
        }
        if (phone != null) {
            user.setPhone(phone.trim());
        }
        user.setFirstName(firstName);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        userDto.setId(userDto1.getId());
        userDto.setUpdateBy(UserContext.getLoginUserID());
        userApplication.updateUser(userDto);
        return new ResponseVO();
    }

    @Override
    public ResponseVO getDyeType() {
        return new ResponseVO(paramSettingApplication.getContentToListByKey(SystemKey.SpecialDye.toString()));
    }

    @Override
    public ResponseVO cancelApplication(Long id) throws BuzException {
        if (id <= 0) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        ApplicationDto applicationDto = applicationApplication.getApplicationById(id);
        if (applicationDto == null) {
            throw new BuzException(BuzExceptionCode.ApplicationNotExists);
        }
        if (!applicationDto.getCreateBy().equals(UserContext.getLoginUserID())) {
            throw new BuzException(BuzExceptionCode.AccessDenied);
        }
        ResearchDto research = applicationDto.getResearch();
        if (research == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        Integer researchType = research.getResearchType();
        applicationDto.setUpdateBy(UserContext.getLoginUserID());
        if (researchType.equals(ResearchType.Freeze.toCode())) {
            applicationDto.setStatus(ApplicationStatus.Cancel.toCode());
            applicationApplication.cancelFreezeApplication(applicationDto);
        } else {
            Integer status = applicationDto.getStatus();
            if (!status.equals(ApplicationStatus.PrepareRegister.toCode())) {
                throw new BuzException(BuzExceptionCode.ApplicationStatusChange);
            }
            applicationDto.setStatus(ApplicationStatus.Cancel.toCode());
            applicationApplication.updateApplicationStatus(applicationDto);
        }
        return new ResponseVO();
    }

    @Override
    public ResponseVO cancelDye(Long id) throws BuzException {
        applicationIhcApplication.cancelApplication(id);
        return new ResponseVO();
    }

    @Override
    public ResponseVO testCookie() {
        return new ResponseVO();
    }

    @Override
    public ResponseVO getApplicationDetail(long id, int type) throws BuzException {
        if (WechatApplyType.Normal.toCode().equals(type)) {
            PathologyDto pathologyDto = pathologyApplication.getPathologyByApplicationId(id);
            if (pathologyDto != null && pathologyDto.getId() != null) {
                List<ApplicationDetailVO> queryConfirmVOs = new ArrayList<>();
                List<TrackingDto> blockTracking = trackingApplication.getBlockTracking(pathologyDto.getId());
                for (TrackingDto trackingDto : blockTracking) {
                    queryConfirmVOs.add(trackingDtoToVO(trackingDto));
                }
                Collections.sort(queryConfirmVOs);
                return new ResponseVO(queryConfirmVOs);
            } else {
                return new ResponseVO();
            }
        } else if (WechatApplyType.Freeze.toCode().equals(type)) {
            ApplicationDto applicationDto = applicationApplication.getApplicationById(id);
            if (applicationDto == null) {
                throw new BuzException(BuzExceptionCode.ErrorParam);
            }
            ResearchDto research = applicationDto.getResearch();
            if (research == null) {
                throw new BuzException(BuzExceptionCode.ErrorParam);
            }
            FreezeDetailVO freezeDetailVO = new FreezeDetailVO();
            List<BookDto> bookDtos = bookingApplication.getBooksByApplicationId(id);
            if (bookDtos != null && bookDtos.size() > 0) {
                List<BookVO> bookVOS = new ArrayList<>();
                BookVO bookVO = null;
                for (BookDto bookDto : bookDtos) {
                    bookVO = new BookVO();
                    BeanUtils.copyProperties(bookDto, bookVO);
                    bookVOS.add(bookVO);
                }
                freezeDetailVO.setBooks(bookVOS);
            }
            List<SampleDto> samples = applicationDto.getSamples();
            List<SampleVO> sampleVOS = new ArrayList<>();
            SampleVO sampleVO = null;
            if (samples != null && samples.size() > 0) {
                for (SampleDto sampleDto : samples) {
                    sampleVO = new SampleVO();
                    BeanUtils.copyProperties(sampleDto, sampleVO);
                    sampleVOS.add(sampleVO);
                }
            }
            freezeDetailVO.setSamples(sampleVOS);
            return new ResponseVO(freezeDetailVO);
        } else {
            List<SlideDto> slideDtos = blockApplication.getSlideInfoByIhcId(id);
            if (slideDtos != null && slideDtos.size() > 0) {
                List<DyeDetailVO> dyeDetailVOS = new ArrayList<>();
                DyeDetailVO dyeDetailVO;
                for (SlideDto dto : slideDtos) {
                    dyeDetailVO = new DyeDetailVO();
                    dyeDetailVO.setBlockSubId(dto.getBlockSubId());
                    dyeDetailVO.setDyeCategory(dto.getSpecialDye());
                    dyeDetailVO.setDyeCategoryDesc(dto.getSpecialDyeDesc());
                    dyeDetailVO.setPathNo(dto.getPathNo());
                    dyeDetailVO.setId(dto.getId());
                    dyeDetailVO.setIhcMarker(dto.getMarker());
                    dyeDetailVO.setOperateTime(dto.getUpdateTime());
                    dyeDetailVO.setSlideSubId(dto.getSubId());
                    dyeDetailVOS.add(dyeDetailVO);
                }
                return new ResponseVO(dyeDetailVOS);
            } else {
                return new ResponseVO();
            }
        }
    }

    @Override
    public ResponseVO getMyApplications(@DefaultValue("1") Integer page, @DefaultValue("10") Integer length,
                                        Long createTimeStart, Long createTimeEnd,
                                        Integer status, Integer type, String filter, Integer sort) throws BuzException {
        if (page < 0 || length < 1) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        WechatCondition con = new WechatCondition();
        con.setSize(length);
        con.setStart((page - 1) * length);
        con.setCreateBy(UserContext.getLoginUserID());

        if (createTimeStart != null) {
            con.setCreateTimeStart(new Date(createTimeStart));
        }
        if (createTimeEnd != null) {
            con.setCreateTimeEnd(new Date(createTimeEnd));
        }

        if (status != null) {
            con.setStatus(status);
        }

        if (type != null) {
            con.setType(type);
        }

        if (filter != null) {
            con.setFilter(StringUtil.escapeExprSpecialWord(filter));
        }
        if (sort != null) {
            if (sort == 1) {
                con.setOrder("applicationTime" + " " + "asc");
            }
            if (sort == 2) {
                con.setOrder("applicationTime" + " " + "desc");
            }

        }


        List<WechatInfoDto> data = pathologyApplication.getMyApplications(con);
        Long total = pathologyApplication.getMyApplicationsTotal(con);


        return new PageDataVO(page, length, total, data);
    }

    @Override
    public ResponseVO checkMessage() throws BuzException {
        //todo
        return null;
    }

    private ApplicationDetailVO trackingDtoToVO(TrackingDto trackingDto) {
        ApplicationDetailVO applicationDetailVO = new ApplicationDetailVO();
        if (trackingDto != null) {
            long blockId = trackingDto.getBlockId();
            BlockDto blockDto = blockApplication.getBlockById(blockId);
            String subId;
            Long parentId = blockDto.getParentId();
            if (parentId == null) {
                subId = blockDto.getSubId();
            } else {
                subId = blockApplication.getBlockById(parentId).getSubId();
                applicationDetailVO.setSlideSubId(blockDto.getSubId());
            }
            BeanUtils.copyProperties(trackingDto, applicationDetailVO);
            applicationDetailVO.setBlockSubId(subId);
            applicationDetailVO.setOperationDesc(trackingDto.getOperationName());
            UserSimpleDto userSimpleDto = userApplication.getUserSimpleInfoById(trackingDto.getOperatorId());
            applicationDetailVO.setOperator(userSimpleDto.getFirstName());
        }
        return applicationDetailVO;
    }

    public static List<SampleDto> sampleVOsToDtos(List<SampleVO> sampleVOS) {
        if (sampleVOS != null && sampleVOS.size() > 0) {
            SampleDto sampleDto = null;
            List<SampleDto> sampleDtos = new ArrayList<>();
            for (SampleVO sampleVO : sampleVOS) {
                if (sampleVO == null || sampleVO.getName() == null || "".equals(sampleVO.getName())) {
                    throw new BuzException(BuzExceptionCode.ErrorParam);
                }
                sampleDto = new SampleDto();
                BeanUtils.copyProperties(sampleVO, sampleDto);
                sampleDtos.add(sampleDto);
            }
            return sampleDtos;
        }
        return null;
    }


    private ResearchVO researchDtoToVO(ResearchDto dto) {
        if (dto != null) {
            ResearchVO researchVO = new ResearchVO();
            BeanUtils.copyProperties(dto, researchVO);
            if (ResearchType.Freeze.toCode().equals(dto.getResearchType())) {
                List<BookDto> bookDtos = bookingApplication.getBooksByApplicationId(dto.getId());
                if (bookDtos != null && bookDtos.size() > 0) {
                    List<BookVO> bookVOS = new ArrayList<>();
                    BookVO bookVO = null;
                    for (BookDto bookDto : bookDtos) {
                        bookVO = new BookVO();
                        BeanUtils.copyProperties(bookDto, bookVO);
                        bookVOS.add(bookVO);
                    }
                    researchVO.setBooks(bookVOS);
                }
            }
            researchVO.setResearchTypeDesc(ResearchType.getNameByCode(dto.getResearchType()));
            return researchVO;
        } else {
            return null;
        }
    }

}
