package com.lifetech.dhap.pathcloud.file.application.impl;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.file.application.FileMappingApplication;
import com.lifetech.dhap.pathcloud.file.domain.FileMappingRepository;
import com.lifetech.dhap.pathcloud.file.domain.model.FileMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by LiuMei on 2017-12-11.
 */
@Service
public class FileMappingApplicationImpl implements FileMappingApplication {

    @Autowired
    private FileMappingRepository fileMappingRepository;

    @Override
    public void addMapping(FileMapping fileMapping) throws BuzException{
        Long fileId = fileMapping.getFileId();
        Long pathId = fileMapping.getPathId();
        if(fileId == null || pathId == null){
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        fileMappingRepository.insert(fileMapping);
    }

    @Override
    public void deleteMapping(FileMapping fileMapping) throws BuzException{
        Long fileId = fileMapping.getFileId();
        Long pathId = fileMapping.getPathId();
        Long specialId = fileMapping.getSpecialId();
        if(fileId == null || pathId == null){
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        fileMappingRepository.deleteByUnique(pathId,specialId,fileId);
    }

    @Override
    public FileMapping selectByUnique(long pathId, Long specialId, long fileId) {
        return fileMappingRepository.selectByUnique(pathId,specialId,fileId);
    }
}
