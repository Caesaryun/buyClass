package io.renren.modules.generator.form;

import lombok.Data;

import javax.validation.constraints.Min;

//这个 Data 必须得记住了
@Data
public class SearchCurriculumForm {

    @Min(1)
    private Integer curriculumId;

//    public Integer getCurriculumId() {
//        return curriculumId;
//    }

}

