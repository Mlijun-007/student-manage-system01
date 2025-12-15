<template>
  <div id="app">
    <!-- 顶部导航栏 -->
    <header class="header">
      <h1>机构学生信息管理系统</h1>
    </header>
    
    <!-- 主体内容区域 -->
    <div class="main-container">
      <!-- 左侧菜单栏 -->
      <aside class="sidebar">
        <el-menu :default-active="activeMenu" class="el-menu-vertical-demo" @select="handleMenuSelect">
          <el-menu-item index="dashboard">
            <i class="el-icon-data-line"></i>
            <span slot="title">首页仪表盘</span>
          </el-menu-item>
          <el-menu-item index="student">
            <i class="el-icon-user"></i>
            <span slot="title">学员管理</span>
          </el-menu-item>
          <el-menu-item index="course">
            <i class="el-icon-collection"></i>
            <span slot="title">课程管理</span>
          </el-menu-item>
          <el-menu-item index="class">
            <i class="el-icon-s-grid"></i>
            <span slot="title">班级管理</span>
          </el-menu-item>
        </el-menu>
      </aside>
      
      <!-- 右侧内容区域 -->
      <main class="content">
        <!-- 首页仪表盘 -->
        <div v-show="activeMenu === 'dashboard'" class="dashboard">
          <h2>首页仪表盘</h2>
          <div class="dashboard-cards">
            <el-card class="card" shadow="hover">
              <div class="card-content">
                <div class="card-title">学员总数</div>
                <div class="card-value">{{ statistics.totalStudents }}</div>
              </div>
            </el-card>
            <el-card class="card" shadow="hover">
              <div class="card-content">
                <div class="card-title">课程总数</div>
                <div class="card-value">{{ statistics.totalCourses }}</div>
              </div>
            </el-card>
            <el-card class="card" shadow="hover">
              <div class="card-content">
                <div class="card-title">班级总数</div>
                <div class="card-value">{{ statistics.totalClasses }}</div>
              </div>
            </el-card>
            <el-card class="card" shadow="hover">
              <div class="card-content">
                <div class="card-title">今日新报名</div>
                <div class="card-value">{{ statistics.todayStudents }}</div>
              </div>
            </el-card>
          </div>
        </div>
        
        <!-- 学员管理 -->
        <div v-show="activeMenu === 'student'" class="student-management">
          <h2>学员管理</h2>
          <div class="action-bar">
            <el-button type="primary" @click="showAddStudentDialog">新增学员</el-button>
            <div class="search-bar">
              <el-input placeholder="姓名搜索" v-model="studentSearch.name" style="width: 200px; margin-right: 10px;"></el-input>
              <el-select placeholder="所属班级" v-model="studentSearch.classId" style="width: 200px; margin-right: 10px;">
                <el-option label="全部" value=""></el-option>
                <el-option v-for="clazz in classes" :key="clazz.classId" :label="clazz.className" :value="clazz.classId"></el-option>
              </el-select>
              <el-button type="success" @click="searchStudents">搜索</el-button>
            </div>
          </div>
          <el-table :data="filteredStudents" style="width: 100%">
            <el-table-column prop="studentId" label="学员ID" width="120"></el-table-column>
            <el-table-column prop="name" label="姓名" width="120"></el-table-column>
            <el-table-column prop="gender" label="性别" width="100"></el-table-column>
            <el-table-column prop="phone" label="联系电话" width="150"></el-table-column>
            <el-table-column prop="classId" label="所属班级" width="120">
              <template slot-scope="scope">
                {{ getClassName(scope.row.classId) }}
              </template>
            </el-table-column>
            <el-table-column prop="registerTime" label="报名时间" width="180"></el-table-column>
            <el-table-column label="操作" width="180" fixed="right">
              <template slot-scope="scope">
                <el-button type="primary" size="small" @click="showEditStudentDialog(scope.row)">编辑</el-button>
                <el-button type="danger" size="small" @click="confirmDeleteStudent(scope.row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
        
        <!-- 课程管理 -->
        <div v-show="activeMenu === 'course'" class="course-management">
          <h2>课程管理</h2>
          <div class="action-bar">
            <el-button type="primary" @click="showAddCourseDialog">新增课程</el-button>
          </div>
          <el-table :data="courses" style="width: 100%">
            <el-table-column prop="courseId" label="课程ID" width="120"></el-table-column>
            <el-table-column prop="courseName" label="课程名称" width="200"></el-table-column>
            <el-table-column prop="classHour" label="课时总数" width="120"></el-table-column>
            <el-table-column prop="price" label="课程价格" width="120">
              <template slot-scope="scope">
                ¥{{ scope.row.price }}
              </template>
            </el-table-column>
            <el-table-column prop="description" label="课程描述"></el-table-column>
            <el-table-column label="操作" width="150" fixed="right">
              <template slot-scope="scope">
                <el-button type="primary" size="small" @click="showEditCourseDialog(scope.row)">编辑</el-button>
                <el-button type="danger" size="small" @click="confirmDeleteCourse(scope.row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
        
        <!-- 班级管理 -->
        <div v-show="activeMenu === 'class'" class="class-management">
          <h2>班级管理</h2>
          <div class="action-bar">
            <el-button type="primary" @click="showAddClassDialog">新增班级</el-button>
            <div class="search-bar">
              <el-input placeholder="班级名称搜索" v-model="classSearch" style="width: 300px; margin-right: 10px;"></el-input>
              <el-button type="success" @click="searchClasses">搜索</el-button>
            </div>
          </div>
          <el-table :data="filteredClasses" style="width: 100%">
            <el-table-column prop="classId" label="班级ID" width="120"></el-table-column>
            <el-table-column prop="className" label="班级名称" width="200"></el-table-column>
            <el-table-column prop="courseId" label="关联课程" width="150">
              <template slot-scope="scope">
                {{ getCourseName(scope.row.courseId) }}
              </template>
            </el-table-column>
            <el-table-column prop="startDate" label="开班时间" width="150"></el-table-column>
            <el-table-column prop="teacher" label="授课老师" width="150"></el-table-column>
            <el-table-column prop="status" label="班级状态" width="120"></el-table-column>
            <el-table-column label="操作" width="150" fixed="right">
              <template slot-scope="scope">
                <el-button type="primary" size="small" @click="viewClassDetails(scope.row)">详情</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </main>
    </div>
    
    <!-- 新增学员对话框 -->
    <el-dialog title="新增学员" :visible.sync="dialogs.addStudentVisible">
      <el-form :model="formData.student" :rules="studentRules" ref="studentForm">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="formData.student.name"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="formData.student.gender">
            <el-radio label="男">男</el-radio>
            <el-radio label="女">女</el-radio>
            <el-radio label="其他">其他</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="formData.student.phone"></el-input>
        </el-form-item>
        <el-form-item label="所属班级" prop="classId">
          <el-select v-model="formData.student.classId">
            <el-option v-for="clazz in classes" :key="clazz.classId" :label="clazz.className" :value="clazz.classId"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="报名时间" prop="registerTime">
          <el-date-picker v-model="formData.student.registerTime" type="date" placeholder="选择报名时间" style="width: 100%;"></el-date-picker>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input type="textarea" v-model="formData.student.remark"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogs.addStudentVisible = false">取消</el-button>
        <el-button type="primary" @click="addStudent">确定</el-button>
      </div>
    </el-dialog>
    
    <!-- 编辑学员对话框 -->
    <el-dialog title="编辑学员" :visible.sync="dialogs.editStudentVisible">
      <el-form :model="formData.student" :rules="studentRules" ref="studentForm">
        <el-form-item label="学员ID">
          <el-input v-model="formData.student.studentId" disabled></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="formData.student.name"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="formData.student.gender">
            <el-radio label="男">男</el-radio>
            <el-radio label="女">女</el-radio>
            <el-radio label="其他">其他</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="formData.student.phone"></el-input>
        </el-form-item>
        <el-form-item label="所属班级" prop="classId">
          <el-select v-model="formData.student.classId">
            <el-option v-for="clazz in classes" :key="clazz.classId" :label="clazz.className" :value="clazz.classId"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="报名时间" prop="registerTime">
          <el-date-picker v-model="formData.student.registerTime" type="date" placeholder="选择报名时间" style="width: 100%;"></el-date-picker>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input type="textarea" v-model="formData.student.remark"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogs.editStudentVisible = false">取消</el-button>
        <el-button type="primary" @click="updateStudent">确定</el-button>
      </div>
    </el-dialog>
    
    <!-- 新增课程对话框 -->
    <el-dialog title="新增课程" :visible.sync="dialogs.addCourseVisible">
      <el-form :model="formData.course" :rules="courseRules" ref="courseForm">
        <el-form-item label="课程名称" prop="courseName">
          <el-input v-model="formData.course.courseName"></el-input>
        </el-form-item>
        <el-form-item label="课程描述" prop="description">
          <el-input type="textarea" v-model="formData.course.description"></el-input>
        </el-form-item>
        <el-form-item label="课时总数" prop="classHour">
          <el-input type="number" v-model.number="formData.course.classHour" min="0"></el-input>
        </el-form-item>
        <el-form-item label="课程价格" prop="price">
          <el-input type="number" v-model.number="formData.course.price" min="0" step="0.01"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogs.addCourseVisible = false">取消</el-button>
        <el-button type="primary" @click="addCourse">确定</el-button>
      </div>
    </el-dialog>
    
    <!-- 编辑课程对话框 -->
    <el-dialog title="编辑课程" :visible.sync="dialogs.editCourseVisible">
      <el-form :model="formData.course" :rules="courseRules" ref="courseForm">
        <el-form-item label="课程ID">
          <el-input v-model="formData.course.courseId" disabled></el-input>
        </el-form-item>
        <el-form-item label="课程名称" prop="courseName">
          <el-input v-model="formData.course.courseName"></el-input>
        </el-form-item>
        <el-form-item label="课程描述" prop="description">
          <el-input type="textarea" v-model="formData.course.description"></el-input>
        </el-form-item>
        <el-form-item label="课时总数" prop="classHour">
          <el-input type="number" v-model.number="formData.course.classHour" min="0"></el-input>
        </el-form-item>
        <el-form-item label="课程价格" prop="price">
          <el-input type="number" v-model.number="formData.course.price" min="0" step="0.01"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogs.editCourseVisible = false">取消</el-button>
        <el-button type="primary" @click="updateCourse">确定</el-button>
      </div>
    </el-dialog>
    
    <!-- 新增班级对话框 -->
    <el-dialog title="新增班级" :visible.sync="dialogs.addClassVisible">
      <el-form :model="formData.clazz" :rules="classRules" ref="classForm">
        <el-form-item label="班级名称" prop="className">
          <el-input v-model="formData.clazz.className"></el-input>
        </el-form-item>
        <el-form-item label="关联课程" prop="courseId">
          <el-select v-model="formData.clazz.courseId">
            <el-option v-for="course in courses" :key="course.courseId" :label="course.courseName" :value="course.courseId"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="开班时间" prop="startDate">
          <el-date-picker v-model="formData.clazz.startDate" type="date" placeholder="选择开班时间" style="width: 100%;"></el-date-picker>
        </el-form-item>
        <el-form-item label="授课老师" prop="teacher">
          <el-input v-model="formData.clazz.teacher"></el-input>
        </el-form-item>
        <el-form-item label="班级状态" prop="status">
          <el-select v-model="formData.clazz.status">
            <el-option label="未开班" value="未开班"></el-option>
            <el-option label="已开班" value="已开班"></el-option>
            <el-option label="已结课" value="已结课"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogs.addClassVisible = false">取消</el-button>
        <el-button type="primary" @click="addClass">确定</el-button>
      </div>
    </el-dialog>
    
    <!-- 班级详情对话框 -->
    <el-dialog title="班级详情" :visible.sync="dialogs.classDetailsVisible" width="80%">
      <div class="class-details">
        <div class="details-header">
          <h3>{{ selectedClass.className }}</h3>
          <el-button type="primary" @click="showAddStudentDialogForClass">新增学员</el-button>
        </div>
        <el-divider></el-divider>
        <div class="class-info">
          <el-descriptions :column="3" border>
            <el-descriptions-item label="班级ID">{{ selectedClass.classId }}</el-descriptions-item>
            <el-descriptions-item label="关联课程">{{ getCourseName(selectedClass.courseId) }}</el-descriptions-item>
            <el-descriptions-item label="开班时间">{{ selectedClass.startDate }}</el-descriptions-item>
            <el-descriptions-item label="授课老师">{{ selectedClass.teacher }}</el-descriptions-item>
            <el-descriptions-item label="班级状态">{{ selectedClass.status }}</el-descriptions-item>
          </el-descriptions>
        </div>
        <el-divider></el-divider>
        <div class="students-list">
          <h4>学员列表</h4>
          <el-table :data="getStudentsByClass(selectedClass.classId)" style="width: 100%">
            <el-table-column prop="studentId" label="学员ID" width="120"></el-table-column>
            <el-table-column prop="name" label="姓名" width="120"></el-table-column>
            <el-table-column prop="gender" label="性别" width="100"></el-table-column>
            <el-table-column prop="phone" label="联系电话" width="150"></el-table-column>
            <el-table-column prop="registerTime" label="报名时间" width="180"></el-table-column>
          </el-table>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios';

