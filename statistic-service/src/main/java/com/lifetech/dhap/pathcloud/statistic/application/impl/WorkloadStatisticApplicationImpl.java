package com.lifetech.dhap.pathcloud.statistic.application.impl;

import com.lifetech.dhap.pathcloud.setting.application.ParamSettingApplication;
import com.lifetech.dhap.pathcloud.setting.application.dto.InspectCategoryDto;
import com.lifetech.dhap.pathcloud.setting.application.infrastructure.code.SystemKey;
import com.lifetech.dhap.pathcloud.statistic.application.WorkloadStatisticApplication;
import com.lifetech.dhap.pathcloud.statistic.application.condition.CoincidenceCondition;
import com.lifetech.dhap.pathcloud.statistic.application.condition.QualityControlCon;
import com.lifetech.dhap.pathcloud.statistic.application.condition.ReportCondition;
import com.lifetech.dhap.pathcloud.statistic.application.condition.WorkloadCondition;
import com.lifetech.dhap.pathcloud.statistic.application.dto.*;
import com.lifetech.dhap.pathcloud.statistic.domain.WorkloadStatisticRepository;
import com.lifetech.dhap.pathcloud.statistic.domain.model.GroupInspectCategory;
import com.lifetech.dhap.pathcloud.statistic.domain.model.MonthInspectCategory;
import com.lifetech.dhap.pathcloud.statistic.domain.model.PersonInspectCategory;
import com.lifetech.dhap.pathcloud.statistic.domain.model.QualityMonth;
import com.lifetech.dhap.pathcloud.statistic.domain.model.QualityPersonal;
import com.lifetech.dhap.pathcloud.tracking.application.infrastructure.code.TrackingOperation;
import com.lifetech.dhap.pathcloud.user.application.UserApplication;
import com.lifetech.dhap.pathcloud.user.application.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2017-02-13-10:26
 */
@Service("workloadStatisticApplication")
public class WorkloadStatisticApplicationImpl implements WorkloadStatisticApplication {

    @Autowired
    private WorkloadStatisticRepository workloadStatisticRepository;

    @Autowired
    private ParamSettingApplication paramSettingApplication;

    @Autowired
    private UserApplication userApplication;

    @Override
    public List<MonthInspectCategoryDto> monthInspectCategory(WorkloadCondition con) {
        List<MonthInspectCategoryDto> lists = new ArrayList<>();

        List<MonthInspectCategory> data = workloadStatisticRepository.monthInspectCategory(con);

        MonthInspectCategoryDto dto;
        List<InspectCategoryDto> inspectCategory = paramSettingApplication.getContentToListByKey(SystemKey.InspectCategory.toString());
        for (InspectCategoryDto inspectCategoryDto : inspectCategory) {
            dto = new MonthInspectCategoryDto();
            dto.setInspectCategory(inspectCategoryDto.getCode());
            dto.setInspectCategoryName(inspectCategoryDto.getTypeDesc());

            long total = 0L;
            if (data != null && !data.isEmpty()) {
                for (MonthInspectCategory monthInspectCategory : data) {
                    if (monthInspectCategory.getInspectCategory().equals(dto.getInspectCategory())) {
                        if (monthInspectCategory.getPathologyMonth() == 1) {
                            dto.setJanuary(monthInspectCategory.getNum());
                        } else if (monthInspectCategory.getPathologyMonth() == 2) {
                            dto.setFebruary(monthInspectCategory.getNum());
                        } else if (monthInspectCategory.getPathologyMonth() == 3) {
                            dto.setMarch(monthInspectCategory.getNum());
                        } else if (monthInspectCategory.getPathologyMonth() == 4) {
                            dto.setApril(monthInspectCategory.getNum());
                        } else if (monthInspectCategory.getPathologyMonth() == 5) {
                            dto.setMay(monthInspectCategory.getNum());
                        } else if (monthInspectCategory.getPathologyMonth() == 6) {
                            dto.setJune(monthInspectCategory.getNum());
                        } else if (monthInspectCategory.getPathologyMonth() == 7) {
                            dto.setJuly(monthInspectCategory.getNum());
                        } else if (monthInspectCategory.getPathologyMonth() == 8) {
                            dto.setAugust(monthInspectCategory.getNum());
                        } else if (monthInspectCategory.getPathologyMonth() == 9) {
                            dto.setSeptember(monthInspectCategory.getNum());
                        } else if (monthInspectCategory.getPathologyMonth() == 10) {
                            dto.setOctober(monthInspectCategory.getNum());
                        } else if (monthInspectCategory.getPathologyMonth() == 11) {
                            dto.setNovember(monthInspectCategory.getNum());
                        } else if (monthInspectCategory.getPathologyMonth() == 12) {
                            dto.setDecember(monthInspectCategory.getNum());
                        }
                        total += monthInspectCategory.getNum();
                    }
                    if (monthInspectCategory.getInspectCategory() > dto.getInspectCategory()) {
                        break;
                    }
                }
            }
            dto.setTotal(total);
            lists.add(dto);
        }
        return lists;
    }

