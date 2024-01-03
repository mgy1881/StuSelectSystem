<script setup>
import {
    getTeacherInfoService,
    getDeptInfoService,
} from "@/api/teacher.js"
import { ref } from 'vue'

const teacherInfo = ref({
    "id": '',
    "tno": '',
    "tname": '',
    "tjob": '',
    "tdeptId": '',
    "tgender": '',
});

const loading = ref(true);


const getInfo = async () => {
    loading.value = true;
    teacherInfo.value = (await getTeacherInfoService()).data;
    teacherInfo.value.genderName = teacherInfo.value.Tgender === '0' ? '男' : '女';
    teacherInfo.value.deptName = (await getDeptInfoService(teacherInfo.value.sdeptId)).data[0].name;
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
        <el-descriptions direction="vertical" :column="3" border v-loading="loading">
            <el-descriptions-item label="姓名">{{ teacherInfo.tname }}</el-descriptions-item>
            <el-descriptions-item label="教工号">{{ teacherInfo.tno }}</el-descriptions-item>
            <el-descriptions-item label="性别">{{ teacherInfo.genderName }}</el-descriptions-item>
            <!-- <el-descriptions-item label="年龄">{{ teacherInfo.sage }}</el-descriptions-item> -->
            <el-descriptions-item label="院系">{{ teacherInfo.deptName }}</el-descriptions-item>
        </el-descriptions>
    </el-card>
</template>

<style scoped></style>