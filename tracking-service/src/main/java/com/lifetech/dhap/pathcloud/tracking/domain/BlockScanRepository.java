package com.lifetech.dhap.pathcloud.tracking.domain;

import com.lifetech.dhap.pathcloud.tracking.domain.model.BlockScan;
import java.util.List;

public interface BlockScanRepository {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table block_scan
     *
     * @mbggenerated Wed Mar 22 14:42:29 CST 2017
     */
    int insert(BlockScan record);

    int delete(Long slideId);

    int deleteBatch(List<Long> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table block_scan
     *
     * @mbggenerated Wed Mar 22 14:42:29 CST 2017
     */
    List<BlockScan> selectAll();

    List<BlockScan> selectByLocation(int location);
}