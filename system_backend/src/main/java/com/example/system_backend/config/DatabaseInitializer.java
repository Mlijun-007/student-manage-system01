package com.example.system_backend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DatabaseInitializer implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 检查数据库中是否有课程数据
        List<Map<String, Object>> courses = jdbcTemplate.queryForList("SELECT * FROM courses");
        logger.info("Found {} courses in database", courses.size());
        
        if (courses.isEmpty()) {
            logger.info("No courses found, inserting test data...");
            
            // 手动执行课程插入
            jdbcTemplate.execute("INSERT INTO courses (course_name, description, price, class_hour) VALUES " +
                "('Java编程基础', 'Java语言入门课程', 500.00, 48), " +
                "('Web前端开发', 'HTML/CSS/JavaScript基础', 450.00, 40), " +
                "('数据库原理', '关系数据库设计', 480.00, 48), " +
                "('Python数据分析', 'Python数据处理', 520.00, 40)");
            
            logger.info("Inserted test courses data");
            
            // 检查插入后的数据
            courses = jdbcTemplate.queryForList("SELECT * FROM courses");
            logger.info("Now found {} courses in database", courses.size());
        }
    }
}