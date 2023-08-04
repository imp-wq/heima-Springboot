package com.itniuma.controller;

import com.itniuma.domain.Book;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;

import java.util.List;
@RestController
@RequestMapping("/books")
public class BookController {


    @PostMapping
    public String save(@RequestBody Book book) {
        System.out.println("book save ==> " + book);
        return "{'module':'book save success!'}";
    }

    @GetMapping
    public List<Book> getAll() {
        Book book1 = new Book();
        book1.setType("computer science");
        book1.setName("Sping MVC entry");
        book1.setDescription("小试牛刀");

        Book book2 = new Book();
        book2.setType("computer science");
        book2.setName("C++ primer plus");
        book2.setDescription("一代宗师");

        ArrayList<Book> bookslist = new ArrayList<>();
        bookslist.add(book1);
        bookslist.add(book2);

        return bookslist;
    }
}
