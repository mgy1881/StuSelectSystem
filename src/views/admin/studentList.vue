<script setup>
import { ref } from 'vue'
import {
    Edit,
    Delete
} from '@element-plus/icons-vue'
import { studentListService, getMajorListService, getDeptListService, addStudentService, deleteStudentService, updateStudentService }
    from '@/api/admin.js'
import { ElMessage } from 'element-plus'

const studentList = ref([{
    id: '',
    sno: '',
    sname: '',
    sgender: '',
    sage: '',
    smajorId: '',
    sdeptId: ''
}])

const studentQuery = ref({
    sno: '',
    sname: '',
    sgender: '',
    sage: '',
    smajorId: '',
    sdeptId: '',
    pageNo: 1,
    pageSize: 5
})

const studentInfo = ref({
    id: '',
    sno: "21001",
    sname: "张三",
    sgender: 1,
    sage: 20,
    smajorId: 1,
    sdeptId: 2
})

const rules = {
    sname: [
        { required: true, message: '请输入学生姓名', trigger: 'blur' },
    ],
    sage: [
        { required: true, message: '请输入年龄', trigger: 'blur' }
    ],
    sno: [
        { required: true, message: '请输入学号', trigger: 'blur' }
    ]
}

const majorList = ref([])
const deptList = ref([])
const total = ref()
const visibleDrawer = ref(false)
const dialogVisible = ref(false)
const loading = ref(true)
// const pages = ref()

const getMajorList = async () => {
    let res = await getMajorListService();
    majorList.value = res.data
}

const getDeptList = async () => {
    let res = await getDeptListService();
    deptList.value = res.data
}
const getStudentList = async () => {
    loading.value = true;
    let res = await studentListService(studentQuery.value)

    total.value = parseInt(res.data.total)
    // pages.value = res.data.pages

    studentList.value = res.data.list

    for (let i = 0; i < studentList.value.length; i++) {
        let stu = studentList.value[i];
        stu.genderName = stu.sgender == '0' ? '男' : '女';
        for (let j = 0; j < majorList.value.length; j++) {
            if (stu.smajorId == majorList.value[j].id) {
                stu.majorName = majorList.value[j].name;
            }
        }
        for (let j = 0; j < deptList.value.length; j++) {
            if (stu.sdeptId == deptList.value[j].id) {
                stu.deptName = deptList.value[j].name;
            }
        }
    }
    loading.value = false;
}
const addStudent = async (data) => {
    let result = await addStudentService(studentInfo.value);
    ElMessage.success('添加成功')

    //调用获取所有文章分类的函数
    getStudentList();
    visibleDrawer.value = false;
}
import { ElMessageBox } from 'element-plus'

const deleteStudent = async (id) => {
    ElMessageBox.confirm(
        '你确认要删除该学生信息吗?',
        '温馨提示',
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
        }
    ).then(async () => {
        //调用接口
        let result = await deleteStudentService(id);
        ElMessage({
            type: 'success',
            message: '删除成功',
        })
        //刷新列表
        getStudentList();
    }).catch(() => {
        ElMessage({
            type: 'info',
            message: '取消了删除',
        })
    })

}

const updateStudent = async () => {
    let result = await updateStudentService(studentInfo.value);
    ElMessage.success("修改成功")
    dialogVisible.value = false
    getStudentList()
}
const clearData = () => {
    studentInfo.value = {
        id: '',
        sno: "",
        sname: "",
        sgender: '',
        sage: '',
        smajorId: '',
        sdeptId: ''
    }
}
const showDialog = (row) => {
    console.log(row);
    studentInfo.value = {
        id: row.id,
        sno: row.sno,
        sname: row.sname,
        sgender: row.sgender,
        sage: row.sage,
        smajorId: row.smajorId,
        sdeptId: row.sdeptId
    }
    console.log(studentInfo.value);
    dialogVisible.value = true
}


getMajorList();
getDeptList();
getStudentList();
</script>

