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

import io.renren.modules.generator.dao.CurriculumCommentDao;
import io.renren.modules.generator.entity.CurriculumCommentEntity;
import io.renren.modules.generator.service.CurriculumCommentService;


@Service("curriculumCommentService")
public class CurriculumCommentServiceImpl extends ServiceImpl<CurriculumCommentDao, CurriculumCommentEntity> implements CurriculumCommentService {
    @Autowired
    private  CurriculumCommentDao curriculumCommentDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CurriculumCommentEntity> page = this.page(
                new Query<CurriculumCommentEntity>().getPage(params),
                new QueryWrapper<CurriculumCommentEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<HashMap> searchCurriculumComment(Integer curriculumId) {
        List<HashMap> list=curriculumCommentDao.searchCurriculumComment(curriculumId);
        return list;
    }

    @Override
    public List<HashMap> searchCommentByPage(HashMap param) {
        List<HashMap> list=curriculumCommentDao.searchCommentByPage(param);
        return list;

    }

}