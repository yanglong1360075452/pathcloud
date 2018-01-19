package com.lifetech.dhap.pathcloud.file.domain;

import com.lifetech.dhap.pathcloud.file.domain.model.FileMapping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FileMappingRepository {
    int deleteByPrimaryKey(Long id);

    int insert(FileMapping record);

    FileMapping selectByPrimaryKey(Long id);

    List<FileMapping> selectAll();

    int updateByPrimaryKey(FileMapping record);

    FileMapping selectByUnique(@Param("pathId") long pathId,@Param("specialId") Long specialId, @Param("fileId") long fileId);

    void deleteByUnique(@Param("pathId") long pathId,@Param("specialId") Long specialId, @Param("fileId") long fileId);
}