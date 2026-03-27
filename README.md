Task Management System

Dự án quản lý công việc được xây dựng bằng Spring Boot. Hệ thống cho phép quản lý User, Task và Project với phân quyền theo role.

---

1 - Công nghệ sử dụng

- Java 17
- Spring Boot 3
- Spring Security + JWT
- JPA / Hibernate
- SQL Server
- Swagger (SpringDoc)
- Maven
- Lombok

---

2 -  Cách cài đặt và chạy project

1. Yêu cầu cài đặt trước

- Java 17
- Maven
- SQL Server

2. Clone project về máy

git clone https://github.com/Phrova/Taskmanagement/tree/main

3. Tạo database

Mở SQL Server và chạy lệnh:

CREATE DATABASE task_management;

4. Cấu hình database

Mở file `src/main/resources/application.properties` và sửa lại thông tin kết nối:

spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=task_management
spring.datasource.username=your_username
spring.datasource.password=your_password

5. Chạy project

mvn spring-boot:run

Project sẽ chạy tại: `http://localhost:8081`

---

3 - Swagger UI

Sau khi chạy project, mở trình duyệt và vào:

http://localhost:8081/swagger-ui/index.html

---

Cách test API

Bước 1: Đăng nhập

Gọi API login:

POST /api/auth/login

Body:

{
  "email": "manager@gmail.com",
  "password": "12345"
}

Bước 2: Copy token

Sau khi login thành công, copy token trong response.

Bước 3: Dán token vào Authorize

Trong Swagger UI, click nút **Authorize** ở góc trên bên phải, dán token vào theo định dạng:

Bearer <token>

Bước 4: Gọi API

Sau khi Authorize xong là có thể gọi các API tùy theo role.

---

Phân quyền

| Chức năng | MANAGER | USER |
|-----------|---------|------|
| Quản lý tất cả user | ✅ | ❌ |
| Xem/sửa/xóa bản thân | ✅ | ✅ |
| Tạo/sửa/xóa project | ✅ | ❌ |
| Xem project của mình | ✅ | ✅ |
| Tạo/sửa/xóa task | ✅ | ❌ |
| Xem task của mình | ✅ | ✅ |

---

Các endpoint chính

Auth
- `POST /api/auth/login` — Đăng nhập
- `POST /api/users/register` — Đăng ký

User
- `GET /api/users` — Lấy danh sách user
- `GET /api/users/{id}` — Lấy user theo ID
- `PUT /api/users/{id}` — Cập nhật user
- `DELETE /api/users/{id}` — Xóa user

Task
- `GET /api/tasks` — Lấy tất cả task
- `POST /api/tasks` — Tạo task mới
- `PUT /api/tasks/{id}` — Cập nhật task
- `DELETE /api/tasks/{id}` — Xóa task
- `PATCH /api/tasks/{taskId}/status` — Cập nhật trạng thái task
- `PUT /api/tasks/{taskId}/assign/{userId}` — Gán task cho user
- `GET /api/tasks/user/{userId}` — Lấy task theo user
- `GET /api/tasks/project/{projectId}` — Lấy task theo project

Project
- `GET /api/projects` — Lấy tất cả project
- `POST /api/projects` — Tạo project mới
- `PUT /api/projects/{id}` — Cập nhật project
- `DELETE /api/projects/{id}` — Xóa project
- `POST /api/projects/{projectId}/users/{userId}` — Thêm user vào project
- `DELETE /api/projects/{projectId}/users/{userId}` — Xóa user khỏi project
