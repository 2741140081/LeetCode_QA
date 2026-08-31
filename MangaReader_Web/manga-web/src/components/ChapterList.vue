<template>
  <el-drawer
    :model-value="visible"
    @update:model-value="$emit('update:visible', $event)"
    title="章节目录"
    direction="ltr"
    size="300px"
  >
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
  </el-drawer>
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
.chapter-list {
  display: flex;
  flex-direction: column;
  gap: 4px;
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
  background: #f0f7ff;
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
