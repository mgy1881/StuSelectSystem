<script setup>
import { ref } from 'vue'
import {
    Edit,
    Delete,
    MessageBox
} from '@element-plus/icons-vue'
import {
    getStudentListByStudentIdService,
    getStudentListByCourseIdService,
    getTeacherListService,
    getMajorListService,
    courseListService,
    addCourseService,
    deleteCourseService,
    updateCourseService,
    getDeptListService
}
    from '@/api/admin.js'
import { ElMessage } from 'element-plus'

const courseList = ref([{
    id: '',
    cno: '',
    cname: '',
    teacherId: '',
    capacity: '',
    selectedNumber: '',
    date: '',
    majorId: ''
}])

const courseQuery = ref({
    id: '',
    cno: '',
    cname: '',
    teacherId: '',
    selectedNumber: '',
    date: '',
    majorId: '',
    pageNo: 1,
    pageSize: 5
})

const courseInfo = ref({
    id: '',
    cno: "string",
    cname: "string",
    teacherId: 0,
    capacity: 0,
    selectedNumber: 0,
    date: "string",
    major_id: 0
})

const studentList = ref([{
    id: '',
    sno: '',
    sname: '',
    sgender: '',
    sage: '',
    smajorId: '',
    sdeptId: ''
}])

const teacherList = ref([]);

const csList = ref([{
    id: '',
    courseId: '',
    studentId: '',
    needbook: ''
}])

const rules = {
    cno: [
        { required: true, message: '请输入课程号', trigger: 'blur' },
    ],
    cname: [
        { required: true, message: '请输入课程名', trigger: 'blur' },
    ]
}

const majorList = ref([])
const deptList = ref([])
const total = ref(20)
const visibleDrawer = ref(false)
const visibleDrawer_1 = ref(false)
const dialogVisible = ref(false)

const getMajorList = async () => {
    let res = await getMajorListService();
    majorList.value = res.data
}

const getDeptList = async () => {
    let res = await getDeptListService();
    deptList.value = res.data
}

const getTeacherList = async () => {
    let res = await getTeacherListService();
    teacherList.value = res.data
}

const getCourseList = async () => {
    let res = await courseListService(courseQuery.value)

    total.value = parseInt(res.data.total)
    // pages.value = res.data.pages

    courseList.value = res.data.list

    for (let i = 0; i < courseList.value.length; i++) {
        let cour = courseList.value[i];
        for (let j = 0; j < majorList.value.length; j++) {
            if (cour.majorId == majorList.value[j].id) {
                cour.majorName = majorList.value[j].name;
            }
        }
    }
}

const addCourse = async (data) => {
    let v = courseInfo.value;
    v.selectedNumber = 0;
    let result = await addCourseService(v);
    ElMessage.success('添加成功')

    //调用获取所有文章分类的函数
    getCourseList();
    visibleDrawer.value = false;
}

import { ElMessageBox } from 'element-plus'

