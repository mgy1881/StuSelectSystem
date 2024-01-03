<script setup>
import { ref } from 'vue'
import { getMajorListService, getDeptListService, updateMajorService, addMajorService, deleteMajorService } from '@/api/admin.js'
import {
    Edit,
    Delete
} from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'


const majorList = ref([])
const deptList = ref([])
const loading = ref(false)
const majorInfo = ref({})
const dialogVisible1 = ref(false)
const dialogVisible2 = ref(false)


const getDepts = async () => {
    let res = await getDeptListService();
    deptList.value = res.data
}

const getMajors = async () => {
    loading.value = true
    let res = await getMajorListService();
    majorList.value = res.data
    for (let i = 0; i < majorList.value.length; ++i) {
        let major = majorList.value[i]
        for (let j = 0; j < deptList.value.length; ++j) {
            if (major.deptId == deptList.value[j].id) {
                major.deptName = deptList.value[j].name;
            }
        }
    }
    loading.value = false
}

const showEditDialog = (data) => {
    majorInfo.value = data;
    dialogVisible1.value = true;
}

const updateMajor = async () => {
    await updateMajorService(majorInfo.value);
    ElMessage.success("修改成功")
    dialogVisible1.value = false
    getMajors()
}

const addMajor = async () => {
    await addMajorService(majorInfo.value);
    ElMessage.success('添加成功')
    dialogVisible2.value = false
    getMajors()
}
import { ElMessageBox } from 'element-plus'

const deleteMajor = async (id) => {
    ElMessageBox.confirm(
        '你确认要删除该院系吗?',
        '温馨提示',
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
        }
    ).then(async () => {
        //调用接口
        await deleteMajorService(id);
        ElMessage({
            type: 'success',
            message: '删除成功',
        })
        //刷新列表
        getMajors();
    }).catch(() => {
        ElMessage({
            type: 'info',
            message: '取消了删除',
        })
    })
}

const clearData = () => {
    majorInfo.value = {}
}
getDepts();
getMajors();
</script>

<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>专业管理</span>
                <div class="extra">
                    <el-button type="primary" @click="dialogVisible2 = true; title = '添加专业'; clearData()">添加专业</el-button>
                </div>
            </div>
        </template>

        <el-table :data="majorList" style="width: 100%" v-loading="loading">
            <el-table-column label="ID" width="400" prop="id"></el-table-column>
            <el-table-column label="专业名称" prop="name"></el-table-column>
            <el-table-column label="所属院系" prop="deptName"></el-table-column>

            <!-- <el-table-column label="状态" prop="state"></el-table-column> -->
            <el-table-column label="操作" width="100">
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary" @click="showEditDialog(row)"></el-button>
                    <el-button :icon="Delete" circle plain type="danger" @click="deleteMajor(row.id)"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>

        <el-dialog v-model="dialogVisible1" title="修改专业信息" width="30%">
            <el-form :model="majorInfo" :rules="rules" label-width="100px" style="padding-right: 30px">
                <el-form-item label="专业名称" prop="name">
                    <el-input v-model="majorInfo.name" minlength="1" maxlength="10"></el-input>
                </el-form-item>
                <el-form-item label="院系：">
                    <el-select placeholder="请选择" v-model="majorInfo.deptId">
                        <el-option v-for="c in deptList" :key="c.id" :label="c.name" :value="c.id">
                        </el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible1 = false">取消</el-button>
                    <el-button type="primary" @click="updateMajor"> 确认 </el-button>
                </span>
            </template>
        </el-dialog>
        <el-dialog v-model="dialogVisible2" title="添加专业信息" width="30%">
            <el-form :model="majorInfo" :rules="rules" label-width="100px" style="padding-right: 30px">
                <el-form-item label="专业名称" prop="name">
                    <el-input v-model="majorInfo.name" minlength="1" maxlength="10"></el-input>
                </el-form-item>
                <el-form-item label="院系：">
                    <el-select placeholder="请选择" v-model="majorInfo.deptId">
                        <el-option v-for="c in deptList" :key="c.id" :label="c.name" :value="c.id">
                        </el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible2 = false">取消</el-button>
                    <el-button type="primary" @click="addMajor"> 确认 </el-button>
                </span>
            </template>
        </el-dialog>
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