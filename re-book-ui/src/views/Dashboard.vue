<template>
  <div class="dashboard-container">
    <el-row :gutter="20">
      <el-col :span="6" v-for="item in stats" :key="item.title">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-content">
            <div class="stat-info">
              <div class="stat-title">{{ item.title }}</div>
              <div class="stat-number">{{ item.value }}</div>
            </div>
            <el-icon :size="40" :color="item.color">
              <component :is="item.icon" />
            </el-icon>
          </div>
          <div class="stat-footer">
            <span>较昨日 </span>
            <span :class="item.growth > 0 ? 'up' : 'down'">
              {{ item.growth > 0 ? '+' : '' }}{{ item.growth }}%
            </span>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="16">
        <el-card shadow="never" header="回收走势图 (近7天)">
          <div class="chart-placeholder">
            <p>图表加载中... (待集成 ECharts)</p>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="never" header="分类占比">
          <div class="chart-placeholder mini">
            <p>饼图占位</p>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref } from 'vue'

// 模拟看板数据
const stats = ref([
  { title: '今日回收量', value: '128', icon: 'Reading', color: '#409EFF', growth: 12 },
  { title: '待审核书籍', value: '45', icon: 'Stamp', color: '#E6A23C', growth: -5 },
  { title: '累计回收金额', value: '¥12,450', icon: 'Money', color: '#67C23A', growth: 8 },
  { title: '活跃用户', value: '1,204', icon: 'User', color: '#F56C6C', growth: 20 }
])
</script>

<style scoped>
.stat-card {
  border: none;
  border-radius: 8px;
}
.stat-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.stat-title {
  color: #909399;
  font-size: 14px;
}
.stat-number {
  font-size: 24px;
  font-weight: bold;
  margin-top: 8px;
  color: #303133;
}
.stat-footer {
  margin-top: 15px;
  padding-top: 15px;
  border-top: 1px solid #f2f6fc;
  font-size: 12px;
  color: #999;
}
.up { color: #f56c6c; font-weight: bold; }
.down { color: #67c23a; font-weight: bold; }

.chart-placeholder {
  height: 300px;
  background: #f9fafc;
  display: flex;
  justify-content: center;
  align-items: center;
  color: #909399;
  border: 1px dashed #dcdfe6;
}
.mini { height: 300px; }
</style>