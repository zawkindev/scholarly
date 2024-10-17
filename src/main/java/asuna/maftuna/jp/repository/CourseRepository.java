package asuna.maftuna.jp.repository;

import asuna.maftuna.jp.entity.CourseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface CourseRepository extends CrudRepository<CourseEntity, Integer> {
    @Query("select c from CourseEntity c where c.id = :id")
    CourseEntity getById(@Param("id") Integer id);

    @Query("select c from CourseEntity c where c.name = :name")
    List<CourseEntity> findByName(@Param("name") String name);

    @Query("select c from CourseEntity c where c.price = :price")
    List<CourseEntity> findByPrice(@Param("price") double price);

    @Query("select c from CourseEntity c where c.duration = :duration")
    List<CourseEntity> findByDuration(@Param("duration") LocalTime duration);

    @Query("select c from CourseEntity c where c.price between :from and :to")
    List<CourseEntity> findByPriceBetween(@Param("from") Double from, @Param("to") Double to);

    @Query("select c from CourseEntity c where c.createdDate between :from and :to")
    List<CourseEntity> findByCreatedDateBetween(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);

    @Query("select c from CourseEntity c")
    Page<CourseEntity> findAll(Pageable p);

    @Query("select c from CourseEntity c order by c.createdDate")
    Page<CourseEntity> findAllOrderByCreatedDate(Pageable p);

    @Query("select c from CourseEntity c where c.price = :price order by c.createdDate")
    Page<CourseEntity> findByPriceOrderByCreatedDate(@Param("price") double price, Pageable p);

    @Query("select c from CourseEntity c where c.price between :from and :to order by c.createdDate")
    Page<CourseEntity> findByPriceBetweenOrderByCreatedDate(@Param("from") double from, @Param("to") double to, Pageable p);
}
