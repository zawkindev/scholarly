package asuna.maftuna.jp.repository;

import asuna.maftuna.jp.entity.StudentCourseMarkEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StudentCourseMarkRepository extends CrudRepository<StudentCourseMarkEntity, Integer> {
    @Query("select m from StudentCourseMarkEntity m where m.id = :id")
    StudentCourseMarkEntity getById(@Param("id") Integer id);

    @Query("select m from StudentCourseMarkEntity m where m.studentId = :studentId")
    List<StudentCourseMarkEntity> findByStudentId(@Param("studentId") Integer studentId);

    @Query("select m from StudentCourseMarkEntity m where m.courseId = :courseId")
    List<StudentCourseMarkEntity> findByCourseId(@Param("courseId") Integer courseId);

    @Query("select m from StudentCourseMarkEntity m where m.createdDate between :startDate and :endDate")
    List<StudentCourseMarkEntity> findByCreatedDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("select m from StudentCourseMarkEntity m where m.studentId = :studentId and m.createdDate > :from and m.createdDate < :to")
    List<StudentCourseMarkEntity> findMarksByStudentOnDate(@Param("studentId") Integer studentId, @Param("from") LocalDateTime from, @Param("to") LocalDateTime to);

    @Query("select m from StudentCourseMarkEntity m where m.studentId = :studentId and m.createdDate between :startDate and :endDate")
    List<StudentCourseMarkEntity> findMarksByStudentBetweenDates(@Param("studentId") Integer studentId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("select m from StudentCourseMarkEntity m where m.studentId = :studentId order by m.createdDate desc")
    List<StudentCourseMarkEntity> findAllMarksByStudentSortedByDate(@Param("studentId") Integer studentId);

    @Query("select m from StudentCourseMarkEntity m where m.studentId = :studentId and m.courseId = :courseId order by m.createdDate desc")
    List<StudentCourseMarkEntity> findMarksByStudentForCourseSortedByDate(@Param("studentId") Integer studentId, @Param("courseId") Integer courseId);

    @Query("select m from StudentCourseMarkEntity m where m.studentId = :studentId order by m.createdDate desc")
    Page<StudentCourseMarkEntity> findAllByStudentId(@Param("studentId") Integer studentId, Pageable pageable);

    @Query("select m from StudentCourseMarkEntity m where m.courseId = :courseId order by m.createdDate desc")
    Page<StudentCourseMarkEntity> findAllByCourseId(@Param("courseId") Integer courseId, Pageable pageable);

    @Query("select m from StudentCourseMarkEntity m")
    Page<StudentCourseMarkEntity> findAll(Pageable p);

    @Query("select m from StudentCourseMarkEntity m where m.studentId = :studentId order by m.createdDate desc")
    Optional<StudentCourseMarkEntity> findFirstByStudentIdOrderByCreatedDateDesc(@Param("studentId") Integer studentId);

    @Query("select m from StudentCourseMarkEntity m where m.studentId = :studentId order by m.mark desc")
    List<StudentCourseMarkEntity> findTop3ByStudentIdOrderByMarkDesc(@Param("studentId") Integer studentId);

    @Query("select m from StudentCourseMarkEntity m where m.studentId = :studentId order by m.createdDate asc")
    Optional<StudentCourseMarkEntity> findFirstByStudentIdOrderByCreatedDateAsc(@Param("studentId") Integer studentId);

    @Query("select m from StudentCourseMarkEntity m where m.studentId = :studentId and m.courseId = :courseId order by m.createdDate asc")
    Optional<StudentCourseMarkEntity> findFirstByStudentIdAndCourseIdOrderByCreatedDateAsc(@Param("studentId") Integer studentId, @Param("courseId") Integer courseId);

    @Query("select m from StudentCourseMarkEntity m where m.studentId = :studentId and m.courseId = :courseId order by m.mark desc")
    Optional<StudentCourseMarkEntity> findTopByStudentIdAndCourseIdOrderByMarkDesc(@Param("studentId") Integer studentId, @Param("courseId") Integer courseId);

    @Query("select avg(m.mark) from StudentCourseMarkEntity m where m.studentId = :studentId")
    Double findAverageMarkByStudentId(@Param("studentId") Integer studentId);

    @Query("select avg(m.mark) from StudentCourseMarkEntity m where m.studentId = :studentId and m.courseId = :courseId")
    Double findAverageMarkByStudentIdAndCourseId(@Param("studentId") Integer studentId, @Param("courseId") Integer courseId);

    @Query("select count(m) from StudentCourseMarkEntity m where m.studentId = :studentId and m.mark > :mark")
    Long countByStudentIdAndMarkGreaterThan(@Param("studentId") Integer studentId, @Param("mark") Double mark);

    @Query("select m from StudentCourseMarkEntity m where m.courseId = :courseId order by m.mark desc")
    Optional<StudentCourseMarkEntity> findTopByCourseIdOrderByMarkDesc(@Param("courseId") Integer courseId);

    @Query("select count(m) from StudentCourseMarkEntity m where m.courseId = :courseId")
    Long countByCourseId(@Param("courseId") Integer courseId);
}
