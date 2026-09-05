import axios from 'axios'
import { ElMessage } from 'element-plus'
import router from '@/router'
import { TOKEN_KEY } from '@/constants'
import { decryptFields, ENCRYPTED_FIELDS } from '@/utils/crypto'

const request = axios.create({
  baseURL: '/api',
  timeout: 30000,
})

// 请求拦截器：自动附加 Authorization 头
request.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem(TOKEN_KEY)
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => Promise.reject(error)
)

// 响应拦截器
request.interceptors.response.use(
  (response) => {
    const res = response.data
    // 对加密字段自动解密（在 code 检查之前）
    if (res.data && typeof res.data === 'object') {
      decryptFields(res.data, ENCRYPTED_FIELDS)
    }
    if (res.code !== 200) {
      ElMessage.error(res.message || '请求失败')
      return Promise.reject(new Error(res.message || '请求失败'))
    }
    return res
  },
  (error) => {
    const status = error.response?.status
    const message = error.response?.data?.message || error.message || '网络错误'

    if (status === 401) {
      // Token 过期或无效，清除本地状态并跳转登录
      localStorage.removeItem(TOKEN_KEY)
      localStorage.removeItem('manga_user')
      ElMessage.error('登录已过期，请重新登录')
      router.push('/login')
    } else {
      ElMessage.error(message)
    }

    return Promise.reject(error)
  }
)

export default request
