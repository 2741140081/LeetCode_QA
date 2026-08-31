<template>
  <div class="autoplay-bar">
    <el-button
      :type="isPlaying ? 'danger' : 'primary'"
      @click="$emit('toggle')"
      round
    >
      <el-icon v-if="isPlaying"><VideoPause /></el-icon>
      <el-icon v-else><VideoPlay /></el-icon>
      {{ isPlaying ? '暂停' : '播放' }}
    </el-button>

    <el-button @click="$emit('speed-down')" :disabled="!isPlaying" round>
      <el-icon><DArrowLeft /></el-icon>
      减速
    </el-button>

    <span class="speed-display">{{ scrollDistance.toFixed(1) }}px</span>

    <el-button @click="$emit('speed-up')" :disabled="!isPlaying" round>
      加速
      <el-icon><DArrowRight /></el-icon>
    </el-button>
  </div>
</template>

<script setup lang="ts">
defineProps<{
  isPlaying: boolean
  scrollDistance: number
}>()

defineEmits<{
  toggle: []
  'speed-up': []
  'speed-down': []
}>()
</script>

<style scoped>
.autoplay-bar {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 8px 16px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.speed-display {
  font-size: 14px;
  font-weight: 500;
  color: #409eff;
  min-width: 60px;
  text-align: center;
}
</style>
