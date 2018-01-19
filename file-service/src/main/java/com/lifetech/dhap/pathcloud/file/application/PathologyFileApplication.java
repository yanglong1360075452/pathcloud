package com.lifetech.dhap.pathcloud.file.application;

import com.lifetech.dhap.pathcloud.file.application.dto.PathologyFileDto;

import java.util.List;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2016-12-22-13:17
 */
public interface PathologyFileApplication {

    void addPathologyFile(PathologyFileDto fileDto);

    void updatePathologyFile(PathologyFileDto fileDto);

    List<PathologyFileDto> getFileByPathologyId(Long pathologyId, Integer operation,String tag);

    /**
     * 查询取材确认图像
     * @param blockId
     * @return
     */
    PathologyFileDto getFileByBlockId(Long blockId);

    PathologyFileDto getFileBySavename(String savename);

    PathologyFileDto getFileById(Long id);

    void deleteFile(long fileId);

    void batchInsert(List<PathologyFileDto> fileDtos);

    List<PathologyFileDto> getConfirmFileByBlockIds(List<Long> blockIds);

    void batchUpdate(List<PathologyFileDto> fileDtos);
}
