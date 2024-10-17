package asuna.maftuna.jp.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class FilterCourseDTO {
    private Integer id;
    private String name;
    private Double price;
    private LocalTime duration;
    private LocalDateTime createdDateFrom;
    private LocalDateTime createdDateTo;
}
