package io.renren.modules.generator.form;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class SearchCurriculumPageForm {

      private String keyword;

      private String difficulty;

      @Min(1)
      private Integer page;


      @Min(1)
      @Max(100)
      private Integer length;




}
