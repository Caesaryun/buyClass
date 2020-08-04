package io.renren.modules.generator.form;


import io.swagger.models.auth.In;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class BkSearchTeacherPageForm {

    @Min(0)
    private  Integer page;

    @Min(1)
    @Max(100)
    private  Integer limit;

    private Integer id;

    private String nickname;

    private String title;

}
