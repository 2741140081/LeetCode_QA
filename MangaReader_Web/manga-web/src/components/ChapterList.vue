<template>
  <div class="chapter-sidebar" :class="{ collapsed: !visible }">
    <div class="sidebar-toggle" @click="$emit('update:visible', !visible)">
      <el-icon :size="16">
        <component :is="visible ? 'DArrowLeft' : 'DArrowRight'" />
      </el-icon>
    </div>
    <div class="sidebar-content" v-show="visible">
      <div class="sidebar-header">
        <span class="sidebar-title">章节目录</span>
        <span class="chapter-count">共 {{ chapters.length }} 话</span>
      </div>
      <div class="chapter-list">
        <div
          v-for="chapter in chapters"
          :key="chapter.chapterId"
          class="chapter-item"
          :class="{ active: chapter.chapterId === currentChapterId }"
          @click="$emit('select', chapter)"
        >
          <span class="chapter-num">第{{ chapter.chapterNum }}话</span>
          <span class="chapter-title">{{ chapter.title }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { Chapter } from '@/api/chapter'

defineProps<{
  visible: boolean
  chapters: Chapter[]
  currentChapterId: number | null
}>()

defineEmits<{
  'update:visible': [value: boolean]
  select: [chapter: Chapter]
}>()
</script>

<style scoped>
.chapter-sidebar {
  width: 260px;
  min-width: 260px;
  background: #fff;
  border-right: 1px solid #eee;
  display: flex;
  flex-direction: row;
  position: relative;
  transition: width 0.3s, min-width 0.3s;
  overflow: hidden;
}

.chapter-sidebar.collapsed {
  width: 0;
  min-width: 0;
  border-right: none;
}

.sidebar-toggle {
  position: absolute;
  right: -24px;
  top: 50%;
  transform: translateY(-50%);
  width: 24px;
  height: 48px;
  background: #fff;
  border: 1px solid #eee;
  border-left: none;
  border-radius: 0 6px 6px 0;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  z-index: 10;
  color: #666;
  transition: color 0.2s;
}

.sidebar-toggle:hover {
  color: #409eff;
}

.sidebar-content {
  width: 260px;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.sidebar-header {
  padding: 16px;
  border-bottom: 1px solid #f0f0f0;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.sidebar-title {
  font-size: 15px;
  font-weight: 600;
  color: #333;
}

.chapter-count {
  font-size: 12px;
  color: #999;
}

.chapter-list {
  flex: 1;
  overflow-y: auto;
  padding: 8px;
  display: flex;
  flex-direction: column;
  gap: 2px;
}

.chapter-item {
  padding: 10px 12px;
  border-radius: 6px;
  cursor: pointer;
  transition: background 0.2s;
  display: flex;
  gap: 8px;
}

.chapter-item:hover {
  background: #f5f7fa;
}

.chapter-item.active {
  background: #ecf5ff;
  color: #409eff;
  font-weight: 500;
}

.chapter-num {
  white-space: nowrap;
  font-weight: 500;
}

.chapter-title {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  color: #666;
}

.chapter-item.active .chapter-title {
  color: #409eff;
}
</style>
