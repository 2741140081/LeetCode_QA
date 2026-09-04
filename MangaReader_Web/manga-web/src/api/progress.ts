import request from './request'

export interface ReadingProgressVO {
  chapterId: number | null
  imageIndex: number
  pageIndex: number
  totalImages: number
  progressPct: number
  lastReadAt: string | null
}

export interface SaveProgressRequest {
  mangaId: number
  chapterId: number
  imageIndex: number
  pageIndex: number
  totalImages: number
}

/** 获取某漫画的阅读进度 */
export function getProgress(mangaId: number) {
  return request.get<any, { code: number; data: ReadingProgressVO }>(`/reading-progress/${mangaId}`)
}

/** 保存/更新阅读进度 */
export function saveProgress(data: SaveProgressRequest) {
  return request.post<any, { code: number; message: string }>('/reading-progress', data)
}
