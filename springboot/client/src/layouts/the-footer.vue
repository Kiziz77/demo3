<template>
  <div class="footer">
    <p>{{ config.projectTitle }}</p>
  </div>
</template>

<script setup>
import config from '@/config/config.js';

</script>

<style lang="scss" scoped>
.footer {
  background: linear-gradient(135deg,#f8f9fa 0%, #e9ecef 25%, #dee2e6 50%, #6c757d 100%  ); /* 渐变背景 */
  color: white;
  text-align: center;
  padding: 20px;
  font-family: "Arial", sans-serif;
  font-size: 1rem;

  bottom: 0;
  left: 0;

  box-shadow: 0px -4px 10px rgba(0, 0, 0, 0.1); /* 底部阴影效果 */

  p {
    margin: 0;
    font-weight: 500;
    letter-spacing: 1px;
  }

  /* 适应小屏幕的字体大小调整 */
  @media (max-width: 600px) {
    font-size: 0.9rem;
  }
}
</style>
