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
        <el-header style="text-align: right; font-size: 18px; background-color: #409EFF; border-radius: 3px">
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