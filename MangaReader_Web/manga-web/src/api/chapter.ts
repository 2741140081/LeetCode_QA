import request from './request'

export interface Chapter {
  chapterId: number
  mangaId: number
  chapterNum: number
  chapterUrl: string
  title: string
  imageCount: number
  createdAt: string
}

export interface ChapterVO extends Chapter {
  prevChapterId: number | null
  nextChapterId: number | null
}

export interface ChapterImageVO {
  imageId: number
  sortOrder: number
  url: string
  width: number
  height: number
}

export interface PagedImagesResult {
  images: ChapterImageVO[]
  currentPage: number
  totalPages: number
  totalCount: number
  hasNext: boolean
}

/** 获取章节列表 */
export function getChapters(mangaId: number) {
  return request.get<any, { code: number; data: Chapter[] }>(`/manga/${mangaId}/chapters`)
}

/** 获取章节详情（含上下章导航） */
export function getChapterDetail(chapterId: number) {
  return request.get<any, { code: number; data: ChapterVO }>(`/chapter/${chapterId}`)
}

/** 获取章节图片列表（全量） */
export function getChapterImages(chapterId: number) {
  return request.get<any, { code: number; data: ChapterImageVO[] }>(`/chapter/${chapterId}/images`)
}

/** 分页获取章节图片 */
export function getChapterImagesPaged(chapterId: number, page: number = 0, size: number = 100) {
  return request.get<any, { code: number; data: PagedImagesResult }>(`/chapter/${chapterId}/images/paged`, {
    params: { page, size },
  })
}

/** 获取上一章 */
export function getPrevChapter(chapterId: number) {
  return request.get<any, { code: number; data: Chapter | null }>(`/chapter/${chapterId}/prev`)
}

/** 获取下一章 */
export function getNextChapter(chapterId: number) {
  return request.get<any, { code: number; data: Chapter | null }>(`/chapter/${chapterId}/next`)
}
