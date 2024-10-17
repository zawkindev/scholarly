package asuna.maftuna.jp.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FilterStudentCourseMarkDTO {
    private Integer id;
    private Integer studentId;
    private Integer courseId;
    private String  studentName;
    private String  courseName;
    private Integer markFrom;
    private Integer markTo;
    private LocalDateTime createdDateFrom;
    private LocalDateTime createdDateTo;
}
