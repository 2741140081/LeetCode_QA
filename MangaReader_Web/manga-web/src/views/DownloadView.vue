<template>
  <div class="download-view">
    <!-- 新建任务 -->
    <el-card class="add-task-card">
      <template #header>
        <span>添加漫画下载任务</span>
      </template>
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="漫画名称" prop="mangaName">
          <el-input v-model="form.mangaName" placeholder="请输入漫画名称" />
        </el-form-item>
        <el-form-item label="漫画网址" prop="mangaUrl">
          <el-input v-model="form.mangaUrl" placeholder="请输入漫画目录网址" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitTask" :loading="submitting">
            提交下载
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 图片下载统计 -->
    <el-row :gutter="16" class="stats-row">
      <el-col :span="6">
        <el-card shadow="hover">
          <el-statistic title="待下载" :value="stats.pending" />
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <el-statistic title="下载中" :value="stats.downloading" />
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <el-statistic title="已完成" :value="stats.completed" />
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <el-statistic title="成功率" :value="stats.successRate" suffix="%" />
        </el-card>
      </el-col>
    </el-row>

    <!-- 任务列表 -->
    <el-card>
      <template #header>
        <div class="task-header">
          <span>下载任务</span>
          <el-button @click="loadTaskList" :loading="loadingTasks">
            <el-icon><Refresh /></el-icon>
            刷新
          </el-button>
        </div>
      </template>
      <el-table :data="taskList" stripe>
        <el-table-column prop="mangaName" label="漫画名称" min-width="200" />
        <el-table-column label="状态" width="120">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.mangaStatusCode)">
              {{ row.mangaStatusDesc }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="进度" width="200">
          <template #default="{ row }">
            <el-progress
              :percentage="getProgress(row)"
              :status="row.mangaStatusCode === 2 ? 'success' : row.mangaStatusCode === 3 ? 'exception' : undefined"
            />
            <span class="progress-text">
              {{ row.processedChapters || 0 }}/{{ row.totalChapters || 0 }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="updatedAt" label="最后更新" width="180" />
      </el-table>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, onBeforeUnmount } from 'vue'
import { ElMessage } from 'element-plus'
import type { FormInstance } from 'element-plus'
import { addManga } from '@/api/manga'
import { getDownloadList, getImageStats, createSSEConnection } from '@/api/download'
import type { MangaTaskVO, DownloadStatsVO } from '@/api/download'

const formRef = ref<FormInstance>()
const form = reactive({
  mangaName: '',
  mangaUrl: '',
})
const rules = {
  mangaName: [{ required: true, message: '请输入漫画名称', trigger: 'blur' }],
  mangaUrl: [{ required: true, message: '请输入漫画网址', trigger: 'blur' }],
}
const submitting = ref(false)
const loadingTasks = ref(false)
const taskList = ref<MangaTaskVO[]>([])
const stats = reactive<DownloadStatsVO>({
  total: 0, pending: 0, downloading: 0, completed: 0, failed: 0, successRate: 100,
})
let sseConnection: EventSource | null = null

async function submitTask() {
  if (!formRef.value) return
  await formRef.value.validate()
  submitting.value = true
  try {
    const res = await addManga({ mangaName: form.mangaName, mangaUrl: form.mangaUrl })
    ElMessage.success(res.message || '漫画已添加到下载队列')
    form.mangaName = ''
    form.mangaUrl = ''
    loadTaskList()
  } catch (e) {
    // 错误已被拦截器处理
  } finally {
    submitting.value = false
  }
}

async function loadTaskList() {
  loadingTasks.value = true
  try {
    const res = await getDownloadList()
    taskList.value = res.data
  } catch (e) {
    console.error('加载任务列表失败', e)
  } finally {
    loadingTasks.value = false
  }
}

async function loadStats() {
  try {
    const res = await getImageStats()
    Object.assign(stats, res.data)
  } catch (e) {
    console.error('加载统计失败', e)
  }
}

function getStatusType(code: number): string {
  switch (code) {
    case 0: return 'info'
    case 1: return 'warning'
    case 2: return 'success'
    case 3: return 'danger'
    default: return 'info'
  }
}

function getProgress(row: MangaTaskVO): number {
  if (!row.totalChapters || row.totalChapters === 0) return 0
  return Math.round(((row.processedChapters || 0) / row.totalChapters) * 100)
}

function initSSE() {
  sseConnection = createSSEConnection(
    (data) => {
      // 更新任务进度
      const task = taskList.value.find(t => t.mangaId === data.mangaId)
      if (task) {
        task.processedChapters = data.processedChapters
        task.totalChapters = data.totalChapters
        task.mangaStatusCode = data.statusCode
        task.mangaStatusDesc = data.statusDesc
      }
    },
    (data) => {
      // 更新任务状态
      const task = taskList.value.find(t => t.mangaId === data.mangaId)
      if (task) {
        task.mangaStatusCode = data.statusCode
        task.mangaStatusDesc = data.statusDesc
      }
    },
    (data) => {
      // 更新统计
      Object.assign(stats, data)
    }
  )
}

onMounted(() => {
  loadTaskList()
  loadStats()
  initSSE()
})

onBeforeUnmount(() => {
  sseConnection?.close()
})
</script>

<style scoped>
.download-view {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.add-task-card {
  max-width: 600px;
}

.stats-row {
  margin-bottom: 0;
}

.task-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.progress-text {
  font-size: 12px;
  color: #999;
  margin-left: 8px;
}
</style>
