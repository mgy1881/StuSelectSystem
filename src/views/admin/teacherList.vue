
<style scoped></style>
<script setup>
import { ref } from 'vue'
import {
    Edit,
    Delete
} from '@element-plus/icons-vue'
import { teacherListService, getMajorListService, getDeptListService, addTeacherService, deleteTeacherService, updateTeacherService }
    from '@/api/admin.js'
import { ElMessage } from 'element-plus'

const teacherList = ref([{
    id: '',
    tno: '',
    tname: '',
    tjob: '',
    tdeptId: '',
    tgender: '',
}])

const teacherQuery = ref({
    id: '',
    tno: '',
    tname: '',
    tjob: '',
    tdeptId: '',
    tgender: '',
    pageNo: 1,
    pageSize: 5
})

const teacherInfo = ref({
    id: '',
    tno: '',
    tname: '',
    tjob: '123',
    tdeptId: '',
    tgender: ''
})

const rules = {
    tname: [
        { required: true, message: '请输入老师姓名', trigger: 'blur' },
    ],
    tno: [
        { required: true, message: '请输入教工号', trigger: 'blur' },
        { type: 'number', message: '请输入数字', trigger: 'blur' }
    ]
}

const majorList = ref([])
const deptList = ref([])
const total = ref()
const visibleDrawer = ref(false)
const dialogVisible = ref(false)
// const pages = ref()

const getMajorList = async () => {
    let res = await getMajorListService();
    majorList.value = res.data
}

const getDeptList = async () => {
    let res = await getDeptListService();
    deptList.value = res.data
}
const getTeacherList = async () => {
    let res = await teacherListService(teacherQuery.value)

    total.value = parseInt(res.data.total) 
    // pages.value = res.data.pages

    teacherList.value = res.data.list

    for (let i = 0; i < teacherList.value.length; i++) {
        let tea = teacherList.value[i];
        tea.genderName = tea.tgender == '0' ? '男' : '女';
        for (let j = 0; j < majorList.value.length; j++) {
            if (tea.tmajorId == majorList.value[j].id) {
                tea.majorName = majorList.value[j].name;
            }
        }
        for (let j = 0; j < deptList.value.length; j++) {
            if (tea.tdeptId == deptList.value[j].id) {
                tea.deptName = deptList.value[j].name;
            }
        }
    }
}
const addTeacher = async (data) => {
    console.log(teacherInfo.value);
    let result = await addTeacherService(teacherInfo.value);
    ElMessage.success('添加成功')

    //调用获取所有文章分类的函数
    getTeacherList();
    visibleDrawer.value = false;
}
import { ElMessageBox } from 'element-plus'

