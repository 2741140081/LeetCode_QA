<template>
  <el-container class="app-container">
    <el-header class="app-header">
      <div class="header-content">
        <h1 class="app-title" @click="router.push('/')">MangaReader</h1>
        <el-menu
          :default-active="activeMenu"
          mode="horizontal"
          router
          class="nav-menu"
        >
          <el-menu-item index="/">
            <el-icon><Grid /></el-icon>
            <span>书架</span>
          </el-menu-item>
          <el-menu-item index="/download">
            <el-icon><Download /></el-icon>
            <span>下载中心</span>
          </el-menu-item>
        </el-menu>
        <div class="header-right">
          <template v-if="userStore.isLoggedIn">
            <UserAvatar
              :display-name="userStore.nickname"
              :avatar-url="userStore.avatarUrl"
            />
          </template>
          <template v-else>
            <el-button type="primary" size="small" @click="router.push('/login')">
              登录
            </el-button>
            <el-button size="small" @click="router.push('/register')">
              注册
            </el-button>
          </template>
        </div>
      </div>
    </el-header>
    <el-main class="app-main">
      <router-view />
    </el-main>
  </el-container>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import UserAvatar from '@/components/UserAvatar.vue'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const activeMenu = computed(() => {
  if (route.path.startsWith('/reader')) return '/'
  return route.path
})
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
  background-color: #f5f5f5;
  color: #333;
}

.app-container {
  min-height: 100vh;
}

.app-header {
  background: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  padding: 0 24px;
  height: 60px !important;
  position: sticky;
  top: 0;
  z-index: 100;
}

.header-content {
  display: flex;
  align-items: center;
  height: 100%;
  max-width: 1400px;
  margin: 0 auto;
}

.app-title {
  font-size: 20px;
  font-weight: 700;
  color: #409eff;
  cursor: pointer;
  margin-right: 32px;
  white-space: nowrap;
}

.nav-menu {
  border-bottom: none !important;
  flex: 1;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-left: auto;
}

.app-main {
  max-width: 1400px;
  margin: 0 auto;
  padding: 24px;
  width: 100%;
}
</style>
