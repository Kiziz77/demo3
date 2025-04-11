<template>
  <div class="personal-center">
    <!-- 顶部导航 -->
    <div class="nav-bar">
      <div class="nav-item" @click="activeTab = 'profile'" :class="{ active: activeTab === 'profile' }">个人信息</div>
      <div class="nav-item" @click="activeTab = 'updatePassword'" :class="{ active: activeTab === 'updatePassword' }">修改密码</div>
      <div class="nav-item" @click="activeTab = 'myCollect'" :class="{ active: activeTab === 'myCollect' }">我的收藏</div>
      <div class="nav-item" @click="activeTab = 'address'" :class="{ active: activeTab === 'address' }">收货地址</div>
      <div class="nav-item" @click="activeTab = 'myOrder'" :class="{ active: activeTab === 'myOrder' }">我的订单</div>
    </div>

    <!-- 内容区域 -->
    <div class="content">
      <!-- 个人信息 -->
      <div v-if="activeTab === 'profile'" class="profile-section">
        <el-card class="profile-card">
          <div class="avatar-section">
            <el-avatar :size="100" :src="userInfo.avatar || 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'" />
          </div>

          <el-descriptions :column="1" border class="info-list">
            <el-descriptions-item label="用户名">{{ userInfo.userName }}</el-descriptions-item>
            <el-descriptions-item label="邮箱">{{ userInfo.email }}</el-descriptions-item>
            <el-descriptions-item label="手机号">{{ userInfo.phone }}</el-descriptions-item>
            <el-descriptions-item label="性别">{{ userInfo.gender || "未设置" }}</el-descriptions-item>
          </el-descriptions>

          <el-button type="primary" class="edit-info-btn" @click="showEditDialog">编辑资料</el-button>
        </el-card>
      </div>

      <!-- 修改密码 -->
      <div v-if="activeTab === 'updatePassword'" class="other-section">
        <reset-password></reset-password>
      </div>

      <!-- 我的收藏 -->
      <div v-if="activeTab === 'myCollect'" class="other-section">
        <my-Collect></my-Collect>
      </div>

      <!-- 我的订单 -->
      <div v-if="activeTab === 'myOrder'" class="other-section">
        <my-order></my-order>
      </div>
      <!-- 收货地址 -->
      <div v-if="activeTab === 'address'" class="other-section">
        <receiving-address></receiving-address>
      </div>
    </div>

    <!-- 编辑对话框 -->
    <el-dialog v-model="editDialogVisible" title="编辑资料" width="30%">
      <el-form :model="editForm" label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="editForm.userName" disabled />
        </el-form-item>
        <el-form-item label="头像">
          <file-upload action="file/upload" :fileUrls="editForm.avatar" :limit="1" :show-file-list="false" @change="handleAvatarChange" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="editForm.email" />
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="editForm.phone" />
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="editForm.gender" placeholder="请选择性别">
            <el-option label="男" value="男" />
            <el-option label="女" value="女" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="editDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveEdit">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, watch } from "vue";
import { ElMessage } from "element-plus";
import { useUserStore } from "@/stores/user";
import ResetPassword from "@/views/reset-password/reset-password.vue";
import storage from "@/utils/storage";
import config from "@/config/config";
import request from "@/utils/request.js";
const userStore = useUserStore();
const tableName = storage.get(config.CURRENT_SESSION_TABLE_KEY);

const userInfo = ref({});

// 编辑表单数据
const editForm = ref({});

const activeTab = ref("profile"); // 当前激活的标签
const editDialogVisible = ref(false); // 编辑对话框显示状态

// 显示编辑对话框
const showEditDialog = () => {
  editForm.value = { ...userInfo.value }; // 初始化编辑表单数据
  editDialogVisible.value = true;
};

// 保存编辑
const saveEdit = () => {
  request.post(`/${tableName}/update`, editForm.value).then(async () => {
    const { data } = await request.get(`/${tableName}/session`);
    userStore.setUserInfo(data);
    ElMessage.success("编辑成功!");
    editDialogVisible.value = false;
  });
  editDialogVisible.value = false;
};

// 处理头像上传
const handleAvatarChange = (fileUrls) => {
  editForm.value.avatar = fileUrls;
};

watch(
  () => userStore.userInfo,
  () => {
    userInfo.value = userStore.getUserInfo();
  },
  {
    immediate: true,
  }
);
</script>

<style lang="scss" scoped>
.personal-center {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;

  .nav-bar {
    display: flex;
    justify-content: center;
    margin-bottom: 20px;
    background-color: var(--publicSubColor);
    border-radius: 8px;
    padding: 10px;

    .nav-item {
      padding: 10px 20px;
      color: #fff;
      cursor: pointer;
      transition: all 0.3s ease;

      &:hover {
        background-color: rgba(255, 255, 255, 0.1);
        border-radius: 4px;
      }

      &.active {
        color: #909399;
        border-bottom: 2px solid #909399;
      }
    }
  }

  .content {
    .profile-section {
      .profile-card {
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);

        .avatar-section {
          text-align: center;
          margin-bottom: 20px;

          .edit-btn {
            margin-top: 10px;
          }
        }

        .info-list {
          margin-bottom: 20px;
        }

        .edit-info-btn {
          width: 100%;
        }
      }
    }

    .other-section {
      h3 {
        margin-bottom: 20px;
        color: #2d3a4b;
      }
    }
  }
}
</style>
