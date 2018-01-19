package com.lifetech.dhap.pathcloud.setting.api.impl;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.setting.api.ParamSettingApi;
import com.lifetech.dhap.pathcloud.setting.api.vo.*;
import com.lifetech.dhap.pathcloud.setting.application.ParamSettingApplication;
import com.lifetech.dhap.pathcloud.setting.application.dto.DepartmentSettingDto;
import com.lifetech.dhap.pathcloud.setting.application.dto.InspectHospitalDto;
import com.lifetech.dhap.pathcloud.setting.application.dto.ParamSettingDto;
import com.lifetech.dhap.pathcloud.setting.application.dto.QualityScoreDto;
import com.lifetech.dhap.pathcloud.setting.application.infrastructure.code.ParamKey;
import com.lifetech.dhap.pathcloud.tracking.application.BlockApplication;
import com.lifetech.dhap.pathcloud.tracking.application.infrastructure.code.TrackingOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuMei on 2016-12-07.
 */
@Component("paramSettingApi")
public class ParamSettingApiImpl implements ParamSettingApi {

    @Autowired
    private ParamSettingApplication paramSettingApplication;

    @Autowired
    private BlockApplication blockApplication;

    @Override
    public ResponseVO getDepartments(String filter) throws BuzException {
        List<DepartmentSettingDto> data = paramSettingApplication.getContentToListByKey(ParamKey.Departments.toString());
        if (CollectionUtils.isNotEmpty(data)) {
            List<DepartmentSettingDto> list = new ArrayList<>();
            for (DepartmentSettingDto psd : data) {
                if (psd.getName() != null) {
                    list.add(psd);
                }
            }
            if (StringUtils.isBlank(filter)) {
                return new ResponseVO(list);
            }
            List<DepartmentSettingDto> filterData = new ArrayList<>();
            for (DepartmentSettingDto dsd : list) {
                if (dsd.getName() != null && dsd.getName().contains(filter)) {
                    filterData.add(dsd);
                }
            }
            return new ResponseVO(filterData);
        }
        return new ResponseVO();
    }

