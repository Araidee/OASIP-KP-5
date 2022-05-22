package com.example.backend221.dtos;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
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
    @Size(min=1,max = 480 , message = "event duration must be between 1 to 480 minutes")
    private Integer eventDuration;
}
