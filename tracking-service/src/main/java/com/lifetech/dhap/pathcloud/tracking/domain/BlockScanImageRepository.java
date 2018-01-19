package com.lifetech.dhap.pathcloud.tracking.domain;

import com.lifetech.dhap.pathcloud.tracking.domain.model.BlockScanImage;
import java.util.List;

public interface BlockScanImageRepository {
    int insert(BlockScanImage record);

    int update(BlockScanImage record);

    List<BlockScanImage> selectAll();

    BlockScanImage selectNewestImageByLocation(int location);

    BlockScanImage selectByPath(String path);

    Long last();
}