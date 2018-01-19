package com.lifetech.dhap.pathcloud.dehydrate.application;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.dehydrate.application.dto.DehydrateDto;

import java.util.List;

/**
 * Created by LiuMei on 2016-12-13.
 */
public interface DehydrateApplication {

    void startDehydrate(DehydrateDto dto) throws BuzException;

    void endDehydrate(List<Long> dehydratorsId) throws BuzException;
}
