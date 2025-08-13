package com.jongkeun.backend.repository;

import com.jongkeun.backend.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByCompletedOrderByCreatedAtDesc(Boolean completed);

    List<Todo> findAllByOrderByCreatedAtDesc();

    @Query("SELECT t FROM Todo t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :title, '%')) ORDER BY t.createdAt DESC")
    List<Todo> findByTitleContainingIgnoreCase(@Param("title") String title);

    long countByCompleted(Boolean completed);
}