    @Override
    public List<PersonInspectCategoryDto> personInspectCategory(WorkloadCondition con) {
        Integer start = con.getStart();
        Integer size = con.getSize();

        con.setStart(0);
        con.setSize(Integer.MAX_VALUE);
        List<PersonInspectCategory> data = workloadStatisticRepository.personInspectCategory(con);

        List<PersonInspectCategory> users = new ArrayList<>();
        List<String> usersStr = new ArrayList<>();
        for (PersonInspectCategory personInspectCategory : data) {
            if (!usersStr.contains(personInspectCategory.getOperatorId() + "_" + personInspectCategory.getOperation())) {
                users.add(personInspectCategory);
                usersStr.add(personInspectCategory.getOperatorId() + "_" + personInspectCategory.getOperation());
            }
        }

        List<PersonInspectCategoryDto> lists = new ArrayList<>();
        PersonInspectCategoryDto dto;
        List<InspectCategoryDto> inspectCategory = paramSettingApplication.getContentToListByKey(SystemKey.InspectCategory.toString());

        Integer index = 0;
        for (PersonInspectCategory user : users) {
            index++;
            if (index <= start) {
                continue;
            } else if (index > start + size) {
                break;
            }
            List<PersonInspectCategory> items = new ArrayList<>();
            Long total = 0L;
            UserDto userDto = userApplication.getUserByUserID(user.getOperatorId());
            dto = new PersonInspectCategoryDto();
            dto.setUserName(userDto.getUserName());
            dto.setFirstName(userDto.getFirstName());
            for (InspectCategoryDto inspectCategoryDto : inspectCategory) {
                PersonInspectCategory inspectItem = new PersonInspectCategory();
                inspectItem.setNum(0L);
                inspectItem.setInspectCategory(inspectCategoryDto.getCode());
                inspectItem.setInspectCategoryName(inspectCategoryDto.getTypeDesc());
                for (PersonInspectCategory personInspectCategory : data) {
                    if (personInspectCategory.getOperatorId().equals(user.getOperatorId()) && personInspectCategory.getOperation().equals(user.getOperation()) && personInspectCategory.getInspectCategory().equals(inspectItem.getInspectCategory())) {
                        inspectItem.setNum(personInspectCategory.getNum());
                        total += personInspectCategory.getNum();
                        break;
                    }
                }
                items.add(inspectItem);
            }
            dto.setItems(items);
            dto.setTotal2(total);
            dto.setStation(TrackingOperation.getNameByCode(user.getOperation()));
            lists.add(dto);
        }

        return lists;
    }

    @Override
    public Long personInspectCategoryTotal(WorkloadCondition con) {
        return workloadStatisticRepository.personInspectCategoryTotal(con);
    }

