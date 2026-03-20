# Server Side Lab

本项目按《2025服务端-3.12》作业要求完成，包含以下内容：

- `User` 实体类
- 基于四大请求映射注解的 RESTful 用户接口
- 使用 `@RequestBody` 和 `@PathVariable` 的接口联调
- 统一响应结构 `Result<T>`
- 全局异常处理器 `GlobalExceptionHandler`
- MockMvc 自动化测试

## 项目结构

- `src/main/java/com/stu/helloserver/entity/User.java`
- `src/main/java/com/stu/helloserver/controller/UserController.java`
- `src/main/java/com/stu/helloserver/common/Result.java`
- `src/main/java/com/stu/helloserver/exception/GlobalExceptionHandler.java`

## 运行方式

在项目根目录执行：

```bash
./gradlew bootRun
```

启动成功后，控制台会看到 Spring Boot 启动日志，默认端口为 `8080`。

## 接口说明

### 1. 查询用户

- Method: `GET`
- URL: `http://localhost:8080/api/users/1001`

返回示例：

```json
{
  "code": 200,
  "msg": "查询成功，正在返回 ID 为 1001 的用户信息",
  "data": {
    "id": 1001,
    "name": "用户1001",
    "age": 20
  }
}
```

### 2. 新增用户

- Method: `POST`
- URL: `http://localhost:8080/api/users`
- Body: `raw` + `JSON`

```json
{
  "name": "张三",
  "age": 21
}
```

### 3. 修改用户

- Method: `PUT`
- URL: `http://localhost:8080/api/users/1002`
- Body: `raw` + `JSON`

```json
{
  "name": "李四",
  "age": 22
}
```

### 4. 删除用户

- Method: `DELETE`
- URL: `http://localhost:8080/api/users/1003`

### 5. 异常拦截验证

- Method: `GET`
- URL: `http://localhost:8080/api/users/0`

访问该地址会故意触发异常，并由全局异常处理器返回统一 JSON：

```json
{
  "code": 500,
  "msg": "服务器内部错误：/ by zero",
  "data": null
}
```

## 建议截图清单

按作业要求，建议至少截图以下内容：

1. Spring Boot 启动成功控制台，能看到 `Tomcat started on port 8080`。
2. `User.java` 实体类代码。
3. `UserController.java` 中四个请求映射方法代码。
4. `Result.java` 统一响应结构代码。
5. `GlobalExceptionHandler.java` 全局异常处理代码。
6. Postman 的 GET `/api/users/1001` 请求和返回结果。
7. Postman 的 POST `/api/users` 请求、JSON 请求体和返回结果。
8. Postman 的 PUT `/api/users/1002` 请求和返回结果。
9. Postman 的 DELETE `/api/users/1003` 请求和返回结果。
10. Postman 的异常测试 GET `/api/users/0` 请求和统一 JSON 错误返回。

## 测试

执行：

```bash
./gradlew test
```

本地已验证测试通过。
