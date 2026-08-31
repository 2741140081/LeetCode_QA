import request from './request'

export interface MangaVO {
  mangaId: number
  mangaName: string
  coverUrl: string | null
  mangaStatusCode: number
  mangaStatusDesc: string
  totalChapters: number
  processedChapters: number
  createdAt: string
  updatedAt: string
}

export interface MangaAddRequest {
  mangaName: string
  mangaUrl: string
}

/** 获取书架漫画列表 */
export function getMangaList() {
  return request.get<any, { code: number; data: MangaVO[] }>('/manga/list')
}

/** 获取漫画详情 */
export function getMangaDetail(mangaId: number) {
  return request.get<any, { code: number; data: MangaVO }>(`/manga/${mangaId}`)
}

/** 新增漫画下载任务 */
export function addManga(data: MangaAddRequest) {
  return request.post<any, { code: number; message: string; data: MangaVO }>('/manga', data)
}