    @Override
    public List<QualityPersonalDto> personalQualityControl(QualityControlCon con) {
        List<QualityPersonalDto> lists = new ArrayList<>();
        List<QualityPersonal> data = workloadStatisticRepository.personQualityControl(con);
        if (data != null && !data.isEmpty()) {
            for (QualityPersonal qualityPersonal : data) {
                QualityPersonalDto dto = new QualityPersonalDto();
                BeanUtils.copyProperties(qualityPersonal, dto);
                dto.setOperationName(TrackingOperation.getNameByCode(qualityPersonal.getOperation()));
                UserDto user = userApplication.getUserByUserID(qualityPersonal.getOperatorId());
                if (user != null) {
                    dto.setUserName(user.getUserName());
                    dto.setFirstName(user.getFirstName());
                }
                lists.add(dto);
            }
        }
        return lists;
    }

    @Override
    public Long personalQualityControlTotal(QualityControlCon con) {
        return workloadStatisticRepository.countPersonQualityControl(con);
    }

    @Override
    public List<QualityMonthDto> monthQualityControl(QualityControlCon con) {
        List<QualityMonth> data = workloadStatisticRepository.monthQualityControl(con);
        return qualityMonthTrans(data);
    }

    @Override
    public List<QualityMonthDto> goodQualityControl(QualityControlCon con) {
        List<QualityMonth> data = workloadStatisticRepository.goodQualityControl(con);
        return qualityMonthTrans(data);
    }

    @Override
    public List<QualityMonthDto> reportDelay(ReportCondition con) {
        List<QualityMonth> data = workloadStatisticRepository.reportDelay(con);
        return qualityMonthTrans(data);
    }

    @Override
    public List<QualityMonthDto> coincidence(CoincidenceCondition con) {
        List<QualityMonth> data = workloadStatisticRepository.coincidence(con);
        return qualityMonthTrans(data);
    }

    private List<QualityMonthDto> qualityMonthTrans(List<QualityMonth> data) {
        List<QualityMonthDto> lists = new ArrayList<>();
        for (int month = 1; month < 13; month++) {
            QualityMonthDto dto = new QualityMonthDto();
            if (data != null && !data.isEmpty()) {
                for (QualityMonth qualityMonth : data) {
                    if (qualityMonth.getPathologyMonth() == month) {
                        BeanUtils.copyProperties(qualityMonth, dto);
                        break;
                    }
                }
            }
            dto.setPathologyMonth(month);
            lists.add(dto);
        }
        return lists;
    }


