<template>
  <div class="folder-sidebar">
    <div class="sidebar-header">
      <span class="sidebar-title">我的书架</span>
      <el-button type="primary" link size="small" @click="$emit('createFolder')">
        <el-icon><Plus /></el-icon>
      </el-button>
    </div>

    <div class="folder-list">
      <!-- 全部 -->
      <div
        class="folder-item"
        :class="{ active: currentFolderId === null }"
        @click="$emit('select', null)"
      >
        <el-icon><Grid /></el-icon>
        <span class="folder-name">全部漫画</span>
      </div>

      <!-- 未分类 -->
      <div
        class="folder-item"
        :class="{ active: currentFolderId === -1 }"
        @click="$emit('select', -1)"
      >
        <el-icon><QuestionFilled /></el-icon>
        <span class="folder-name">未分类</span>
      </div>

      <el-divider style="margin: 8px 0" />

      <!-- 自定义文件夹 -->
      <div
        v-for="folder in folders"
        :key="folder.folderId"
        class="folder-item"
        :class="{ active: currentFolderId === folder.folderId }"
        @click="$emit('select', folder.folderId)"
      >
        <el-icon><Folder /></el-icon>
        <span class="folder-name">{{ folder.folderName }}</span>
        <span class="folder-count">{{ folder.mangaCount }}</span>
        <el-dropdown trigger="click" @command="(cmd: string) => $emit('folderAction', folder.folderId, cmd)" @click.stop>
          <el-icon class="folder-more" @click.stop><MoreFilled /></el-icon>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="rename">重命名</el-dropdown-item>
              <el-dropdown-item command="delete" divided>删除</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { ShelfFolderVO } from '@/api/shelf'

defineProps<{
  folders: ShelfFolderVO[]
  currentFolderId: number | null
}>()

defineEmits<{
  select: [folderId: number | null]
  createFolder: []
  folderAction: [folderId: number, action: string]
}>()
</script>

<style scoped>
.folder-sidebar {
  width: 200px;
  min-width: 200px;
  background: #fff;
  border-radius: 8px;
  padding: 16px 0;
  margin-right: 16px;
  height: fit-content;
  position: sticky;
  top: 84px;
}

.sidebar-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 16px 12px;
}

.sidebar-title {
  font-size: 15px;
  font-weight: 600;
  color: #333;
}

.folder-list {
  display: flex;
  flex-direction: column;
  gap: 2px;
}

.folder-item {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 16px;
  cursor: pointer;
  transition: background 0.2s;
  font-size: 14px;
  color: #555;
  position: relative;
}

.folder-item:hover {
  background: #f5f7fa;
}

.folder-item.active {
  background: #ecf5ff;
  color: #409eff;
  font-weight: 500;
}

.folder-name {
  flex: 1;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.folder-count {
  font-size: 12px;
  color: #999;
  background: #f0f0f0;
  padding: 1px 6px;
  border-radius: 10px;
}

.folder-more {
  opacity: 0;
  transition: opacity 0.2s;
  cursor: pointer;
  color: #999;
}

.folder-item:hover .folder-more {
  opacity: 1;
}
</style>
