<template>
  <div class="shelf-view">
    <div class="shelf-layout">
      <!-- 左侧文件夹侧边栏 -->
      <FolderSidebar
        :folders="shelfStore.folders"
        :current-folder-id="shelfStore.currentFolderId"
        @select="onFolderSelect"
        @create-folder="openCreateDialog"
        @folder-action="onFolderAction"
      />

      <!-- 右侧漫画网格 -->
      <div class="shelf-main">
        <div class="shelf-toolbar">
          <el-button type="primary" @click="refreshShelf" :loading="shelfStore.loading">
            <el-icon><Refresh /></el-icon>
            刷新
          </el-button>
          <el-button type="success" @click="$router.push('/download')">
            <el-icon><Plus /></el-icon>
            添加漫画
          </el-button>
          <el-button
            :type="isManageMode ? 'warning' : 'default'"
            @click="toggleManageMode"
          >
            <el-icon><Setting /></el-icon>
            {{ isManageMode ? '完成管理' : '管理' }}
          </el-button>
        </div>

        <!-- 管理模式底部操作栏 -->
        <div v-if="isManageMode" class="manage-bar">
          <el-checkbox
            :model-value="isAllSelected"
            @change="toggleSelectAll"
          >
            全选
          </el-checkbox>
          <span class="selected-count">已选 {{ selectedMangaIds.length }} 项</span>
          <el-button
            type="danger"
            size="small"
            :disabled="selectedMangaIds.length === 0"
            :loading="deleting"
            @click="handleBatchDelete"
          >
            <el-icon><Delete /></el-icon>
            删除
          </el-button>
        </div>

        <el-empty v-if="!shelfStore.loading && shelfStore.mangas.length === 0" description="书架为空，去添加漫画吧" />

        <div class="manga-grid" v-else>
          <div
            v-for="manga in shelfStore.mangas"
            :key="manga.mangaId"
            class="manga-card-wrapper"
          >
            <MangaCard
              :manga="manga"
              :selectable="isManageMode"
              :selected="selectedMangaIds.includes(manga.mangaId)"
              @click="goToReader"
              @select="onMangaSelect"
            />
            <el-dropdown trigger="click" class="card-menu" @command="(cmd: string) => onMangaAction(manga, cmd)">
              <el-button size="small" circle>
                <el-icon><MoreFilled /></el-icon>
              </el-button>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item
                    v-for="folder in shelfStore.folders"
                    :key="folder.folderId"
                    :command="'move:' + folder.folderId"
                  >
                    移动到 {{ folder.folderName }}
                  </el-dropdown-item>
                  <el-dropdown-item command="move:null">移至未分类</el-dropdown-item>
                  <el-dropdown-item command="remove" divided>从书架移除</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </div>
      </div>
    </div>

    <!-- 文件夹弹窗 -->
    <FolderDialog
      v-model:visible="folderDialogVisible"
      :is-edit="folderDialogIsEdit"
      :initial-value="folderDialogInitial"
      @submit="onFolderDialogSubmit"
      ref="folderDialogRef"
    />
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useShelfStore } from '@/stores/shelf'
import { ElMessage, ElMessageBox } from 'element-plus'
import MangaCard from '@/components/MangaCard.vue'
import FolderSidebar from '@/components/FolderSidebar.vue'
import FolderDialog from '@/components/FolderDialog.vue'
import type { ShelfMangaVO } from '@/api/shelf'
import { batchDeleteMangas } from '@/api/manga'

const router = useRouter()
const shelfStore = useShelfStore()

// 文件夹弹窗状态
const folderDialogVisible = ref(false)
const folderDialogIsEdit = ref(false)
const folderDialogInitial = ref('')
const folderDialogEditId = ref<number | null>(null)
const folderDialogRef = ref<InstanceType<typeof FolderDialog>>()

// 管理模式状态
const isManageMode = ref(false)
const selectedMangaIds = ref<number[]>([])
const deleting = ref(false)

const isAllSelected = computed(() => {
  if (shelfStore.mangas.length === 0) return false
  return shelfStore.mangas.every(m => selectedMangaIds.value.includes(m.mangaId))
})

function refreshShelf() {
  shelfStore.loadFolders()
  shelfStore.loadMangas()
}

function onFolderSelect(folderId: number | null) {
  // -1 表示未分类
  if (folderId === -1) {
    shelfStore.selectFolder(null, true)
  } else {
    shelfStore.selectFolder(folderId, false)
  }
}

