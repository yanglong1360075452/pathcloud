package com.lifetech.dhap.pathcloud.file.application;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.file.domain.model.FileMapping;

/**
 * Created by LiuMei on 2017-12-11.
 */
public interface FileMappingApplication {

    /**
     * 添加mapping
     * @param fileMapping
     */
    void addMapping(FileMapping fileMapping) throws BuzException;

    /**
     * 删除mapping
     * @param fileMapping
     */
    void deleteMapping(FileMapping fileMapping) throws BuzException;

    FileMapping selectByUnique(long pathId,Long specialId,long fileId);
}
