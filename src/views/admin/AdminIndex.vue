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
            <div>
                <el-button link style="color: #333333; font-size: 18px" @click="out()">
                    logout
                </el-button>
                <el-divider direction="vertical" style="color: #333333"></el-divider>
                <i class="el-icon-paperclip" style="margin-right: 18px"></i>
                <span>test1</span>
                <el-divider direction="vertical"></el-divider>
                <i class="el-icon-user" style="margin-right: 18px"></i>
                <span>test2</span>
            </div>
        </el-header>

        <el-container>
            <el-aside width="200px" style="background-color: rgb(238, 241, 246); height: 810px; border: 1px solid #eee">
                <div class="el-aside__logo"></div>
                <el-menu router>
                    <el-menu-item index="/admin/student">
                        <el-icon>
                            <Reading />
                        </el-icon>
                        <span>学生管理</span>
                    </el-menu-item>
                    <el-menu-item index="/admin/teacher">
                        <el-icon>
                            <Avatar />
                        </el-icon>
                        <span>教师管理</span>
                    </el-menu-item>
                    <el-menu-item index="/admin/course">
                        <el-icon>
                            <DataBoard />
                        </el-icon>
                        <span>课程管理</span>
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