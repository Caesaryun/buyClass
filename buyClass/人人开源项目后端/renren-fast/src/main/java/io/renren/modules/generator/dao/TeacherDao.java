package io.renren.modules.generator.dao;

import io.renren.modules.generator.entity.TeacherEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

/**
 * 讲师表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-07-08 19:04:22
 */
@Mapper
public interface TeacherDao extends BaseMapper<TeacherEntity> {
	public HashMap  searchCurriculumTeacher(Integer curriculumId);
}
