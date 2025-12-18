# 数据库设置指南

本项目使用MySQL数据库进行开发和生产环境部署。以下是详细的数据库设置步骤：

## 1. MySQL数据库安装

### Windows系统
1. 下载MySQL安装包：访问[MySQL官方网站](https://dev.mysql.com/downloads/mysql/)下载适合您Windows版本的MySQL安装包
2. 运行安装程序，选择"Developer Default"或"Custom"安装类型
3. 按照安装向导完成安装，设置root用户密码
4. 确保MySQL服务已启动

## 2. 数据库配置

### 2.1 创建数据库和用户

1. 登录MySQL服务器：
   ```bash
   mysql -u root -p
   ```
   输入您在安装时设置的root密码

2. 创建整个数据库（不是单个表）：
   ```sql
   -- 创建名为student_manager_system的完整数据库，并设置字符集为utf8mb4
   CREATE DATABASE student_manager_system CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```

3. 创建用户并授予权限：
   ```sql
   -- 注意：这里的密码'password'只是示例，请使用您自己的数据库密码
   CREATE USER 'root'@'localhost' IDENTIFIED BY 'password';
   # 授予用户对student_manager_system数据库中所有表的全部权限
GRANT ALL PRIVILEGES ON student_manager_system.* TO 'root'@'localhost';
   FLUSH PRIVILEGES;
   ```

   **重要提示**：
   - 文档中的密码仅为示例，每个开发人员应使用自己本地MySQL数据库的实际密码
   - 请确保在`src/main/resources/application-dev.properties`和`src/main/resources/application-prod.properties`文件中，将数据库密码配置为您自己的实际密码

### 2.2 验证数据库连接

```bash
mysql -u root -p student_manager_system
```

如果成功登录，说明数据库和用户配置正确。

## 3. 项目数据库配置

项目的数据库配置位于`src/main/resources/application-dev.properties`文件中。默认配置如下：

```properties
# MySQL Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/student_manager_system?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.defer-datasource-initialization=true

# SQL脚本自动执行配置
spring.sql.init.mode=always
spring.sql.init.continue-on-error=true
```

**注意**：如果您修改了数据库用户名、密码或数据库名称，请相应地更新上述配置。

## 4. 初始化数据库数据

项目启动时会自动执行`src/main/resources/data.sql`文件中的SQL语句，初始化数据库数据。

### 4.1 手动执行初始化脚本（可选）

如果需要手动执行初始化脚本，可以使用以下命令：

```bash
mysql -u root -p student_manager_system < src/main/resources/data.sql
```

## 5. 启动项目验证数据库连接

1. 启动Spring Boot项目：
   ```bash
   mvn spring-boot:run
   ```

2. 检查控制台输出，确认没有数据库连接错误

3. 访问以下URL检查API是否正常工作：
   ```
   http://localhost:8080/api/courses
   http://localhost:8080/api/students
   http://localhost:8080/api/classes
   ```

如果能够正常返回JSON数据，说明数据库连接和初始化成功。

## 6. 常见问题排查

### 6.1 数据库连接失败

- 检查MySQL服务是否已启动
- 检查`application-dev.properties`中的数据库连接配置是否正确
- 确认数据库用户名和密码是否正确
- 确认MySQL服务器是否允许本地连接

### 6.2 初始化脚本执行失败

- 检查`data.sql`文件中的SQL语句是否符合MySQL语法
- 确认数据库表结构是否已正确创建
- 检查控制台输出的错误信息，针对性解决问题

### 6.3 中文乱码问题

- 确认数据库使用的是`utf8mb4`字符集
- 检查数据库连接URL中是否包含`useUnicode=true&characterEncoding=utf8`参数

## 7. 注意事项

1. 开发环境和生产环境应使用不同的数据库配置
2. 生产环境中应使用强密码并限制数据库用户权限
3. 定期备份数据库以防止数据丢失
4. 不要将数据库密码等敏感信息提交到代码仓库

---

通过以上步骤，您可以成功配置并初始化MySQL数据库，使项目能够正常运行。如果在配置过程中遇到任何问题，请参考常见问题排查部分或联系项目管理员。