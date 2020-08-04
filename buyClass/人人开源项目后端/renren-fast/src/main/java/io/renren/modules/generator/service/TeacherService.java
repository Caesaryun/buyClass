package io.renren.modules.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.generator.entity.TeacherEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * 讲师表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-07-08 19:04:22
 */
public interface TeacherService extends IService<TeacherEntity> {

    PageUtils queryPage(Map<String, Object> params);
    public HashMap searchCurriculumTeacher(Integer curriculumId);
}

