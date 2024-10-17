package asuna.maftuna.jp.repository;

import asuna.maftuna.jp.dto.FilterStudentCourseMarkDTO;
import asuna.maftuna.jp.entity.StudentCourseMarkEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CustomStudentCourseMarkRepository {
    @Autowired
    private EntityManager entityManager;

    public Page<StudentCourseMarkEntity> filter(FilterStudentCourseMarkDTO filter, int page, int size) {
        StringBuilder selectQueryBuilder = new StringBuilder("SELECT s FROM StudentCourseMarkEntity s ");
        StringBuilder countQueryBuilder = new StringBuilder("SELECT count(s) FROM StudentCourseMarkEntity s ");

        StringBuilder builder = new StringBuilder(" where s.visible = true ");

        Map<String, Object> params = new HashMap<>();

        if (filter.getId() != null) { // condition by id
            builder.append(" and s.id =:id ");
            params.put("id", filter.getId());
        }
        if (filter.getStudentId() != null) { // condition by name
            builder.append(" and s.studentId =:studentId ");
            params.put("name", filter.getStudentId());
        }
        if (filter.getCourseId() != null) {
            builder.append(" and s.courseId =:courseId ");
            params.put("age", filter.getCourseId());
        }
        if (filter.getStudentName() != null) { // condition by name
            builder.append(" and s.studentName =:studentName ");
            params.put("name", filter.getStudentName());
        }
        if (filter.getCourseName() != null) {
            builder.append(" and s.courseName =:courseName ");
            params.put("age", filter.getCourseName());
        }
        if (filter.getMarkFrom() != null) {
            builder.append(" and s.mark > :markFrom ");
            params.put("age", filter.getMarkFrom());
        }
        if (filter.getMarkTo() != null) {
            builder.append(" and s.mark < :markTo ");
            params.put("age", filter.getMarkTo());
        }
        if (filter.getCreatedDateFrom() != null && filter.getCreatedDateTo() != null) {
            builder.append(" and s.createdDate between :createdDateFrom and :createdDateTo ");
            params.put("createdDateFrom", filter.getCreatedDateFrom());
            params.put("createdDateTo", filter.getCreatedDateTo());
        } else if (filter.getCreatedDateFrom() != null) {
            builder.append(" and s.createdDate > :createdDateFrom ");
            params.put("createdDateFrom", filter.getCreatedDateFrom());
        } else if (filter.getCreatedDateTo() != null) {
            builder.append(" and s.createdDate < :createdDateTo ");
            params.put("createdDateTo", filter.getCreatedDateTo());
        }

        selectQueryBuilder.append(builder);
        countQueryBuilder.append(builder);

        // select query
        Query selectQuery = entityManager.createQuery(selectQueryBuilder.toString());
        selectQuery.setFirstResult((page) * size); // 50
        selectQuery.setMaxResults(size); // 30
        params.forEach(selectQuery::setParameter);

        // execute select query
        List<StudentCourseMarkEntity> profileEntityList = selectQuery.getResultList();

        // totalCount query
        Query countQuery = entityManager.createQuery(countQueryBuilder.toString());
        params.forEach(countQuery::setParameter);

        // execute count query
        Long totalElements = (Long) countQuery.getSingleResult();

        return new PageImpl<>(profileEntityList, PageRequest.of(page, size), totalElements);
    }
}
