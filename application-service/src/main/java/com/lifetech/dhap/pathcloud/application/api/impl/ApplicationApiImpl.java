package com.lifetech.dhap.pathcloud.application.api.impl;

import com.google.gson.Gson;
import com.lifetech.dhap.pathcloud.application.api.ApplicationApi;
import com.lifetech.dhap.pathcloud.application.api.vo.*;
import com.lifetech.dhap.pathcloud.application.application.ApplicationApplication;
import com.lifetech.dhap.pathcloud.application.application.BookingApplication;
import com.lifetech.dhap.pathcloud.application.application.condition.ApplicationCondition;
import com.lifetech.dhap.pathcloud.application.application.dto.*;
import com.lifetech.dhap.pathcloud.application.application.infrastructure.code.ApplicationStatus;
import com.lifetech.dhap.pathcloud.application.application.infrastructure.code.ApplyType;
import com.lifetech.dhap.pathcloud.application.application.infrastructure.code.ResearchType;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.common.response.PageDataVO;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.common.utils.StringUtil;
import com.lifetech.dhap.pathcloud.security.UserContext;
import com.lifetech.dhap.pathcloud.setting.application.ParamSettingApplication;
import com.lifetech.dhap.pathcloud.setting.application.dto.ApplicationRequiredDto;
import com.lifetech.dhap.pathcloud.setting.application.infrastructure.code.SystemKey;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.DefaultValue;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by LuoMo on 2016-11-22.
 */
