package com.lifetech.dhap.pathcloud.tracking.api.vo;

/**
 * @author LiuMei
 * @date 2017-11-21.
 */
public class SummaryVO {

    /**
     * 当前用户计数
     */
    private Long currentUserCount;

    /**
     * 全部计数
     */
    private Long totalCount;

    public Long getCurrentUserCount() {
        return currentUserCount;
    }

    public void setCurrentUserCount(Long currentUserCount) {
        this.currentUserCount = currentUserCount;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }
}
