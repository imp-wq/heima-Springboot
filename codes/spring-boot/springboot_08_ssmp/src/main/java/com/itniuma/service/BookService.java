package com.itniuma.service;

import com.itniuma.domain.Book;
import com.itniuma.service.utils.IPage;

import java.util.List;

public interface BookService {
    Boolean save(Book book);

    Boolean update(Book book);

    Boolean delete(Integer id);

    Book getById(Integer id);

    List<Book> getAll();

    //IPage getPage(Integer currentPage, Integer pageSize);

    IPage getPage(Integer currentPage, Integer pageSize,Book book);

}
