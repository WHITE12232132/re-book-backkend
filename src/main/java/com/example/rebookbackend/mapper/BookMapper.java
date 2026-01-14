package com.example.rebookbackend.mapper;

import com.example.rebookbackend.entity.Book;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BookMapper {

    @Select("SELECT * FROM book ORDER BY create_time DESC")
    List<Book> findAll();


    @Select("SELECT * FROM book WHERE id = ${id}")
    Book findById(Long id);

    @Insert("INSERT INTO book(title, author, price, stock, status, cover_url)" + "VALUES(#{title},#{author},#{price},#{stock},#{status}), #{coverUrl}")
    int insert(Book book);

    @Delete("DELETE FROM book WHERE id = #{id}")
    int deleteById(Long id);

    @Update("UPDATE book SET title=#{title}, author=#{author}, price=#{price}, " +
            "stock=#{stock}, status=#{status}, cover_url=#{coverUrl} WHERE id=#{id}")
    int update(Book book);

    @Select("SELECT * FROM book WHERE title LIKE CONCAT('%', #{title}, '%') ORDER BY create_time DESC")
    List<Book> searchByTitle(String title);



}
