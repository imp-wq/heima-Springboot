package com.itniuma.service.impl;

import com.itniuma.dao.BookDao;
import com.itniuma.domain.Book;
import com.itniuma.service.BookService;
import com.itniuma.service.utils.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    @Override
    public Boolean save(Book book) {
        return this.bookDao.insert(book) > 0;
    }

    @Override
    public Boolean update(Book book) {
        return this.bookDao.update(book) > 0;
    }

    @Override
    public Boolean delete(Integer id) {
        return this.bookDao.deleteById(id) > 0;
    }

    @Override
    public Book getById(Integer id) {
        return this.bookDao.selectById(id);
    }

    @Override
    public List<Book> getAll() {
        return this.bookDao.selectList();
    }

    //@Override
    //public IPage getPage(Integer currentPage, Integer pageSize) {
    //    int offset = (currentPage - 1) * pageSize;
    //    Object records = this.bookDao.selectPage(offset, pageSize);
    //    int total = this.bookDao.getTotal();
    //    return new IPage(records, total, currentPage, pageSize);
    //}

    @Override
    public IPage getPage(Integer currentPage, Integer pageSize, Book book) {
        int offset = (currentPage - 1) * pageSize;
        Object records = this.bookDao.selectPage(offset, pageSize, book);
        int total = this.bookDao.getTotal();
        return new IPage(records, total, currentPage, pageSize);
    }


}
