package com.example.rebookbackend.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data // 自动生成 get/set/toString
public class User {
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String avatar;
    private LocalDateTime createTime;
}
