package com.fsnteam.fsnweb.service;

import com.fsnteam.fsnweb.entity.SelectMemberModelRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fsnteam.fsnweb.util.Result;

import java.util.Map;

/**
 * <p>
 * 败方免单规则，1为固定击杀数，0为达到进行局数的百分比 服务类
 * </p>
 *
 * @author StupidBear
 * @since 2022-08-14
 */
public interface SelectMemberModelRecordService extends IService<SelectMemberModelRecord> {

    Result insertSelectMemberModelRecord(Map params);

    Result selectMemberModelCheckScore(Map params);

    Result getOnGoingCompetition(Map params);

    Result getRecordBillInfo(Map params);

    Result deleteCurrentSelectMemberModelRecord(Map params);

    Result ifHasCaptainAuthority();

    Result getCurrentAccountID();

    Result getFourVsFourSelectMemberModelHistory(Map params);

    Result resultPageDeleteCurrentRecord(Map params);

    Result getCurrentRoundPlayers(Map params);
}
