# 学生管理系统后端

## 项目概述
本项目是学生管理系统的后端服务，基于Spring Boot构建，提供学生、课程和班级管理的API接口。

## 技术栈
- Spring Boot 3.0+
- Spring Data JPA
- Maven 3.8+
- Java 17+

## 项目结构
src/
├── main/
│   ├── java/com/example/system_backend/
│   │   ├── controller/      # 控制器层
│   │   ├── service/         # 服务层
│   │   ├── entity/          # 实体类
│   │   ├── dto/             # 数据传输对象
│   │   ├── exception/       # 异常处理
│   │   └── config/          # 配置类
│   └── resources/
│       ├── application.properties     # 全局配置
│       ├── application-dev.properties # 开发环境配置
│       └── application-prod.properties # 生产环境配置
└── test/                    # 测试代码
```

## 依赖安装与运行

### 1. 环境要求
- Java 17或更高版本
- Maven 3.8或更高版本

### 2. 检查Java版本
```bash
java -version
# 输出应显示 Java 17 或更高版本
```

### 3. 检查Maven版本
```bash
mvn -version
# 输出应显示 Maven 3.8 或更高版本
```

### 4. 安装依赖
在项目根目录下执行以下命令：
```bash
# Windows
./mvnw.cmd clean install
```

### 5. 运行项目
```bash
# Windows
./mvnw.cmd spring-boot:run
```

### 6. 访问API
项目启动后，可以通过以下地址访问API：
- 学生API：`http://localhost:8080/api/students`
- 课程API：`http://localhost:8080/api/courses`
- 班级API：`http://localhost:8080/api/classes`

## 主要API接口

### 学生管理
- `GET /api/students` - 获取所有学生列表
- `POST /api/students` - 创建新学生
- `GET /api/students/{id}` - 获取学生详情
- `PUT /api/students/{id}` - 更新学生信息
- `DELETE /api/students/{id}` - 删除学生

### 课程管理
- `GET /api/courses` - 获取所有课程列表
- `POST /api/courses` - 创建新课程
- `GET /api/courses/{id}` - 获取课程详情
- `PUT /api/courses/{id}` - 更新课程信息
- `DELETE /api/courses/{id}` - 删除课程

### 班级管理
- `GET /api/classes` - 获取所有班级列表
- `POST /api/classes` - 创建新班级
- `GET /api/classes/{id}` - 获取班级详情
- `PUT /api/classes/{id}` - 更新班级信息
- `DELETE /api/classes/{id}` - 删除班级

## 数据库配置
请参考 `DATABASE_SETUP.md` 文件获取详细的数据库配置信息。

## 开发与调试

### 修改配置
- 开发环境配置：`src/main/resources/application-dev.properties`
- 生产环境配置：`src/main/resources/application-prod.properties`

### 日志
- 日志配置：`src/main/resources/logback-spring.xml`
- 开发环境日志级别：`DEBUG`
- 生产环境日志级别：`INFO`

## 构建与部署

### 构建生产环境包
```bash
# Windows
./mvnw.cmd clean package -DskipTests -Pprod
```

### 运行生产环境包
```bash
java -jar target/system-backend-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

## 常见问题

### Maven依赖下载失败
- 检查网络连接
- 清理Maven本地仓库：`rm -rf ~/.m2/repository` (Windows: `rmdir /s /q %USERPROFILE%\.m2\repository`)
- 重新执行安装命令

### 端口被占用
```bash
# Windows
netstat -ano | findstr :8080
taskkill /PID [PID] /F
