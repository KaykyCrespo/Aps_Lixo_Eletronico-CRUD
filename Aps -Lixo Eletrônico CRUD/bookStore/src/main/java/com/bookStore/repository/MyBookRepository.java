package com.bookStore.repository;

import com.bookStore.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.bookStore.entity.MyBookList;

import java.util.List;

@Repository
public interface MyBookRepository extends JpaRepository<MyBookList,Integer>{
    @Query("""
            SELECT name,author,price FROM Books
            """)
    Book pegarTudo();
}
