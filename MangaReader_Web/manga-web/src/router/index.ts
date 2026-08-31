import { createRouter, createWebHashHistory } from 'vue-router'

const router = createRouter({
  history: createWebHashHistory(),
  routes: [
    {
      path: '/',
      name: 'shelf',
      component: () => import('@/views/ShelfView.vue'),
      meta: { title: '书架' },
    },
    {
      path: '/reader/:mangaId',
      name: 'reader',
      component: () => import('@/views/ReaderView.vue'),
      meta: { title: '阅读' },
    },
    {
      path: '/download',
      name: 'download',
      component: () => import('@/views/DownloadView.vue'),
      meta: { title: '下载中心' },
    },
  ],
})

router.beforeEach((to) => {
  document.title = `${to.meta.title || 'MangaReader'} - MangaReader Web`
})

export default router
