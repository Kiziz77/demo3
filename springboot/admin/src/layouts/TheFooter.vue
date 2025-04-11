<template>
  <el-footer class="footer">
    <span class="footer-title">{{ config.projectTitle }}</span>
  </el-footer>
</template>

<script setup>
import config from "@/config/config";
</script>

<style lang="scss" scoped>
.el-footer {
  background-color: var(--publicMainColor);
  line-height: 30px;
  height: 60px; 
  display: flex;
  justify-content: center; 
  align-items: center; 
  box-shadow: 0px -2px 10px rgba(0, 0, 0, 0.1); 
  border-top: 2px solid #ccc; 
  padding: 10px 20px; 
}

.footer-title {
  font-size: 18px;
  color: white; 
  font-weight: bold; 
  text-transform: uppercase; 
  letter-spacing: 1px; 
}


</style>
