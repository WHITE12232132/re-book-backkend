package com.example.rebookbackend.controller;

import com.example.rebookbackend.common.Result;
import com.example.rebookbackend.entity.Book;
import com.example.rebookbackend.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookMapper bookMapper;

    @GetMapping("/list")
    public Result list(){
        return Result.success(bookMapper.findAll());
    }
    @PostMapping("/add")
    public Result add(@RequestBody Book book){
        bookMapper.insert(book);
        return Result.success(null);
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        bookMapper.deleteById(id);
        return Result.success("删除成功");
    }

    @PutMapping("/update")
    public Result update(@RequestBody Book book) {
        bookMapper.update(book);
        return Result.success("修改成功");
    }

    @GetMapping("/search")
    public Result search(@RequestParam String title) {
        // 如果搜索词为空，就调之前的全量查询，否则调搜索查询
        if (title == null || title.isEmpty()) {
            return Result.success(bookMapper.findAll());
        }
        return Result.success(bookMapper.searchByTitle(title));
    }


}
