package asuna.maftuna.jp.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class StudentCourseMarkDTO {
    @NotNull
    private Integer id;
    @NotNull
    private Integer studentId;
    @NotNull
    private Integer courseId;
    private StudentDTO student;
    private CourseDTO course;
    private Integer mark;
    private LocalDateTime createdDate = LocalDateTime.now();
}