const deleteTeacher = async (id) => {
    ElMessageBox.confirm(
        '你确认要删除该老师信息吗?',
        '温馨提示',
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(async () => {
            //调用接口
            let result = await deleteTeacherService(id);
            ElMessage({
                type: 'success',
                message: '删除成功',
            })
            //刷新列表
            getTeacherList();
        })
        .catch(() => {
            ElMessage({
                type: 'info',
                message: '用户取消了删除',
            })
        })

}

const updateTeacher = async () => {
    let result = await updateTeacherService(teacherInfo.value);
    ElMessage.success("修改成功")
    dialogVisible.value = false
    getTeacherList()
}
const clearData = () => {
    teacherInfo.value = {
    id: '',
    tno: '',
    tname: '',
    tjob: '',
    tdeptId: '',
    tgender: '',
    }
}
const showDialog = (row) => {
    console.log(row);
    teacherInfo.value = {
        id: row.id,
        tno: row.tno,
        tname: row.tname,
        tjob: row.tjob,
        tdeptId: row.tdeptId,
        tgender: row.tgender,
    }
    console.log(teacherInfo.value);
    dialogVisible.value = true
}


getMajorList();
getDeptList();
getTeacherList();
</script>

<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>教师管理</span>
                <div class="extra">
                    <el-button type="primary" @click="visibleDrawer = true; clearData()">添加教师</el-button>
                </div>
            </div>
        </template>
        <!-- 搜索表单 -->
        <el-form inline>
            <el-form-item label="院系：">
                <el-select placeholder="请选择" v-model="teacherQuery.tdeptId" clearable>
                    <el-option v-for="c in deptList" :key="c.id" :label="c.name" :value="c.id">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="姓名">
                <el-input v-model="teacherQuery.tname" minlength="1" maxlength="10"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="getTeacherList">搜索</el-button>
                <el-button @click="teacherQuery.tmajorId = '';">重置</el-button>
            </el-form-item>
        </el-form>

        <el-table :data="teacherList" style="width: 100%">
            <el-table-column label="教工号" width="400" prop="tno"></el-table-column>
            <el-table-column label="姓名" prop="tname"></el-table-column>
            <el-table-column label="性别" prop="genderName"> </el-table-column>
            <el-table-column label="院系" prop="deptName"> </el-table-column>

            <!-- <el-table-column label="状态" prop="state"></el-table-column> -->
            <el-table-column label="操作" width="100">
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary" @click="showDialog(row)"></el-button>
                    <el-button :icon="Delete" circle plain type="danger" @click="deleteTeacher(row.id)"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>
        <!-- 分页条 -->
        <el-pagination v-model:current-page="teacherQuery.pageNo" v-model:page-size="teacherQuery.pageSize"
            :page-sizes="[3, 5, 10, 15]" layout="jumper, total, sizes, prev, pager, next" background :total="total"
            @size-change="getTeacherList" @current-change="getTeacherList"
            style="margin-top: 20px; justify-content: flex-end" />

        <el-dialog v-model="dialogVisible" title="修改教师信息" width="30%">
            <el-form :model="teacherInfo" :rules="rules" label-width="100px" style="padding-right: 30px">
                <el-form-item label="教师姓名：" prop="tname">
                    <el-input v-model="teacherInfo.tname" minlength="1" maxlength="10"></el-input>
                </el-form-item>
                <el-form-item label="性别：" prop="tgender">
                    <el-radio-group v-model="teacherInfo.tgender">
                        <el-radio :label='0'>男</el-radio>
                        <el-radio :label='1'>女</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="院系：">
                    <el-select placeholder="请选择" v-model="teacherInfo.tdeptId">
                        <el-option v-for="c in deptList" :key="c.id" :label="c.name" :value="c.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="专业：">
                    <el-select placeholder="请选择" v-model="teacherInfo.tmajorId" no-data-text="请先选择院系">
                        <template v-for="c in majorList">
                            <el-option v-if="c.deptId === teacherInfo.tdeptId" :key="c.id" :label="c.name" :value="c.id">
                            </el-option>
                        </template>
                    </el-select>
                </el-form-item>

            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="updateTeacher"> 确认 </el-button>
                </span>
            </template>
        </el-dialog>
        <!-- 抽屉 -->
        <el-drawer v-model="visibleDrawer" title="添加教师" direction="rtl" size="30%">
            <!-- 添加文章表单 -->
            <el-form :model="teacherInfo" label-width="100px" :rules="rules">
                <el-form-item label="姓名：" prop="tname">
                    <el-input v-model=" teacherInfo.tname " placeholder="请输入教师姓名"></el-input>
                </el-form-item>
                <el-form-item label="教工号：" prop="tno">
                    <el-input v-model=" teacherInfo.tno " placeholder="请输入教工号"></el-input>
                </el-form-item>
                <el-form-item label="职称：" prop="tjob">
                    <el-input v-model=" teacherInfo.tjob " placeholder="请输入职称"></el-input>
                </el-form-item>
                <el-form-item label="性别：" prop="tgender">
                    <el-radio-group v-model="teacherInfo.tgender">
                        <el-radio :label='0'>男</el-radio>
                        <el-radio :label='1'>女</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="院系：">
                    <el-select placeholder="请选择" v-model=" teacherInfo.tdeptId ">
                        <el-option v-for=" c  in  deptList " :key=" c.id " :label=" c.name " :value=" c.id ">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="专业：">
                    <el-select placeholder="请选择" v-model=" teacherInfo.tmajorId " no-data-text="请先选择院系">
                        <template v-for=" c  in  majorList ">
                            <el-option v-if=" c.deptId === teacherInfo.tdeptId " :key=" c.id " :label=" c.name " :value=" c.id ">
                            </el-option>
                        </template>
                    </el-select>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click=" addTeacher ">确认</el-button>
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
·