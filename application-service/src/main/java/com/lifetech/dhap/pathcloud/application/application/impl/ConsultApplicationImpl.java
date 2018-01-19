package com.lifetech.dhap.pathcloud.application.application.impl;

import com.lifetech.dhap.pathcloud.application.application.ConsultApplication;
import com.lifetech.dhap.pathcloud.application.application.dto.ConsultDto;
import com.lifetech.dhap.pathcloud.application.domain.ConsultRepository;
import com.lifetech.dhap.pathcloud.application.domain.PathologyRepository;
import com.lifetech.dhap.pathcloud.application.domain.model.Consult;
import com.lifetech.dhap.pathcloud.common.constant.Config;
import com.lifetech.dhap.pathcloud.common.data.PathologyStatus;
import com.lifetech.dhap.pathcloud.common.data.SequenceName;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.setting.application.ParamSettingApplication;
import com.lifetech.dhap.pathcloud.setting.application.dto.PathNoRuleDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by LiuMei on 2017-10-09.
 */
@Service
public class ConsultApplicationImpl implements ConsultApplication {

    @Autowired
    private ConsultRepository consultRepository;

    @Autowired
    private PathologyRepository pathologyRepository;

    @Autowired
    private ParamSettingApplication paramSettingApplication;

    @Override
    public void add(ConsultDto consultDto) {
        if(consultDto != null){
            Consult consult = new Consult();
            BeanUtils.copyProperties(consultDto,consult);
            PathNoRuleDto pathNoRule = paramSettingApplication.getPathNoRule();
            if (pathNoRule == null) {
                throw new BuzException(BuzExceptionCode.SettingError);
            }
            String formatDigit = "%0" + pathNoRule.getDigit() + "d";
            String formatDate = new SimpleDateFormat(pathNoRule.getTime()).format(new Date());
            consult.setNumber(Config.CONSULTLETTER + formatDate + String.format(formatDigit, pathologyRepository.getNextNumber(SequenceName.ConsultNumber.toString())));
            consult.setStatus(PathologyStatus.PrepareFirstDiagnose.toCode());
            consultRepository.insert(consult);
        }
    }
}
