/**
 * 前端全局常量定义
 * 统一管理魔法数字，方便维护
 */

/** ========== 阅读器常量 ========== */

/** 每页加载的图片数量 */
export const PAGE_SIZE = 50

/** 阅读进度自动保存间隔（毫秒）：30 秒 */
export const PROGRESS_SAVE_INTERVAL = 30_000

/** 缩放比例 sessionStorage Key */
export const ZOOM_SCALE_KEY = 'manga_zoom_scale'

/** 默认缩放比例 */
export const DEFAULT_ZOOM_SCALE = 1.0

/** 最小缩放比例 */
export const MIN_ZOOM_SCALE = 0.5

/** 最大缩放比例 */
export const MAX_ZOOM_SCALE = 2.0

/** 缩放步进值 */
export const ZOOM_STEP = 0.1

/** ========== ComicScroller 常量 ========== */

/** scroll-end 事件节流间隔（毫秒） */
export const SCROLL_END_THROTTLE = 500

/** 触底检测距离阈值（像素） */
export const BOTTOM_DISTANCE_THRESHOLD = 100

/** 内存回收距离倍数（相对视口高度） */
export const RECYCLE_DISTANCE_MULTIPLIER = 2

/** ========== 侧边栏常量 ========== */

/** 侧边栏空闲自动隐藏延迟（毫秒） */
export const SIDEBAR_AUTO_HIDE_DELAY = 5000

/** 侧边栏模式 sessionStorage Key */
export const SIDEBAR_MODE_KEY = 'manga_sidebar_mode'

/** ========== 书架常量 ========== */

/** 未分类文件夹标识 */
export const UNCATEGORIZED_FOLDER_ID = -1

/** 漫画下载完成状态码 */
export const MANGA_STATUS_COMPLETED = 2

/** ========== 侧边栏模式枚举 ========== */

/** 侧边栏行为模式 */
export const SidebarMode = {
  /** 进入阅读时自动收起，手动切换 */
  AUTO_COLLAPSE: 'auto_collapse' as const,
  /** 无操作自动隐藏，鼠标悬停唤出 */
  HOVER_SHOW: 'hover_show' as const,
  /** 常驻显示 */
  ALWAYS_VISIBLE: 'always_visible' as const,
}

export type SidebarMode = (typeof SidebarMode)[keyof typeof SidebarMode]

/** ========== 存储 Key 常量 ========== */

export const TOKEN_KEY = 'manga_token'
export const USER_KEY = 'manga_user'
