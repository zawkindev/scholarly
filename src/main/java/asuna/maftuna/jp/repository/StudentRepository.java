package asuna.maftuna.jp.repository;

import asuna.maftuna.jp.entity.StudentEntity;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface StudentRepository extends CrudRepository<StudentEntity, Integer> {
    @Query("select s from StudentEntity s where s.id = :id")
    StudentEntity getById(@Param("id") Integer id);

    @Query("select s from StudentEntity s where s.name = :name")
    List<StudentEntity> findByName(@Param("name") String name);

    @Query("select s from StudentEntity s where s.surname = :surname")
    List<StudentEntity> findBySurname(@Param("surname") String surname);

    @Query("select s from StudentEntity s where s.level = :level")
    List<StudentEntity> findByLevel(@Param("level") String level);

    @Query("select s from StudentEntity s where s.age = :age")
    List<StudentEntity> findByAge(@Param("age") int age);

    @Query("select s from StudentEntity s where s.gender = :gender")
    List<StudentEntity> findByGender(@Param("gender") String gender);

    @Query("select s from StudentEntity s where s.createdDate between :from and :to")
    List<StudentEntity> findByCreatedDateBetween(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);

    @Query("select s from StudentEntity s where s.createdDate = :localDate")
    List<StudentEntity> findByCreatedDate(@Param("localDate") LocalDate localDate);

    @Query("select s from StudentEntity s")
    Page<StudentEntity> findAll(Pageable p);

    @Query("select s from StudentEntity s where s.level = :level")
    Page<StudentEntity> findByLevel(@Param("level") String level, Pageable p);

    @Query("select s from StudentEntity s where s.gender = :gender")
    Page<StudentEntity> findByGender(@Param("gender") String gender, Pageable p);



}
