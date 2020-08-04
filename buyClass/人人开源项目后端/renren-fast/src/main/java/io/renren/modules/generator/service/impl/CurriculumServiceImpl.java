package io.renren.modules.generator.service.impl;

import org.apache.shiro.crypto.hash.Hash;
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

import io.renren.modules.generator.dao.CurriculumDao;
import io.renren.modules.generator.entity.CurriculumEntity;
import io.renren.modules.generator.service.CurriculumService;
import org.springframework.web.servlet.HandlerAdapter;


@Service("curriculumService")
public class CurriculumServiceImpl extends ServiceImpl<CurriculumDao, CurriculumEntity> implements CurriculumService {
    @Autowired
    private CurriculumDao curriculumDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CurriculumEntity> page = this.page(
                new Query<CurriculumEntity>().getPage(params),
                new QueryWrapper<CurriculumEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<HashMap> searchTop() {
        List<HashMap> list=curriculumDao.searchTop();
        return list;    //差点被蒙了
       // return null;
    }

    @Override
    public HashMap searchHot() {
        HashMap map = curriculumDao.searchHot();
        return map;
    }

    @Override
    public List<HashMap> searchByDifficulty(String difficulty) {
        List<HashMap> list=curriculumDao.searchByDifficulty(difficulty);
        return list;
    }

    @Override
    public List<HashMap> searchByPage(HashMap param) {
        List<HashMap> list=curriculumDao.searchByPage(param);
        return list;
    }

    @Override
    public HashMap searchSummary(Integer id) {
        HashMap map=curriculumDao.searchSummary(id);
        return map;
    }


}