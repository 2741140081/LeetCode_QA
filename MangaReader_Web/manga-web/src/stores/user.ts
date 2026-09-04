import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { login as loginApi, logout as logoutApi, getMe, type UserVO, type LoginRequest } from '@/api/auth'

const TOKEN_KEY = 'manga_token'
const USER_KEY = 'manga_user'

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

  /** 登出 */
  async function logout() {
    try {
      await logoutApi()
    } catch {
      // 即使接口失败也清除本地状态
    }
    token.value = ''
    user.value = null
    localStorage.removeItem(TOKEN_KEY)
    localStorage.removeItem(USER_KEY)
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
