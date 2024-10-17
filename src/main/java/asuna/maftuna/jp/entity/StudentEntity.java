package asuna.maftuna.jp.entity;

import asuna.maftuna.jp.enumtype.GenderEnum;
import asuna.maftuna.jp.enumtype.LevelEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "student")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private Integer age;
    @Column
    private LocalDateTime createdDate;

    @Enumerated(EnumType.STRING)
    @Column
    private LevelEnum level;

    @Enumerated(EnumType.STRING)
    @Column
    private GenderEnum gender;
}
