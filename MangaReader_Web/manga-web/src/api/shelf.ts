import request from './request'

export interface ShelfFolderVO {
  folderId: number
  folderName: string
  sortOrder: number
  mangaCount: number
  createdAt: string
}

export interface ShelfMangaVO {
  mangaId: number
  mangaName: string
  coverUrl: string | null
  mangaStatusCode: number
  mangaStatusDesc: string
  totalChapters: number
  processedChapters: number
  folderId: number | null
  folderName: string | null
  addedAt: string
}

/** 获取文件夹列表 */
export function getFolders() {
  return request.get<any, { code: number; data: ShelfFolderVO[] }>('/shelf/folders')
}

/** 创建文件夹 */
export function createFolder(folderName: string) {
  return request.post<any, { code: number; message: string; data: ShelfFolderVO }>('/shelf/folder', { folderName })
}

/** 重命名文件夹 */
export function renameFolder(folderId: number, folderName: string) {
  return request.put<any, { code: number; message: string }>(`/shelf/folder/${folderId}`, { folderName })
}

/** 删除文件夹 */
export function deleteFolder(folderId: number) {
  return request.delete<any, { code: number; message: string }>(`/shelf/folder/${folderId}`)
}

/** 获取书架漫画列表 */
export function getShelfMangas(folderId?: number | null, uncategorized?: boolean) {
  const params: Record<string, any> = {}
  if (uncategorized) {
    params.uncategorized = true
  } else if (folderId != null) {
    params.folderId = folderId
  }
  return request.get<any, { code: number; data: ShelfMangaVO[] }>('/shelf/mangas', { params })
}

/** 添加漫画到书架 */
export function addMangaToShelf(mangaId: number, folderId?: number | null) {
  return request.post<any, { code: number; message: string }>('/shelf/manga', { mangaId, folderId })
}

/** 移动漫画到文件夹 */
export function moveMangaToFolder(mangaId: number, folderId: number | null) {
  return request.put<any, { code: number; message: string }>(`/shelf/manga/${mangaId}/folder`, { folderId })
}

/** 从书架移除漫画 */
export function removeMangaFromShelf(mangaId: number) {
  return request.delete<any, { code: number; message: string }>(`/shelf/manga/${mangaId}`)
}
