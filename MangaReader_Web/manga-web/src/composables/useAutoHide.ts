import { ref, onMounted, onBeforeUnmount } from 'vue'
import { SIDEBAR_AUTO_HIDE_DELAY } from '@/constants'

/**
 * 空闲检测 composable：在指定延迟内无鼠标活动后触发回调
 *
 * @param delay 空闲时间（毫秒），默认使用常量配置
 * @param onHide 空闲后触发的回调
 */
export function useAutoHide(delay: number = SIDEBAR_AUTO_HIDE_DELAY, onHide: () => void) {
  const isActive = ref(true)
  let timer: ReturnType<typeof setTimeout> | null = null

  function resetTimer() {
    isActive.value = true
    if (timer) clearTimeout(timer)
    timer = setTimeout(() => {
      isActive.value = false
      onHide()
    }, delay)
  }

  function stop() {
    if (timer) {
      clearTimeout(timer)
      timer = null
    }
  }

  function start() {
    resetTimer()
  }

  onBeforeUnmount(() => {
    stop()
  })

  return { isActive, resetTimer, stop, start }
}
