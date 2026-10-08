package com.umc.study.service;

import com.umc.study.dto.BookResponse;
import com.umc.study.dto.CreateBookRequest;
import com.umc.study.entity.Book;
import com.umc.study.entity.Category;
import com.umc.study.exception.CategoryNotFoundException;
import com.umc.study.exception.DuplicateBookTitleException;
import com.umc.study.repository.BookJpaRepository;
import com.umc.study.repository.CategoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookJpaRepository bookJpaRepository;
    private final CategoryJpaRepository categoryJpaRepository;

    @Transactional(readOnly = true)
    public List<BookResponse> getBooks(String keyword) {
        List<Book> books = (keyword == null || keyword.isBlank())
                ? bookJpaRepository.findAllByOrderByBookIdDesc()
                : bookJpaRepository.findAllByTitleContainingOrderByBookIdDesc(keyword.trim());
        return books.stream()
                .map(BookResponse::from)
                .toList();
    }

    @Transactional
    public BookResponse createBook(CreateBookRequest request) {
        if (bookJpaRepository.existsByTitle(request.title())) {
            throw new DuplicateBookTitleException(request.title());
        }

        Category category = categoryJpaRepository.findById(request.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException(request.categoryId()));

        Book book = new Book(category, request.title(), request.description());
        return BookResponse.from(bookJpaRepository.save(book));
    }

    @Transactional(readOnly = true)
    public List<BookResponse> getBooksByCategory(Long categoryId) {
        return bookJpaRepository.findAllByCategoryCategoryIdOrderByBookIdDesc(categoryId).stream()
                .map(BookResponse::from)
                .toList();
    }
}
