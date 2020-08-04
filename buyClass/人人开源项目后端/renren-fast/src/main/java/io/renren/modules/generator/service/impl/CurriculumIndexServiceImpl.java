package io.renren.modules.generator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.generator.dao.CurriculumIndexDao;
import io.renren.modules.generator.entity.CurriculumIndexEntity;
import io.renren.modules.generator.service.CurriculumIndexService;


@Service("curriculumIndexService")
public class CurriculumIndexServiceImpl extends ServiceImpl<CurriculumIndexDao, CurriculumIndexEntity> implements CurriculumIndexService {
    @Autowired
    private CurriculumIndexDao curriculumIndexDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CurriculumIndexEntity> page = this.page(
                new Query<CurriculumIndexEntity>().getPage(params),
                new QueryWrapper<CurriculumIndexEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public String searchPublicVideo(Integer curriculumId) {
        String publicVideo=curriculumIndexDao.searchPublicVideo(curriculumId);
        return publicVideo;
    }

    @Override
    public List<HashMap> searchCurriculumIndex(Integer curriculumId) {
        List<HashMap> list=curriculumIndexDao.searchCurriculumIndex(curriculumId);
        return list;
    }

}