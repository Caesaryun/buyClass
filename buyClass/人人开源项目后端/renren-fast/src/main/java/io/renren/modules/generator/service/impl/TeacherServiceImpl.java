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

import io.renren.modules.generator.dao.TeacherDao;
import io.renren.modules.generator.entity.TeacherEntity;
import io.renren.modules.generator.service.TeacherService;


@Service("teacherService")
public class TeacherServiceImpl extends ServiceImpl<TeacherDao, TeacherEntity> implements TeacherService {
    @Autowired
    private  TeacherDao teacherDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Integer id=null;
        String nickname=null;
        String title=null;
        QueryWrapper<TeacherEntity> queryWrapper=new QueryWrapper<>();

        if(params.containsKey("id")){
           id=(Integer)params.get("id");
           queryWrapper.eq("id",id);
        }
        if(params.containsKey("nickname")){
            nickname=(String)params.get("nickname");
            queryWrapper.like("nick_name","%"+nickname+"%");
        }
        if(params.containsKey("title")){
            title=(String)params.get("title");
            queryWrapper.eq("title",title);
        }

        IPage<TeacherEntity> page = this.page(
                new Query<TeacherEntity>().getPage(params),
                //new QueryWrapper<TeacherEntity>()
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public HashMap searchCurriculumTeacher(Integer curriculumId) {
         HashMap map=teacherDao.searchCurriculumTeacher(curriculumId);
         return  map;
    }

}