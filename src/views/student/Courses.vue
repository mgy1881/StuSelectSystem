<script setup>
import { ref } from 'vue'
import { getCourseInfoService, selectCourseService } from '@/api/student.js'
import { getMajorListService } from '@/api/admin';

const courseList = ref([]);

const courseQuery = ref({
    majorId: '',
    cno: '',
    cname: '',
    pageNo: 1,
    pageSize: 5
})

const majorList = ref({});
const total = ref()
const dialogVisible = ref(false)
const loading = ref(true)

const selectCourseInfo = ref({
    needBook: false,
    courseId: 0
})

const getMajorList = async () => {
    let res = await getMajorListService();
    majorList.value = res.data
}

const getCourseList = async () => {
    loading.value = true;
    let res = await getCourseInfoService(courseQuery.value);
    courseList.value = res.data.list;
    total.value = parseInt(res.data.total)

    for (let i = 0; i < courseList.value.length; i++) {
        let c = courseList.value[i];
        for (let j = 0; j < majorList.value.length; j++) {
            if (c.majorId == majorList.value[j].id) {
                c.majorName = majorList.value[j].name;
                break;
            }
        }
    }
    loading.value = false
}

const clearQuery = () => {
    courseQuery.value.majorId = ''
    courseQuery.value.cno = ''
    courseQuery.value.cname = ''
}


import { ElMessage } from 'element-plus'

const selectCourse = async (data) => {
    data.needBook = data.needBook ? 1 : 0;
    await selectCourseService(data);
    ElMessage.success("选课成功");
    data.needBook = false;
    getCourseList();
}

getMajorList();
getCourseList();

</script>

<template>
    <el-card class="page-container">
        <!-- 搜索表单 -->
        <el-form inline>
            <el-form-item label="专业：">
                <el-select placeholder="请选择" v-model="courseQuery.majorId" clearable>
                    <template v-for="c in majorList" :key="c.id">
                        <el-option :label="c.name" :value="c.id">
                        </el-option>
                    </template>
                </el-select>
            </el-form-item>
            <el-form-item label="课程名">
                <el-input v-model="courseQuery.cname" minlength="1" maxlength="10"></el-input>
            </el-form-item>
            <el-form-item label="课程号">
                <el-input v-model="courseQuery.cno" minlength="1" maxlength="10"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="getCourseList">搜索</el-button>
                <el-button @click="clearQuery">重置</el-button>
            </el-form-item>
        </el-form>

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
                    <el-button type="primary" @click="dialogVisible = true, selectCourseInfo.courseId = row.id"
                        round>选课</el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>
        <!-- 选课确认对话框 -->
        <el-dialog v-model="dialogVisible" title="确认选课" width="30%">
            <el-checkbox v-model="selectCourseInfo.needBook" label="是否购买教材" size="large" />
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="dialogVisible = false, selectCourse(selectCourseInfo)">
                        确认
                    </el-button>
                </span>
            </template>
        </el-dialog>
        <!-- 分页条 -->
        <el-pagination v-model:current-page="courseQuery.pageNo" v-model:page-size="courseQuery.pageSize"
            :page-sizes="[3, 5, 10, 15]" layout="jumper, total, sizes, prev, pager, next" background :total="total"
            @size-change="getCourseList" @current-change="getCourseList"
            style="margin-top: 20px; justify-content: flex-end" />
    </el-card>
</template>

<style>
.dialog-footer button:first-child {
    margin-right: 10px;
}
</style>