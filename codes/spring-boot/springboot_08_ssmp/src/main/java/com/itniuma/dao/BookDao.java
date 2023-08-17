package com.itniuma.dao;

import com.itniuma.domain.Book;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.apache.logging.log4j.util.Strings;

import java.util.List;

@Mapper
public interface BookDao {
    @Select("select * from tbl_book where id = #{id}")
    Book selectById(Integer id);

    @Insert("insert into tbl_book(type, name, description) values(#{type}, #{name}, #{description})")
    Integer insert(Book book);

    @Update("update tbl_book set type=#{type},name=#{name},description=#{description} where id=#{id}")
    Integer update(Book book);

    @Delete("delete from tbl_book where id=#{id}")
    Integer deleteById(Integer id);

    @Select("select * from tbl_book")
    List<Book> selectList();

    //@Select("select * from tbl_book limit #{offset},#{rows}")
    //List<Book> selectPage(Integer offset, Integer rows);

    @SelectProvider(type = BookSqlBuilder.class, method = "buildSelectPage")
    List<Book> selectPage(Integer offset, Integer rows, Book book);

    class BookSqlBuilder {
        public static String buildSelectPage(Book book) {
            return new SQL() {{
                SELECT("*");
                FROM("tbl_book");
                LIMIT("#{offset},#{rows}");
                if (Strings.isNotEmpty(book.getName())) WHERE("name like \"%\"#{book.name}\"%\"");
                if (Strings.isNotEmpty(book.getType())) WHERE("type like #{book.type}");
                if (Strings.isNotEmpty(book.getDescription())) WHERE("description like #{book.description}");
            }}.toString();
        }
    }


    @Select("select count(*) from tbl_book")
    Integer getTotal();
}


