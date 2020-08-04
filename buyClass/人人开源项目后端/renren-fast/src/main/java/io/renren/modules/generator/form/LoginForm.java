package io.renren.modules.generator.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginForm {
    @NotBlank(message = "昵称不能为空")
    private String nickName;
    @NotBlank(message = "头像路径不能为空")
    private String avatarUrl;
    @NotBlank(message = "临时授权字符串不能为空")
    private String code;

}
