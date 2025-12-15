import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import App from './App.vue'

// 注册Element UI组件库
Vue.use(ElementUI)

// 创建Vue实例
new Vue({
  el: '#app',
  render: h => h(App)
})
