<template>
  <div class="comic-scroller" ref="scrollContainer" @scroll="onScroll">
    <div
      v-for="(img, index) in images"
      :key="img.imageId"
      class="image-slot"
      :ref="(el) => setSlotRef(el as HTMLElement, index)"
      :style="{ height: getPlaceholderHeight(img) + 'px' }"
    >
      <img
        v-if="visibleImages.has(index)"
        :src="img.url"
        :alt="`第 ${img.sortOrder} 页`"
        class="comic-image"
        loading="lazy"
        @load="onImageLoad($event, index)"
      />
      <div v-else class="placeholder" :style="{ height: getPlaceholderHeight(img) + 'px' }">
        <el-icon class="is-loading" :size="32"><Loading /></el-icon>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, watch, onMounted, onBeforeUnmount, nextTick } from 'vue'
import type { ChapterImageVO } from '@/api/chapter'

const props = defineProps<{
  images: ChapterImageVO[]
}>()

const emit = defineEmits<{
  'page-change': [index: number]
  'scroll-end': []
}>()

const scrollContainer = ref<HTMLElement>()
const visibleImages = ref(new Set<number>())
const slotRefs = new Map<number, HTMLElement>()
let observer: IntersectionObserver | null = null

function setSlotRef(el: HTMLElement | null, index: number) {
  if (el) {
    slotRefs.set(index, el)
    observer?.observe(el)
  }
}

function getPlaceholderHeight(img: ChapterImageVO): number {
  if (img.width && img.height) {
    // 根据容器宽度等比计算高度
    const containerWidth = scrollContainer.value?.clientWidth || 800
    return (img.height / img.width) * containerWidth
  }
  return 600 // 默认高度
}

function onImageLoad(event: Event, index: number) {
  // 图片加载完成后的回调
}

function onScroll() {
  if (!scrollContainer.value) return
  const { scrollTop, scrollHeight, clientHeight } = scrollContainer.value
  // 检测是否滚动到底部
  if (scrollHeight - scrollTop - clientHeight < 100) {
    emit('scroll-end')
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

// 计算当前可见的第一张图片索引
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
            // 扩展可见区域：上下各 2 屏
            visibleImages.value.add(index)
          } else {
            // 回收远离视口的图片
            const containerRect = scrollContainer.value?.getBoundingClientRect()
            const entryRect = entry.boundingClientRect
            if (containerRect && (entryRect.bottom < containerRect.top - window.innerHeight * 2 ||
                entryRect.top > containerRect.bottom + window.innerHeight * 2)) {
              visibleImages.value.delete(index)
            }
          }
          emit('page-change', getCurrentPageIndex())
        }
      })
    },
    {
      root: scrollContainer.value,
      rootMargin: '200% 0px',
      threshold: 0,
    }
  )
})

onBeforeUnmount(() => {
  observer?.disconnect()
})

// 图片列表变化时重置
watch(() => props.images, () => {
  visibleImages.value = new Set()
  nextTick(() => scrollToTop())
}, { deep: true })
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
}

.comic-image {
  max-width: 100%;
  display: block;
  margin: 0 auto;
}

.placeholder {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  background: #222;
  color: #666;
}
</style>
