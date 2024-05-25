package com.bookStore.controller;

import com.bookStore.entity.Book;
import com.bookStore.entity.MyBookList;

public record DetalharDados(int id,String name, String author, String price){

    public DetalharDados(MyBookList dados) {
        this(dados.getId(), dados.getName(), dados.getAuthor(), dados.getPrice());
    }
}
