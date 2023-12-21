<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import { User, Lock } from '@element-plus/icons-vue'


const registerData = ref({
    username: '',
    password: '',
    level: '2'
})

const rules = {
    username: [
        { required: true, message: '请输入用户名', trigger: 'blur' }
    ],
    password: [
        { required: true, message: '请输入密码', trigger: 'blur' }
    ],
    level: [
        { required: true, message: '请选择', trigger: 'change' }
    ]
}

import { loginService } from "@/api/login.js"
import { useTokenStore } from '@/stores/token.js'
import useUserInfoStore from '@/stores/userInfo.js'
import { useRouter } from 'vue-router'
const router = useRouter()
const tokenStore = useTokenStore();
const userInfoStore = useUserInfoStore();
const login = async () => {
    let res = await loginService(registerData.value)
    ElMessage.success("登录成功")
    tokenStore.setToken(res.data)
    let type = ''
    if (registerData.value.level === '0') {
        type = 'admin'
    }
    else if (registerData.value.level === '1') {
        type = 'teacher'
    }
    else {
        type = 'student'
    }
    userInfoStore.setType(type)
    router.push("/" + type)
}
</script>

<template>
    <!-- <div>
        <el-container>
            <el-header>
                <div style="text-align: center; font-size: 25px; font-weight: bolder">
                    <i class="el-icon-s-home" style="margin-right: 25px"></i>
                    选课管理系统
                </div>
            </el-header> -->
    <el-row class="login-page">
        <el-col :span="12" class="bg"></el-col>
        <el-col :span="6" :offset="3" class="form">
            <!-- <el-card class="login-module"> -->
            <!-- <div slot="header" class="clearfix">
                <span style="text-align: center; font-size: 20px; font-family: 'Microsoft YaHei'">
                    <p><i class="el-icon-office-building" style="margin-right: 18px"></i>登录</p>
                </span>
            </div> -->
            <!-- 
            <el-form size="large" autocomplete="off" ref="form" :model="registerData" :rules="rules" label-width="100px" class="demo-registerData">
                <el-form-item label="用户名" prop="username">
                    <el-input :prefix-icon="User" v-model="registerData.username" placeholder="请输入用户名"></el-input>
                </el-form-item>
                <el-form-item label="用户密码" prop="password">
                    <el-input :prefix-icon="Lock" v-model="registerData.password" placeholder="请输入密码"
                        show-password></el-input>
                </el-form-item>
                <el-form-item label="用户类型" prop="level">
                    <el-radio-group v-model="registerData.level">
                        <el-radio :label="2">学生</el-radio>
                        <el-radio :label="1">老师</el-radio>
                        <el-radio :label="0">管理员</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="login">登陆</el-button>
                    <el-button @click="resetForm('registerData')">重置</el-button>
                    <el-button @click="test('registerData')">test</el-button>
                </el-form-item>
            </el-form> -->

            <el-form ref="form" size="large" autocomplete="off" :model="registerData" :rules="rules">
                <el-form-item>
                    <h1>登录</h1>
                </el-form-item>
                <el-form-item prop="username">
                    <el-input :prefix-icon="User" placeholder="请输入用户名" v-model="registerData.username"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input name="password" :prefix-icon="Lock" type="password" placeholder="请输入密码"
                        v-model="registerData.password"></el-input>
                </el-form-item>
                <!-- <el-form-item class="flex">
                    <div class="flex">
                        <el-checkbox>记住我</el-checkbox>
                        <el-link type="primary" :underline="false">忘记密码？</el-link>
                    </div>
                </el-form-item> -->
                <el-form-item prop="level">
                    <el-radio-group v-model="registerData.level">
                        <el-radio label="2">学生</el-radio>
                        <el-radio label="1">老师</el-radio>
                        <el-radio label="0">管理员</el-radio>
                    </el-radio-group>
                </el-form-item>
                <!-- 登录按钮 -->
                <el-form-item>
                    <el-button class="button" type="primary" auto-insert-space @click="login">登录</el-button>
                </el-form-item>
                <!-- <el-form-item class="flex">
                    <el-link type="info" :underline="false" @click="isRegister = true;clearRegisterData()">
                        注册 →
                    </el-link>
                </el-form-item> -->
            </el-form>

            <!-- </el-card> -->
        </el-col>
    </el-row>
</template>

<style scoped>
.login-page {
    height: 100vh;
    background-color: #fff;

    .bg {
        /* background: url('@/assets/logo2.png') no-repeat 60% center / 240px auto,
            url('@/assets/login_bg.jpg') no-repeat center / cover; */
        background: url('https://id.ouc.edu.cn/sso/casLogin/img/banner15-1694742541774.jpg') no-repeat center / cover;

        border-radius: 0 20px 20px 0;
    }

    .form {
        display: flex;
        flex-direction: column;
        justify-content: center;
        user-select: none;

        .title {
            margin: 0 auto;
        }

        .button {
            width: 100%;
        }

        .flex {
            width: 100%;
            display: flex;
            justify-content: space-between;
        }
    }
}
</style>