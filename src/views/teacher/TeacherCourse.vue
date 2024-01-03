<script setup>
import { ref } from 'vue'
import {
    getCourseListService,
    deleteCourseService,
    addCourseService,
    updateCourseService
} from '@/api/teacher.js'
import {
    Edit,
    Delete
} from '@element-plus/icons-vue'
import { getMajorListService } from '@/api/admin';


const courseList = ref([]);
const courseInfo = ref();
const majorList = ref({});
const loading = ref(true);
const dialogVisible = ref(false)
const visibleDrawer = ref(false)

const getMajorList = async () => {
    let res = await getMajorListService();
    majorList.value = res.data
}

const getCourseList = async () => {
    loading.value = true;
    let res = await getCourseListService();
    courseList.value = res.data
    for (let i = 0; i < courseList.value.length; i++) {
        let c = courseList.value[i];
        c.capacity = parseInt(c.capacity)
        c.selectedNumber = parseInt(c.selectedNumber)
        for (let j = 0; j < majorList.value.length; j++) {
            if (c.majorId == majorList.value[j].id) {
                c.majorName = majorList.value[j].name;
                break;
            }
        }
    }
    loading.value = false;
}

const addCourse = async () => {
    courseInfo.value.selectedNumber = 0
    await addCourseService(courseInfo.value);
    ElMessage.success('添加成功')
    //调用获取所有文章分类的函数
    getCourseList();
    visibleDrawer.value = false;
}

const updateCourse = async () => {
    await updateCourseService(courseInfo.value);
    ElMessage.success("修改成功")
    dialogVisible.value = false
    getCourseList()
}

import { ElMessageBox, ElMessage } from 'element-plus'
const deleteCourse = (id) => {
    ElMessageBox.confirm(
        '你确认要删除该课程吗?',
        '温馨提示',
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(async () => {
            //调用接口
            let result = await deleteCourseService(id);
            ElMessage({
                type: 'success',
                message: '删除成功',
            })
            //刷新列表
            getCourseList();
        })
        .catch(() => {
            ElMessage({
                type: 'info',
                message: '取消删除',
            })
        })

}

const clearData = () => {
    courseInfo.value = {}
    courseInfo.value.capacity = 20
}

const showDialog = (row) => {
    courseInfo.value = row
    dialogVisible.value = true
}

getMajorList();
getCourseList();
</script>

<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>课程管理</span>
                <div class="extra">
                    <el-button type="primary" @click="visibleDrawer = true; clearData()">添加课程</el-button>
                </div>
            </div>
        </template>
        <el-table :data="courseList" style="width: 100%" v-loading="loading">
            <el-table-column label="课程号" prop="cno"></el-table-column>
            <el-table-column label="课程名" prop="cname"></el-table-column>
            <el-table-column label="专业" prop="majorName"> </el-table-column>
            <el-table-column label="课程容量" prop="capacity"> </el-table-column>
            <el-table-column label="已选人数" prop="selectedNumber"> </el-table-column>


            <!-- <el-table-column label="状态" prop="state"></el-table-column> -->
            <el-table-column label="操作" width="100">
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary" @click="showDialog(row)"></el-button>
                    <el-button :icon="Delete" circle plain type="danger" @click="deleteCourse(row.id)"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>
        <el-dialog v-model="dialogVisible" title="修改课程信息" width="30%">
            <el-form :model="courseInfo" :rules="rules" label-width="100px" style="padding-right: 30px">
                <el-form-item label="课程号：" prop="cno">
                    <el-input v-model="courseInfo.cno" minlength="1" maxlength="20"></el-input>
                </el-form-item>
                <el-form-item label="课程名：" prop="cname">
                    <el-input v-model="courseInfo.cname" minlength="1" maxlength="20"></el-input>
                </el-form-item>
                <el-form-item label="限选人数：" prop="capacity">
                    <el-input-number v-model="courseInfo.capacity" :min="1" :max="500" />
                </el-form-item>
                <el-form-item label="上课时间：" prop="date">
                    <el-input v-model="courseInfo.date" minlength="1" maxlength="15"></el-input>
                </el-form-item>
                <el-form-item label="专业：">
                    <el-select placeholder="请选择" v-model="courseInfo.majorId">
                        <el-option v-for="c in majorList" :key="c.id" :label="c.name" :value="c.id">
                        </el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="updateCourse">确认</el-button>
                </span>
            </template>
        </el-dialog>
        <!-- 添加课程抽屉 -->
        <el-drawer v-model="visibleDrawer" title="添加课程" direction="rtl" size="50%">
            <el-form :model="courseInfo" label-width="100px" :rules="rules">
                <el-form-item label="课程号" prop="cno">
                    <el-input v-model="courseInfo.cno" placeholder="请输入课程号"></el-input>
                </el-form-item>
                <el-form-item label="课程名" prop="cname">
                    <el-input v-model="courseInfo.cname" placeholder="请输入课程名"></el-input>
                </el-form-item>
                <el-form-item label="限选人数" prop="capacity">
                    <el-input-number v-model="courseInfo.capacity" :min="1" :max="500" />
                </el-form-item>
                <el-form-item label="上课时间" prop="date">
                    <el-input v-model="courseInfo.date" placeholder="请输入上课时间"></el-input>
                </el-form-item>
                <el-form-item label="专业">
                    <el-select placeholder="请选择" v-model="courseInfo.majorId">
                        <el-option v-for="c in majorList" :key="c.id" :label="c.name" :value="c.id">
                        </el-option>
                    </el-select>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="addCourse">确认</el-button>
                    <el-button type="primary" @click="visibleDrawer = false">取消</el-button>
                </el-form-item>
            </el-form>
        </el-drawer>
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
