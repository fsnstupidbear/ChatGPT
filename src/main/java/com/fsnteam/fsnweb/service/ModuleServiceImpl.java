package com.fsnteam.fsnweb.service;

import com.fsnteam.fsnweb.entity.Module;
import com.fsnteam.fsnweb.mapper.ModuleMapper;
import com.fsnteam.fsnweb.service.ModuleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author StupidBear
 * @since 2021-03-22
 */
@Service
public class ModuleServiceImpl extends ServiceImpl<ModuleMapper, Module> implements ModuleService {

}
