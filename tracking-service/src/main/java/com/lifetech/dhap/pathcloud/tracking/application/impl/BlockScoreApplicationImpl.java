package com.lifetech.dhap.pathcloud.tracking.application.impl;

import com.lifetech.dhap.pathcloud.tracking.application.BlockScoreApplication;
import com.lifetech.dhap.pathcloud.tracking.application.dto.BlockScoreDto;
import com.lifetech.dhap.pathcloud.tracking.domain.BlockScoreRepository;
import com.lifetech.dhap.pathcloud.tracking.domain.model.BlockScore;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by LiuMei on 2017-01-11.
 */
@Service
public class BlockScoreApplicationImpl implements BlockScoreApplication {

    @Autowired
    private BlockScoreRepository blockScoreRepository;

    @Override
    public BlockScoreDto getBlockScoreBySlideIdAndType(Long slideId,Integer type) {
        BlockScore blockScore = blockScoreRepository.selectByBlockIdAndType(slideId,type);
        if (blockScore != null && blockScore.getBlockId() != null) {
            BlockScoreDto blockScoreDto = new BlockScoreDto();
            BeanUtils.copyProperties(blockScore, blockScoreDto);
            return blockScoreDto;
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addBlockScore(BlockScoreDto blockScoreDto) {
        BlockScore blockScore = new BlockScore();
        BeanUtils.copyProperties(blockScoreDto, blockScore);
        blockScoreRepository.insert(blockScore);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBlockScore(BlockScoreDto blockScoreDto) {
        BlockScore blockScore = new BlockScore();
        BeanUtils.copyProperties(blockScoreDto, blockScore);
        blockScoreRepository.updateByPrimaryKey(blockScore);
    }

}
