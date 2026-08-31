<template>
  <div class="shelf-view">
    <div class="shelf-toolbar">
      <el-button type="primary" @click="loadMangaList" :loading="loading">
        <el-icon><Refresh /></el-icon>
        刷新
      </el-button>
      <el-button type="success" @click="$router.push('/download')">
        <el-icon><Plus /></el-icon>
        添加漫画
      </el-button>
    </div>

    <el-empty v-if="!loading && mangaList.length === 0" description="书架为空，去添加漫画吧" />

    <div class="manga-grid" v-else>
      <MangaCard
        v-for="manga in mangaList"
        :key="manga.mangaId"
        :manga="manga"
        @click="goToReader"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getMangaList, type MangaVO } from '@/api/manga'
import MangaCard from '@/components/MangaCard.vue'

const router = useRouter()
const mangaList = ref<MangaVO[]>([])
const loading = ref(false)

async function loadMangaList() {
  loading.value = true
  try {
    const res = await getMangaList()
    mangaList.value = res.data
  } catch (e) {
    console.error('加载书架失败', e)
  } finally {
    loading.value = false
  }
}

function goToReader(manga: MangaVO) {
  if (manga.mangaStatusCode === 2) {
    router.push(`/reader/${manga.mangaId}`)
  }
}

onMounted(() => {
  loadMangaList()
})
</script>

<style scoped>
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
</style>