// 创建API服务实例
const apiClient = axios.create({
  baseURL: 'http://localhost:8081',
  headers: {
    'Content-Type': 'application/json'
  }
});

export default {
  data() {
    return {
      // 当前激活的菜单
      activeMenu: 'dashboard',
      
      // 数据存储
      students: [],
      courses: [],
      classes: [],
      
      // 搜索条件
      studentSearch: {
        name: '',
        classId: ''
      },
      classSearch: '',
      
      // 对话框显示状态
      dialogs: {
        addStudentVisible: false,
        editStudentVisible: false,
        addCourseVisible: false,
        editCourseVisible: false,
        addClassVisible: false,
        classDetailsVisible: false
      },
      
      // 表单数据
      formData: {
        student: {
          studentId: '',
          name: '',
          gender: '男',
          phone: '',
          classId: '',
          registerTime: new Date(),
          remark: ''
        },
        course: {
          courseId: '',
          courseName: '',
          description: '',
          classHour: 0,
          price: 0
        },
        clazz: {
          classId: '',
          className: '',
          courseId: '',
          startDate: new Date(),
          teacher: '',
          status: '未开班'
        }
      },
      
      // 选中的班级（用于详情查看）
      selectedClass: {},
      
      // 统计数据
      statistics: {
        totalStudents: 0,
        totalCourses: 0,
        totalClasses: 0,
        todayStudents: 0
      },
      
      // 表单验证规则
      studentRules: {
        name: [
          { required: true, message: '请输入学员姓名', trigger: 'blur' }
        ],
        gender: [
          { required: true, message: '请选择性别', trigger: 'change' }
        ],
        phone: [
          { required: true, message: '请输入联系电话', trigger: 'blur' },
          { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号格式', trigger: 'blur' }
        ],
        classId: [
          { required: true, message: '请选择所属班级', trigger: 'change' }
        ],
        registerTime: [
          { required: true, message: '请选择报名时间', trigger: 'change' }
        ]
      },
      
      courseRules: {
        courseName: [
          { required: true, message: '请输入课程名称', trigger: 'blur' }
        ],
        classHour: [
          { required: true, message: '请输入课时总数', trigger: 'blur' },
          { type: 'number', min: 0, message: '课时总数不能为负数', trigger: 'blur' }
        ],
        price: [
          { required: true, message: '请输入课程价格', trigger: 'blur' },
          { type: 'number', min: 0, message: '课程价格不能为负数', trigger: 'blur' }
        ]
      },
      
      classRules: {
        className: [
          { required: true, message: '请输入班级名称', trigger: 'blur' }
        ],
        courseId: [
          { required: true, message: '请选择关联课程', trigger: 'change' }
        ],
        startDate: [
          { required: true, message: '请选择开班时间', trigger: 'change' }
        ],
        teacher: [
          { required: true, message: '请输入授课老师', trigger: 'blur' }
        ],
        status: [
          { required: true, message: '请选择班级状态', trigger: 'change' }
        ]
      }
    };
  },
  
  computed: {
    // 过滤后的学员列表
    filteredStudents() {
      let result = [...this.students];
      
      // 按姓名搜索
      if (this.studentSearch.name) {
        const searchName = this.studentSearch.name.toLowerCase();
        result = result.filter(student => student.name.toLowerCase().includes(searchName));
      }
      
      // 按班级搜索
      if (this.studentSearch.classId) {
        result = result.filter(student => student.classId === this.studentSearch.classId);
      }
      
      return result;
    },
    
    // 过滤后的班级列表
    filteredClasses() {
      let result = [...this.classes];
      
      // 按班级名称搜索
      if (this.classSearch) {
        const searchName = this.classSearch.toLowerCase();
        result = result.filter(clazz => clazz.className.toLowerCase().includes(searchName));
      }
      
      return result;
    }
  },
  
  mounted() {
    // 初始化数据
    this.initData();
  },
  
  methods: {
    // 初始化数据 - 从API获取
    async initData() {
      try {
        // 显示加载状态
        this.$message.loading('正在加载数据...', 0);
        
        // 并行请求获取所有数据
        const [studentsRes, coursesRes, classesRes] = await Promise.all([
          apiClient.get('/api/students'),
          apiClient.get('/api/courses'),
          apiClient.get('/api/classes')
        ]);
        
        // 更新数据
        this.students = studentsRes.data;
        this.courses = coursesRes.data;
        this.classes = classesRes.data;
        
        // 更新统计数据
        this.updateStatistics();
        
        // 关闭加载提示
        this.$message.closeAll();
      } catch (error) {
        console.error('获取数据失败:', error);
        this.$message.closeAll();
        this.$message.error('获取数据失败，请检查后端服务是否运行');
        
        // 如果API调用失败，使用本地模拟数据
        this.initMockData();
      }
    },
    
    // 初始化模拟数据（当API调用失败时使用）
    initMockData() {
      // 初始化课程数据
      this.courses = [
        { courseId: 1, courseName: 'JavaScript基础', description: 'JavaScript入门课程', classHour: 40, price: 1280 },
        { courseId: 2, courseName: 'Vue.js高级开发', description: 'Vue.js框架深入学习', classHour: 30, price: 1680 },
        { courseId: 3, courseName: 'Python数据分析', description: 'Python数据分析与可视化', classHour: 50, price: 1980 }
      ];
      
      // 初始化班级数据
      this.classes = [
        { classId: 1, className: 'JS202301', courseId: 1, startDate: '2023-01-15', teacher: '张老师', status: '已结课' },
        { classId: 2, className: 'Vue202303', courseId: 2, startDate: '2023-03-10', teacher: '李老师', status: '已开班' },
        { classId: 3, className: 'Python202305', courseId: 3, startDate: '2023-05-20', teacher: '王老师', status: '未开班' }
      ];
      
      // 初始化学员数据
      this.students = [
        { studentId: 1, name: '张三', gender: '男', phone: '13800138001', classId: 1, registerTime: '2023-01-10', remark: '无' },
        { studentId: 2, name: '李四', gender: '女', phone: '13800138002', classId: 1, registerTime: '2023-01-12', remark: '无' },
        { studentId: 3, name: '王五', gender: '男', phone: '13800138003', classId: 2, registerTime: '2023-03-05', remark: '无' },
        { studentId: 4, name: '赵六', gender: '女', phone: '13800138004', classId: 2, registerTime: '2023-03-08', remark: '无' },
        { studentId: 5, name: '钱七', gender: '男', phone: '13800138005', classId: 3, registerTime: new Date().toISOString().split('T')[0], remark: '今日新报名' }
      ];
      
      // 更新统计数据
      this.updateStatistics();
      
      this.$message.info('已使用本地模拟数据');
    },
    
    // 菜单选择处理
    handleMenuSelect(key) {
      this.activeMenu = key;
    },
    
    // 更新统计数据
    updateStatistics() {
      // 学员总数
      this.statistics.totalStudents = this.students.length;
      
      // 课程总数
      this.statistics.totalCourses = this.courses.length;
      
      // 班级总数
      this.statistics.totalClasses = this.classes.length;
      
      // 今日新报名学员数
      const today = new Date().toISOString().split('T')[0];
      this.statistics.todayStudents = this.students.filter(student => student.registerTime === today).length;
    },
    
    // 获取班级名称
    getClassName(classId) {
      const clazz = this.classes.find(c => c.classId === classId);
      return clazz ? clazz.className : '未知班级';
    },
    
    // 获取课程名称
    getCourseName(courseId) {
      const course = this.courses.find(c => c.courseId === courseId);
      return course ? course.courseName : '未知课程';
    },
    
    // 根据班级ID获取学员列表
    getStudentsByClass(classId) {
      return this.students.filter(student => student.classId === classId);
    },
    
    // 学员管理相关方法
    showAddStudentDialog() {
      // 重置表单
      this.resetStudentForm();
      // 打开对话框
      this.dialogs.addStudentVisible = true;
    },
    
    showAddStudentDialogForClass() {
      // 重置表单
      this.resetStudentForm();
      // 设置默认班级
      this.formData.student.classId = this.selectedClass.classId;
      // 打开对话框
      this.dialogs.addStudentVisible = true;
    },
    
    showEditStudentDialog(student) {
      // 复制学员数据到表单
      this.formData.student = JSON.parse(JSON.stringify(student));
      // 转换日期格式
      this.formData.student.registerTime = new Date(student.registerTime);
      // 打开对话框
      this.dialogs.editStudentVisible = true;
    },
    
    resetStudentForm() {
      this.formData.student = {
        studentId: '',
        name: '',
        gender: '男',
        phone: '',
        classId: this.classes.length > 0 ? this.classes[0].classId : '',
        registerTime: new Date(),
        remark: ''
      };
    },
    
    async addStudent() {
      this.$refs.studentForm.validate(async (valid) => {
        if (valid) {
          try {
            // 转换日期格式
            const registerTime = this.formData.student.registerTime instanceof Date 
              ? this.formData.student.registerTime.toISOString().split('T')[0] 
              : this.formData.student.registerTime;
            
            // 创建新学员数据
            const newStudent = {
              ...this.formData.student,
              registerTime: registerTime
            };
            
            // 发送POST请求添加学员
            const response = await apiClient.post('/api/students', newStudent);
            
            // 添加到学员列表
            this.students.push(response.data);
            
            // 关闭对话框
            this.dialogs.addStudentVisible = false;
            
            // 更新统计数据
            this.updateStatistics();
            
            // 显示成功消息
            this.$message.success('学员添加成功！');
          } catch (error) {
            console.error('添加学员失败:', error);
            this.$message.error('添加学员失败，请重试');
          }
        } else {
          return false;
        }
      });
    },
    
    async updateStudent() {
      this.$refs.studentForm.validate(async (valid) => {
        if (valid) {
          try {
            // 转换日期格式
            const registerTime = this.formData.student.registerTime instanceof Date 
              ? this.formData.student.registerTime.toISOString().split('T')[0] 
              : this.formData.student.registerTime;
            
            // 更新学员数据
            const updatedStudent = {
              ...this.formData.student,
              registerTime: registerTime
            };
            
            // 发送PUT请求更新学员
            const response = await apiClient.put(`/api/students/${updatedStudent.studentId}`, updatedStudent);
            
            // 更新本地数据
            const index = this.students.findIndex(s => s.studentId === updatedStudent.studentId);
            if (index !== -1) {
              this.students[index] = response.data;
            }
            
            // 关闭对话框
            this.dialogs.editStudentVisible = false;
            
            // 显示成功消息
            this.$message.success('学员信息更新成功！');
          } catch (error) {
            console.error('更新学员失败:', error);
            this.$message.error('更新学员失败，请重试');
          }
        } else {
          return false;
        }
      });
    },
    
    async confirmDeleteStudent(student) {
      this.$confirm('确定要删除该学员吗？', '删除确认', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        try {
          // 发送DELETE请求删除学员
          await apiClient.delete(`/api/students/${student.studentId}`);
          
          // 更新本地数据
          this.students = this.students.filter(s => s.studentId !== student.studentId);
          
          // 更新统计数据
          this.updateStatistics();
          
          // 显示成功消息
          this.$message.success('学员删除成功！');
        } catch (error) {
          console.error('删除学员失败:', error);
          this.$message.error('删除学员失败，请重试');
        }
      }).catch(() => {
        // 取消删除
        this.$message.info('已取消删除');
      });
    },
    
    searchStudents() {
      // 搜索逻辑已在computed属性中实现
      this.$message.info('搜索完成');
    },
    
    // 课程管理相关方法
    showAddCourseDialog() {
      // 重置表单
      this.resetCourseForm();
      // 打开对话框
      this.dialogs.addCourseVisible = true;
    },
    
    showEditCourseDialog(course) {
      // 复制课程数据到表单
      this.formData.course = JSON.parse(JSON.stringify(course));
      // 打开对话框
      this.dialogs.editCourseVisible = true;
    },
    
    resetCourseForm() {
      this.formData.course = {
        courseId: '',
        courseName: '',
        description: '',
        classHour: 0,
        price: 0
      };
    },
    
    async addCourse() {
      this.$refs.courseForm.validate(async (valid) => {
        if (valid) {
          try {
            // 创建新课程数据
            const newCourse = {
              ...this.formData.course
            };
            
            // 发送POST请求添加课程
            const response = await apiClient.post('/api/courses', newCourse);
            
            // 添加到课程列表
            this.courses.push(response.data);
            
            // 关闭对话框
            this.dialogs.addCourseVisible = false;
            
            // 更新统计数据
            this.updateStatistics();
            
            // 显示成功消息
            this.$message.success('课程添加成功！');
          } catch (error) {
            console.error('添加课程失败:', error);
            this.$message.error('添加课程失败，请重试');
          }
        } else {
          return false;
        }
      });
    },
    
    async updateCourse() {
      this.$refs.courseForm.validate(async (valid) => {
        if (valid) {
          try {
            // 更新课程数据
            const updatedCourse = {...this.formData.course};
            
            // 发送PUT请求更新课程
            const response = await apiClient.put(`/api/courses/${updatedCourse.courseId}`, updatedCourse);
            
            // 更新本地数据
            const index = this.courses.findIndex(c => c.courseId === updatedCourse.courseId);
            if (index !== -1) {
              this.courses[index] = response.data;
            }
            
            // 关闭对话框
            this.dialogs.editCourseVisible = false;
            
            // 显示成功消息
            this.$message.success('课程信息更新成功！');
          } catch (error) {
            console.error('更新课程失败:', error);
            this.$message.error('更新课程失败，请重试');
          }
        } else {
          return false;
        }
      });
    },
    
    async confirmDeleteCourse(course) {
      try {
        // 检查课程是否被班级引用
        const isUsed = this.classes.some(clazz => clazz.courseId === course.courseId);
        
        if (isUsed) {
          this.$message.error('该课程已有班级在使用，无法删除');
          return;
        }
        
        this.$confirm('确定要删除该课程吗？', '删除确认', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(async () => {
          try {
            // 发送DELETE请求删除课程
            await apiClient.delete(`/api/courses/${course.courseId}`);
            
            // 删除课程
            this.courses = this.courses.filter(c => c.courseId !== course.courseId);
            
            // 更新统计数据
            this.updateStatistics();
            
            // 显示成功消息
            this.$message.success('课程删除成功！');
          } catch (error) {
            console.error('删除课程失败:', error);
            this.$message.error('删除课程失败，请重试');
          }
        }).catch(() => {
          // 取消删除
          this.$message.info('已取消删除');
        });
      } catch (error) {
        console.error('删除课程失败:', error);
        this.$message.error('删除课程失败，请重试');
      }
    },
    
    // 班级管理相关方法
    showAddClassDialog() {
      // 重置表单
      this.resetClassForm();
      // 打开对话框
      this.dialogs.addClassVisible = true;
    },
    
    viewClassDetails(clazz) {
      // 复制班级数据
      this.selectedClass = JSON.parse(JSON.stringify(clazz));
      // 打开对话框
      this.dialogs.classDetailsVisible = true;
    },
    
    resetClassForm() {
      this.formData.clazz = {
        classId: '',
        className: '',
        courseId: this.courses.length > 0 ? this.courses[0].courseId : '',
        startDate: new Date(),
        teacher: '',
        status: '未开班'
      };
    },
    
    async addClass() {
      this.$refs.classForm.validate(async (valid) => {
        if (valid) {
          try {
            // 转换日期格式
            const startDate = this.formData.clazz.startDate instanceof Date 
              ? this.formData.clazz.startDate.toISOString().split('T')[0] 
              : this.formData.clazz.startDate;
            
            // 创建新班级数据
            const newClass = {
              ...this.formData.clazz,
              startDate: startDate
            };
            
            // 发送POST请求添加班级
            const response = await apiClient.post('/api/classes', newClass);
            
            // 添加到班级列表
            this.classes.push(response.data);
            
            // 关闭对话框
            this.dialogs.addClassVisible = false;
            
            // 更新统计数据
            this.updateStatistics();
            
            // 显示成功消息
            this.$message.success('班级添加成功！');
          } catch (error) {
            console.error('添加班级失败:', error);
            this.$message.error('添加班级失败，请重试');
          }
        } else {
          return false;
        }
      });
    },
    
    searchClasses() {
      // 搜索逻辑已在computed属性中实现
      this.$message.info('搜索完成');
    }
  }
};
</script>

<style>
/* 全局样式重置 */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: 'Helvetica Neue', Helvetica, 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei', Arial, sans-serif;
  background-color: #f5f7fa;
  color: #333;
}

