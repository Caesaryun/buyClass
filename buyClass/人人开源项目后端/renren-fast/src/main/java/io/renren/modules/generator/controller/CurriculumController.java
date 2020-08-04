package io.renren.modules.generator.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.app.annotation.Login;
import io.renren.modules.app.utils.JwtUtils;
import io.renren.modules.generator.form.SearchCurriculumForm;
import io.renren.modules.generator.form.SearchCurriculumPageForm;
import io.renren.modules.generator.form.searchCommentPageForm;
import io.renren.modules.generator.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.generator.entity.CurriculumEntity;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 课程概要表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-07-08 19:04:22
 */
@RestController
@RequestMapping("app/curriculum")
@Api("课程网络类")
public class CurriculumController {
    @Autowired
    private LearningService learningService;  //将service导入

    @Autowired
    private CurriculumService curriculumService;

    @Autowired
    private CurriculumIndexService curriculumIndexService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CurriculumCommentService curriculumCommentService;

    @Autowired
    private JwtUtils jwtUtils;
    //该方法访问的地址
    @RequestMapping("/searchTop")
    @ApiOperation("查询置顶课程")
    @Login
    public R searchTop(){
        List<HashMap> list= curriculumService.searchTop();
        return R.ok().put("result",list);
    }

    @RequestMapping("/searchHot")
    @ApiOperation("查询热门课程")
    public R searchHot(){
         HashMap map = curriculumService.searchHot();
         return R.ok().put("result",map);
    }

    @RequestMapping("/searchRefined")
    @ApiOperation("查询精制课程")
    public R searchRefined(){
        List<HashMap> list_1=curriculumService.searchByDifficulty("入门");
        List<HashMap> list_2=curriculumService.searchByDifficulty("简单");
        List<HashMap> list_3=curriculumService.searchByDifficulty("中等");
        return R.ok().put("list_1",list_1).put("list_2",list_2).put("list_3",list_3);
    }


    @RequestMapping("/searchByPage")
    @ApiOperation("查询分页数据")
    public R searchByPage(@RequestBody SearchCurriculumPageForm form)
    {
        Integer page = form.getPage();
        System.out.println("显示page:");
        System.out.println(page);
        Integer length = form.getLength();
        Integer start = (page-1)*length;
        HashMap param = new HashMap();
        param.put("start",start);
        param.put("length",length);
        param.put("difficulty",form.getDifficulty());
        param.put("keyword",form.getKeyword());
        List<HashMap> list = curriculumService.searchByPage(param);
        return  R.ok().put("result",list);
    }

    @RequestMapping("/searchCommentByPage")
    @ApiOperation("查询分页评论")
    public R searchCommentByPage(@RequestBody searchCommentPageForm form)
    {
         String wang=form.getWang();
         System.out.print(wang);

         Integer page=form.getPage();
         Integer length = form.getLength();

         Integer start = (page-1)*length;

         HashMap param=new HashMap();
         param.put("curriculum_Id",form.getCurriculumId());
         param.put("start",start);
         param.put("length",length);

         List<HashMap> list = curriculumCommentService.searchCommentByPage(param);
         return R.ok().put("result",list);

    }


    @RequestMapping("/searchCurriculum")
    @ApiOperation("查询课程详情")
    public R searchCurriculum(@RequestBody SearchCurriculumForm form , @RequestHeader HashMap header){
        ValidatorUtils.validateEntity(form);
        String token=header.get("token").toString();
        Integer userId=Integer.parseInt(jwtUtils.getClaimByToken(token).getSubject());

        HashMap summary = curriculumService.searchSummary(form.getCurriculumId());
        String publicVideo=curriculumIndexService.searchPublicVideo(form.getCurriculumId());
        HashMap teacher=teacherService.searchCurriculumTeacher(form.getCurriculumId());
        List<HashMap> index=curriculumIndexService.searchCurriculumIndex(form.getCurriculumId());
        List<HashMap> comment=curriculumCommentService.searchCurriculumComment(form.getCurriculumId());
        //learningService
        HashMap param=new HashMap();
        param.put("userId",userId);
        param.put("curriculumId",form.getCurriculumId());
        HashMap learning=learningService.searchUserLearning(param);

        return R.ok().put("summary",summary)
                .put("publicVideo",publicVideo).
                        put("teacher",teacher).
                        put("index",index).
                        put("comment",comment).
                        put("learning",learning);


    }



    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:curriculum:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = curriculumService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:curriculum:info")
    public R info(@PathVariable("id") Integer id){
		CurriculumEntity curriculum = curriculumService.getById(id);

        return R.ok().put("curriculum", curriculum);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:curriculum:save")
    public R save(@RequestBody CurriculumEntity curriculum){
		curriculumService.save(curriculum);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:curriculum:update")
    public R update(@RequestBody CurriculumEntity curriculum){
		curriculumService.updateById(curriculum);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:curriculum:delete")
    public R delete(@RequestBody Integer[] ids){
		curriculumService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }



    //public

}
