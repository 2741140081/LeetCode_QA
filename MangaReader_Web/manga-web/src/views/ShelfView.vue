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
              @click="goToReader"
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
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useShelfStore } from '@/stores/shelf'
import { ElMessage, ElMessageBox } from 'element-plus'
import MangaCard from '@/components/MangaCard.vue'
import FolderSidebar from '@/components/FolderSidebar.vue'
import FolderDialog from '@/components/FolderDialog.vue'
import type { ShelfMangaVO } from '@/api/shelf'

const router = useRouter()
const shelfStore = useShelfStore()

// 文件夹弹窗状态
const folderDialogVisible = ref(false)
const folderDialogIsEdit = ref(false)
const folderDialogInitial = ref('')
const folderDialogEditId = ref<number | null>(null)
const folderDialogRef = ref<InstanceType<typeof FolderDialog>>()

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
</style>
