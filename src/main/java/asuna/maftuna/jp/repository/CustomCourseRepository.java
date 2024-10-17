package asuna.maftuna.jp.repository;

import asuna.maftuna.jp.dto.FilterCourseDTO;
import asuna.maftuna.jp.entity.CourseEntity;
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
public class CustomCourseRepository {
    @Autowired
    private EntityManager entityManager;

    public Page<CourseEntity> filter(FilterCourseDTO filter, int page, int size) {
        StringBuilder selectQueryBuilder = new StringBuilder("SELECT s FROM CourseEntity s ");
        StringBuilder countQueryBuilder = new StringBuilder("SELECT count(s) FROM CourseEntity s ");

        StringBuilder builder = new StringBuilder(" where s.visible = true ");

        Map<String, Object> params = new HashMap<>();

        if (filter.getId() != null) { // condition by id
            builder.append(" and s.id =:id ");
            params.put("id", filter.getId());
        }
        if (filter.getName() != null) { // condition by name
            builder.append(" and s.name=:name ");
            params.put("name", filter.getName());
        }
        if (filter.getPrice() != null) {
            builder.append(" and s.age =:age ");
            params.put("age", filter.getPrice());
        }
        if (filter.getDuration() != null) {
            builder.append(" and s.age =:age ");
            params.put("age", filter.getDuration());
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
        List<CourseEntity> profileEntityList = selectQuery.getResultList();

        // totalCount query
        Query countQuery = entityManager.createQuery(countQueryBuilder.toString());
        params.forEach(countQuery::setParameter);

        // execute count query
        Long totalElements = (Long) countQuery.getSingleResult();

        return new PageImpl<>(profileEntityList, PageRequest.of(page, size), totalElements);
    }
}
