package com.lifetech.dhap.pathcloud.tracking.application;

import com.lifetech.dhap.pathcloud.tracking.application.dto.BlockScoreDto;

/**
 * Created by LiuMei on 2017-01-11.
 */
public interface BlockScoreApplication {

    BlockScoreDto getBlockScoreBySlideIdAndType(Long slideId,Integer type);

    void addBlockScore(BlockScoreDto blockScoreDto);

    void updateBlockScore(BlockScoreDto blockScoreDto);
}
