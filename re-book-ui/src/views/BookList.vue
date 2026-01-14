<template>
  <div class="book-list-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <div class="left-tools">
            <span>书籍库存管理</span>
            <el-input
                v-model="searchQuery"
                placeholder="输入书名搜索..."
                style="width: 200px; margin-left: 20px"
                clearable
                @clear="loadBooks"
                @keyup.enter="handleSearch"
            />
            <el-button type="primary" style="margin-left: 10px" @click="handleSearch">搜索</el-button>
          </div>
          <el-button type="primary" @click="hanleAdd">新增书籍</el-button>
        </div>
      </template>
      <el-table :data="tableData" border style = "width:100%">
        <el-table-column prop="title" label="书名" />
        <el-table-column prop="author" label="作者" width="150" />
        <el-table-column prop="price" label="价格" width="120" >
           <template #default="scope">
             <span class="price-tag">¥{{ scope.row.price }}</span>
           </template>
        </el-table-column>
        <el-table-column label="封面" width="100">
          <template #default="scope">
            <el-image
                style="width: 50px; height: 70px; border-radius: 4px"
                :src="scope.row.coverUrl"
                :preview-src-list="[scope.row.coverUrl]"
                fit="cover"
                preview-teleported
            >
              <template #error>
                <div style="background: #f5f7fa; height: 100%; display: flex; align-items: center; justify-content: center; font-size: 12px; color: #909399;">
                  无图
                </div>
              </template>
            </el-image>
          </template>
        </el-table-column>
        <el-table-column prop="stock" label="当前库存" width="120" />
        <el-table-column  label="操作" width="150" >
          <template #default="scope">
            <el-button size="small" type="primary" @click="handleEdit(scope.row)">修改</el-button>
            <el-button size="small" type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
  <el-dialog v-model="dialogVisible" :title="isEdit ? '修改书籍信息' : '新增旧书信息'" width='500px'>
    <el-form :model="bookForm" label-width="80px">
      <el-form-item label="书名">
        <el-input v-model="bookForm.title" placeholder="请输入书名 " />
      </el-form-item>
      <el-form-item label="作者">
        <el-input v-model="bookForm.author" placeholder="请输入作者 " />
      </el-form-item>
      <el-form-item label="售价">
        <el-input v-model="bookForm.price" :precision="2" :step="0.1" />
      </el-form-item>
      <el-form-item label="库存量">
        <el-input-number v-model="bookForm.stock" :min="1" />
      </el-form-item>
      <el-form-item label="封面链接">
        <el-input v-model="bookForm.coverUrl" placeholder="请输入图片网络地址" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="saveBook">确认提交</el-button>
    </template>
  </el-dialog>
</template>
<script setup >
import { ref, onMounted } from 'vue'
import request from '../utils/request'
import {ElMessage,ElMessageBox } from "element-plus";
const searchQuery = ref('')
const isEdit = ref(false)
const tableData = ref([])
const dialogVisible = ref(false)
const bookForm = ref({
  title:'',
  author:'',
  price:'',
  stock:1,
  status:1,
  coverUrl: ''
})
const handleSearch = async () => {
  try {
    const res = await request.get('/book/search',{
      params:{ title: searchQuery.value }
      })
    tableData.value = res
    } catch (error) {
    console.error('搜索失败',error)
  }
}
const handleEdit = (row) => {
  isEdit.value = true
  dialogVisible.value = true
  bookForm.value = { ...row }
}
const handleDelete = (id) => {
  ElMessageBox.confirm('确定要删除这本旧书吗？数据删除后无法恢复！','警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  }).then(async () => {
    try {
      await request.delete(`/book/delete/${id}`)

      ElMessage.success('删除成功')
      loadBooks()
    }catch (error){
      console.error('删除失败',error)
    }
  }).catch(() => {})
}
const hanleAdd = () => {
  isEdit.value =false
  bookForm.value ={
    title:'',
    author:'',
    price:'',
    stock:1,
    status:1
  }
  dialogVisible.value = true
}
const saveBook = async () => {
  try {
    if (isEdit.value){
      await request.put('/book/update', bookForm.value)
    }else {
      await request.post('/book/add', bookForm.value)
    }

    ElMessage.success(isEdit.value ? '修改成功' : '新增成功')
    dialogVisible.value = false
    loadBooks()
  } catch (error)
  {
    console.error('保存失败', error)
  }
}

const loadBooks = async () =>{
  try {
    const res = await request.get('/book/list')
    tableData.value = res

  } catch (error) {
    console.error('获取书籍失败', error)
  }

}

onMounted(() => {
  loadBooks()
})

</script>

<style scoped>

.book-list-container {
  padding: 20px;
}


.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}


.card-header span {
  font-weight: bold;
  font-size: 18px;
  color: #303133;
}


.price-tag {
  color: #f56c6c; /* 红色，代表钱 */
  font-weight: bold;
  font-family: 'Courier New', Courier, monospace; /* 让数字看起来更整齐 */
}


.el-table {
  margin-top: 10px;
}
</style>