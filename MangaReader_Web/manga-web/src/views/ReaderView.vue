<template>
  <div class="reader-view">
    <!-- 左侧章节侧边栏 -->
    <ChapterList
      v-model:visible="sidebarVisible"
      :chapters="chapters"
      :current-chapter-id="currentChapterId"
      :hover-mode="sidebarMode === SidebarMode.HOVER_SHOW"
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
        <el-button size="small" @click="cycleSidebarMode" :title="'侧边栏模式: ' + sidebarModeLabel">
          {{ sidebarModeIcon }}
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
          :reset-key="resetKey"
          :zoom-scale="zoomScale"
          @page-change="onPageChange"
          @scroll-end="onScrollEnd"
        />
      </div>

      <!-- 底部状态栏 -->
      <div class="reader-footer">
        <el-button size="small" @click="loadPrevPage" :disabled="currentImagePage <= 0">
          上一页
        </el-button>
        <span v-if="totalPages > 1" class="page-info">
          图片分页: 第 {{ currentImagePage + 1 }} / {{ totalPages }} 页
        </span>
        <el-button size="small" @click="loadNextPage" :disabled="currentImagePage >= totalPages - 1">
          下一页
        </el-button>
        <!-- 缩放控制 -->
        <div class="zoom-controls">
          <el-button size="small" @click="zoomOut" :disabled="zoomScale <= MIN_ZOOM_SCALE">-</el-button>
          <span class="zoom-info">{{ Math.round(zoomScale * 100) }}%</span>
          <el-button size="small" @click="zoomIn" :disabled="zoomScale >= MAX_ZOOM_SCALE">+</el-button>
        </div>
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
import { ref, computed, onMounted, onBeforeUnmount } from 'vue'
import { useRoute } from 'vue-router'
import { getChapters, getChapterDetail, getChapterImagesPaged } from '@/api/chapter'
import type { Chapter, ChapterVO, ChapterImageVO } from '@/api/chapter'
import { getProgress, saveProgress } from '@/api/progress'
import { getAutoplayConfig, type AutoplayConfig as AutoplayConfigType } from '@/api/config'
import ComicScroller from '@/components/ComicScroller.vue'
import AutoPlayBar from '@/components/AutoPlayBar.vue'
import ChapterList from '@/components/ChapterList.vue'
import {
  PAGE_SIZE,
  PROGRESS_SAVE_INTERVAL,
  ZOOM_SCALE_KEY,
  DEFAULT_ZOOM_SCALE,
  MIN_ZOOM_SCALE,
  MAX_ZOOM_SCALE,
  ZOOM_STEP,
  SIDEBAR_MODE_KEY,
  SidebarMode,
} from '@/constants'

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
const sidebarVisible = ref(false)
const scroller = ref<InstanceType<typeof ComicScroller>>()
const resetKey = ref(0)

// 侧边栏模式（从 sessionStorage 恢复）
const sidebarMode = ref<SidebarMode>(
  (sessionStorage.getItem(SIDEBAR_MODE_KEY) as SidebarMode) || SidebarMode.AUTO_COLLAPSE
)

const sidebarModeLabel = computed(() => {
  switch (sidebarMode.value) {
    case SidebarMode.AUTO_COLLAPSE: return '自动收起'
    case SidebarMode.HOVER_SHOW: return '悬停唤出'
    case SidebarMode.ALWAYS_VISIBLE: return '常驻显示'
    default: return '未知'
  }
})

const sidebarModeIcon = computed(() => {
  switch (sidebarMode.value) {
    case SidebarMode.AUTO_COLLAPSE: return '📖'
    case SidebarMode.HOVER_SHOW: return '👁'
    case SidebarMode.ALWAYS_VISIBLE: return '📌'
    default: return '📖'
  }
})

function cycleSidebarMode() {
  const modes: SidebarMode[] = [
    SidebarMode.AUTO_COLLAPSE,
    SidebarMode.HOVER_SHOW,
    SidebarMode.ALWAYS_VISIBLE,
  ]
  const currentIdx = modes.indexOf(sidebarMode.value)
  const nextMode = modes[(currentIdx + 1) % modes.length]
  sidebarMode.value = nextMode
  sessionStorage.setItem(SIDEBAR_MODE_KEY, nextMode)
}

// 缩放状态（从 sessionStorage 恢复）
const zoomScale = ref(Number(sessionStorage.getItem(ZOOM_SCALE_KEY)) || DEFAULT_ZOOM_SCALE)

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

  // 自动播放模式下追加图片，手动翻页时替换
  if (isPlaying.value && page > 0) {
    images.value = [...images.value, ...data.images]
  } else {
    images.value = data.images
  }
  resetKey.value++

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

// 防止重复触发下一页加载
let isLoadingNextPage = false

async function onScrollEnd() {
  // 防止重复加载
  if (isLoadingNextPage) return

  // 检查是否需要加载下一页图片
  if (currentImagePage.value < totalPages.value - 1) {
    // 还有下一页：加载下一页图片（无论是否自动播放）
    isLoadingNextPage = true
    try {
      await loadNextPage()
    } finally {
      isLoadingNextPage = false
    }
  } else if (isPlaying.value) {
    // 自动播放且已到本章最后一页：切换下一章
    loadNextChapter()
  }
}

async function loadPrevPage() {
  if (currentImagePage.value > 0 && currentChapterId.value) {
    const prevPage = currentImagePage.value - 1
    currentImagePage.value = prevPage
    await loadImagesPage(currentChapterId.value, prevPage)
  }
}

async function loadNextPage() {
  if (currentImagePage.value < totalPages.value - 1 && currentChapterId.value) {
    const nextPage = currentImagePage.value + 1
    currentImagePage.value = nextPage
    await loadImagesPage(currentChapterId.value, nextPage)
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

// 缩放控制
function zoomIn() {
  const newScale = Math.min(zoomScale.value + ZOOM_STEP, MAX_ZOOM_SCALE)
  zoomScale.value = Math.round(newScale * 10) / 10
  sessionStorage.setItem(ZOOM_SCALE_KEY, String(zoomScale.value))
}

function zoomOut() {
  const newScale = Math.max(zoomScale.value - ZOOM_STEP, MIN_ZOOM_SCALE)
  zoomScale.value = Math.round(newScale * 10) / 10
  sessionStorage.setItem(ZOOM_SCALE_KEY, String(zoomScale.value))
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
  }, PROGRESS_SAVE_INTERVAL)
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
  height: calc(100% + 24px);
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

.zoom-controls {
  display: flex;
  align-items: center;
  gap: 4px;
}

.zoom-info {
  font-size: 12px;
  color: #666;
  min-width: 40px;
  text-align: center;
}
</style>
