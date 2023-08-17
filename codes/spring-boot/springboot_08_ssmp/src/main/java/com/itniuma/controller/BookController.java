package com.itniuma.controller;

import com.itniuma.controller.utils.Result;
import com.itniuma.domain.Book;
import com.itniuma.service.BookService;
import com.itniuma.service.utils.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public Result getAll() {
        return new Result(true, this.bookService.getAll());
    }

    @PostMapping
    public Result save(@RequestBody Book book) throws IOException {
        if (book.getName().equals("error")) throw new IOException();
        return new Result(this.bookService.save(book));
    }

    @PutMapping
    public Result update(@RequestBody Book book) {
        return new Result(this.bookService.update(book));
    }

    @DeleteMapping("{id}")
    public Result delete(@PathVariable Integer id) {
        return new Result(this.bookService.delete(id));
    }

    @GetMapping("{id}")
    public Result getById(@PathVariable Integer id) {
        return new Result(true, this.bookService.getById(id));
    }

    @GetMapping("/page")
    public Result getPage(Integer currentPage, Integer pageSize, Book book) {
        IPage iPage = this.bookService.getPage(currentPage, pageSize, book);
        System.out.println(book);
        int total = iPage.getTotal();
        // 对删除页面中最后一条数据的情况进行特殊处理，如果查询页码>最大页码，则返回最后一页数据。
        if (currentPage > total) {
            iPage = this.bookService.getPage(total, pageSize, book);
        }
        return new Result(true, iPage);
    }
}
