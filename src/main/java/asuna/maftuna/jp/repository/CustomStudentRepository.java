package asuna.maftuna.jp.repository;

import asuna.maftuna.jp.dto.FilterStudentDTO;
import asuna.maftuna.jp.entity.StudentEntity;
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
public class CustomStudentRepository {
    @Autowired
    private EntityManager entityManager;

    public Page<StudentEntity> filter(FilterStudentDTO filter, int page, int size) {
        StringBuilder selectQueryBuilder = new StringBuilder("SELECT s FROM StudentEntity s ");
        StringBuilder countQueryBuilder = new StringBuilder("SELECT count(s) FROM StudentEntity s ");

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
        if (filter.getSurname() != null) { // condition by surname with like
            builder.append(" and LOWER(s.surname) like :surname ");
            params.put("surname", "%" + filter.getSurname().toLowerCase() + "%");
        }
        if (filter.getAge() != null) {
            builder.append(" and s.age =:age ");
            params.put("age", filter.getAge());
        }

        if (filter.getBirthDateFrom() != null && filter.getBirthDateTo() != null) {
            builder.append(" and s.birthdate between :birthDateFrom and :birthDateTo ");
            params.put("birthDateFrom", filter.getBirthDateFrom());
            params.put("birthDateTo", filter.getBirthDateTo());
        } else if (filter.getBirthDateFrom() != null) {
            builder.append(" and s.birthdate > :birthDateFrom ");
            params.put("birthDateFrom", filter.getBirthDateFrom());
        } else if (filter.getBirthDateTo() != null) {
            builder.append(" and s.birthdate < :birthDateTo ");
            params.put("birthDateTo", filter.getBirthDateTo());
        }

        selectQueryBuilder.append(builder);
        countQueryBuilder.append(builder);

        // select query
        Query selectQuery = entityManager.createQuery(selectQueryBuilder.toString());
        selectQuery.setFirstResult((page) * size); // 50
        selectQuery.setMaxResults(size); // 30
        params.forEach(selectQuery::setParameter);

        // execute select query
        List<StudentEntity> profileEntityList = selectQuery.getResultList();

        // totalCount query
        Query countQuery = entityManager.createQuery(countQueryBuilder.toString());
        params.forEach(countQuery::setParameter);

        // execute count query
        Long totalElements = (Long) countQuery.getSingleResult();

        return new PageImpl<>(profileEntityList, PageRequest.of(page, size), totalElements);
    }
}
