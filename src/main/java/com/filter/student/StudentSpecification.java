package com.filter.student;

import com.patika.entity.Student;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StudentSpecification {
    public static Specification<Student> build(final StudentFilter filter) {
        return new Specification<Student>() {
            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                if (filter.getFirstName() != null && !filter.getFirstName().isBlank()) {
                    predicates.add(
                            cb.like(cb.lower(root.get("firstName")), "%" + filter.getFirstName().toLowerCase() + "%")
                    );
                }

                if (filter.getLastName() != null && !filter.getLastName().isBlank()) {
                    predicates.add(
                            cb.like(cb.lower(root.get("lastName")), "%" + filter.getLastName().toLowerCase() + "%")
                    );
                }

                if (filter.getDepartmentName() != null && !filter.getDepartmentName().isBlank()) {
                    // department ilişkisi üzerinden department name ile filtre
                    predicates.add(
                            cb.like(cb.lower(root.get("department").get("departmentName")), "%" + filter.getDepartmentName().toLowerCase() + "%")
                    );
                }

                if (filter.getCreatedAtBegin() != null) {
                    predicates.add(
                            cb.greaterThanOrEqualTo(root.get("createdAt"), filter.getCreatedAtBegin())
                    );
                }

                if (filter.getCreatedAtEnd() != null) {
                    predicates.add(
                            cb.lessThanOrEqualTo(root.get("createdAt"), filter.getCreatedAtEnd())
                    );
                }

                return cb.and(predicates.toArray(new Predicate[0]));
            }
        };
    }
}
