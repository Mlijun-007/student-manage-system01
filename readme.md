# 学生管理系统
## 开发需知
- git仓库遵循git flow工作流模式进行git协作，即：
    - master 分支：
        永远保持稳定和可发布的状态。
        每次发布一个新的版本时，都会从 develop 分支合并到 master 分支。

    - develop 分支：
        用于集成所有的开发分支。
        代表了最新的开发进度。
        功能分支、发布分支和修复分支都从这里分支出去，最终合并回这里。

    - feature 分支：
        用于开发新功能。
        从 develop 分支创建，开发完成后合并回 develop 分支。
        命名规范：feature/feature-name。

    - release 分支：
        用于准备新版本的发布。
        从 develop 分支创建，进行最后的测试和修复，然后合并回 develop 和 master 分支，并打上版本标签。
        命名规范：release/release-name。

    - hotfix 分支：
        用于修复紧急问题。
        从 master 分支创建，修复完成后合并回 master 和 develop 分支，并打上版本标签。
        命名规范：hotfix/hotfix-name。

- 前端请在system_frontend中进行工作，且遵循相应的代码规范。
- 后端请在system_backend中进行工作，且遵循相应的代码规范。
- 每次进行开发后，请再下一次开发时先爬取最新develop分支
- 任何问题请及时反馈