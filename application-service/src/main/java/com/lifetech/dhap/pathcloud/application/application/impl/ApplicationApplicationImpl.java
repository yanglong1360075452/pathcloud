package com.lifetech.dhap.pathcloud.application.application.impl;

import com.google.gson.Gson;
import com.lifetech.dhap.pathcloud.application.application.ApplicationApplication;
import com.lifetech.dhap.pathcloud.application.application.PathologyApplication;
import com.lifetech.dhap.pathcloud.application.application.condition.ApplicationCondition;
import com.lifetech.dhap.pathcloud.application.application.condition.SampleCondition;
import com.lifetech.dhap.pathcloud.application.application.dto.*;
import com.lifetech.dhap.pathcloud.application.application.infrastructure.code.ApplicationStatus;
import com.lifetech.dhap.pathcloud.application.application.infrastructure.code.ApplyType;
import com.lifetech.dhap.pathcloud.application.application.infrastructure.code.ResearchType;
import com.lifetech.dhap.pathcloud.application.domain.ApplicationRepository;
import com.lifetech.dhap.pathcloud.application.domain.BookingRepository;
import com.lifetech.dhap.pathcloud.application.domain.SampleRepository;
import com.lifetech.dhap.pathcloud.application.domain.model.*;
import com.lifetech.dhap.pathcloud.application.infrastructure.code.BookingFlag;
import com.lifetech.dhap.pathcloud.common.constant.Config;
import com.lifetech.dhap.pathcloud.common.data.PathologyStatus;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.security.UserContext;
import com.lifetech.dhap.pathcloud.setting.application.ParamSettingApplication;
import com.lifetech.dhap.pathcloud.setting.application.dto.PathNoRuleDto;
import com.lifetech.dhap.pathcloud.setting.application.infrastructure.code.SystemKey;
import com.lifetech.dhap.pathcloud.user.application.UserApplication;
import com.lifetech.dhap.pathcloud.user.application.dto.UserSimpleDto;
import net.sf.json.JSONArray;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by LuoMo on 2016-11-22.
 */
@Service("applicationApplication")
public class ApplicationApplicationImpl implements ApplicationApplication {

    private final static Logger log = LoggerFactory.getLogger(ApplicationApplicationImpl.class);

    private Gson gson = new Gson();

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private SampleRepository sampleRepository;

    @Autowired
    private ParamSettingApplication paramSettingApplication;

