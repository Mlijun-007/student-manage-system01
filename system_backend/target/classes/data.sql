-- 初始化课程数据
INSERT INTO courses (course_name, description, price, class_hour) VALUES
('Java编程基础', 'Java语言入门课程，包含基本语法、面向对象编程等内容', 500.00, 48),
('Web前端开发', 'HTML/CSS/JavaScript基础，以及响应式设计', 450.00, 40),
('数据库原理', '关系数据库设计与SQL语言，MySQL数据库使用', 480.00, 48),
('Python数据分析', 'Python语言基础，以及pandas、numpy等数据分析库', 520.00, 40);

-- 初始化班级数据
INSERT INTO classes (class_name, status, course_id, start_date, teacher) VALUES
('Java基础班-2024春季', 'IN_PROGRESS', 1, '2024-03-01', '张老师'),
('Web前端班-2024春季', 'NOT_STARTED', 2, '2024-04-01', '李老师'),
('数据库原理班-2024春季', 'IN_PROGRESS', 3, '2024-03-01', '王老师'),
('Python数据分析班-2024春季', 'NOT_STARTED', 4, '2024-04-01', '赵老师');

-- 初始化学生数据
INSERT INTO students (name, phone, class_id, gender, register_time, remark) VALUES
('张三', '13800138001', 1, 'MALE', '2024-01-15', '优秀学生'),
('李四', '13800138002', 1, 'FEMALE', '2024-01-16', '认真刻苦'),
('王五', '13800138003', 3, 'MALE', '2024-01-17', '积极主动'),
('赵六', '13800138004', 1, 'FEMALE', '2024-01-18', '成绩优异'),
('孙七', '13800138005', 3, 'MALE', '2024-01-19', '热爱编程'),
('周八', '13800138006', 2, 'OTHER', '2024-02-01', '新生');

-- 初始化报名记录
INSERT INTO enrollments (student_id, course_id, semester, grade) VALUES
(1, 1, '2024春季', 'A'),
(1, 3, '2024春季', 'A-'),
(2, 1, '2024春季', 'B+'),
(3, 3, '2024春季', 'A'),
(4, 1, '2024春季', 'A'),
(4, 2, '2024春季', NULL),
(5, 3, '2024春季', 'B'),
(5, 4, '2024春季', NULL),
(6, 2, '2024春季', NULL);