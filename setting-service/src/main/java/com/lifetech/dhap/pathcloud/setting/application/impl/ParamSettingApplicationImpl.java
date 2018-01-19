package com.lifetech.dhap.pathcloud.setting.application.impl;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lifetech.dhap.pathcloud.application.application.ApplicationApplication;
import com.lifetech.dhap.pathcloud.application.application.PathologyApplication;
import com.lifetech.dhap.pathcloud.application.application.dto.PathologyDto;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.security.UserContext;
import com.lifetech.dhap.pathcloud.setting.application.ParamSettingApplication;
import com.lifetech.dhap.pathcloud.setting.application.dto.*;
import com.lifetech.dhap.pathcloud.setting.application.infrastructure.code.ParamKey;
import com.lifetech.dhap.pathcloud.setting.application.infrastructure.code.SystemKey;
import com.lifetech.dhap.pathcloud.setting.domain.ParamSettingRepository;
import com.lifetech.dhap.pathcloud.setting.domain.model.ParamSetting;
import com.lifetech.dhap.pathcloud.tracking.application.BlockApplication;
import com.lifetech.dhap.pathcloud.tracking.application.dto.BlockDto;
import net.sf.json.JSONArray;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuMei on 2016-12-07.
 */
@Service
public class ParamSettingApplicationImpl implements ParamSettingApplication {

    private Gson gson = new Gson();

    @Autowired
    private ParamSettingRepository paramSettingRepository;

    @Autowired
    private ApplicationApplication applicationApplication;

    @Autowired
    private PathologyApplication pathologyApplication;

    @Autowired
    private BlockApplication blockApplication;

    @Override
    public List getContentToListByKey(String key) {
        String content = paramSettingRepository.selectByKey(key);
        if (key.equals(SystemKey.InspectCategory.toString())) {
            return (List) gson.fromJson(content, new TypeToken<List<InspectCategoryDto>>() {
            }.getType());
        } else if (key.equals(ParamKey.QualityScore.toString())) {
            return (List) gson.fromJson(content, new TypeToken<List<QualityScoreDto>>() {
            }.getType());
        } else if (key.equals(SystemKey.ApplicationRequired.toString())) {
            return (List) gson.fromJson(content, new TypeToken<List<ApplicationRequiredDto>>() {
            }.getType());
        } else if (key.equals(SystemKey.TrackingList.toString()) || key.equals(SystemKey.FreezeTrackingList.toString())) {
            return (List) gson.fromJson(content, new TypeToken<List<TrackingListDto>>() {
            }.getType());
        } else if (key.equals(SystemKey.TrackingStatus.toString()) || key.equals(SystemKey.TrackingOperation.toString()) ||
                key.equals(SystemKey.FreezeTrackingStatus.toString()) || key.equals(SystemKey.FreezeTrackingOperation.toString())) {
            return (List) gson.fromJson(content, new TypeToken<List<Integer>>() {
            }.getType());
        } else if (key.equals(SystemKey.QueryTimeRange.toString())) {
            return (List) gson.fromJson(content, new TypeToken<List<QueryTimeRangeDto>>() {
            }.getType());
        } else if (key.equals(ParamKey.InspectHospital.toString())) {
            return (List) gson.fromJson(content, new TypeToken<List<InspectHospitalDto>>() {
            }.getType());
        } else if (key.equals(ParamKey.Departments.toString())) {
            return (List) gson.fromJson(content, new TypeToken<List<DepartmentSettingDto>>() {
            }.getType());
        } else {
            return (List) gson.fromJson(content, new TypeToken<List<ParamSettingDto>>() {
            }.getType());
        }
    }

    @Override
    public String getContentByKey(String key) throws BuzException {
        return paramSettingRepository.selectByKey(key);
    }

