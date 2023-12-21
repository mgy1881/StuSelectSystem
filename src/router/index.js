import { createRouter, createWebHistory } from 'vue-router'

import LoginVue from '@/views/Login.vue'
import AdminIndexVue from '@/views/admin/AdminIndex.vue'
import AdminStudentListVue from '@/views/admin/studentList.vue'
import AdminTeacherListVue from '@/views/admin/teacherList.vue'
import AdminCourseListVue from '@/views/admin/courseList.vue'

import StudentIndexVue from '@/views/student/StudentIndex.vue'


const routes = [
  { path: '/login', component: LoginVue, name: 'login'},
  { path: '/', component: LoginVue, name: 'index', redirect: '/login'},
  {
    path: '/admin', 
    component: AdminIndexVue, 
    name: 'admin', 
    redirect: '/admin/student', 
    meta: {requireAuth: true}, 
    children: [
      { path: '/admin/student', component: AdminStudentListVue, name: '学生管理', meta: {requireAuth: true} },
      { path: '/admin/teacher', component: AdminTeacherListVue, name: '教师管理', meta: {requireAuth: true} },
      { path: '/admin/course', component: AdminCourseListVue, name: '课程管理', meta: {requireAuth: true} }
    ]
  },
  {
    path: '/student', component: StudentIndexVue, name: 'student', meta: {requireAuth: true}
  }
]

//创建路由器
const router = createRouter({
  history: createWebHistory(),
  routes: routes
})

import { useTokenStore } from '@/stores/token.js'
import { ElMessage } from 'element-plus'


router.beforeEach((to, from, next) => {
  /**
   * 未登录则跳转到登录页
   * 未登录跳转到登录页,也可以通过axios的响应拦截器去实现,但是会先在当前页面渲染一下,再跳转到登录页,会有个闪动的现象
   * 这里通过路由守卫的方式,不会在当前页闪现一下,但是需要在每个路由组件添加一个是否需要登录的标识位,如本项目中的requireAuth字段
   */
  if (to.meta.requireAuth) {
    const tokenStore = useTokenStore();
    let token = tokenStore.token;
    if (token) {
      next();
    } else {
      ElMessage.error("请先登录")
      next({
        path: "/login"
      });
    }
  } else {
    next();
  }
})

//导出路由
export default router