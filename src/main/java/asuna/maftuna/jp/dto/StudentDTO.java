package asuna.maftuna.jp.dto;

import asuna.maftuna.jp.enumtype.GenderEnum;
import asuna.maftuna.jp.enumtype.LevelEnum;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class StudentDTO {
    private Integer id;
    @NotBlank(message = "Name is blank")
    @Size(min = 3, max = 32, message = "Name should be 3...32 character")
    private String name;

    @NotBlank(message = "Surname is blank")
    @Size(min = 3, max = 32, message = "Surname should be 3...32 character")
    private String surname;

    private Integer age;

    @Pattern(regexp = "NOOB|MID|PRO", message = "level must be NOOB, MID or PRO")
    private LevelEnum level;

    @PastOrPresent
    @NotNull
    private LocalDateTime createdDate;

    @Pattern(regexp = "MALE|FEMALE", message = "gender should be MALE or FEMALE")
    private GenderEnum gender;
}