function openCreateDialog() {
  folderDialogIsEdit.value = false
  folderDialogInitial.value = ''
  folderDialogEditId.value = null
  folderDialogVisible.value = true
}

function onFolderAction(folderId: number, action: string) {
  if (action === 'rename') {
    const folder = shelfStore.folders.find(f => f.folderId === folderId)
    folderDialogIsEdit.value = true
    folderDialogInitial.value = folder?.folderName || ''
    folderDialogEditId.value = folderId
    folderDialogVisible.value = true
  } else if (action === 'delete') {
    ElMessageBox.confirm('删除文件夹后，其中的漫画将移至未分类。确定删除？', '提示', {
      type: 'warning',
    }).then(() => {
      shelfStore.deleteFolder(folderId)
      ElMessage.success('文件夹已删除')
    }).catch(() => {})
  }
}

async function onFolderDialogSubmit(name: string) {
  try {
    if (folderDialogIsEdit.value && folderDialogEditId.value) {
      await shelfStore.renameFolder(folderDialogEditId.value, name)
      ElMessage.success('重命名成功')
    } else {
      await shelfStore.createFolder(name)
      ElMessage.success('文件夹创建成功')
    }
    folderDialogVisible.value = false
  } catch {
    // 错误已由拦截器处理
  }
}

function goToReader(manga: any) {
  if (manga.mangaStatusCode === 2) {
    router.push(`/reader/${manga.mangaId}`)
  }
}

function toggleManageMode() {
  isManageMode.value = !isManageMode.value
  if (!isManageMode.value) {
    selectedMangaIds.value = []
  }
}

function onMangaSelect(manga: any) {
  const id = manga.mangaId
  const idx = selectedMangaIds.value.indexOf(id)
  if (idx >= 0) {
    selectedMangaIds.value.splice(idx, 1)
  } else {
    selectedMangaIds.value.push(id)
  }
}

function toggleSelectAll(val: boolean) {
  if (val) {
    selectedMangaIds.value = shelfStore.mangas.map(m => m.mangaId)
  } else {
    selectedMangaIds.value = []
  }
}

async function handleBatchDelete() {
  if (selectedMangaIds.value.length === 0) return

  const count = selectedMangaIds.value.length
  try {
    await ElMessageBox.confirm(
      `确定要删除选中的 ${count} 部漫画吗？此操作将同时删除本地文件，不可恢复！`,
      '确认删除',
      { type: 'warning', confirmButtonText: '删除', cancelButtonText: '取消' }
    )
  } catch {
    return
  }

  deleting.value = true
  try {
    const res = await batchDeleteMangas(selectedMangaIds.value)
    const data = res.data
    ElMessage.success(`删除完成，成功 ${data.success} 个，失败 ${data.failed} 个`)
    selectedMangaIds.value = []
    isManageMode.value = false
    refreshShelf()
  } catch {
    // 错误已由拦截器处理
  } finally {
    deleting.value = false
  }
}

function onMangaAction(manga: ShelfMangaVO, command: string) {
  if (command === 'remove') {
    ElMessageBox.confirm('确定从书架移除该漫画？', '提示', {
      type: 'warning',
    }).then(() => {
      shelfStore.removeManga(manga.mangaId)
      ElMessage.success('已从书架移除')
    }).catch(() => {})
  } else if (command.startsWith('move:')) {
    const folderIdStr = command.substring(5)
    const folderId = folderIdStr === 'null' ? null : Number(folderIdStr)
    shelfStore.moveManga(manga.mangaId, folderId)
    ElMessage.success('移动成功')
  }
}

onMounted(() => {
  refreshShelf()
})
</script>

<style scoped>
.shelf-layout {
  display: flex;
  align-items: flex-start;
}

.shelf-main {
  flex: 1;
  min-width: 0;
}

.shelf-toolbar {
  display: flex;
  gap: 12px;
  margin-bottom: 24px;
}

.manga-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(160px, 1fr));
  gap: 20px;
}

.manga-card-wrapper {
  position: relative;
}

.card-menu {
  position: absolute;
  top: 4px;
  right: 4px;
  opacity: 0;
  transition: opacity 0.2s;
}

.manga-card-wrapper:hover .card-menu {
  opacity: 1;
}

.manage-bar {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 12px 16px;
  margin-bottom: 16px;
  background: #f5f7fa;
  border-radius: 8px;
}

.selected-count {
  font-size: 14px;
  color: #666;
  flex: 1;
}
</style>
