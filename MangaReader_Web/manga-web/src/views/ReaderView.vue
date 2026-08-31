<template>
  <div class="reader-view">
    <!-- 顶部工具栏 -->
    <div class="reader-toolbar">
      <el-button @click="chapterListVisible = true">
        <el-icon><Menu /></el-icon>
        目录
      </el-button>
      <el-button @click="loadPrevChapter" :disabled="!chapterDetail?.prevChapterId">
        <el-icon><ArrowLeft /></el-icon>
        上一章
      </el-button>
      <span class="chapter-title-display">
        {{ chapterDetail?.title || '加载中...' }}
      </span>
      <el-button @click="loadNextChapter" :disabled="!chapterDetail?.nextChapterId">
        下一章
        <el-icon><ArrowRight /></el-icon>
      </el-button>
    </div>

    <!-- 主内容区 -->
    <div class="reader-content">
      <ComicScroller
        ref="scroller"
        :images="images"
        @page-change="onPageChange"
        @scroll-end="onScrollEnd"
      />
    </div>

    <!-- 底部状态栏 -->
    <div class="reader-footer">
      <span>第 {{ currentPage + 1 }} / {{ images.length }} 页</span>
      <AutoPlayBar
        :is-playing="isPlaying"
        :scroll-distance="scrollDistance"
        @toggle="toggleAutoplay"
        @speed-up="speedUp"
        @speed-down="speedDown"
      />
    </div>

    <!-- 章节目录抽屉 -->
    <ChapterList
      v-model:visible="chapterListVisible"
      :chapters="chapters"
      :current-chapter-id="currentChapterId"
      @select="onChapterSelect"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch, onBeforeUnmount } from 'vue'
import { useRoute } from 'vue-router'
import { getChapters, getChapterDetail, getChapterImages } from '@/api/chapter'
import type { Chapter, ChapterVO, ChapterImageVO } from '@/api/chapter'
import { getAutoplayConfig, type AutoplayConfig as AutoplayConfigType } from '@/api/config'
import ComicScroller from '@/components/ComicScroller.vue'
import AutoPlayBar from '@/components/AutoPlayBar.vue'
import ChapterList from '@/components/ChapterList.vue'

const route = useRoute()
const mangaId = Number(route.params.mangaId)

const chapters = ref<Chapter[]>([])
const images = ref<ChapterImageVO[]>([])
const chapterDetail = ref<ChapterVO | null>(null)
const currentChapterId = ref<number | null>(null)
const currentPage = ref(0)
const chapterListVisible = ref(false)
const scroller = ref<InstanceType<typeof ComicScroller>>()

// 自动播放状态
const isPlaying = ref(false)
const scrollDistance = ref(1)
const autoplayConfig = ref<AutoplayConfigType | null>(null)
let playTimer: ReturnType<typeof setInterval> | null = null

async function loadChapters() {
  try {
    const res = await getChapters(mangaId)
    chapters.value = res.data
    if (chapters.value.length > 0) {
      await loadChapter(chapters.value[0].chapterId)
    }
  } catch (e) {
    console.error('加载章节列表失败', e)
  }
}

async function loadChapter(chapterId: number) {
  try {
    const [detailRes, imagesRes] = await Promise.all([
      getChapterDetail(chapterId),
      getChapterImages(chapterId),
    ])
    chapterDetail.value = detailRes.data
    currentChapterId.value = chapterId
    images.value = imagesRes.data
    currentPage.value = 0
    scroller.value?.scrollToTop()
  } catch (e) {
    console.error('加载章节失败', e)
  }
}

async function loadPrevChapter() {
  if (chapterDetail.value?.prevChapterId) {
    await loadChapter(chapterDetail.value.prevChapterId)
  }
}

async function loadNextChapter() {
  if (chapterDetail.value?.nextChapterId) {
    await loadChapter(chapterDetail.value.nextChapterId)
  } else {
    stopAutoplay()
  }
}

function onChapterSelect(chapter: Chapter) {
  loadChapter(chapter.chapterId)
  chapterListVisible.value = false
}

function onPageChange(index: number) {
  currentPage.value = index
}

function onScrollEnd() {
  // 自动播放到底时切换下一章
  if (isPlaying.value) {
    loadNextChapter()
  }
}

// 自动播放控制
async function toggleAutoplay() {
  if (isPlaying.value) {
    stopAutoplay()
  } else {
    startAutoplay()
  }
}

function startAutoplay() {
  if (!autoplayConfig.value) {
    // 加载配置
    getAutoplayConfig().then((res) => {
      autoplayConfig.value = res.data
      scrollDistance.value = res.data.defaultScrollDistance
      doStartAutoplay()
    })
  } else {
    doStartAutoplay()
  }
}

function doStartAutoplay() {
  isPlaying.value = true
  playTimer = setInterval(() => {
    scroller.value?.scrollBy(scrollDistance.value)
  }, autoplayConfig.value?.defaultScrollInterval || 16)
}

function stopAutoplay() {
  isPlaying.value = false
  if (playTimer) {
    clearInterval(playTimer)
    playTimer = null
  }
}

function speedUp() {
  if (!autoplayConfig.value) return
  scrollDistance.value = Math.min(
    scrollDistance.value + autoplayConfig.value.scrollDistanceStep,
    autoplayConfig.value.maxScrollDistance
  )
  // 重建定时器
  if (playTimer) {
    clearInterval(playTimer)
    doStartAutoplay()
  }
}

function speedDown() {
  if (!autoplayConfig.value) return
  scrollDistance.value = Math.max(
    scrollDistance.value - autoplayConfig.value.scrollDistanceStep,
    autoplayConfig.value.minScrollDistance
  )
  if (playTimer) {
    clearInterval(playTimer)
    doStartAutoplay()
  }
}

onMounted(() => {
  loadChapters()
})

onBeforeUnmount(() => {
  stopAutoplay()
})
</script>

<style scoped>
.reader-view {
  display: flex;
  flex-direction: column;
  height: calc(100vh - 60px - 48px);
  background: #1a1a1a;
  border-radius: 8px;
  overflow: hidden;
}

.reader-toolbar {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 8px 16px;
  background: #fff;
  border-bottom: 1px solid #eee;
}

.chapter-title-display {
  flex: 1;
  text-align: center;
  font-size: 14px;
  font-weight: 500;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.reader-content {
  flex: 1;
  display: flex;
  overflow: hidden;
}

.reader-footer {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 8px 16px;
  background: #fff;
  border-top: 1px solid #eee;
  font-size: 13px;
  color: #666;
}
</style>
