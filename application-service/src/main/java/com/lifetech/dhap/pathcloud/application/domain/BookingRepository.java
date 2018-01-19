package com.lifetech.dhap.pathcloud.application.domain;

import com.lifetech.dhap.pathcloud.application.domain.model.Booking;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface BookingRepository {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table booking
     *
     * @mbggenerated Wed Mar 29 10:56:21 CST 2017
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table booking
     *
     * @mbggenerated Wed Mar 29 10:56:21 CST 2017
     */
    int insert(Booking record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table booking
     *
     * @mbggenerated Wed Mar 29 10:56:21 CST 2017
     */
    Booking selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table booking
     *
     * @mbggenerated Wed Mar 29 10:56:21 CST 2017
     */
    List<Booking> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table booking
     *
     * @mbggenerated Wed Mar 29 10:56:21 CST 2017
     */
    int updateByPrimaryKey(Booking record);

    List<Booking> freezeBookingQuery(@Param("starttime") Date starttime, @Param("endtime") Date endtime, @Param("instrumentId") Integer instrumentId);

    List<Booking> freezeBookingExport(@Param("starttime") Date starttime, @Param("endtime") Date endtime);

    int deleteByApplicationId(long applicationId);

    List<Booking> selectBookingByApplicationId(long applicationId);
}