    @Autowired
    private UserApplication userApplication;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PathologyApplication pathologyApplication;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApplicationDto createApplication(ApplicationDto applicationDto) throws BuzException {
        PathNoRuleDto pathNoRule = paramSettingApplication.getPathNoRule();
        if (pathNoRule == null) {
            throw new BuzException(BuzExceptionCode.SettingError);
        }
        String formatDate = new SimpleDateFormat(pathNoRule.getTime()).format(new Date());
        /*
        0 代表前面补充0
        pathNoRule.getDigit() 代表长度为
        d 代表参数为正数型
         */
        String formatDigit = "%0" + pathNoRule.getDigit() + "d";
        Application application = new Application();
        BeanUtils.copyProperties(applicationDto, application);
        applicationRepository.insert(application);
        Long id = applicationRepository.last();
        application = applicationRepository.selectByPrimaryKey(id);

        String endId = String.format(formatDigit, id);
        ResearchDto researchDto = applicationDto.getResearch();
        application.setSerialNumber(Config.APPLICATIONLETTER + formatDate + endId);
        if (researchDto != null) {
            if (researchDto.getBooks() != null) {
                for (BookDto bookDto : researchDto.getBooks()) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    String date = format.format(bookDto.getFreezeStartTime());
                    format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    try {
                        List<Integer> cells = bookDto.getCells();
                        if (cells != null && cells.size() > 0) {
                            Date startTime = format.parse(date + " " + BookingFlag.getStartTimeByCode(bookDto.getCells().get(0)));
                            Date endTime = format.parse(date + " " + BookingFlag.getEndTimeByCode(bookDto.getCells().get(bookDto.getCells().size() - 1)));
                            BookingAddDto dto = new BookingAddDto();
                            dto.setStartTime(startTime);
                            dto.setEndTime(endTime);
                            dto.setBookingPid(UserContext.getLoginUserID());
                            dto.setBookingPerson(researchDto.getApplicant());
                            dto.setBookingPhone(researchDto.getPhone());
                            dto.setTimeFlag(bookDto.getCells().toString());
                            dto.setApplicationId(id);
                            if (bookDto.getInstrumentId() == null) {
                                dto.setInstrumentId(1);
                            } else {
                                dto.setInstrumentId(bookDto.getInstrumentId());
                            }
                            addBooking(dto);
                        }
                    } catch (ParseException e) {
                        log.error("Parse error", e);
                        throw new BuzException(BuzExceptionCode.DataError);
                    }
                }
            }
            Research research = new Research();
            BeanUtils.copyProperties(researchDto, research);
            research.setId(id);
            application.setResearch(gson.toJson(research));
        }
        Long currentUser = application.getCreateBy();
        application.setUpdateBy(currentUser);
        applicationRepository.updateByPrimaryKey(application);
        List<SampleDto> sampleDtoList = applicationDto.getSamples();
        if (CollectionUtils.isNotEmpty(sampleDtoList)) {
            Sample sample;
            for (SampleDto sampleDto : sampleDtoList) {
                sample = new Sample();
                BeanUtils.copyProperties(sampleDto, sample);
                sample.setApplicationId(id);
                sample.setCreateBy(currentUser);
                sampleRepository.insert(sample);
                Long sampleId = sampleRepository.last();
                sample = sampleRepository.selectByPrimaryKey(sampleId);
                sample.setSerialNumber(Config.SAMPLELETTER + formatDate + String.format(formatDigit, sampleId));
                sample.setDelete(false);
                sample.setUpdateBy(currentUser);
                sampleRepository.updateByPrimaryKey(sample);
            }
        }
        return applicationToDto(application);
    }

    @Override
    public ApplicationDto getApplicationById(Long id) throws BuzException {
        Application application = applicationRepository.selectByPrimaryKey(id);
        return applicationToDto(application);
    }

    @Override
    public ApplicationDto getApplicationBySerialNumber(String serialNumber) throws BuzException {
        Application application = applicationRepository.selectBySerialNumber(serialNumber);
        return applicationToDto(application);

    }

    @Override
    public List<ApplicationDto> getApplicationByPathologyNo(String pathologyNo) throws BuzException {
        List<ApplicationDto> applicationDtoList = null;
        List<Application> applications = applicationRepository.selectByPathologyNo(pathologyNo);
        if (CollectionUtils.isNotEmpty(applications)) {
            applicationDtoList = new ArrayList<>();
            for (Application application : applications) {
                applicationDtoList.add(applicationToDto(application));
            }
        }
        return applicationDtoList;
    }

    @Override
    public ApplicationDto getLastApplicationByPathNo(String pathNo) throws BuzException {
        return applicationToDto(applicationRepository.selectLastByPathNo(pathNo));
    }

    private ApplicationDto applicationToDto(Application application) {
        ApplicationDto applicationDto = null;
        if (application != null) {
            applicationDto = new ApplicationDto();
            BeanUtils.copyProperties(application, applicationDto);
            SampleCondition sampleCondition = new SampleCondition();
            sampleCondition.setApplicationId(application.getId());
            sampleCondition.setDelete(false);
            List<Sample> samples = sampleRepository.selectByApplicationId(sampleCondition);
            List<SampleDto> sampleDtoList = samplesToDtos(samples);
            if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(sampleDtoList)) {
                applicationDto.setSamples(sampleDtoList);
            }
            applicationDto.setStatusName(ApplicationStatus.valueOf(application.getStatus()).toString());
            applicationDto.setApplyTypeDesc(ApplyType.getNameByCode(applicationDto.getApplyType()));
            String research = application.getResearch();
            if (research != null && research.length() > 0) {
                ResearchDto researchDto = gson.fromJson(research, ResearchDto.class);
                applicationDto.setResearch(researchDto);
            }
            Long pathologyId = application.getPathologyId();
            if (pathologyId != null) {
                PathologyDto pathologyDto = pathologyApplication.getSimplePathById(pathologyId);
                if (pathologyDto != null) {
                    Integer status = pathologyDto.getStatus();
                    applicationDto.setPathologyId(pathologyId);
                    applicationDto.setPathNo(pathologyDto.getSerialNumber());
                    applicationDto.setPathologyStatus(status);
                    applicationDto.setPathologyStatusDesc(PathologyStatus.getNameByCode(status));
                    applicationDto.setPathCreateTime(pathologyDto.getCreateTime());
                    applicationDto.setNote(pathologyDto.getNote());
                    applicationDto.setInspectCategory(pathologyDto.getInspectCategory());
                }
            }
        }
        return applicationDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateApplicationStatus(ApplicationDto applicationDto) throws BuzException {
        Application application = new Application();
        BeanUtils.copyProperties(applicationDto, application);
        if (application.getId() == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }

        applicationRepository.updateStatusById(application.getId(), application.getStatus(), application.getUpdateBy(),
                application.getRejectReason(), application.getReasonType(), application.getPathologyId(), applicationDto.getNumber());
    }

    @Override
    public List<UserSimpleDto> getInspectDoctor() {
        List<UserSimpleDto> userSimpleDtoList = new ArrayList<>();
        List<Long> users = applicationRepository.selectInspectDoctor();
        for (Long user : users) {
            userSimpleDtoList.add(userApplication.getUserSimpleInfoById(user));
        }
        return userSimpleDtoList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addBooking(BookingAddDto dto) {
        Booking book = new Booking();
        BeanUtils.copyProperties(dto, book);
        bookingRepository.insert(book);
    }

    @Override
    public List<ApplicationDto> getApplicationByCondition(ApplicationCondition con) throws BuzException {
        List<Application> applications = applicationRepository.selectByCondition(con);

        List<ApplicationDto> data = new ArrayList<>();
        for (Application application : applications) {
            data.add(applicationToDto(application));
        }

        return data;
    }

    @Override
    public List<ApplicationBriefDto> getApplicationBriefByCondition(ApplicationCondition con) throws BuzException {
        List<Application> applications = applicationRepository.selectByCondition(con);

        List<ApplicationBriefDto> data = new ArrayList<>();
        ApplicationBriefDto dto;
        for (Application application : applications) {
            dto = new ApplicationBriefDto();
            BeanUtils.copyProperties(application, dto);
            dto.setStatusName(ApplicationStatus.valueOf(application.getStatus()).toString());
            dto.setApplyTypeDesc(ApplyType.getNameByCode(application.getApplyType()));
            String research = application.getResearch();
            if (research != null && research.length() > 0) {
                ResearchDto researchDto = gson.fromJson(research, ResearchDto.class);
                Integer researchType = researchDto.getResearchType();
                dto.setResearchType(researchType);
                dto.setResearchTypeDesc(ResearchType.getNameByCode(researchType));
            }
            SampleCondition sampleCondition = new SampleCondition();
            sampleCondition.setApplicationId(application.getId());
            sampleCondition.setDelete(false);
            List<Sample> samples = sampleRepository.selectByApplicationId(sampleCondition);

            List<SampleDto> sampleDtoList = samplesToDtos(samples);
            if (CollectionUtils.isNotEmpty(sampleDtoList)) {
                dto.setSamples(sampleDtoList);
            }
            data.add(dto);
        }

        return data;
    }

    @Override
    public Long getApplicationTotalByCondition(ApplicationCondition con) throws BuzException {
        return applicationRepository.countByCondition(con);
    }

    @Override
    public List<ApplicationSampleDto> getApplicationAndSamplesByCondition(ApplicationCondition con) {
        List<ApplicationSample> applications = applicationRepository.selectSampleAndApplicationByCondition(con);

        List<ApplicationSampleDto> data = new ArrayList<>();
        ApplicationSampleDto dto;
        for (ApplicationSample application : applications) {
            dto = new ApplicationSampleDto();
            BeanUtils.copyProperties(application, dto);
            dto.setStatusName(ApplicationStatus.valueOf(application.getStatus()).toString());
            if (dto.getUpdateBy() != null) {
                dto.setUpdateByName(userApplication.getUserByUserID(dto.getUpdateBy()).getFirstName());
            }


            Long assignGrossing = application.getAssignGrossing();
            if (assignGrossing != null) {
                dto.setAssignGrossing(userApplication.getUserSimpleInfoById(assignGrossing));
            }

            Long sampleCreateBy = application.getSampleCreateBy();
            if (sampleCreateBy != null) {
                dto.setSampleCreateBy(userApplication.getUserSimpleInfoById(sampleCreateBy));
            }

            Long sampleUpdateBy = application.getSampleUpdateBy();
            if (sampleUpdateBy != null) {
                dto.setSampleUpdateBy(userApplication.getUserSimpleInfoById(sampleUpdateBy));
            }
            Integer pathologyStatus = application.getPathologyStatus();
            if (pathologyStatus != null) {
                dto.setPathologyStatusName(PathologyStatus.valueOf(application.getPathologyStatus()).toString());
            }
            data.add(dto);
        }

        return data;
    }

    @Override
    public Long countApplicationAndSamplesByCondition(ApplicationCondition con) {
        return applicationRepository.countSampleAndApplicationByCondition(con);
    }


    @Override
    public List<FreezeBookingDto> freezeBookingQuery(Date startTime, Date endTime) {
        List<Booking> bookings = bookingRepository.freezeBookingQuery(startTime, endTime, null);
        List<FreezeBookingDto> lists = new ArrayList<>();

        long lastDay = (endTime.getTime() - startTime.getTime()) / 24 / 3600000L;
        BookingDto bookingDto;
        FreezeBookingDto dto;
        BookingPerson person;

        for (long firstDay = 0; firstDay < lastDay; firstDay++) {
            for (int j = 1; j <= 2; j++) {//2台切片机
                List<BookingDto> bookingDtos = new ArrayList<>();
                dto = new FreezeBookingDto();
                Date time = new Date(startTime.getTime() + firstDay * 24 * 3600000L);
                dto.setDate(time);

                int instrumentId = j;//切片机ID
                dto.setInstrumentId(instrumentId);
                for (int i = 16; i <= 35; i++) {
                    bookingDto = new BookingDto();
                    bookingDto.setBooked(false);
                    bookingDto.setTimeflag(i);
                    bookingDto.setInstrumentId(instrumentId);
                    boolean find = false;
                    for (Booking booking : bookings) {
                        if (booking.getInstrumentId().equals(instrumentId) && booking.getStartTime().equals(time)) {
                            if (booking.getTimeFlag() != null) {
                                String timeFlag = booking.getTimeFlag();

                                List<Integer> list = JSONArray.fromObject(timeFlag);
                                for (Integer flag : list) {
                                    if (flag.equals(i)) {
                                        bookingDto.setBooked(true);
                                        person = new BookingPerson();
                                        person.setBookingName(booking.getBookingPerson());
                                        person.setPhone(booking.getBookingPhone());
                                        bookingDto.setBookingPerson(person);
                                        find = true;
                                        break;
                                    }
                                }
                            }
                        }
                        if (find) {
                            break;
                        }
                    }
                    bookingDtos.add(bookingDto);
                }
                dto.setBookingDto(bookingDtos);
                lists.add(dto);
            }
        }

        return lists;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelFreezeApplication(ApplicationDto applicationDto) {
        updateApplicationStatus(applicationDto);
        Long id = applicationDto.getId();
        bookingRepository.deleteByApplicationId(id);
    }

    @Override
    public Long countDepartments(Integer code) {


        return applicationRepository.countDepartments(code);
    }

    @Override
    public List<SampleDto> samplesToDtos(Object o) {
        List<Sample> samples = (List<Sample>) o;
        if (samples != null && samples.size() > 0) {
            List<SampleDto> sampleDtos = new ArrayList<>();
            SampleDto sampleDto;
            for (Sample sample : samples) {
                sampleDto = new SampleDto();
                BeanUtils.copyProperties(sample, sampleDto);
                String categoryName = paramSettingApplication.getNameByKeyAndCode(SystemKey.SampleCategory.toString(), sample.getCategory());
                sampleDto.setCategoryName(categoryName);
                sampleDtos.add(sampleDto);
            }
            return sampleDtos;
        }
        return null;
    }

    @Override
    public ApplicationDto getApplicationBySampleSerialNumber(String serialNumber) {
        Application application = applicationRepository.getApplicationBySampleSerialNumber(serialNumber);
        return applicationToDto(application);
    }

    @Override
    public List<Long> getIdsByPathId(long pathId) {
        return applicationRepository.selectIdsByPathId(pathId);
    }


}
