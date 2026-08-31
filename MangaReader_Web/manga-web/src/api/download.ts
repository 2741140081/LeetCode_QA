import request from './request'

export interface MangaTaskVO {
  mangaId: number
  mangaName: string
  mangaStatusCode: number
  mangaStatusDesc: string
  totalChapters: number
  processedChapters: number
  createdAt: string
  updatedAt: string
}

export interface DownloadStatsVO {
  total: number
  pending: number
  downloading: number
  completed: number
  failed: number
  successRate: number
}

/** 获取下载任务列表 */
export function getDownloadList(status?: number) {
  const params = status !== undefined ? { status } : {}
  return request.get<any, { code: number; data: MangaTaskVO[] }>('/download/manga/list', { params })
}

/** 获取图片下载统计 */
export function getImageStats() {
  return request.get<any, { code: number; data: DownloadStatsVO }>('/download/image/stats')
}

/** 创建 SSE 连接 */
export function createSSEConnection(
  onMangaProgress: (data: any) => void,
  onMangaStatus: (data: any) => void,
  onImageStats: (data: any) => void
) {
  const eventSource = new EventSource('/api/download/events')

  eventSource.addEventListener('manga-progress', (event) => {
    try {
      onMangaProgress(JSON.parse(event.data))
    } catch (e) {
      console.error('解析 manga-progress 事件失败', e)
    }
  })

  eventSource.addEventListener('manga-status', (event) => {
    try {
      onMangaStatus(JSON.parse(event.data))
    } catch (e) {
      console.error('解析 manga-status 事件失败', e)
    }
  })

  eventSource.addEventListener('image-stats', (event) => {
    try {
      onImageStats(JSON.parse(event.data))
    } catch (e) {
      console.error('解析 image-stats 事件失败', e)
    }
  })

  eventSource.onerror = () => {
    console.warn('SSE 连接断开，浏览器将自动重连...')
  }

  return eventSource
}
