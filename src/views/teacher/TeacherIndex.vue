<script setup>
import { useRouter } from 'vue-router'
import { useTokenStore } from '@/stores/token.js'
const router = useRouter();
const out = () => {
    const tokenStore = useTokenStore();
    tokenStore.removeToken();
    router.push('/login')
}
</script>


<template>
    <el-container>
        <el-header
            style="display: flex; justify-content: space-between; align-items: center; text-align: right; font-size: 18px; background-color: #409EFF; border-radius: 3px">
            <div style="display: flex; align-items: center; color: #fff; font-weight: bold; letter-spacing: 2px;">
                <img src="https://my.ouc.edu.cn/static/img/logo-1702866032996.png" alt="Logo"
                    style="height: 24px; margin-right: 10px;">
                教务管理系统
            </div>
            <el-button type="primary" color="#333333" style="margin-top: 10px;" @click="out()" round>退出登录</el-button>
        </el-header>
        <el-container>
            <el-aside width="200px" style="background-color: rgb(238, 241, 246); height: 810px; border: 1px solid #eee">
                <div class="el-aside__logo"></div>
                <el-menu router>
                    <el-menu-item index="/teacher/info">
                        <el-icon>
                            <Reading />
                        </el-icon>
                        <span>个人信息</span>
                    </el-menu-item>
                    <el-menu-item index="/teacher/course">
                        <el-icon>
                            <Reading />
                        </el-icon>
                        <span>我的课程</span>
                    </el-menu-item>
                </el-menu>
            </el-aside>
            <el-main>
                <router-view></router-view>
            </el-main>
        </el-container>
    </el-container>
</template>
  
<style scoped>
/* .el-aside__logo {
    height: 120px;
    background: url('https://id.ouc.edu.cn/sso/casLogin/img/bgtxt-1694742541774.png') no-repeat center / 200px auto;
} */
</style>