    @Override
    public String getNameByKeyAndCode(String key, int code) throws BuzException {
        List<ParamSettingDto> paramSettingDTOs = getContentToListByKey(key);
        if (CollectionUtils.isNotEmpty(paramSettingDTOs)) {
            for (ParamSettingDto p : paramSettingDTOs) {
                if (p.getCode().equals(code)) {
                    return p.getName();
                }
            }
        }
        return null;
    }

    @Override
    public Integer getCodeByKeyAndName(String key, String name) throws BuzException {
        List<ParamSettingDto> paramSettingDTOs = getContentToListByKey(key);
        for (ParamSettingDto p : paramSettingDTOs) {
            if (p.getName().equals(name)) {
                return p.getCode();
            }
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ParamSettingDto addParam(String key, ParamSettingDto paramSettingDto) throws BuzException {
        String name = paramSettingDto.getName();
        if (StringUtils.isBlank(key) || StringUtils.isBlank(name)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        List<ParamSettingDto> params = getContentToListByKey(key);
        if (params == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        Integer code = 0;
        if (params.size() > 0) {
            for (ParamSettingDto p : params) {
                if (p != null) {
                    if (p.getCode() >= code) {
                        code = p.getCode();
                    }
                }
            }
        }
        paramSettingDto.setCode(code + 1);
        params.add(paramSettingDto);
        String result = gson.toJson(params);
        ParamSetting paramSetting = new ParamSetting();
        paramSetting.setUpdateBy(UserContext.getLoginUserID());
        paramSetting.setContent(result);
        paramSetting.setKey(key);
        paramSettingRepository.updateContentByKey(paramSetting);
        return paramSettingDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteParam(String key, int code) throws BuzException {
        if (StringUtils.isBlank(key)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        List<ParamSettingDto> params = getContentToListByKey(key);
        if (params == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        if (params.size() > 0) {
            for (ParamSettingDto p : params) {
                if (p.getCode().equals(code)) {
                    params.remove(p);
                    break;
                }
            }
            String result = JSONArray.fromObject(params).toString();
            ParamSetting paramSetting = new ParamSetting();
            paramSetting.setUpdateBy(UserContext.getLoginUserID());
            paramSetting.setContent(result);
            paramSetting.setKey(key);
            paramSettingRepository.updateContentByKey(paramSetting);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateContentByKey(String key, String content) throws BuzException {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(content)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        ParamSetting paramSetting = new ParamSetting();
        paramSetting.setUpdateBy(UserContext.getLoginUserID());
        paramSetting.setContent(content);
        paramSetting.setKey(key);
        paramSettingRepository.updateContentByKey(paramSetting);
    }

    @Override
    public Integer getNextStatusByStatusAndId(int status, long id) {
        BlockDto blockDto = blockApplication.getSimpleBlockById(id);
        if (blockDto != null) {
            PathologyDto pathologyDto = pathologyApplication.getSimplePathById(blockDto.getPathId());
            if (pathologyDto != null) {
                Integer inspectCategory = pathologyDto.getInspectCategory();
                List statusList;
                if (inspectCategory != null && inspectCategory.equals(3)) {//冰冻
                    statusList = getContentToListByKey(SystemKey.FreezeTrackingStatus.toString());
                } else {
                    statusList = getContentToListByKey(SystemKey.TrackingStatus.toString());
                }
                int index = statusList.indexOf(status);
                if (index == -1) {
                    throw new BuzException(BuzExceptionCode.ErrorParam);
                }
                if (index == statusList.size() - 1) {
                    return null;
                }
                return (Integer) statusList.get(index + 1);
            }
        }
        return null;
    }

    @Override
    public Integer getPreOperationByOperation(int operation) {
        List operationList = getContentToListByKey(SystemKey.TrackingOperation.toString());
        int index = operationList.indexOf(operation);
        if (index == -1) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        return (Integer) operationList.get(index - 1);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addInspectCategory(InspectCategoryDto dto) {
        List<InspectCategoryDto> params = getContentToListByKey(SystemKey.InspectCategory.toString());
        Integer code = 0;
        if (params.size() > 0) {
            for (InspectCategoryDto p : params) {
                if (p != null) {
                    if (p.getCode() >= code) {
                        code = p.getCode();
                    }
                }
            }
        }
        dto.setCode(code + 1);
        params.add(dto);
        String result = JSONArray.fromObject(params).toString();
        ParamSetting paramSetting = new ParamSetting();
        paramSetting.setUpdateBy(UserContext.getLoginUserID());
        paramSetting.setContent(result);
        paramSetting.setKey(SystemKey.InspectCategory.toString());
        paramSettingRepository.updateContentByKey(paramSetting);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteInspectCategory(int code) {
        String key = SystemKey.InspectCategory.toString();
        List<InspectCategoryDto> params = getContentToListByKey(key);
        if (params.size() > 0) {
            for (InspectCategoryDto p : params) {
                if (p.getCode().equals(code)) {
                    params.remove(p);
                    break;
                }
            }
            String result = JSONArray.fromObject(params).toString();
            ParamSetting paramSetting = new ParamSetting();
            paramSetting.setUpdateBy(UserContext.getLoginUserID());
            paramSetting.setContent(result);
            paramSetting.setKey(key);
            paramSettingRepository.updateContentByKey(paramSetting);
        }
    }

    @Override
    public InspectCategoryDto getInspectCategoryByCode(int code) {
        List<InspectCategoryDto> inspectCategoryDTOs = getContentToListByKey(SystemKey.InspectCategory.toString());
        for (InspectCategoryDto p : inspectCategoryDTOs) {
            if (p.getCode().equals(code)) {
                return p;
            }
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addQualityScore(QualityScoreDto qualityScoreDto) {
        String key = ParamKey.QualityScore.toString();
        List<QualityScoreDto> params = getContentToListByKey(ParamKey.QualityScore.toString());
        String s = paramSettingRepository.selectByKey(key);
        List<QualityScoreDto> data = new ArrayList<>();
        ParamSetting paramSetting;
        Integer code = 0;
        if (s == null) {
            qualityScoreDto.setCode(code + 1);
            data.add(qualityScoreDto);
            paramSetting = new ParamSetting();
            paramSetting.setKey(ParamKey.QualityScore.toString());
            paramSetting.setContent(gson.toJson(data));
            paramSetting.setCreateBy(UserContext.getLoginUserID());
            paramSettingRepository.insert(paramSetting);
        } else {
            for (QualityScoreDto q : params) {
                if (q != null) {
                    if (q.getCode() >= code && !q.getWorkstation().equals(qualityScoreDto.getWorkstation())) {
                        code = q.getCode();
                        data.add(q);
                    } else {
                        throw new BuzException(BuzExceptionCode.ErrorParam);
                    }
                }
            }
            qualityScoreDto.setCode(code + 1);
            data.add(qualityScoreDto);
            paramSetting = new ParamSetting();
            paramSetting.setKey(key);
            paramSetting.setContent(gson.toJson(data));
            paramSetting.setUpdateBy(UserContext.getLoginUserID());
            paramSettingRepository.updateContentByKey(paramSetting);

        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateQualityScore(String key, QualityScoreDto qualityScoreDto) {
        List<QualityScoreDto> data = getContentToListByKey(key);
        List<QualityScoreDto> lists = new ArrayList<>();
        if (data.size() > 0) {
            for (QualityScoreDto q : data) {
                if (q.getCode().equals(qualityScoreDto.getCode())) {
                    BeanUtils.copyProperties(qualityScoreDto, q);
                }
                lists.add(q);
            }
        } else {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }

        ParamSetting paramSetting = new ParamSetting();
        paramSetting.setKey(key);
        paramSetting.setContent(gson.toJson(lists));
        paramSetting.setUpdateBy(UserContext.getLoginUserID());
        paramSettingRepository.updateContentByKey(paramSetting);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteQualityScoreByCode(String key, int code) {
        if (StringUtils.isBlank(key)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        List<QualityScoreDto> params = getContentToListByKey(key);
        if (params == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        if (params.size() > 0) {
            for (QualityScoreDto q : params) {
                if (q.getCode().equals(code)) {
                    params.remove(q);
                    break;
                }
            }
            ParamSetting paramSetting = new ParamSetting();
            paramSetting.setUpdateBy(UserContext.getLoginUserID());
            paramSetting.setContent(gson.toJson(params));
            paramSetting.setKey(key);
            paramSettingRepository.updateContentByKey(paramSetting);
        }
    }

    @Override
    public Integer getQualifiedScoreByWorkstation(Integer workstation) {
        Integer score = null;
        List<QualityScoreDto> qualityScoreDTOs = getContentToListByKey(ParamKey.QualityScore.toString());
        if (CollectionUtils.isNotEmpty(qualityScoreDTOs)) {
            for (QualityScoreDto qualityScoreDto : qualityScoreDTOs) {
                if (qualityScoreDto != null) {
                    if (workstation.equals(qualityScoreDto.getWorkstation())) {
                        score = qualityScoreDto.getQualified();
                    }
                }
            }
        }
        return score;
    }

    @Override
    public String getSpecialDyeDesc(int specialDye) {
        String desc = getNameByKeyAndCode(SystemKey.SpecialDye.toString(), specialDye);
        if (desc == null) {
            return "白片";
        } else {
            return desc;
        }
    }

    @Override
    public void updateQueryTimeRange(int code) {
        String key = SystemKey.QueryTimeRange.toString();
        List<QueryTimeRangeDto> query = (List<QueryTimeRangeDto>) getContentToListByKey(key);
        for (QueryTimeRangeDto queryTimeRangeDto : query) {
            if (queryTimeRangeDto.getCode().equals(code)) {
                queryTimeRangeDto.setChecked(true);
            } else {
                queryTimeRangeDto.setChecked(false);
            }
        }
        ParamSetting paramSetting = new ParamSetting();
        paramSetting.setKey(key);
        paramSetting.setContent(gson.toJson(query));
        paramSetting.setUpdateBy(UserContext.getLoginUserID());
        paramSettingRepository.updateContentByKey(paramSetting);
    }

    @Override
    public List<DepartmentSettingDto> getDepartments() {
        String content = paramSettingRepository.selectByKey(ParamKey.Departments.toString());
        return (List) gson.fromJson(content, new TypeToken<List<DepartmentSettingDto>>() {
        }.getType());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDyeType(String key, ParamSettingDto paramSettingDto) {
        List<ParamSettingDto> data = getContentToListByKey(key);
        List<ParamSettingDto> lists = new ArrayList<>();
        if (data.size() > 0) {
            for (ParamSettingDto q : data) {
                if (q.getCode().equals(paramSettingDto.getCode())) {
                    BeanUtils.copyProperties(paramSettingDto, q);
                }
                lists.add(q);
            }
        } else {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }

        ParamSetting paramSetting = new ParamSetting();
        paramSetting.setKey(key);
        paramSetting.setContent(gson.toJson(lists));
        paramSetting.setUpdateBy(UserContext.getLoginUserID());
        paramSettingRepository.updateContentByKey(paramSetting);
    }

    @Override
    public PathNoRuleDto getPathNoRule() {
        String s = paramSettingRepository.selectByKey(SystemKey.PathNoRule.toString());
        if (StringUtils.isNotBlank(s)) {
            return gson.fromJson(s, new TypeToken<PathNoRuleDto>() {
            }.getType());
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addInspectHospital(InspectHospitalDto inspectHospitalDto) {

        String key = ParamKey.InspectHospital.toString();
        List<InspectHospitalDto> params = getContentToListByKey(ParamKey.InspectHospital.toString());
        String s = paramSettingRepository.selectByKey(key);
        List<InspectHospitalDto> data = new ArrayList<>();
        ParamSetting paramSetting;
        Integer code = 0;
        if (s == null) {
            inspectHospitalDto.setCode(code + 1);
            data.add(inspectHospitalDto);
            paramSetting = new ParamSetting();
            paramSetting.setKey(ParamKey.InspectHospital.toString());
            paramSetting.setContent(gson.toJson(data));
            paramSetting.setCreateBy(UserContext.getLoginUserID());
            paramSettingRepository.insert(paramSetting);
        } else {
            if (params != null) {
                for (InspectHospitalDto q : params) {
                    if (q != null) {
                        if (q.getCode() >= code) {
                            code = q.getCode();
                            data.add(q);
                        } else {
                            throw new BuzException(BuzExceptionCode.ErrorParam);
                        }
                    }
                    if (q.getName().equals(inspectHospitalDto.getName())) {
                        throw new BuzException(BuzExceptionCode.InspectHospitalExist);
                    }
                }
            }

            inspectHospitalDto.setCode(code + 1);
            data.add(inspectHospitalDto);
            paramSetting = new ParamSetting();
            paramSetting.setKey(key);
            paramSetting.setContent(gson.toJson(data));
            paramSetting.setUpdateBy(UserContext.getLoginUserID());
            paramSettingRepository.updateContentByKey(paramSetting);

        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteInspectHospitals(Integer code) {

        String key = ParamKey.InspectHospital.toString();
        List<InspectHospitalDto> params = getContentToListByKey(ParamKey.InspectHospital.toString());
        if (params == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        boolean a = true;
        if (params.size() > 0) {
            for (InspectHospitalDto q : params) {
                if (q.getCode().equals(code)) {
                    a = false;
                    params.remove(q);
                    break;
                }
            }
            if (a) {
                throw new BuzException(BuzExceptionCode.ErrorParam);
            }

            ParamSetting paramSetting = new ParamSetting();
            paramSetting.setUpdateBy(UserContext.getLoginUserID());
            paramSetting.setContent(gson.toJson(params));
            paramSetting.setKey(key);
            paramSettingRepository.updateContentByKey(paramSetting);
        }

    }

    @Override
    public String getHospitalDescByCode(Integer code) {
        List<InspectHospitalDto> content = getContentToListByKey(ParamKey.InspectHospital.toString());
        if (CollectionUtils.isNotEmpty(content)) {
            for (InspectHospitalDto dto : content) {
                if (dto.getCode().equals(code)) {
                    return dto.getName();
                }
            }
        }
        return null;
    }

    @Override
    public String getDepartmentDescByCode(int code) {
        List<DepartmentSettingDto> departments = getDepartments();
        String desc = null;
        if (CollectionUtils.isNotEmpty(departments)) {
            for (DepartmentSettingDto departmentSettingDto : departments) {
                if (departmentSettingDto.getCode().equals(code)) {
                    desc = departmentSettingDto.getName();
                }
            }
        }
        return desc;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addDepartment(DepartmentSettingDto departmentSettingDto) {
        String key = ParamKey.Departments.toString();
        List<DepartmentSettingDto> params = getDepartments();
        String s = paramSettingRepository.selectByKey(key);
        List<DepartmentSettingDto> data = new ArrayList<>();
        ParamSetting paramSetting;
        Integer code = 0;
        Integer id = 0;
        if (s == null) {
            departmentSettingDto.setCode(code + 1);
            departmentSettingDto.setId(id + 1);
            data.add(departmentSettingDto);
            paramSetting = new ParamSetting();
            paramSetting.setKey(ParamKey.Departments.toString());
            paramSetting.setContent(gson.toJson(data));
            paramSetting.setCreateBy(UserContext.getLoginUserID());
            paramSettingRepository.insert(paramSetting);
        } else {
            for (DepartmentSettingDto dsd : params) {
                if (dsd != null) {

                    for (DepartmentSettingDto dsd1 : params) {
                        if (dsd1.getDepartmentCategory().equals(departmentSettingDto.getDepartmentCategory())) {
                            departmentSettingDto.setId(dsd1.getId());
                            break;
                        } else {
                            id = dsd.getId();
                            departmentSettingDto.setId(id + 1);
                        }
                    }

                    if (dsd.getCode() >= code) {
                        code = dsd.getCode();
                        data.add(dsd);
                    } else {
                        throw new BuzException(BuzExceptionCode.ErrorParam);
                    }
                    if (departmentSettingDto.getName() != null && dsd.getName() != null) {
                        if (dsd.getName().equals(departmentSettingDto.getName())) {
                            throw new BuzException(BuzExceptionCode.DepartmentNameExists);
                        }
                    }

                }
            }
            departmentSettingDto.setCode(code + 1);

            data.add(departmentSettingDto);
            paramSetting = new ParamSetting();
            paramSetting.setKey(key);
            paramSetting.setContent(gson.toJson(data));
            paramSetting.setUpdateBy(UserContext.getLoginUserID());
            paramSettingRepository.updateContentByKey(paramSetting);

        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDepartmentsCategory(int id) {
        List<DepartmentSettingDto> data = getDepartments();
        List<DepartmentSettingDto> lists = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(data)) {
            for (DepartmentSettingDto dsd : data) {
                boolean a = true;
                if (dsd.getId().equals(id)) {
                    Long count = applicationApplication.countDepartments(dsd.getCode());
                    if (count > 0) {
                        throw new BuzException(BuzExceptionCode.LabelCannotDelete);
                    } else {
                        a = false;
                    }
                }
                if (a) {
                    lists.add(dsd);
                }
            }
            ParamSetting paramSetting = new ParamSetting();
            paramSetting.setUpdateBy(UserContext.getLoginUserID());
            paramSetting.setContent(gson.toJson(lists));
            paramSetting.setKey(ParamKey.Departments.toString());
            paramSettingRepository.updateContentByKey(paramSetting);
        }


    }

    @Override
    public void renameCategory(DepartmentSettingDto dsd1) {
        List<DepartmentSettingDto> data = getDepartments();

        if (CollectionUtils.isNotEmpty(data)) {
            for (DepartmentSettingDto dsd : data) {
                if (dsd.getId().equals(dsd1.getId())) {
                    dsd.setDepartmentCategory(dsd1.getDepartmentCategory());
                }
                if (dsd.getDepartmentCategory().equals(dsd1.getDepartmentCategory()) && !dsd.getId().equals(dsd1.getId())) {
                    throw new BuzException(BuzExceptionCode.DepartmentCategoryExists);
                }
            }

            ParamSetting paramSetting = new ParamSetting();
            paramSetting.setUpdateBy(UserContext.getLoginUserID());
            paramSetting.setContent(gson.toJson(data));
            paramSetting.setKey(ParamKey.Departments.toString());
            paramSettingRepository.updateContentByKey(paramSetting);
        }
    }

    @Override
    public void editDepartmentName(int id, int code, String name) {
        List<DepartmentSettingDto> data = getDepartments();
        if (CollectionUtils.isNotEmpty(data)) {
            for (DepartmentSettingDto dsd : data) {
                if (dsd.getId().equals(id)) {
                    if (dsd.getCode().equals(code)) {
                        dsd.setName(name);
                    }
                }
            }
            ParamSetting paramSetting = new ParamSetting();
            paramSetting.setUpdateBy(UserContext.getLoginUserID());
            paramSetting.setContent(gson.toJson(data));
            paramSetting.setKey(ParamKey.Departments.toString());
            paramSettingRepository.updateContentByKey(paramSetting);
        }
    }

}
