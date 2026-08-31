import request from './request'

export interface AutoplayConfig {
  defaultScrollDistance: number
  defaultScrollInterval: number
  scrollDistanceStep: number
  minScrollDistance: number
  maxScrollDistance: number
}

export interface ReaderConfig {
  preloadCount: number
  saveInterval: number
  defaultScale: number
  scrollStep: number
  supportedFormats: string
  maxMemoryCacheSize: number
}

/** 获取自动播放配置 */
export function getAutoplayConfig() {
  return request.get<any, { code: number; data: AutoplayConfig }>('/config/autoplay')
}

/** 获取阅读器配置 */
export function getReaderConfig() {
  return request.get<any, { code: number; data: ReaderConfig }>('/config/reader')
}