    @Override
    public ResponseVO paramSetting(String param) throws BuzException {
        ParamKey paramKey = ParamKey.getKeyByParam(param);
        if (paramKey == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        //蜡块编码方式/登记打印方法/打印数量/取材确认是否必须拍照/申请默认是科研还是临床
        if (param.equals(ParamKey.BlockCountType.toParam()) || param.equals(ParamKey.PrintCount.toParam()) ||
                param.equals(ParamKey.PrintType.toParam()) || param.equals(ParamKey.GrossingConfirmPhoto.toParam()) ||
                param.equals(ParamKey.ApplicationDefault.toParam()) || param.equals(ParamKey.FrozenCountType.toParam()) ||
                param.equals(ParamKey.SectionPrintMedium.toParam()) || param.equals(ParamKey.SectionPrintWay.toParam()) ||
                param.equals(ParamKey.GrossingTemplate.toParam()) || param.equals(ParamKey.DiagnoseTemplate.toParam())){
            return new ResponseVO(paramSettingApplication.getContentByKey(ParamKey.getKeyByParam(param).toString()));
        }
        if (param.equals(ParamKey.QualityScore.toParam())) {
            QualityScoreVO qualityScoreVO;
            List<QualityScoreVO> lists = new ArrayList<>();
            List<QualityScoreDto> qualityScoreDTOs = paramSettingApplication.getContentToListByKey(ParamKey.getKeyByParam(param).toString());
            if (CollectionUtils.isNotEmpty(qualityScoreDTOs)) {
                for (QualityScoreDto qualityScoreDto : qualityScoreDTOs) {
                    qualityScoreVO = new QualityScoreVO();
                    BeanUtils.copyProperties(qualityScoreDto, qualityScoreVO);
                    qualityScoreVO.setWorkstationName(TrackingOperation.valueOf(qualityScoreDto.getWorkstation()).toString());
                    lists.add(qualityScoreVO);
                }
            }
            return new ResponseVO(lists);
        }
        return new ResponseVO(paramSettingApplication.getContentToListByKey(ParamKey.getKeyByParam(param).toString()));
    }

    @Override
    public ResponseVO addParam(ParamSettingVO paramSettingVO) throws BuzException {
        String param = paramSettingVO.getParam();
        String name = paramSettingVO.getName();
        if (StringUtils.isBlank(param) || StringUtils.isBlank(name)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        ParamKey paramKey = ParamKey.getKeyByParam(param);
        if (paramKey == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }

        List<ParamSettingDto> content = paramSettingApplication.getContentToListByKey(paramKey.toString());
        for (ParamSettingDto paramSettingDto : content) {
            if (paramSettingDto.getName().equals(name)) {
                throw new BuzException(BuzExceptionCode.ErrorParam);
            }
        }
        ParamSettingDto paramSettingDto = new ParamSettingDto();
        BeanUtils.copyProperties(paramSettingVO, paramSettingDto);
        paramSettingDto = paramSettingApplication.addParam(paramKey.toString(), paramSettingDto);
        return new ResponseVO(paramSettingDto);
    }

    @Override
    public ResponseVO deleteParam(String param, Integer code) throws BuzException {
        if (StringUtils.isBlank(param) || code == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        ParamKey paramKey = ParamKey.getKeyByParam(param);
        if (paramKey == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        if (paramKey.equals(ParamKey.BlockBiaoshi)) {
            Long count = blockApplication.countBlockByBiaoshi(code);
            if (count > 0) {
                throw new BuzException(BuzExceptionCode.LabelCannotDelete);
            }
        }
        if (paramKey.equals(ParamKey.BlockUnit)) {
            Long count = blockApplication.countBlockByUnit(code);
            if (count > 0) {
                throw new BuzException(BuzExceptionCode.LabelCannotDelete);
            }
        }
        if (paramKey.equals(ParamKey.QualityScore)) {
            paramSettingApplication.deleteQualityScoreByCode(paramKey.toString(), code);
        } else {
            paramSettingApplication.deleteParam(paramKey.toString(), code);
        }

        return new ResponseVO();
    }

    @Override
    public ResponseVO updateParam(String param, Integer code) {
        if (code == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        paramSettingApplication.updateContentByKey(ParamKey.getKeyByParam(param).toString(), code.toString());
        return new ResponseVO();
    }

    @Override
    public ResponseVO addQualityScore(QualityScoreVO qualityScoreVO) throws BuzException {

        QualityScoreDto qualityScoreDto = new QualityScoreDto();

        Integer workstation = qualityScoreVO.getWorkstation();
        Integer qualified = qualityScoreVO.getQualified();

        if (workstation == null || workstation <= 0) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }

        if (qualified == null || qualified < 0) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        BeanUtils.copyProperties(qualityScoreVO, qualityScoreDto);

        paramSettingApplication.addQualityScore(qualityScoreDto);

        return new ResponseVO();
    }

    @Override
    public ResponseVO updateQualityScore(QualityScoreVO qualityScoreVO) {
        Integer code = qualityScoreVO.getCode();
        Integer workstation = qualityScoreVO.getWorkstation();
        Integer qualified = qualityScoreVO.getQualified();
        if (workstation == null || workstation <= 0) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        if (code == null || code.intValue() <= 0) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        if (qualified == null || qualified < 0) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        QualityScoreDto qualityScoreDto = new QualityScoreDto();
        BeanUtils.copyProperties(qualityScoreVO, qualityScoreDto);
        paramSettingApplication.updateQualityScore(ParamKey.QualityScore.toString(), qualityScoreDto);
        return new ResponseVO();
    }

    @Override
    public ResponseVO addDepartment(DepartmentSettingVO dsv) throws BuzException {
        String departmentCategory = dsv.getDepartmentCategory();
        if (StringUtils.isBlank(departmentCategory)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        DepartmentSettingDto departmentSettingDto = new DepartmentSettingDto();
        BeanUtils.copyProperties(dsv, departmentSettingDto);
        paramSettingApplication.addDepartment(departmentSettingDto);
        return new ResponseVO();
    }

    @Override
    public ResponseVO getDepartmentsByCategory() throws BuzException {
        DepartmentQueryVO dqv;
        List<DepartmentQueryVO> lists = null;
        List<DepartmentSettingDto> dsdList = paramSettingApplication.getDepartments();
        if (CollectionUtils.isNotEmpty(dsdList)) {
            lists = new ArrayList<>();
            for (DepartmentSettingDto dto : dsdList) {
                Boolean a = true;
                dqv = new DepartmentQueryVO();
                dqv.setId(dto.getId());
                dqv.setDepartmentCategory(dto.getDepartmentCategory());
                List<Department> d = new ArrayList();
                for (DepartmentSettingDto dto1 : dsdList) {
                    if (dto1.getId().equals(dqv.getId())) {
                        Department department = new Department();
                        department.setCode(dto1.getCode());
                        department.setName(dto1.getName());
                        d.add(department);
                    }
                }
                dqv.setDepartments(d);
                if (lists.size() > 0) {
                    for (DepartmentQueryVO dqv1 : lists) {
                        if (dqv1.getId().equals(dqv.getId())) {
                            a = false;
                        }
                    }
                    if (a) {
                        lists.add(dqv);
                    }
                }else {
                    lists.add(dqv);
                }
            }
        }
        return new ResponseVO(lists);
    }

    @Override
    public ResponseVO deleteDepartmentsCategory(Integer id) throws BuzException {

        if (id == null || id < 0) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        paramSettingApplication.deleteDepartmentsCategory(id);

        return new ResponseVO();
    }

    @Override
    public ResponseVO renameCategory(DepartmentSettingVO dsv) throws BuzException {
        Integer id = dsv.getId();
        String departmentCategory = dsv.getDepartmentCategory();
        if (id == null || id < 0 || StringUtils.isBlank(departmentCategory)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        DepartmentSettingDto dsd = new DepartmentSettingDto();
        BeanUtils.copyProperties(dsv, dsd);
        paramSettingApplication.renameCategory(dsd);
        return new ResponseVO();
    }

    @Override
    public ResponseVO editDepartmentName(DepartmentSettingVO dsv) throws BuzException {
        List<DepartmentSettingDto> data = paramSettingApplication.getDepartments();

        String name = dsv.getName();
        for (DepartmentSettingDto dsd : data) {
            if (dsd.getName() != null) {
                if (dsd.getName().equals(name)) {
                    throw new BuzException(BuzExceptionCode.DepartmentNameExists);
                }
            }
        }
        Integer id = dsv.getId();
        Integer code = dsv.getCode();
        if (id == null || id < 0 || StringUtils.isBlank(name) || code == null || code < 0) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        paramSettingApplication.editDepartmentName(id, code, name);
        return new ResponseVO();
    }

    @Override
    public ResponseVO updateDyeType(ParamSettingVO paramSettingVO) throws BuzException {

        Integer code = paramSettingVO.getCode();
        String param = paramSettingVO.getParam();
        String name = paramSettingVO.getName();
        if (StringUtils.isBlank(param) || StringUtils.isBlank(name)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        ParamKey paramKey = ParamKey.getKeyByParam(param);
        if (paramKey == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }

        if (code == null || code <= 0) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }


        ParamSettingDto paramSettingDto = new ParamSettingDto();
        BeanUtils.copyProperties(paramSettingVO, paramSettingDto);


        paramSettingApplication.updateDyeType(paramKey.toString(), paramSettingDto);
        return new ResponseVO();
    }

    @Override
    public ResponseVO addInspectHospital(InspectHospitalVO inspectHospitalVO) throws BuzException {
        String name = inspectHospitalVO.getName();
        String grade = inspectHospitalVO.getGrade();
        if (StringUtils.isBlank(name) || StringUtils.isBlank(grade)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        InspectHospitalDto inspectHospitalDto = new InspectHospitalDto();
        BeanUtils.copyProperties(inspectHospitalVO, inspectHospitalDto);
        paramSettingApplication.addInspectHospital(inspectHospitalDto);
        return new ResponseVO();
    }

    @Override
    public ResponseVO getInspectHospitals() throws BuzException {
        List<InspectHospitalDto> data = paramSettingApplication.getContentToListByKey(ParamKey.InspectHospital.toString());
        return new ResponseVO(data);
    }

    @Override
    public ResponseVO deleteInspectHospitals(Integer code) throws BuzException {
        if (code <= 0) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        paramSettingApplication.deleteInspectHospitals(code);
        return new ResponseVO();
    }


}
