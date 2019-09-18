package com.hb.service.impl;

import com.hb.entity.SysDorm;
import com.hb.mapper.SysDormMapper;
import com.hb.service.ISysDormService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lirc
 * @since 2019-09-03
 */
@Service("sysDormService")
public class SysDormServiceImpl extends ServiceImpl<SysDormMapper, SysDorm> implements ISysDormService {

}