/* 顶部导航栏 */
.header {
  background-color: #409EFF;
  color: white;
  padding: 15px 30px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.header h1 {
  font-size: 24px;
  font-weight: bold;
}

/* 主体内容区域 */
.main-container {
  display: flex;
  height: calc(100vh - 60px);
}

/* 左侧菜单栏 */
.sidebar {
  width: 200px;
  background-color: white;
  border-right: 1px solid #e6e6e6;
}

.sidebar .el-menu {
  height: 100%;
  border-right: none;
}

/* 右侧内容区域 */
.content {
  flex: 1;
  padding: 20px;
  overflow-y: auto;
}

.content h2 {
  font-size: 20px;
  margin-bottom: 20px;
  color: #303133;
}

/* 操作栏 */
.action-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.search-bar {
  display: flex;
  align-items: center;
}

/* 首页仪表盘 */
.dashboard {
  padding: 20px 0;
}

.dashboard-cards {
  display: flex;
  gap: 20px;
  flex-wrap: wrap;
}

.dashboard-cards .card {
  width: 250px;
  margin-bottom: 20px;
}

.card-content {
  text-align: center;
  padding: 20px;
}

.card-title {
  font-size: 16px;
  color: #606266;
  margin-bottom: 10px;
}

.card-value {
  font-size: 36px;
  font-weight: bold;
  color: #409EFF;
}

/* 表格样式 */
.el-table {
  margin-bottom: 20px;
  border-radius: 4px;
  overflow: hidden;
}

/* 班级详情 */
.class-details .details-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.class-details .class-info {
  margin-bottom: 20px;
}

.class-details .students-list h4 {
  margin-bottom: 15px;
  color: #303133;
}

/* 响应式设计 */
@media screen and (max-width: 768px) {
  .main-container {
    flex-direction: column;
  }
  
  .sidebar {
    width: 100%;
    height: auto;
  }
  
  .dashboard-cards {
    justify-content: center;
  }
  
  .action-bar {
    flex-direction: column;
    gap: 15px;
    align-items: stretch;
  }
  
  .search-bar {
    justify-content: center;
  }
}
</style>