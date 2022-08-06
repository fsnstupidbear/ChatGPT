package com.fsnteam.fsnweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fsnteam.fsnweb.entity.WhoIsUndercoverGamer;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author StupidBear
 * @since 2021-03-08
 */
public interface WhoIsUndercoverGamerMapper extends BaseMapper<WhoIsUndercoverGamer> {

    @Update("truncate table WhoIsUndercoverGamer")
    public void deleteGamerInfo();
}
