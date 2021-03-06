package com.lifetech.dhap.pathcloud.tracking.domain;


import com.lifetech.dhap.pathcloud.tracking.domain.model.BlockScore;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlockScoreRepository {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table block_score
     *
     * @mbggenerated Wed Jan 11 11:30:42 CST 2017
     */
    int deleteByPrimaryKey(Long blockId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table block_score
     *
     * @mbggenerated Wed Jan 11 11:30:42 CST 2017
     */
    int insert(BlockScore record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table block_score
     *
     * @mbggenerated Wed Jan 11 11:30:42 CST 2017
     */
    BlockScore selectByPrimaryKey(Long blockId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table block_score
     *
     * @mbggenerated Wed Jan 11 11:30:42 CST 2017
     */
    List<BlockScore> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table block_score
     *
     * @mbggenerated Wed Jan 11 11:30:42 CST 2017
     */
    int updateByPrimaryKey(BlockScore record);

    BlockScore selectByBlockIdAndType(@Param("blockId") Long blockId,@Param("type") Integer type);
}