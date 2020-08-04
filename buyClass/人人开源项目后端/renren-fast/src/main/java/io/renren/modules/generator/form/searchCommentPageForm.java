package io.renren.modules.generator.form;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class searchCommentPageForm {

   //若想要获取到到数值，这里应该与前端进行传递来的数值进行对应

    @Min(1)
    private Integer curriculumId;   //课程id

    @Min(1)
    private Integer page;   //


    @Min(1)
    @Max(100)
    private Integer length;   //每页大小


    private String wang; //test name

}