    @Override
    public List<GroupInspectCategoryDto> groupInspectCategory(WorkloadCondition con) {
        List<GroupInspectCategoryDto> lists = new ArrayList<>();

        GroupInspectCategoryDto groupInspectCategoryDto;
        List<GroupInspectCategory> data = workloadStatisticRepository.groupInspectCategory(con);
        List<InspectCategoryDto> inspectCategory = paramSettingApplication.getContentToListByKey(SystemKey.InspectCategory.toString());

        for (InspectCategoryDto inspectCategoryDto : inspectCategory) {
            groupInspectCategoryDto = new GroupInspectCategoryDto();
            groupInspectCategoryDto.setInspectCategory(inspectCategoryDto.getCode());
            groupInspectCategoryDto.setInspectCategoryName(inspectCategoryDto.getTypeDesc());
            Long pathologyTotal = 0L;
            Long blockTotal = 0L;
            if (data != null) {
                for (GroupInspectCategory groupInspectCategory : data) {
                    if (groupInspectCategory.getInspectCategory().equals(groupInspectCategoryDto.getInspectCategory())) {
                        if (groupInspectCategory.getPathologyMonth() == 1) {
                            Group group = new Group();
                            group.setPathNum(groupInspectCategory.getPathologyNum());
                            group.setBlockNum(groupInspectCategory.getBlockNum());
                            groupInspectCategoryDto.setJanuary(group);
                        } else if (groupInspectCategory.getPathologyMonth() == 2) {
                            Group group = new Group();
                            group.setPathNum(groupInspectCategory.getPathologyNum());
                            group.setBlockNum(groupInspectCategory.getBlockNum());
                            groupInspectCategoryDto.setFebruary(group);
                        } else if (groupInspectCategory.getPathologyMonth() == 3) {
                            Group group = new Group();
                            group.setPathNum(groupInspectCategory.getPathologyNum());
                            group.setBlockNum(groupInspectCategory.getBlockNum());
                            groupInspectCategoryDto.setMarch(group);
                        } else if (groupInspectCategory.getPathologyMonth() == 4) {
                            Group group = new Group();
                            group.setPathNum(groupInspectCategory.getPathologyNum());
                            group.setBlockNum(groupInspectCategory.getBlockNum());
                            groupInspectCategoryDto.setApril(group);
                        } else if (groupInspectCategory.getPathologyMonth() == 5) {
                            Group group = new Group();
                            group.setPathNum(groupInspectCategory.getPathologyNum());
                            group.setBlockNum(groupInspectCategory.getBlockNum());
                            groupInspectCategoryDto.setMay(group);
                        } else if (groupInspectCategory.getPathologyMonth() == 6) {
                            Group group = new Group();
                            group.setPathNum(groupInspectCategory.getPathologyNum());
                            group.setBlockNum(groupInspectCategory.getBlockNum());
                            groupInspectCategoryDto.setJune(group);
                        } else if (groupInspectCategory.getPathologyMonth() == 7) {
                            Group group = new Group();
                            group.setPathNum(groupInspectCategory.getPathologyNum());
                            group.setBlockNum(groupInspectCategory.getBlockNum());
                            groupInspectCategoryDto.setJuly(group);
                        } else if (groupInspectCategory.getPathologyMonth() == 8) {
                            Group group = new Group();
                            group.setPathNum(groupInspectCategory.getPathologyNum());
                            group.setBlockNum(groupInspectCategory.getBlockNum());
                            groupInspectCategoryDto.setAugust(group);
                        } else if (groupInspectCategory.getPathologyMonth() == 9) {
                            Group group = new Group();
                            group.setPathNum(groupInspectCategory.getPathologyNum());
                            group.setBlockNum(groupInspectCategory.getBlockNum());
                            groupInspectCategoryDto.setSeptember(group);
                        } else if (groupInspectCategory.getPathologyMonth() == 10) {
                            Group group = new Group();
                            group.setPathNum(groupInspectCategory.getPathologyNum());
                            group.setBlockNum(groupInspectCategory.getBlockNum());
                            groupInspectCategoryDto.setOctober(group);
                        } else if (groupInspectCategory.getPathologyMonth() == 11) {
                            Group group = new Group();
                            group.setPathNum(groupInspectCategory.getPathologyNum());
                            group.setBlockNum(groupInspectCategory.getBlockNum());
                            groupInspectCategoryDto.setNovember(group);
                        } else if (groupInspectCategory.getPathologyMonth() == 12) {
                            Group group = new Group();
                            group.setPathNum(groupInspectCategory.getPathologyNum());
                            group.setBlockNum(groupInspectCategory.getBlockNum());
                            groupInspectCategoryDto.setDecember(group);
                        }
                        pathologyTotal += groupInspectCategory.getPathologyNum();
                        blockTotal += groupInspectCategory.getBlockNum();

                    }
                    if (groupInspectCategory.getInspectCategory() > groupInspectCategoryDto.getInspectCategory()) {
                        break;
                    }
                }

            }

            groupInspectCategoryDto.setPathologyTotal(pathologyTotal);
            groupInspectCategoryDto.setBlockTotal(blockTotal);
            lists.add(groupInspectCategoryDto);
        }

        return lists;
    }
}
