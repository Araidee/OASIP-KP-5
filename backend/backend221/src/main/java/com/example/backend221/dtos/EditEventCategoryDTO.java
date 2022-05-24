package com.example.backend221.dtos;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class EditEventCategoryDTO {
    @Size(max = 100,message = "CategoryName must be lower than 500 characters")
    private String eventCategoryName;
    @Size(max = 500,message = "CategoryDescription must be lower than 500 characters")
    private String eventCategoryDescription;
    @Min(value=1, message = "out of range")
    @Max(value=480 , message = "out of range")
    private Integer eventDuration;
}
