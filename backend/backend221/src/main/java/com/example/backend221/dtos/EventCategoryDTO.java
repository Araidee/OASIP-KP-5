package com.example.backend221.dtos;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EventCategoryDTO {
    private String categoryId;
    @Max(value = 100,message = "CategoryName must be lower than 500 characters")
    private String eventCategoryName;
    @Max(value = 500,message = "CategoryDescription must be lower than 500 characters")
    private String eventCategoryDescription;
    @Min(value=1, message = "out of range")
    @Max(value=480 , message = "out of range")
    private Integer eventDuration;
}
