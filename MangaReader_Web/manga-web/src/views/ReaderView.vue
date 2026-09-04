<template>
  <div class="reader-view">
    <!-- 左侧章节侧边栏 -->
    <ChapterList
      v-model:visible="sidebarVisible"
      :chapters="chapters"
      :current-chapter-id="currentChapterId"
      @select="onChapterSelect"
    />

    <!-- 右侧主区域 -->
    <div class="reader-main">
      <!-- 顶部工具栏 -->
      <div class="reader-toolbar">
        <el-button @click="sidebarVisible = !sidebarVisible">
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
        <span>第 {{ currentImageIndex + 1 }} / {{ totalImageCount }} 页</span>
        <span v-if="totalPages > 1" class="page-info">
          图片分页: 第 {{ currentImagePage + 1 }} / {{ totalPages }} 页
        </span>
        <AutoPlayBar
          :is-playing="isPlaying"
          :scroll-distance="scrollDistance"
          @toggle="toggleAutoplay"
          @speed-up="speedUp"
          @speed-down="speedDown"
        />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount } from 'vue'
import { useRoute } from 'vue-router'
import { getChapters, getChapterDetail, getChapterImagesPaged } from '@/api/chapter'
import type { Chapter, ChapterVO, ChapterImageVO } from '@/api/chapter'
import { getProgress, saveProgress } from '@/api/progress'
import { getAutoplayConfig, type AutoplayConfig as AutoplayConfigType } from '@/api/config'
import ComicScroller from '@/components/ComicScroller.vue'
import AutoPlayBar from '@/components/AutoPlayBar.vue'
import ChapterList from '@/components/ChapterList.vue'

const PAGE_SIZE = 100

const route = useRoute()
const mangaId = Number(route.params.mangaId)

const chapters = ref<Chapter[]>([])
const images = ref<ChapterImageVO[]>([])
const chapterDetail = ref<ChapterVO | null>(null)
const currentChapterId = ref<number | null>(null)
const currentImageIndex = ref(0)
const currentImagePage = ref(0)
const totalPages = ref(0)
const totalImageCount = ref(0)
const sidebarVisible = ref(true)
const scroller = ref<InstanceType<typeof ComicScroller>>()

// 自动播放状态
const isPlaying = ref(false)
const scrollDistance = ref(1)
const autoplayConfig = ref<AutoplayConfigType | null>(null)
let playTimer: ReturnType<typeof setInterval> | null = null

// 阅读进度保存定时器
let saveProgressTimer: ReturnType<typeof setInterval> | null = null

async function loadChapters() {
  try {
    const res = await getChapters(mangaId)
    chapters.value = res.data

    // 尝试恢复阅读进度
    await restoreProgress()
  } catch (e) {
    console.error('加载章节列表失败', e)
  }
}

async function restoreProgress() {
  try {
    const progress = await getProgress(mangaId)
    const data = progress.data
    if (data.chapterId && chapters.value.length > 0) {
      // 找到对应章节
      const targetChapter = chapters.value.find(c => c.chapterId === data.chapterId)
      if (targetChapter) {
        await loadChapter(targetChapter.chapterId, data.imageIndex || 0, data.pageIndex || 0)
        return
      }
    }
  } catch {
    // 无进度或接口失败，从第一章开始
  }

  // 默认加载第一章
  if (chapters.value.length > 0) {
    await loadChapter(chapters.value[0].chapterId)
  }
}

async function loadChapter(chapterId: number, restoreImageIndex: number = 0, restorePage: number = 0) {
  try {
    const detailRes = await getChapterDetail(chapterId)
    chapterDetail.value = detailRes.data
    currentChapterId.value = chapterId

    // 加载第一页图片
    currentImagePage.value = restorePage
    await loadImagesPage(chapterId, restorePage, restoreImageIndex)
  } catch (e) {
    console.error('加载章节失败', e)
  }
}

async function loadImagesPage(chapterId: number, page: number, scrollToIndex: number = 0) {
  const res = await getChapterImagesPaged(chapterId, page, PAGE_SIZE)
  const data = res.data

  if (page === 0) {
    images.value = data.images
  } else {
    // 追加图片到现有列表
    images.value = [...images.value, ...data.images]
  }

  totalPages.value = data.totalPages
  totalImageCount.value = data.totalCount
  currentImageIndex.value = scrollToIndex

  // 滚动到指定位置
  if (scrollToIndex > 0) {
    setTimeout(() => {
      scroller.value?.scrollToIndex(scrollToIndex)
    }, 200)
  } else {
    scroller.value?.scrollToTop()
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
}

function onPageChange(index: number) {
  currentImageIndex.value = index
}

async function onScrollEnd() {
  // 自动播放到底时切换下一章
  if (isPlaying.value) {
    loadNextChapter()
    return
  }

  // 检查是否需要加载下一页图片
  if (currentImagePage.value < totalPages.value - 1) {
    const nextPage = currentImagePage.value + 1
    currentImagePage.value = nextPage
    await loadImagesPage(currentChapterId.value!, nextPage)
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

// 保存阅读进度
async function doSaveProgress() {
  if (!currentChapterId.value) return
  try {
    await saveProgress({
      mangaId,
      chapterId: currentChapterId.value,
      imageIndex: currentImageIndex.value,
      pageIndex: currentImagePage.value,
      totalImages: totalImageCount.value,
    })
  } catch {
    // 静默失败
  }
}

onMounted(() => {
  loadChapters()

  // 每 30 秒自动保存进度
  saveProgressTimer = setInterval(() => {
    doSaveProgress()
  }, 30000)
})

onBeforeUnmount(() => {
  stopAutoplay()
  // 离开页面时保存进度
  doSaveProgress()
  if (saveProgressTimer) {
    clearInterval(saveProgressTimer)
  }
})
</script>

<style scoped>
.reader-view {
  display: flex;
  height: calc(100vh - 60px);
  background: #1a1a1a;
  border-radius: 8px;
  overflow: hidden;
  margin: -24px;
}

.reader-main {
  flex: 1;
  display: flex;
  flex-direction: column;
  min-width: 0;
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

.page-info {
  color: #999;
  font-size: 12px;
}
</style>
