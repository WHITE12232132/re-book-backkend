package com.example.rebookbackend.controller;

import com.example.rebookbackend.common.JwtUtils;
import com.example.rebookbackend.common.Result;
import com.example.rebookbackend.entity.User;
import com.example.rebookbackend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/login")
    public Result login(@RequestBody User loginUser) {
        // 1. 根据用户名查数据库
        User dbUser = userMapper.findByUsername(loginUser.getUsername());

        // 2. 校验
        if (dbUser == null) {
            return Result.error("用户不存在");
        }
        if (!dbUser.getPassword().equals(loginUser.getPassword())) {
            return Result.error("密码错误");
        }

        // 4. 登录成功，生成 JWT Token
        // 传入用户ID和用户名作为 Payload (载荷)
        String token = JwtUtils.createToken(dbUser.getId(), dbUser.getUsername());

        // 5. 封装返回数据
        // 因为 Result 只能带一个 data，所以我们用 Map 包装 Token 和用户信息
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);

        // 擦除敏感信息后再返回给前端
        dbUser.setPassword(null);
        data.put("user", dbUser);

        return Result.success(data);
    }


}

