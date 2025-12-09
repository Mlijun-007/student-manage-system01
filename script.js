// 培训中心管理系统 - JavaScript 逻辑

// 初始化Vue实例
new Vue({
    el: '#app',
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
        // 初始化示例数据
        initData() {
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
        
        addStudent() {
            this.$refs.studentForm.validate((valid) => {
                if (valid) {
                    // 生成新的学员ID
                    const newStudentId = this.students.length > 0 
                        ? Math.max(...this.students.map(s => s.studentId)) + 1 
                        : 1;
                    
                    // 转换日期格式
                    const registerTime = this.formData.student.registerTime instanceof Date 
                        ? this.formData.student.registerTime.toISOString().split('T')[0] 
                        : this.formData.student.registerTime;
                    
                    // 创建新学员
                    const newStudent = {
                        ...this.formData.student,
                        studentId: newStudentId,
                        registerTime: registerTime
                    };
                    
                    // 添加到学员列表
                    this.students.push(newStudent);
                    
                    // 关闭对话框
                    this.dialogs.addStudentVisible = false;
                    
                    // 更新统计数据
                    this.updateStatistics();
                    
                    // 显示成功消息
                    this.$message.success('学员添加成功！');
                } else {
                    return false;
                }
            });
        },
        
        updateStudent() {
            this.$refs.studentForm.validate((valid) => {
                if (valid) {
                    // 转换日期格式
                    const registerTime = this.formData.student.registerTime instanceof Date 
                        ? this.formData.student.registerTime.toISOString().split('T')[0] 
                        : this.formData.student.registerTime;
                    
                    // 更新学员数据
                    const index = this.students.findIndex(s => s.studentId === this.formData.student.studentId);
                    if (index !== -1) {
                        this.students[index] = {
                            ...this.formData.student,
                            registerTime: registerTime
                        };
                    }
                    
                    // 关闭对话框
                    this.dialogs.editStudentVisible = false;
                    
                    // 显示成功消息
                    this.$message.success('学员信息更新成功！');
                } else {
                    return false;
                }
            });
        },
        
        confirmDeleteStudent(student) {
            this.$confirm('确定要删除该学员吗？', '删除确认', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                // 删除学员
                this.students = this.students.filter(s => s.studentId !== student.studentId);
                
                // 更新统计数据
                this.updateStatistics();
                
                // 显示成功消息
                this.$message.success('学员删除成功！');
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
        
        addCourse() {
            this.$refs.courseForm.validate((valid) => {
                if (valid) {
                    // 生成新的课程ID
                    const newCourseId = this.courses.length > 0 
                        ? Math.max(...this.courses.map(c => c.courseId)) + 1 
                        : 1;
                    
                    // 创建新课程
                    const newCourse = {
                        ...this.formData.course,
                        courseId: newCourseId
                    };
                    
                    // 添加到课程列表
                    this.courses.push(newCourse);
                    
                    // 关闭对话框
                    this.dialogs.addCourseVisible = false;
                    
                    // 更新统计数据
                    this.updateStatistics();
                    
                    // 显示成功消息
                    this.$message.success('课程添加成功！');
                } else {
                    return false;
                }
            });
        },
        
        updateCourse() {
            this.$refs.courseForm.validate((valid) => {
                if (valid) {
                    // 更新课程数据
                    const index = this.courses.findIndex(c => c.courseId === this.formData.course.courseId);
                    if (index !== -1) {
                        this.courses[index] = {...this.formData.course};
                    }
                    
                    // 关闭对话框
                    this.dialogs.editCourseVisible = false;
                    
                    // 显示成功消息
                    this.$message.success('课程信息更新成功！');
                } else {
                    return false;
                }
            });
        },
        
        confirmDeleteCourse(course) {
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
            }).then(() => {
                // 删除课程
                this.courses = this.courses.filter(c => c.courseId !== course.courseId);
                
                // 更新统计数据
                this.updateStatistics();
                
                // 显示成功消息
                this.$message.success('课程删除成功！');
            }).catch(() => {
                // 取消删除
                this.$message.info('已取消删除');
            });
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
        
        addClass() {
            this.$refs.classForm.validate((valid) => {
                if (valid) {
                    // 生成新的班级ID
                    const newClassId = this.classes.length > 0 
                        ? Math.max(...this.classes.map(c => c.classId)) + 1 
                        : 1;
                    
                    // 转换日期格式
                    const startDate = this.formData.clazz.startDate instanceof Date 
                        ? this.formData.clazz.startDate.toISOString().split('T')[0] 
                        : this.formData.clazz.startDate;
                    
                    // 创建新班级
                    const newClass = {
                        ...this.formData.clazz,
                        classId: newClassId,
                        startDate: startDate
                    };
                    
                    // 添加到班级列表
                    this.classes.push(newClass);
                    
                    // 关闭对话框
                    this.dialogs.addClassVisible = false;
                    
                    // 更新统计数据
                    this.updateStatistics();
                    
                    // 显示成功消息
                    this.$message.success('班级添加成功！');
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
});