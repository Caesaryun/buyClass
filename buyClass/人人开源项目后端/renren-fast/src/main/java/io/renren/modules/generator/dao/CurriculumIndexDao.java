package io.renren.modules.generator.dao;

import io.renren.modules.generator.entity.CurriculumIndexEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.List;

/**
 * 课程目录表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-07-08 19:04:22
 */
@Mapper
public interface CurriculumIndexDao extends BaseMapper<CurriculumIndexEntity> {
	public String searchPublicVideo(Integer curriculumId);
    public List<HashMap> searchCurriculumIndex(Integer curriculumId);

}
