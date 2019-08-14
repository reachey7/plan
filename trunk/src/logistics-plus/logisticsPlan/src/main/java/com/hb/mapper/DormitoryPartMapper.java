package com.hb.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hb.entity.DormitoryPart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lirc
 * @since 2019-08-08
 */
public interface DormitoryPartMapper extends BaseMapper<DormitoryPart> {
    public List<Map<String, Object>> selectDormitoryPart(Page page, @Param("param")Map<String,Object> param);
}
