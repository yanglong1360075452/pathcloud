package com.lifetech.dhap.pathcloud.dehydrate.application.dto;

import com.lifetech.dhap.pathcloud.tracking.application.dto.BlockDetailDto;

import java.util.List;

/**
 * Created by LiuMei on 2016-12-13.
 */
public class DehydrateDto {
    //脱水篮编号列表
    List<Integer> baskets;

    Long instrumentId;

    List<Long> instrumentIds;//结束脱水脱水机列表

    List<BlockDetailDto> blocks;

    public List<BlockDetailDto> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<BlockDetailDto> blocks) {
        this.blocks = blocks;
    }

    public List<Integer> getBaskets() {
        return baskets;
    }

    public void setBaskets(List<Integer> baskets) {
        this.baskets = baskets;
    }

    public Long getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(Long instrumentId) {
        this.instrumentId = instrumentId;
    }

    public List<Long> getInstrumentIds() {
        return instrumentIds;
    }

    public void setInstrumentIds(List<Long> instrumentIds) {
        this.instrumentIds = instrumentIds;
    }
}
