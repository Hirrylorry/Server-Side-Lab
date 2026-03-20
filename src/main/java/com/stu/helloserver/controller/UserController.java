package com.stu.helloserver.controller;

import com.stu.helloserver.common.Result;
import com.stu.helloserver.entity.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("/{id}")
    public Result<User> getUser(@PathVariable("id") Long id) {
        if (id == 0L) {
            int error = 1 / 0;
        }

        User user = new User(id, "用户" + id, 20);
        return Result.success("查询成功，正在返回 ID 为 " + id + " 的用户信息", user);
    }

    @PostMapping
    public Result<User> createUser(@RequestBody User user) {
        user.setId(1001L);
        return Result.success("新增成功，接收到用户：" + user.getName() + "，年龄：" + user.getAge(), user);
    }

    @PutMapping("/{id}")
    public Result<User> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        user.setId(id);
        return Result.success("更新成功，ID " + id + " 的用户已修改为：" + user.getName(), user);
    }

    @DeleteMapping("/{id}")
    public Result<String> deleteUser(@PathVariable("id") Long id) {
        return Result.success("删除成功，已移除 ID 为 " + id + " 的用户", null);
    }
}
