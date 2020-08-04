package io.renren.modules.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.generator.entity.CurriculumCommentEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 课程评价表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-07-08 19:04:23
 */
public interface CurriculumCommentService extends IService<CurriculumCommentEntity> {

    PageUtils queryPage(Map<String, Object> params);
    public List<HashMap> searchCurriculumComment(Integer curriculumId);

    public List<HashMap> searchCommentByPage(HashMap param);

}

