package com.stu.helloserver.service.impl;

import com.stu.helloserver.common.Result;
import com.stu.helloserver.common.ResultCode;
import com.stu.helloserver.dto.UserDTO;
import com.stu.helloserver.service.UserService;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final Map<String, String> userDb = new ConcurrentHashMap<>();

    @Override
    public Result<String> register(UserDTO userDTO) {
        if (userDb.containsKey(userDTO.getUsername())) {
            return Result.error(ResultCode.USER_HAS_EXISTED);
        }
        userDb.put(userDTO.getUsername(), userDTO.getPassword());
        return Result.success("注册成功");
    }

    @Override
    public Result<String> login(UserDTO userDTO) {
        if (!userDb.containsKey(userDTO.getUsername())) {
            return Result.error(ResultCode.USER_NOT_EXIST);
        }
        String dbPassword = userDb.get(userDTO.getUsername());
        if (!dbPassword.equals(userDTO.getPassword())) {
            return Result.error(ResultCode.PASSWORD_ERROR);
        }
        return Result.success("Bearer " + UUID.randomUUID());
    }
}
