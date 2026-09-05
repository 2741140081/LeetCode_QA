/**
 * AES 加解密工具（与后端 AesUtils 对应）
 * 使用 AES-128-CBC 模式，密钥与 IV 相同
 */
import CryptoJS from 'crypto-js'

/** 与后端 application.yml 中 manga.encrypt.secret-key 保持一致 */
const SECRET_KEY = 'MangaReader2026A'

/**
 * AES 加密
 */
export function aesEncrypt(plainText: string): string {
  if (!plainText) return plainText
  const key = CryptoJS.enc.Utf8.parse(padKey(SECRET_KEY))
  const iv = key // IV = Key
  const encrypted = CryptoJS.AES.encrypt(plainText, key, {
    iv,
    mode: CryptoJS.mode.CBC,
    padding: CryptoJS.pad.Pkcs7,
  })
  return encrypted.toString() // Base64
}

/**
 * AES 解密
 */
export function aesDecrypt(cipherText: string): string {
  if (!cipherText) return cipherText
  try {
    const key = CryptoJS.enc.Utf8.parse(padKey(SECRET_KEY))
    const iv = key
    const decrypted = CryptoJS.AES.decrypt(cipherText, key, {
      iv,
      mode: CryptoJS.mode.CBC,
      padding: CryptoJS.pad.Pkcs7,
    })
    return decrypted.toString(CryptoJS.enc.Utf8)
  } catch {
    // 解密失败返回原文（可能是未加密的数据）
    return cipherText
  }
}

/**
 * 对响应 data 中指定的字段进行解密
 * @param data 响应数据对象
 * @param fields 需要解密的字段名列表
 */
export function decryptFields(data: any, fields: string[]): void {
  if (!data || typeof data !== 'object') return
  for (const field of fields) {
    if (typeof data[field] === 'string' && data[field]) {
      data[field] = aesDecrypt(data[field])
    }
  }
}

/** 将密钥填充/截断为 16 字节，与后端 normalizeKey 逻辑一致 */
function padKey(key: string): string {
  const bytes = new TextEncoder().encode(key)
  const padded = new Uint8Array(16)
  padded.set(bytes.slice(0, 16))
  return new TextDecoder().decode(padded)
}

/** 需要解密的字段列表（与后端 EncryptProperties.fields 对应） */
export const ENCRYPTED_FIELDS = ['email', 'nickname']
