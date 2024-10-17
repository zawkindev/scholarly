package asuna.maftuna.jp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class CourseDTO {
    private Integer id;
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 32, message = "Name should be 3...32 character")
    private String name;
    private Double price;
    private LocalTime duration;
    @PastOrPresent
    private LocalDateTime createdDate;
}