const deleteCourse = async (id) => {
    ElMessageBox.confirm(
        '你确认要删除该课程信息吗?',
        '温馨提示',
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(async () => {
            //调用接口
            await deleteCourseService(id);
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
                message: '用户取消了删除',
            })
        })
}
const deleteStudent = async (id) => {
    ElMessageBox.confirm(
        '你确认要删除该学生信息吗?',
        '温馨提示',
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(async () => {
            //调用接口
            let result = await deleteStudentService(id);
            ElMessage({
                type: 'success',
                message: '删除成功',
            })
            //刷新列表
            getStudentList();
        })
        .catch(() => {
            ElMessage({
                type: 'info',
                message: '用户取消了删除',
            })
        })

}

const updateCourse = async () => {
    let result = await updateCourseService(courseInfo.value);
    ElMessage.success("修改成功")
    dialogVisible.value = false
    getCourseList()
}

const clearData = () => {
    courseInfo.value = {
        id: '',
        cno: "",
        cname: "",
        teacherId: '',
        capacity: '',
        selectedNumber: '',
        date: "",
        major_id: ''
    }
}

const showDialog = (row) => {
    courseInfo.value = row
    console.log(courseInfo.value);
    dialogVisible.value = true
}


const getStudentListByCourseId = async (id) => {
    let res = await getStudentListByCourseIdService(id)
    // let info = res.data
    total.value = parseInt(res.data.total)
    // pages.value = res.data.pages
    studentList.value = [];
    csList.value = res.data
    for (let i = 0; i < csList.value.length; i++) {
        let cs = csList.value[i];
        let Stu = (await getStudentListByStudentIdService(cs.studentId)).data
        Stu.genderName = Stu.sgender == '0' ? '男' : '女';
        for (let j = 0; j < majorList.value.length; j++) {
            if (Stu.smajorId == majorList.value[j].id) {
                Stu.majorName = majorList.value[j].name;
            }
        }
        for (let j = 0; j < deptList.value.length; j++) {
            if (Stu.sdeptId == deptList.value[j].id) {
                Stu.deptName = deptList.value[j].name;
            }
        }
        studentList.value.push(Stu);
    }
    console.log(studentList.value);
}

getDeptList();
getMajorList();
getTeacherList();
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
                <el-input v-model="courseQuery.cname" minlength="1" maxlength="20"></el-input>
            </el-form-item>
            <el-form-item label="课程号">
                <el-input v-model="courseQuery.cno" minlength="1" maxlength="10"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="getCourseList">搜索</el-button>
                <el-button @click="courseQuery.cname= '',courseQuery.cno='',courseQuery.majorId ='';">重置</el-button>
            </el-form-item>
        </el-form>

        <el-table :data="courseList" style="width: 100%">
            <el-table-column label="专业名"  prop="majorName"></el-table-column>
            <el-table-column label="课程号"  prop="cno"></el-table-column>
            <el-table-column label="课程名"  prop="cname"></el-table-column>
            <el-table-column label="任课教师"  prop="teacherName"> </el-table-column>
            <el-table-column label="限选人数" prop="capacity"> </el-table-column>
            <el-table-column label="上课时间"  prop="date"> </el-table-column>

            <!-- <el-table-column label="状态" prop="state"></el-table-column> -->
            <el-table-column label="操作" >
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary" @click="showDialog(row)"></el-button>
                    <el-button :icon="Delete" circle plain type="danger" @click="deleteCourse(row.id)"></el-button>
                    <el-button :icon='MessageBox' circle plain type="primary"
                        @click="getStudentListByCourseId(row.id), visibleDrawer_1 = true"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>
        <!-- 分页条 -->
        <el-pagination v-model:current-page="courseQuery.pageNo" v-model:page-size="courseQuery.pageSize"
            :page-sizes="[3, 5, 10, 15]" layout="jumper, total, sizes, prev, pager, next" background :total="total"
            @size-change="getCourseList" @current-change="getCourseList"
            style="margin-top: 20px; justify-content: flex-end" />

        <el-dialog v-model="dialogVisible" title="修改课程信息" width="30%">
            <el-form :model="courseInfo" :rules="rules" label-width="100px" style="padding-right: 30px">
                <el-form-item label="课程号：" prop="cno">
                    <el-input v-model="courseInfo.cno" minlength="1" maxlength="20"></el-input>
                </el-form-item>
                <el-form-item label="课程名：" prop="cname">
                    <el-input v-model="courseInfo.cname" minlength="1" maxlength="20"></el-input>
                </el-form-item>
                <!-- <el-form-item label="任课教师：" prop="teacherId">
                    <el-input v-model="courseInfo.teacherId" minlength="1" maxlength="15"></el-input>
                </el-form-item> -->
                <el-form-item label="任课教师：">
                    <el-select placeholder="请选择" v-model="courseInfo.teacherId">
                        <el-option v-for="c in teacherList" :key="c.id" :label="c.tname" :value="c.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="限选人数：" prop="capacity">
                    <el-input v-model="courseInfo.capacity" minlength="1" maxlength="15"></el-input>
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
        <!-- 抽屉 -->
        <el-drawer v-model="visibleDrawer" title="添加课程" direction="rtl" size="30%">
            <!-- 添加文章表单 -->
            <el-form :model="courseInfo" label-width="100px" :rules="rules">
                <el-form-item label="课程号" prop="cno">
                    <el-input v-model="courseInfo.cno" placeholder="请输入课程号"></el-input>
                </el-form-item>
                <el-form-item label="课程名" prop="cname">
                    <el-input v-model="courseInfo.cname" placeholder="请输入课程名"></el-input>
                </el-form-item>
                <el-form-item label="任课教师：">
                    <el-select placeholder="请选择" v-model="courseInfo.teacherId">
                        <el-option v-for="c in teacherList" :key="c.id" :label="c.tname" :value="c.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="限选人数" prop="capacity">
                    <el-input v-model="courseInfo.capacity" placeholder="请输入限选人数"></el-input>
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
        <el-drawer v-model="visibleDrawer_1" title="选课信息详情" direction="rtl" size="50%">
            <!-- 添加文章表单 -->
            <el-table :data="studentList" style="width: 100%">
                <el-table-column label="学号" prop="sno"></el-table-column>
                <el-table-column label="姓名" prop="sname"></el-table-column>
                <el-table-column label="性别" prop="genderName"> </el-table-column>
                <el-table-column label="专业" prop="majorName"> </el-table-column>
                <el-table-column label="院系" prop="deptName"> </el-table-column>
                <el-table-column label="操作" width="100">
                    <template #default="{ row }">
                        <el-button :icon="Delete" circle plain type="danger" @click="deleteStudent(row.id)"></el-button>
                    </template>
                </el-table-column>
                <template #empty>
                    <el-empty description="没有数据" />
                </template>
            </el-table>
        </el-drawer>
    </el-card>
</template>

<style scoped>
.page-container {
    min-height: 100%;
    box-sizing: border-box;

    .header {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
}

.editor {
    width: 100%;

    :deep(.ql-editor) {
        min-height: 200px;
    }
}
</style>