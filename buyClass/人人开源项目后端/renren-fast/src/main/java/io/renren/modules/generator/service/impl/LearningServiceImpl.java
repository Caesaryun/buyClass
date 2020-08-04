package io.renren.modules.generator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.generator.dao.LearningDao;
import io.renren.modules.generator.entity.LearningEntity;
import io.renren.modules.generator.service.LearningService;


@Service("learningService")
public class LearningServiceImpl extends ServiceImpl<LearningDao, LearningEntity> implements LearningService {

     @Autowired
     private  LearningDao learningDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<LearningEntity> page = this.page(
                new Query<LearningEntity>().getPage(params),
                new QueryWrapper<LearningEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public HashMap searchUserLearning(HashMap param) {
        HashMap map=learningDao.searchUserLearning(param);
        return map;
    }

}