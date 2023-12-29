<script setup>
import { ref } from 'vue'
import { getSelectedCourseService ,dropCourseService} from '@/api/student.js'
import { getMajorListService } from '@/api/admin';


const courseList = ref([]);
const majorList = ref({});
const loading = ref(true)

const getMajorList = async () => {
    let res = await getMajorListService();
    majorList.value = res.data
}

const getCourseList = async () => {
    loading.value = true;
    let res = await getSelectedCourseService();
    courseList.value = res.data
    for (let i = 0; i < courseList.value.length; i++) {
        let c = courseList.value[i];
        for (let j = 0; j < majorList.value.length; j++) {
            if (c.majorId == majorList.value[j].id) {
                c.majorName = majorList.value[j].name;
                break;
            }
        }
    }
    loading.value = false;
}

import { ElMessageBox, ElMessage } from 'element-plus'
const dropCourse = (id) => {
    ElMessageBox.confirm(
        '你确认要退选该课程吗?',
        '温馨提示',
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(async () => {
            //调用接口
            let result = await dropCourseService(id);
            ElMessage({
                type: 'success',
                message: '退课成功',
            })
            //刷新列表
            getCourseList();
        })
        .catch(() => {
            ElMessage({
                type: 'info',
                message: '取消退课',
            })
        })

}
getMajorList();
getCourseList();
</script>

<template>
    <el-card class="page-container">
        <el-table :data="courseList" style="width: 100%" v-loading="loading">
            <el-table-column label="课程号" prop="cno"></el-table-column>
            <el-table-column label="课程名" prop="cname"></el-table-column>
            <el-table-column label="开课教师" prop="teacherName"> </el-table-column>
            <el-table-column label="专业" prop="majorName"> </el-table-column>
            <el-table-column label="课程容量" prop="capacity"> </el-table-column>
            <el-table-column label="已选人数" prop="selectedNumber"> </el-table-column>


            <!-- <el-table-column label="状态" prop="state"></el-table-column> -->
            <el-table-column label="操作" width="100">
                <template #default="{ row }">
                    <el-button type="danger" @click="dropCourse(row.id)" round>退课</el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>
    </el-card>
</template>

<style scoped>
.page-container {

    box-sizing: border-box;

    .header {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
}
</style>
