<template>
  <el-dialog
    :model-value="visible"
    @update:model-value="$emit('update:visible', $event)"
    :title="isEdit ? '重命名文件夹' : '新建文件夹'"
    width="400px"
    @close="resetForm"
  >
    <el-form @submit.prevent="handleSubmit">
      <el-form-item label="文件夹名称">
        <el-input
          v-model="folderName"
          placeholder="请输入文件夹名称"
          maxlength="100"
          @keyup.enter="handleSubmit"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="$emit('update:visible', false)">取消</el-button>
      <el-button type="primary" :loading="loading" @click="handleSubmit">确定</el-button>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'

const props = defineProps<{
  visible: boolean
  isEdit: boolean
  initialValue: string
}>()

const emit = defineEmits<{
  'update:visible': [value: boolean]
  submit: [name: string]
}>()

const folderName = ref('')
const loading = ref(false)

watch(() => props.visible, (val) => {
  if (val) {
    folderName.value = props.initialValue || ''
  }
})

function handleSubmit() {
  if (!folderName.value.trim()) return
  loading.value = true
  emit('submit', folderName.value.trim())
  // 由父组件控制 loading 和关闭
}

function resetForm() {
  folderName.value = ''
  loading.value = false
}

defineExpose({ loading })
</script>
