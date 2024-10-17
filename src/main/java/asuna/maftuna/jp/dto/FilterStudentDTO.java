package asuna.maftuna.jp.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class FilterStudentDTO {
    private Integer id;
    private String name;
    private String surname;
    private Integer age;
    private LocalDate birthDateFrom;
    private LocalDate birthDateTo;
}
