package com.example.backend221.dtos;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EventCategoryDTO {
    private String eventCategoryName;
    private String eventCategoryDescription;
    private Integer eventDuration;
}
