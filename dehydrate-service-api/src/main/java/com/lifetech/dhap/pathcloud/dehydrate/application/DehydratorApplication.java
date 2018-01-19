package com.lifetech.dhap.pathcloud.dehydrate.application;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.dehydrate.api.vo.DehydratorVO;
import com.lifetech.dhap.pathcloud.dehydrate.application.condition.DehydratorCondition;
import com.lifetech.dhap.pathcloud.dehydrate.application.condition.DehydratorErrorMsgCondition;
import com.lifetech.dhap.pathcloud.dehydrate.application.condition.GetBlockCondition;
import com.lifetech.dhap.pathcloud.dehydrate.application.dto.BlockInfoDto;
import com.lifetech.dhap.pathcloud.dehydrate.application.dto.DehydratorDto;
import com.lifetech.dhap.pathcloud.dehydrate.application.dto.DehydratorErrorMsgDto;
import com.lifetech.dhap.pathcloud.dehydrate.application.dto.DehydratorStatusDto;

import java.util.List;

public interface DehydratorApplication {

	void addDehydrator(DehydratorVO dehydratorVO);

	int updateDehydratorInfo(DehydratorVO dehydratorVO);

	int deleteDehydrator(long instrumentId);

	String addOrUpdateDehydrator(DehydratorVO dehydratorVO);

	/**
	 * 根据instrumentId获取脱水机信息
	 * @param instrumentId
	 * @return
	 * @throws BuzException
	 */
	DehydratorVO getDehydratorByInstrumentId(long instrumentId) throws BuzException;

	List<DehydratorStatusDto> getAllDehydratorStatus();

	void setInUse(long instrumentId, boolean inUse);
	
	/**
	 * 根据条件获取脱水机列表
	 * @param con
	 * @return
	 */
	List<DehydratorDto> getDehydratorByCondition(DehydratorCondition con);

	Long countDehydratorByCondition(DehydratorCondition con);

	List<DehydratorErrorMsgDto> getErrMsgByCondition(DehydratorErrorMsgCondition condition);
	Long countErrMsgByCondition(DehydratorErrorMsgCondition condition);

	void clearWarning(long instrumentId);

	DehydratorDto getLastDehydrator();

	/**
	 * 获取脱水机内蜡块信息
	 * @param condition
	 * @return
	 */
	List<BlockInfoDto> getBlocksInfoByCondition(GetBlockCondition condition);
}