<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>学生管理</span>
                <div class="extra">
                    <el-button type="primary" @click="visibleDrawer = true; title = '添加分类'; clearData()">添加学生</el-button>
                </div>
            </div>
        </template>
        <!-- 搜索表单 -->
        <el-form inline>
            <el-form-item label="院系：">
                <el-select placeholder="请选择" v-model="studentQuery.sdeptId" clearable>
                    <el-option v-for="c in deptList" :key="c.id" :label="c.name" :value="c.id">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="专业：">
                <el-select placeholder="请选择" v-model="studentQuery.smajorId" no-data-text="请先选择院系" clearable>
                    <template v-for="c in majorList">
                        <el-option v-if="c.deptId === studentQuery.sdeptId" :key="c.id" :label="c.name" :value="c.id">
                        </el-option>
                    </template>
                </el-select>
            </el-form-item>
            <el-form-item label="姓名：">
                <el-input v-model="studentQuery.sname" minlength="1" maxlength="10"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="getStudentList">搜索</el-button>
                <el-button @click="studentQuery.smajorId = '',studentQuery.sdeptId = '',studentQuery.sname='';">重置</el-button>
            </el-form-item>
        </el-form>

        <el-table :data="studentList" style="width: 100%" v-loading="loading">
            <el-table-column label="学号" width="400" prop="sno"></el-table-column>
            <el-table-column label="姓名" prop="sname"></el-table-column>
            <el-table-column label="性别" prop="genderName"> </el-table-column>
            <el-table-column label="专业" prop="majorName"> </el-table-column>
            <el-table-column label="院系" prop="deptName"> </el-table-column>

            <!-- <el-table-column label="状态" prop="state"></el-table-column> -->
            <el-table-column label="操作" width="100">
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary" @click="showDialog(row)"></el-button>
                    <el-button :icon="Delete" circle plain type="danger" @click="deleteStudent(row.id)"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>
        <!-- 分页条 -->
        <el-pagination v-model:current-page="studentQuery.pageNo" v-model:page-size="studentQuery.pageSize"
            :page-sizes="[3, 5, 10, 15]" layout="jumper, total, sizes, prev, pager, next" background :total="total"
            @size-change="getStudentList" @current-change="getStudentList"
            style="margin-top: 20px; justify-content: flex-end" />

        <el-dialog v-model="dialogVisible" title="修改学生信息" width="30%">
            <el-form :model="studentInfo" :rules="rules" label-width="100px" style="padding-right: 30px">
                <el-form-item label="学生姓名：" prop="sname">
                    <el-input v-model="studentInfo.sname" minlength="1" maxlength="10"></el-input>
                </el-form-item>
                <el-form-item label="年龄：" prop="sage">
                    <el-input v-model="studentInfo.sage" minlength="1" maxlength="15"></el-input>
                </el-form-item>
                <el-form-item label="性别：" prop="sgender">
                    <el-radio-group v-model="studentInfo.sgender">
                        <el-radio :label='0'>男</el-radio>
                        <el-radio :label='1'>女</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="院系：">
                    <el-select placeholder="请选择" v-model="studentInfo.sdeptId">
                        <el-option v-for="c in deptList" :key="c.id" :label="c.name" :value="c.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="专业：">
                    <el-select placeholder="请选择" v-model="studentInfo.smajorId" no-data-text="请先选择院系">
                        <template v-for="c in majorList">
                            <el-option v-if="c.deptId === studentInfo.sdeptId" :key="c.id" :label="c.name" :value="c.id">
                            </el-option>
                        </template>
                    </el-select>
                </el-form-item>
            </el-form>

            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="updateStudent"> 确认 </el-button>
                </span>
            </template>
        </el-dialog>
        <!-- 抽屉 -->
        <el-drawer v-model="visibleDrawer" title="添加学生" direction="rtl" size="30%">
            <!-- 添加文章表单 -->
            <el-form :model="studentInfo" label-width="100px" :rules="rules">
                <el-form-item label="姓名：" prop="sname">
                    <el-input v-model="studentInfo.sname" placeholder="请输入学生姓名"></el-input>
                </el-form-item>
                <el-form-item label="性别：" prop="sgender">
                    <el-radio-group v-model="studentInfo.sgender">
                        <el-radio :label='0'>男</el-radio>
                        <el-radio :label='1'>女</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="年龄：" prop="sage">
                    <el-input v-model="studentInfo.sage" minlength="1" maxlength="15"></el-input>
                </el-form-item>
                <el-form-item label="学号：" prop="sno">
                    <el-input v-model="studentInfo.sno" placeholder="请输入学号"></el-input>
                </el-form-item>
                <el-form-item label="院系：">
                    <el-select placeholder="请选择" v-model="studentInfo.sdeptId">
                        <el-option v-for="c in deptList" :key="c.id" :label="c.name" :value="c.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="专业：">
                    <el-select placeholder="请选择" v-model="studentInfo.smajorId" no-data-text="请先选择院系">
                        <template v-for="c in majorList">
                            <el-option v-if="c.deptId === studentInfo.sdeptId" :key="c.id" :label="c.name" :value="c.id">
                            </el-option>
                        </template>
                    </el-select>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="addStudent">确认</el-button>
                    <el-button type="primary" @click="visibleDrawer = false">取消</el-button>
                </el-form-item>
            </el-form>
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
