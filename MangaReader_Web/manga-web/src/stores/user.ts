import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { login as loginApi, logout as logoutApi, getMe, type UserVO, type LoginRequest } from '@/api/auth'
import { TOKEN_KEY, USER_KEY } from '@/constants'

export const useUserStore = defineStore('user', () => {
  const token = ref<string>(localStorage.getItem(TOKEN_KEY) || '')
  const user = ref<UserVO | null>(
    localStorage.getItem(USER_KEY) ? JSON.parse(localStorage.getItem(USER_KEY)!) : null
  )

  const isLoggedIn = computed(() => !!token.value)
  const nickname = computed(() => user.value?.nickname || user.value?.username || '')
  const avatarUrl = computed(() => user.value?.avatarUrl || '')

  /** 登录 */
  async function login(credentials: LoginRequest) {
    const res = await loginApi(credentials)
    const { token: newToken, user: newUser } = res.data
    token.value = newToken
    user.value = newUser
    localStorage.setItem(TOKEN_KEY, newToken)
    localStorage.setItem(USER_KEY, JSON.stringify(newUser))
  }

  /** 登出：先清除本地状态，再通知后端，避免路由守卫竞争 */
  async function logout() {
    const currentToken = token.value
    // 先清除本地状态，确保路由守卫不会拦截跳转
    token.value = ''
    user.value = null
    localStorage.removeItem(TOKEN_KEY)
    localStorage.removeItem(USER_KEY)
    try {
      // 传入 token 手动设置 Authorization 头，因为拦截器已无法读取 localStorage
      await logoutApi(currentToken || undefined)
    } catch {
      // 静默失败，后端登出不影响前端流程
    }
  }

  /** 刷新用户信息 */
  async function fetchUser() {
    if (!token.value) return
    try {
      const res = await getMe()
      user.value = res.data
      localStorage.setItem(USER_KEY, JSON.stringify(res.data))
    } catch {
      // token 过期，清除状态
      token.value = ''
      user.value = null
      localStorage.removeItem(TOKEN_KEY)
      localStorage.removeItem(USER_KEY)
    }
  }

  /** 更新本地用户信息（修改资料后调用） */
  function updateUser(updatedUser: UserVO) {
    user.value = updatedUser
    localStorage.setItem(USER_KEY, JSON.stringify(updatedUser))
  }

  /** 清除所有状态 */
  function clearAuth() {
    token.value = ''
    user.value = null
    localStorage.removeItem(TOKEN_KEY)
    localStorage.removeItem(USER_KEY)
  }

  return {
    token,
    user,
    isLoggedIn,
    nickname,
    avatarUrl,
    login,
    logout,
    fetchUser,
    updateUser,
    clearAuth,
  }
})
