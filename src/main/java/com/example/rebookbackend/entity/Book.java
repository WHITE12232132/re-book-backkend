package com.example.rebookbackend.entity;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Book {
    private Long id;
    private String title;
    private String author;
    private BigDecimal price;

    private Integer stock;
    private Integer status;
    private String coverUrl;
    private LocalDateTime createTime;
}
