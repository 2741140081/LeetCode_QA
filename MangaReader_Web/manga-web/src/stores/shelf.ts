import { defineStore } from 'pinia'
import { ref } from 'vue'
import {
  getFolders,
  getShelfMangas,
  createFolder as createFolderApi,
  renameFolder as renameFolderApi,
  deleteFolder as deleteFolderApi,
  removeMangaFromShelf as removeMangaApi,
  moveMangaToFolder as moveMangaApi,
  type ShelfFolderVO,
  type ShelfMangaVO,
} from '@/api/shelf'

export const useShelfStore = defineStore('shelf', () => {
  const folders = ref<ShelfFolderVO[]>([])
  const mangas = ref<ShelfMangaVO[]>([])
  const currentFolderId = ref<number | null>(null) // null = 全部
  const isUncategorized = ref(false) // true = 未分类
  const loading = ref(false)

  /** 加载文件夹列表 */
  async function loadFolders() {
    try {
      const res = await getFolders()
      folders.value = res.data
    } catch (e) {
      console.error('加载文件夹失败', e)
    }
  }

  /** 加载书架漫画 */
  async function loadMangas(folderId?: number | null, uncategorized?: boolean) {
    loading.value = true
    const fId = folderId !== undefined ? folderId : currentFolderId.value
    const unc = uncategorized !== undefined ? uncategorized : isUncategorized.value
    try {
      const res = await getShelfMangas(fId, unc)
      mangas.value = res.data
    } catch (e) {
      console.error('加载书架漫画失败', e)
    } finally {
      loading.value = false
    }
  }

  /** 创建文件夹 */
  async function createFolder(folderName: string) {
    await createFolderApi(folderName)
    await loadFolders()
  }

  /** 重命名文件夹 */
  async function renameFolder(folderId: number, folderName: string) {
    await renameFolderApi(folderId, folderName)
    await loadFolders()
  }

  /** 删除文件夹 */
  async function deleteFolder(folderId: number) {
    await deleteFolderApi(folderId)
    await loadFolders()
    // 如果当前选中的是被删除的文件夹，切回全部
    if (currentFolderId.value === folderId) {
      currentFolderId.value = null
      isUncategorized.value = false
    }
  }

  /** 从书架移除漫画 */
  async function removeManga(mangaId: number) {
    await removeMangaApi(mangaId)
    await loadMangas()
    await loadFolders()
  }

  /** 移动漫画到文件夹 */
  async function moveManga(mangaId: number, folderId: number | null) {
    await moveMangaApi(mangaId, folderId)
    await loadMangas()
    await loadFolders()
  }

  /** 切换当前文件夹 */
  function selectFolder(folderId: number | null, uncategorized: boolean = false) {
    currentFolderId.value = folderId
    isUncategorized.value = uncategorized
    loadMangas(folderId, uncategorized)
  }

  return {
    folders,
    mangas,
    currentFolderId,
    isUncategorized,
    loading,
    loadFolders,
    loadMangas,
    createFolder,
    renameFolder,
    deleteFolder,
    removeManga,
    moveManga,
    selectFolder,
  }
})
