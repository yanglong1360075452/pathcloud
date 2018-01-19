package com.lifetech.dhap.pathcloud.file.application.impl;

import com.lifetech.dhap.pathcloud.file.application.PathologyFileApplication;
import com.lifetech.dhap.pathcloud.file.application.dto.PathologyFileDto;
import com.lifetech.dhap.pathcloud.file.domain.PathologyFileRepository;
import com.lifetech.dhap.pathcloud.file.domain.model.PathologyFile;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2016-12-22-13:28
 */
@Service("trackingFileApplication")
public class PathologyFileApplicationImpl implements PathologyFileApplication {

    @Autowired
    private PathologyFileRepository pathologyFileRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addPathologyFile(PathologyFileDto fileDto) {
        PathologyFile trackingFile = new PathologyFile();
        BeanUtils.copyProperties(fileDto, trackingFile);
        pathologyFileRepository.insert(trackingFile);
        fileDto.setId(pathologyFileRepository.last());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updatePathologyFile(PathologyFileDto fileDto) {
        PathologyFile trackingFile = new PathologyFile();
        BeanUtils.copyProperties(fileDto, trackingFile);
        pathologyFileRepository.updateByPrimaryKey(trackingFile);
    }

    @Override
    public List<PathologyFileDto> getFileByPathologyId(Long pathologyId, Integer operation,String tag) {
        List<PathologyFile> files = pathologyFileRepository.selectFileByPathologyId(pathologyId, operation,tag);

        List<PathologyFileDto> data = new ArrayList<>();
        for (PathologyFile file : files) {
            PathologyFileDto dto = new PathologyFileDto();
            BeanUtils.copyProperties(file, dto);
            data.add(dto);
        }

        return data;
    }

    @Override
    public PathologyFileDto getFileByBlockId(Long blockId) {
        PathologyFile pathologyFile = pathologyFileRepository.selectFileByBlockId(blockId);
        return pathologyFileToDto(pathologyFile);
    }

    @Override
    public PathologyFileDto getFileBySavename(String savename) {
        PathologyFile file = pathologyFileRepository.selectFileBySavename(savename);
        if (file == null) {
            return null;
        }
        PathologyFileDto dto = new PathologyFileDto();
        BeanUtils.copyProperties(file, dto);
        return dto;
    }

    @Override
    public PathologyFileDto getFileById(Long id) {
        PathologyFile file = pathologyFileRepository.selectByPrimaryKey(id);
        return pathologyFileToDto(file);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFile(long fileId) {
        pathologyFileRepository.deleteByPrimaryKey(fileId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchInsert(List<PathologyFileDto> fileDtoList) {
        if (CollectionUtils.isNotEmpty(fileDtoList)) {
            pathologyFileRepository.batchInsert(pathologyFilesDtoToPO(fileDtoList));
        }
    }

    @Override
    public List<PathologyFileDto> getConfirmFileByBlockIds(List<Long> blockIds) {
        if (CollectionUtils.isNotEmpty(blockIds)) {
            List<PathologyFile> files = pathologyFileRepository.selectConfirmFileByBlockIds(blockIds);
            if (CollectionUtils.isNotEmpty(files)) {
                List<PathologyFileDto> fileDtoList = new ArrayList<>();
                PathologyFileDto fileDto;
                for (PathologyFile file : files) {
                    fileDto = new PathologyFileDto();
                    BeanUtils.copyProperties(file, fileDto);
                    fileDtoList.add(fileDto);
                }
                return fileDtoList;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public void batchUpdate(List<PathologyFileDto> fileDtoList) {
        pathologyFileRepository.batchUpdate(pathologyFilesDtoToPO(fileDtoList));
    }

    private PathologyFileDto pathologyFileToDto(PathologyFile pathologyFile) {
        PathologyFileDto dto = null;
        if (pathologyFile != null) {
            dto = new PathologyFileDto();
            BeanUtils.copyProperties(pathologyFile, dto);
        }
        return dto;
    }

    private List<PathologyFile> pathologyFilesDtoToPO(List<PathologyFileDto> fileDtoList) {
        List<PathologyFile> files = new ArrayList<>();
        PathologyFile pathologyFile;
        for (PathologyFileDto fileDto : fileDtoList) {
            pathologyFile = new PathologyFile();
            BeanUtils.copyProperties(fileDto, pathologyFile);
            files.add(pathologyFile);
        }
        return files;
    }
}
