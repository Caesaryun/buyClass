package io.renren.modules.generator.dao;

import io.renren.modules.generator.entity.OrderTempEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单临时表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-07-08 19:04:22
 */
@Mapper
public interface OrderTempDao extends BaseMapper<OrderTempEntity> {
	
}
