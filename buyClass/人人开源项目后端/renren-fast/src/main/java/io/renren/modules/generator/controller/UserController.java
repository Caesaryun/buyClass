package io.renren.modules.generator.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.app.utils.JwtUtils;
import io.renren.modules.generator.form.LoginForm;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.generator.entity.UserEntity;
import io.renren.modules.generator.service.UserService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 用户表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-07-08 19:04:22
 */
@RestController
@RequestMapping("app/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @RequestMapping("/login")
    public R login(@RequestBody LoginForm form){
     //验证接收的数据
        ValidatorUtils.validateEntity(form);
        HashMap map = new HashMap();
        map.put("appid","wx1d179919a3d6ec0e");
        map.put("secret","7f99692f5b1567fe5c1ead28f2d0be24");
        map.put("js_code",form.getCode());
        map.put("grant_type","authorization_code");
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        String resp = HttpUtil.get(url,map);
        JSONObject json = JSONUtil.parseObj(resp);
        String openid = json.getStr("openid");
        if(openid==null||openid.length()==0)
        {
            return R.error("获取登录凭证错误");
        }
        //select sql's data
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("open_id",openid);    //看一下openid 是否相等 open_id
        int count = userService.count(wrapper);
        //注册用户
        if(count==0)
        {
            UserEntity user = new UserEntity();
            user.setOpenId(openid);
            user.setNickName(form.getNickName());
            user.setPhoto(form.getAvatarUrl());
            user.setCreateTime(new Date());
            userService.save(user); //执行插入
        }

        //执行登录
        UserEntity user = userService.getOne(wrapper);
        long userId = user.getUserId();    //获取相应的 userid
        String token = jwtUtils.generateToken(userId);
        return R.ok().put("token",token);

    }



    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:user:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{userId}")
    @RequiresPermissions("generator:user:info")
    public R info(@PathVariable("userId") Long userId){
		UserEntity user = userService.getById(userId);

        return R.ok().put("user", user);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:user:save")
    public R save(@RequestBody UserEntity user){
		userService.save(user);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:user:update")
    public R update(@RequestBody UserEntity user){
		userService.updateById(user);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:user:delete")
    public R delete(@RequestBody Long[] userIds){
		userService.removeByIds(Arrays.asList(userIds));

        return R.ok();
    }

}
