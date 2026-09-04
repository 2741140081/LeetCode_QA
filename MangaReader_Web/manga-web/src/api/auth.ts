import request from './request'

export interface UserVO {
  userId: number
  username: string
  email: string
  nickname: string
  avatarUrl: string
  createdAt: string
}

export interface LoginVO {
  token: string
  user: UserVO
}

export interface LoginRequest {
  username: string
  password: string
}

export interface RegisterRequest {
  username: string
  password: string
  email?: string
  nickname?: string
}

/** 用户注册 */
export function register(data: RegisterRequest) {
  return request.post<any, { code: number; message: string; data: UserVO }>('/auth/register', data)
}

/** 用户登录 */
export function login(data: LoginRequest) {
  return request.post<any, { code: number; message: string; data: LoginVO }>('/auth/login', data)
}

/** 用户登出 */
export function logout() {
  return request.post<any, { code: number; message: string }>('/auth/logout')
}

/** 获取当前用户信息 */
export function getMe() {
  return request.get<any, { code: number; data: UserVO }>('/auth/me')
}

/** 修改个人信息 */
export function updateProfile(data: { nickname?: string; avatarUrl?: string; email?: string }) {
  return request.put<any, { code: number; message: string; data: UserVO }>('/user/profile', data)
}

/** 修改密码 */
export function changePassword(data: { oldPassword: string; newPassword: string }) {
  return request.put<any, { code: number; message: string }>('/user/password', data)
}

/** 发送密码重置验证码 */
export function sendResetCode(email: string) {
  return request.post<any, { code: number; message: string }>('/user/password/reset/code', { email })
}

/** 重置密码 */
export function resetPassword(data: { email: string; verifyCode: string; newPassword: string }) {
  return request.post<any, { code: number; message: string }>('/user/password/reset', data)
}
