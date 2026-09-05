<template>
  <div class="comic-scroller" ref="scrollContainer" @scroll="onScroll">
    <div
      v-for="(img, index) in images"
      :key="img.imageId"
      class="image-slot"
      :data-index="index"
      :ref="(el) => setSlotRef(el as HTMLElement, index)"
      :style="{ height: getPlaceholderHeight(img) + 'px' }"
    >
      <img
        v-if="visibleImages.has(index)"
        :src="img.url"
        :alt="`第 ${img.sortOrder} 页`"
        class="comic-image"
        :class="{ 'loaded': loadedImages.has(index), 'error': errorImages.has(index) }"
        loading="lazy"
        @load="onImageLoad($event, index)"
        @error="onImageError($event, index)"
      />
      <div v-else class="placeholder" :style="{ height: getPlaceholderHeight(img) + 'px' }">
        <el-icon class="is-loading" :size="32"><Loading /></el-icon>
      </div>
      <!-- 加载失败重试按钮 -->
      <div v-if="errorImages.has(index)" class="error-retry" @click="retryImage(index)">
        <el-icon :size="20"><RefreshRight /></el-icon>
        <span>点击重试</span>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, watch, onMounted, onBeforeUnmount, nextTick } from 'vue'
import type { ChapterImageVO } from '@/api/chapter'

const props = defineProps<{
  images: ChapterImageVO[]
  resetKey?: number
}>()

const emit = defineEmits<{
  'page-change': [index: number]
  'scroll-end': []
}>()

const scrollContainer = ref<HTMLElement>()
const visibleImages = ref(new Set<number>())
const loadedImages = ref(new Set<number>())
const errorImages = ref(new Set<number>())
const slotRefs = new Map<number, HTMLElement>()
let observer: IntersectionObserver | null = null

// scroll-end 节流
let lastScrollEndEmit = 0
const SCROLL_END_THROTTLE = 500 // ms

function setSlotRef(el: HTMLElement | null, index: number) {
  if (el) {
    slotRefs.set(index, el)
    observer?.observe(el)
  }
}

function getPlaceholderHeight(img: ChapterImageVO): number {
  if (img.width && img.height) {
    const containerWidth = scrollContainer.value?.clientWidth || 800
    return (img.height / img.width) * containerWidth
  }
  return 600
}

/** 图片进入视口时标记为可见，触发 img 元素渲染 */
function onImageVisible(index: number) {
  visibleImages.value.add(index)
}

function onImageLoad(event: Event, index: number) {
  loadedImages.value.add(index)
  errorImages.value.delete(index)
}

function onImageError(event: Event, index: number) {
  errorImages.value.add(index)
}

/** 重试加载失败的图片 */
function retryImage(index: number) {
  errorImages.value.delete(index)
  loadedImages.value.delete(index)
  // 先移除再重新加入可见集合，触发 img 重新渲染
  visibleImages.value.delete(index)
  nextTick(() => {
    visibleImages.value.add(index)
  })
}

function onScroll() {
  if (!scrollContainer.value) return
  const { scrollTop, scrollHeight, clientHeight } = scrollContainer.value
  const distanceFromBottom = scrollHeight - scrollTop - clientHeight

  // 检测是否滚动到底部（节流）
  if (distanceFromBottom < 100) {
    const now = Date.now()
    if (now - lastScrollEndEmit > SCROLL_END_THROTTLE) {
      lastScrollEndEmit = now
      emit('scroll-end')
    }
  }
}

function scrollToTop() {
  scrollContainer.value?.scrollTo({ top: 0, behavior: 'smooth' })
}

function scrollToIndex(index: number) {
  const el = slotRefs.get(index)
  if (el) {
    el.scrollIntoView({ behavior: 'smooth', block: 'start' })
  }
}

function scrollBy(distance: number) {
  scrollContainer.value?.scrollBy({ top: distance, behavior: 'auto' })
}

function isAtBottom(): boolean {
  if (!scrollContainer.value) return false
  const { scrollTop, scrollHeight, clientHeight } = scrollContainer.value
  return scrollHeight - scrollTop - clientHeight < 50
}

function getCurrentPageIndex(): number {
  const indices = Array.from(visibleImages.value).sort((a, b) => a - b)
  return indices.length > 0 ? indices[0] : 0
}

defineExpose({ scrollToTop, scrollToIndex, scrollBy, isAtBottom, getCurrentPageIndex })

onMounted(() => {
  observer = new IntersectionObserver(
    (entries) => {
      entries.forEach((entry) => {
        const index = Number(entry.target.getAttribute('data-index'))
        if (!isNaN(index)) {
          if (entry.isIntersecting) {
            // 进入视口：标记为可见，触发 img 元素渲染
            onImageVisible(index)
          } else {
            // 回收远离视口的图片（释放内存）
            const containerRect = scrollContainer.value?.getBoundingClientRect()
            const entryRect = entry.boundingClientRect
            if (containerRect && (entryRect.bottom < containerRect.top - window.innerHeight * 2 ||
                entryRect.top > containerRect.bottom + window.innerHeight * 2)) {
              visibleImages.value.delete(index)
              loadedImages.value.delete(index)
            }
          }
          emit('page-change', getCurrentPageIndex())
        }
      })
    },
    {
      root: scrollContainer.value,
      rootMargin: '150% 0px',
      threshold: 0,
    }
  )
})

onBeforeUnmount(() => {
  observer?.disconnect()
})

// resetKey 变化时重置所有状态（切换章节或向前翻页时触发）
watch(() => props.resetKey, () => {
  visibleImages.value = new Set()
  loadedImages.value = new Set()
  errorImages.value = new Set()
  slotRefs.clear()
  nextTick(() => scrollToTop())
})
</script>

<style scoped>
.comic-scroller {
  flex: 1;
  overflow-y: auto;
  overflow-x: hidden;
  background: #1a1a1a;
  scroll-behavior: auto;
}

.image-slot {
  display: flex;
  justify-content: center;
  align-items: flex-start;
  min-height: 100px;
  position: relative;
}

.comic-image {
  max-width: 100%;
  display: block;
  margin: 0 auto;
  opacity: 0;
  transition: opacity 0.3s ease-in-out;
}

.comic-image.loaded {
  opacity: 1;
}

.comic-image.error {
  opacity: 0;
}

.placeholder {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  background: #222;
  color: #666;
}

.error-retry {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
  color: #999;
  cursor: pointer;
  padding: 16px 24px;
  border: 1px solid #444;
  border-radius: 8px;
  background: rgba(34, 34, 34, 0.9);
  transition: all 0.2s;
  font-size: 13px;
  z-index: 1;
}

.error-retry:hover {
  color: #fff;
  border-color: #666;
  background: rgba(50, 50, 50, 0.95);
}
</style>
