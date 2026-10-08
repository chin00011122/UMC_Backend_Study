package com.umc.study.repository;

import com.umc.study.entity.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookJpaRepository extends JpaRepository<Book, Long> {

    @EntityGraph(attributePaths = "category")
    List<Book> findAllByOrderByBookIdDesc();

    @EntityGraph(attributePaths = "category")
    List<Book> findAllByCategoryCategoryIdOrderByBookIdDesc(Long categoryId);

    @EntityGraph(attributePaths = "category")
    List<Book> findAllByTitleContainingOrderByBookIdDesc(String keyword);

    boolean existsByTitle(String title);
}
