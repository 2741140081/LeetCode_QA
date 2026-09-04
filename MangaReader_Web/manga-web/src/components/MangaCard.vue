<template>
  <div class="manga-card" @click="$emit('click', manga)">
    <div class="cover-wrapper">
      <img
        :src="manga.coverUrl || defaultCover"
        :alt="manga.mangaName"
        loading="lazy"
        class="cover-img"
        @error="onImageError"
      />
      <el-tag
        :type="statusType"
        size="small"
        class="status-tag"
      >
        {{ manga.mangaStatusDesc }}
      </el-tag>
    </div>
    <div class="card-info">
      <h3 class="manga-title">{{ manga.mangaName }}</h3>
      <div class="progress-info" v-if="manga.totalChapters">
        {{ manga.processedChapters }}/{{ manga.totalChapters }} 章
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { MangaVO } from '@/api/manga'
import type { ShelfMangaVO } from '@/api/shelf'
import defaultCover from '@/assets/default_cover.png'

const props = defineProps<{
  manga: MangaVO | ShelfMangaVO
}>()

defineEmits<{
  click: [manga: MangaVO | ShelfMangaVO]
}>()

const statusType = computed(() => {
  switch (props.manga.mangaStatusCode) {
    case 0: return 'info'
    case 1: return 'warning'
    case 2: return 'success'
    case 3: return 'danger'
    default: return 'info'
  }
})

function onImageError(e: Event) {
  const img = e.target as HTMLImageElement
  img.src = defaultCover
}

import { computed } from 'vue'
</script>

<style scoped>
.manga-card {
  cursor: pointer;
  border-radius: 8px;
  overflow: hidden;
  background: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s, box-shadow 0.2s;
}

.manga-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.15);
}

.cover-wrapper {
  position: relative;
  width: 100%;
  padding-top: 133%; /* 3:4 比例 */
  overflow: hidden;
  background: #f0f0f0;
}

.cover-img {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.status-tag {
  position: absolute;
  top: 8px;
  right: 8px;
}

.card-info {
  padding: 8px 12px;
}

.manga-title {
  font-size: 14px;
  font-weight: 500;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.progress-info {
  font-size: 12px;
  color: #999;
  margin-top: 4px;
}
</style>
