package com.itniuma.dao;

import com.itniuma.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookDaoTestCase {
    @Autowired
    private BookDao bookDao;

    @Test
    void testRetrieve() {
        Book book = this.bookDao.selectById(2);
        System.out.println(book);
    }

    @Test
    void testInsert() {
        Book book = new Book();
        book.setName("Spring入门指南");
        book.setType("computer science");
        book.setDescription("入门书籍");
        System.out.println(this.bookDao.insert(book));
    }

    @Test
    void testUpdate() {
        Book book = new Book();
        book.setId(5);
        book.setName("Spring入门精通");
        book.setType("computer science");
        book.setDescription("精通书籍");
        System.out.println(this.bookDao.update(book));
    }

    @Test
    void testDelete() {
        System.out.println(this.bookDao.deleteById(14));
    }

    @Test
    void testGetAll() {
        System.out.println(this.bookDao.selectList());

    }

    //@Test
    //void testPage() {
    //    System.out.println(this.bookDao.selectPage(0,5));
    //}

    @Test
    void testGetTotal() {
        System.out.println(this.bookDao.getTotal());
    }
}
