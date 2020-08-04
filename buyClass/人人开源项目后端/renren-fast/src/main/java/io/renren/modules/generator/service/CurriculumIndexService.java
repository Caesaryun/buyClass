package io.renren.modules.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.generator.entity.CurriculumIndexEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 课程目录表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-07-08 19:04:22
 */
public interface CurriculumIndexService extends IService<CurriculumIndexEntity> {

    PageUtils queryPage(Map<String, Object> params);

    public String searchPublicVideo(Integer curriculumId);
    public List<HashMap> searchCurriculumIndex(Integer curriculumId);
}

