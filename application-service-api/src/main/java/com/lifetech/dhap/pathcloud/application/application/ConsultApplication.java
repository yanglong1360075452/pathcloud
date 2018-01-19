package com.lifetech.dhap.pathcloud.application.application;

import com.lifetech.dhap.pathcloud.application.application.dto.ConsultDto;

/**
 * Created by LiuMei on 2017-10-09.
 */
public interface ConsultApplication {

    /**
     * 添加会诊记录
     * @param consultDto
     */
    void add(ConsultDto consultDto);

}
