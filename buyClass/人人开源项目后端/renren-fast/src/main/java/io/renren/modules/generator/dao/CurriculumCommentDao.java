package io.renren.modules.generator.dao;

import io.renren.modules.generator.entity.CurriculumCommentEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * 课程评价表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-07-08 19:04:23
 */
@Mapper
public interface CurriculumCommentDao extends BaseMapper<CurriculumCommentEntity> {

   public List<HashMap> searchCurriculumComment(Integer curriculumId);

   public List<HashMap> searchCommentByPage(HashMap param);
}
