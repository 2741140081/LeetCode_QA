<template>
  <div class="auth-page">
    <div class="auth-card">
      <h2 class="auth-title">找回密码</h2>

      <!-- 步骤一: 发送验证码 -->
      <div v-if="step === 1">
        <el-form ref="step1FormRef" :model="step1Form" :rules="step1Rules" label-width="0">
          <el-form-item prop="email">
            <el-input
              v-model="step1Form.email"
              placeholder="请输入注册邮箱"
              prefix-icon="Message"
              size="large"
            />
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              size="large"
              style="width: 100%"
              :loading="sending"
              @click="handleSendCode"
            >
              发送验证码
            </el-button>
          </el-form-item>
        </el-form>
      </div>

      <!-- 步骤二: 输入验证码和新密码 -->
      <div v-if="step === 2">
        <el-alert
          title="验证码已发送到您的邮箱，请查收"
          type="success"
          :closable="false"
          show-icon
          style="margin-bottom: 20px"
        />
        <el-form ref="step2FormRef" :model="step2Form" :rules="step2Rules" label-width="0">
          <el-form-item prop="verifyCode">
            <el-input
              v-model="step2Form.verifyCode"
              placeholder="请输入 6 位验证码"
              prefix-icon="Key"
              size="large"
              maxlength="6"
            />
          </el-form-item>
          <el-form-item prop="newPassword">
            <el-input
              v-model="step2Form.newPassword"
              type="password"
              placeholder="请输入新密码 (至少8位)"
              prefix-icon="Lock"
              size="large"
              show-password
            />
          </el-form-item>
          <el-form-item prop="confirmPassword">
            <el-input
              v-model="step2Form.confirmPassword"
              type="password"
              placeholder="确认新密码"
              prefix-icon="Lock"
              size="large"
              show-password
            />
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              size="large"
              style="width: 100%"
              :loading="resetting"
              @click="handleResetPassword"
            >
              重置密码
            </el-button>
          </el-form-item>
        </el-form>
      </div>

      <div class="auth-footer">
        <router-link to="/login">返回登录</router-link>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, type FormInstance } from 'element-plus'
import { sendResetCode, resetPassword } from '@/api/auth'

const router = useRouter()
const step = ref(1)
const sending = ref(false)
const resetting = ref(false)

const step1FormRef = ref<FormInstance>()
const step2FormRef = ref<FormInstance>()

const step1Form = reactive({
  email: '',
})

const step2Form = reactive({
  verifyCode: '',
  newPassword: '',
  confirmPassword: '',
})

const step1Rules = {
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur' },
  ],
}

const validateConfirmPassword = (_rule: any, value: string, callback: any) => {
  if (value !== step2Form.newPassword) {
    callback(new Error('两次输入的密码不一致'))
  } else {
    callback()
  }
}

const step2Rules = {
  verifyCode: [
    { required: true, message: '请输入验证码', trigger: 'blur' },
    { len: 6, message: '验证码为 6 位', trigger: 'blur' },
  ],
  newPassword: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    { min: 8, message: '密码至少 8 位', trigger: 'blur' },
  ],
  confirmPassword: [
    { required: true, message: '请确认新密码', trigger: 'blur' },
    { validator: validateConfirmPassword, trigger: 'blur' },
  ],
}

async function handleSendCode() {
  const valid = await step1FormRef.value?.validate().catch(() => false)
  if (!valid) return

  sending.value = true
  try {
    await sendResetCode(step1Form.email)
    ElMessage.success('验证码已发送，请查收邮箱')
    step.value = 2
  } catch {
    // 错误已由拦截器处理
  } finally {
    sending.value = false
  }
}

async function handleResetPassword() {
  const valid = await step2FormRef.value?.validate().catch(() => false)
  if (!valid) return

  resetting.value = true
  try {
    await resetPassword({
      email: step1Form.email,
      verifyCode: step2Form.verifyCode,
      newPassword: step2Form.newPassword,
    })
    ElMessage.success('密码重置成功，请使用新密码登录')
    router.push('/login')
  } catch {
    // 错误已由拦截器处理
  } finally {
    resetting.value = false
  }
}
</script>

<style scoped>
.auth-page {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: calc(100vh - 200px);
}

.auth-card {
  width: 400px;
  padding: 40px;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
}

.auth-title {
  text-align: center;
  margin-bottom: 32px;
  font-size: 22px;
  color: #333;
}

.auth-footer {
  text-align: center;
  margin-top: 16px;
  font-size: 14px;
  color: #666;
}

.auth-footer a {
  color: #409eff;
  text-decoration: none;
}

.auth-footer a:hover {
  text-decoration: underline;
}
</style>
