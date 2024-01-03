<script setup>
import { ref } from 'vue'
import { getDeptListService, updateDeptService, addDeptService, deleteDeptService } from '@/api/admin.js'
import {
    Edit,
    Delete
} from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'


const deptList = ref([])
const loading = ref(false)
const deptInfo = ref({})
const dialogVisible1 = ref(false)
const dialogVisible2 = ref(false)


const getDepts = async () => {
    loading.value = true
    let res = await getDeptListService();
    deptList.value = res.data
    loading.value = false
}

const showEditDialog = (data) => {
    deptInfo.value = data;
    dialogVisible1.value = true;
}

const updateDept = async () => {
    await updateDeptService(deptInfo.value);
    ElMessage.success("修改成功")
    dialogVisible1.value = false
    getDepts()
}

const addDept = async () => {
    await addDeptService(deptInfo.value);
    ElMessage.success('添加成功')
    dialogVisible2.value = false
    getDepts()
}
import { ElMessageBox } from 'element-plus'

const deleteDept = async (id) => {
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
        await deleteDeptService(id);
        ElMessage({
            type: 'success',
            message: '删除成功',
        })
        //刷新列表
        getDepts();
    }).catch(() => {
        ElMessage({
            type: 'info',
            message: '取消了删除',
        })
    })
}

const clearData = () => {
    deptInfo.value = {}
}
getDepts()
</script>

<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>院系管理</span>
                <div class="extra">
                    <el-button type="primary" @click="dialogVisible2 = true; title = '添加院系'; clearData()">添加院系</el-button>
                </div>
            </div>
        </template>

        <el-table :data="deptList" style="width: 100%" v-loading="loading">
            <el-table-column label="ID" width="400" prop="id"></el-table-column>
            <el-table-column label="院系名称" prop="name"></el-table-column>

            <!-- <el-table-column label="状态" prop="state"></el-table-column> -->
            <el-table-column label="操作" width="100">
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary" @click="showEditDialog(row)"></el-button>
                    <el-button :icon="Delete" circle plain type="danger" @click="deleteDept(row.id)"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>

        <el-dialog v-model="dialogVisible1" title="修改院系信息" width="30%">
            <el-form :model="deptInfo" :rules="rules" label-width="100px" style="padding-right: 30px">
                <el-form-item label="院系名称" prop="name">
                    <el-input v-model="deptInfo.name" minlength="1" maxlength="10"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible1 = false">取消</el-button>
                    <el-button type="primary" @click="updateDept"> 确认 </el-button>
                </span>
            </template>
        </el-dialog>
        <el-dialog v-model="dialogVisible2" title="添加院系信息" width="30%">
            <el-form :model="deptInfo" :rules="rules" label-width="100px" style="padding-right: 30px">
                <el-form-item label="院系名称" prop="name">
                    <el-input v-model="deptInfo.name" minlength="1" maxlength="10"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible2 = false">取消</el-button>
                    <el-button type="primary" @click="addDept"> 确认 </el-button>
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