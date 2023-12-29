<script setup>
import { getStudentInfoService, getMajorInfoService, getDeptInfoService } from "@/api/student.js"
import { ref } from 'vue'

const studentInfo = ref({
    "id": '',
    "sno": '',
    "sname": '',
    "sgender": '',
    "sage": '',
    "smajorId": '',
    "sdeptId": ''
});

const loading = ref(true);


const getInfo = async () => {
    loading.value = true;
    studentInfo.value = (await getStudentInfoService()).data;
    studentInfo.value.majorName = (await getMajorInfoService(studentInfo.value.smajorId)).data[0].name;
    studentInfo.value.deptName = (await getDeptInfoService(studentInfo.value.sdeptId)).data[0].name;
    studentInfo.value.genderName = studentInfo.value.sgender === '0' ? '男' : '女';
    loading.value = false;
}
getInfo();

</script>

<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>基本资料</span>
            </div>
        </template>
        <el-descriptions direction="vertical" :column="3"  border v-loading="loading">
            <el-descriptions-item label="姓名">{{ studentInfo.sname }}</el-descriptions-item>
            <el-descriptions-item label="学号">{{ studentInfo.sno }}</el-descriptions-item>
            <el-descriptions-item label="性别">{{ studentInfo.genderName }}</el-descriptions-item>
            <el-descriptions-item label="年龄">{{studentInfo.sage}}</el-descriptions-item>
            <el-descriptions-item label="专业">{{studentInfo.majorName}}</el-descriptions-item>
            <el-descriptions-item label="院系">{{ studentInfo.deptName }}</el-descriptions-item>
        </el-descriptions>
    </el-card>
</template>

<style scoped></style>