@Component("applicationApi")
public class ApplicationApiImpl implements ApplicationApi {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationApiImpl.class);

    @Autowired
    private ApplicationApplication applicationApplication;

    @Autowired
    private ParamSettingApplication paramSettingApplication;

    @Autowired
    private BookingApplication bookingApplication;

    @Override
    public ResponseVO createApplication(ApplicationVO applicationVO) throws BuzException, IllegalAccessException {
        String doctor = applicationVO.getDoctor();
        if (StringUtils.isBlank(doctor)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        List<ApplicationRequiredDto> applicationRequired = paramSettingApplication.getContentToListByKey(SystemKey.ApplicationRequired.toString());
        if (CollectionUtils.isNotEmpty(applicationRequired)) {
            for (ApplicationRequiredDto applicationRequiredDto : applicationRequired) {
                String name = applicationRequiredDto.getName();
                Boolean required = applicationRequiredDto.getRequired();
                if (required) {
                    //获得对象所有属性
                    Field[] fields = applicationVO.getClass().getDeclaredFields();
                    if (ArrayUtils.isNotEmpty(fields)) {
                        Field field;
                        for (int i = 0; i < fields.length; i++) {
                            field = fields[i];
                            //修改访问权限
                            field.setAccessible(true);
                            if (field.getName().equals(name)) {
                                Object o = field.get(applicationVO);
                                if (o == null) {
                                    throw new BuzException(BuzExceptionCode.ErrorParam);
                                }
                            }
                        }
                    }
                }
            }
        }
        List<SampleVO> samples = applicationVO.getSamples();
        if (CollectionUtils.isEmpty(samples)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        List<SampleDto> sampleDTOs = sampleVOsToDTOs(samples);
        ApplicationDto applicationDto = new ApplicationDto();
        BeanUtils.copyProperties(applicationVO, applicationDto);
        applicationDto.setCreateBy(UserContext.getLoginUserID());
        //设置申请单状态为未登记
        applicationDto.setStatus(ApplicationStatus.PrepareRegister.toCode());
        applicationDto.setSamples(sampleDTOs);
        applicationDto.setApplyType(ApplyType.Clinic.toCode());
        applicationDto = applicationApplication.createApplication(applicationDto);
        BeanUtils.copyProperties(applicationDto, applicationVO);
        return new ResponseVO(applicationVO);
    }

    @Override
    public ResponseVO createResearch(ResearchVO researchVO) throws BuzException, ParseException {
        String applicant = researchVO.getApplicant();
        Integer departments = researchVO.getDepartments();
        String funds = researchVO.getFunds();
        String phone = researchVO.getPhone();
        Integer researchType = researchVO.getResearchType();
        if (StringUtils.isBlank(applicant) || StringUtils.isBlank(funds) || phone == null || researchType == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        Integer status;
        List<BookVO> books = researchVO.getBooks();
        if (researchType.equals(ResearchType.Freeze.toCode())) {
            status = ApplicationStatus.Ending.toCode();
            if (CollectionUtils.isEmpty(books)) {
                throw new BuzException(BuzExceptionCode.ErrorParam);
            }
        } else {
            status = ApplicationStatus.PrepareRegister.toCode();
        }

        List<SampleVO> samples = researchVO.getSamples();
        if (CollectionUtils.isEmpty(samples)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }

        List<SampleDto> sampleDTOs = sampleVOsToDTOs(samples);
        ApplicationDto applicationDto = new ApplicationDto();
        BeanUtils.copyProperties(researchVO, applicationDto);
        applicationDto.setCreateBy(UserContext.getLoginUserID());
        applicationDto.setStatus(status);
        applicationDto.setSamples(sampleDTOs);
        ResearchDto researchDto = new ResearchDto();
        BeanUtils.copyProperties(researchVO, researchDto);
        if (CollectionUtils.isNotEmpty(books)) {
            List<BookDto> bookDTOs = new ArrayList<>();
            BookDto bookDto;
            for (BookVO bookVO : books) {
                bookDto = new BookDto();
                BeanUtils.copyProperties(bookVO, bookDto);
                bookDTOs.add(bookDto);
            }
            researchDto.setBooks(bookDTOs);
        }
        applicationDto.setResearch(researchDto);
        applicationDto.setDepartments(departments);
        applicationDto.setDoctor(UserContext.getUserDetails().getFirstName());
        applicationDto.setDoctorTel(phone);
        applicationDto.setApplyType(ApplyType.Research.toCode());
        ApplicationDto application = applicationApplication.createApplication(applicationDto);
        researchVO = researchDtoToVO(application.getResearch());
        researchVO.setSerialNumber(application.getSerialNumber());
        List<SampleDto> sampleDtoList = application.getSamples();
        if (CollectionUtils.isNotEmpty(sampleDtoList)) {
            List<SampleVO> sampleVOS = new ArrayList<>();
            SampleVO sampleVO;
            for (SampleDto sampleDto : sampleDtoList) {
                sampleVO = new SampleVO();
                BeanUtils.copyProperties(sampleDto, sampleVO);
                sampleVOS.add(sampleVO);
            }
            researchVO.setSamples(sampleVOS);
        }
        return new ResponseVO(researchVO);
    }

    @Override
    public ResponseVO createConsult(ConsultApplicationVO consultApplicationVO) throws BuzException {
        String patientName = consultApplicationVO.getPatientName();
        Integer sex = consultApplicationVO.getSex();
        Integer hospital = consultApplicationVO.getHospital();
        if (StringUtils.isBlank(patientName) || sex == null || hospital == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        ApplicationDto applicationDto = new ApplicationDto();
        BeanUtils.copyProperties(consultApplicationVO, applicationDto);
        List<OriginVO> origins = consultApplicationVO.getOrigins();
        if (!CollectionUtils.isEmpty(origins)) {
            Gson gson = new Gson();
            applicationDto.setMedicalHistory(gson.toJson(origins));
        }
        applicationDto.setCreateBy(UserContext.getLoginUserID());
        //设置申请单状态为未登记
        applicationDto.setStatus(ApplicationStatus.PrepareRegister.toCode());
        applicationDto.setApplyType(ApplyType.Consult.toCode());
        ApplicationDto application = applicationApplication.createApplication(applicationDto);
        return new ResponseVO(application);
    }

    public static List<SampleDto> sampleVOsToDTOs(List<SampleVO> sampleVOS) {
        List<SampleDto> sampleDtoList = null;
        if (CollectionUtils.isNotEmpty(sampleVOS)) {
            sampleDtoList = new ArrayList<>();
            SampleDto sampleDto;
            for (SampleVO sampleVO : sampleVOS) {
                if (sampleVO == null || StringUtils.isBlank(sampleVO.getName())) {
                    throw new BuzException(BuzExceptionCode.ErrorParam);
                }
                sampleDto = new SampleDto();
                BeanUtils.copyProperties(sampleVO, sampleDto);
                sampleDtoList.add(sampleDto);
            }
        }
        return sampleDtoList;
    }

    @Override
    public ResponseVO getApplicationBySerialNumber(String serialNumber) throws BuzException {
        if (StringUtils.isBlank(serialNumber)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }

        ApplicationDto applicationDto;
        if (serialNumber.substring(0, 2).equals("SA")) {
            applicationDto = applicationApplication.getApplicationBySampleSerialNumber(serialNumber);
        } else {
            applicationDto = applicationApplication.getApplicationBySerialNumber(serialNumber);
        }

        if (applicationDto == null) {
            throw new BuzException(BuzExceptionCode.ApplicationNotExists);
        }
        ApplicationVO applicationVO = new ApplicationVO();
        BeanUtils.copyProperties(applicationDto, applicationVO);
        ResearchDto research = applicationDto.getResearch();
        applicationVO.setResearch(researchDtoToVO(research));
        return new ResponseVO(applicationVO);
    }

    @Override
    public ResponseVO getApplicationByPathologyNo(String pathologyNo) throws BuzException {
        if (StringUtils.isBlank(pathologyNo)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        List<ApplicationDto> applicationDtoList = applicationApplication.getApplicationByPathologyNo(pathologyNo);
        List<ApplicationVO> applicationVOS = null;
        if (CollectionUtils.isNotEmpty(applicationDtoList)) {
            applicationVOS = new ArrayList<>();
            ApplicationVO applicationVO;
            for (ApplicationDto applicationDto : applicationDtoList) {
                applicationVO = new ApplicationVO();
                BeanUtils.copyProperties(applicationDto, applicationVO);
                ResearchDto research = applicationDto.getResearch();
                applicationVO.setResearch(researchDtoToVO(research));
                applicationVOS.add(applicationVO);
            }
        }
        return new ResponseVO(applicationVOS);
    }

    private ResearchVO researchDtoToVO(ResearchDto dto) {
        ResearchVO researchVO = null;
        if (dto != null) {
            researchVO = new ResearchVO();
            BeanUtils.copyProperties(dto, researchVO);
            if (ResearchType.Freeze.toCode().equals(dto.getResearchType())) {
                List<BookDto> bookDTOs = bookingApplication.getBooksByApplicationId(dto.getId());
                if (CollectionUtils.isNotEmpty(bookDTOs)) {
                    List<BookVO> bookVOS = new ArrayList<>();
                    BookVO bookVO;
                    for (BookDto bookDto : bookDTOs) {
                        bookVO = new BookVO();
                        BeanUtils.copyProperties(bookDto, bookVO);
                        bookVOS.add(bookVO);
                    }
                    researchVO.setBooks(bookVOS);
                }
            }
            researchVO.setResearchTypeDesc(ResearchType.getNameByCode(dto.getResearchType()));
        }
        return researchVO;
    }

    @Override
    public ResponseVO getApplications(@DefaultValue("1") Integer page, @DefaultValue("10") Integer length,
                                      String filter, Integer status, Long createTimeStart, Long createTimeEnd) throws BuzException {
        long startTime = System.currentTimeMillis();
        if (page < 0 || length < 1) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        ApplicationCondition con = new ApplicationCondition();
        con.setSize(length);
        con.setStart((page - 1) * length);
        if (filter != null) {
            con.setFilter(StringUtil.escapeExprSpecialWord(filter.trim()));
        }
        con.setStatus(status);
        con.setOrder(" create_time desc");
        con.setCreateBy(UserContext.getLoginUserID());
        if (createTimeStart != null) {
            con.setCreateTimeStart(new Date(createTimeStart));
        }
        if (createTimeEnd != null) {
            con.setCreateTimeEnd(new Date(createTimeEnd));
        }
        long startTime1 = System.currentTimeMillis();
        List<ApplicationBriefDto> data = applicationApplication.getApplicationBriefByCondition(con);
        Long total = applicationApplication.getApplicationTotalByCondition(con);

        long endTime = System.currentTimeMillis();
        if (endTime - startTime > 500) {
            logger.warn("getApplications takes " + (endTime - startTime) + " ms. read db takes " + (endTime - startTime1));
        }

        return new PageDataVO(page, length, total, data);
    }

    @Override
    public ResponseVO cancelApplication(long id) throws BuzException {
        if (id <= 0) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        ApplicationDto applicationDto = applicationApplication.getApplicationById(id);
        if (applicationDto == null) {
            throw new BuzException(BuzExceptionCode.ApplicationNotExists);
        }
        Integer status = applicationDto.getStatus();
        if (!status.equals(ApplicationStatus.PrepareRegister.toCode())) {
            throw new BuzException(BuzExceptionCode.ApplicationStatusChange);
        }
        if (!applicationDto.getCreateBy().equals(UserContext.getLoginUserID())) {
            throw new BuzException(BuzExceptionCode.AccessDenied);
        }
        applicationDto.setStatus(ApplicationStatus.Cancel.toCode());
        applicationDto.setUpdateBy(UserContext.getLoginUserID());
        applicationApplication.updateApplicationStatus(applicationDto);

        return new ResponseVO();
    }

    @Override
    public ResponseVO cancelFreezeApplication(long id) throws BuzException {
        if (id <= 0) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        ApplicationDto applicationDto = applicationApplication.getApplicationById(id);
        if (applicationDto == null) {
            throw new BuzException(BuzExceptionCode.ApplicationNotExists);
        }
        applicationDto.setStatus(ApplicationStatus.Cancel.toCode());
        applicationDto.setUpdateBy(UserContext.getLoginUserID());
        applicationApplication.cancelFreezeApplication(applicationDto);
        return new ResponseVO();
    }

    @Override
    public ResponseVO rejectApplication(Long id, RejectVO rejectVO) throws BuzException {
        if (id <= 0 || rejectVO == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        String rejectReason = rejectVO.getRejectReason();
        String reasonType = rejectVO.getReasonType();
        if (StringUtils.isBlank(reasonType) && StringUtils.isBlank(rejectReason)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        if (StringUtils.isBlank(reasonType)) {
            reasonType = "其他";
        }
        ApplicationDto applicationDto = applicationApplication.getApplicationById(id);
        if (applicationDto == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        Integer status = applicationDto.getStatus();
        if (!status.equals(ApplicationStatus.PrepareRegister.toCode())) {
            throw new BuzException(BuzExceptionCode.ApplicationStatusChange);
        }
        applicationDto.setRejectReason(rejectReason);
        applicationDto.setReasonType(reasonType);
        applicationDto.setStatus(ApplicationStatus.Reject.toCode());
        applicationDto.setUpdateBy(UserContext.getLoginUserID());
        applicationApplication.updateApplicationStatus(applicationDto);
        return new ResponseVO();
    }

    @Override
    public ResponseVO getApplicationBooking(Long timeStart, Long timeEnd) throws BuzException {
        return new ResponseVO(applicationApplication.freezeBookingQuery(new Date(timeStart), new Date(timeEnd)));
    }
}
