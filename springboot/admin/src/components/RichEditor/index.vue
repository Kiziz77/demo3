<template>
  <div style="border: 1px solid #ccc; height: 500px;width: 50%;">
    <Toolbar style="border-bottom: 1px solid #ccc" :editor="editorRef" :defaultConfig="toolbarConfig" :mode="mode" />
    <Editor
      style="height: 300px; overflow-y: hidden"
      v-model="valueHtml"
      :defaultConfig="editorConfig"
      :mode="mode"
      @onCreated="handleCreated"
      @onChange="handleChange"
    />
  </div>
</template>

<script setup>
import "@wangeditor/editor/dist/css/style.css"; // 引入 css
import { onBeforeUnmount, ref, shallowRef, watch, onMounted } from "vue";
import { Editor, Toolbar } from "@wangeditor/editor-for-vue";
import config from "@/config/config.js";
import { useUserStore } from "@/stores/user.js";
import { ElMessage } from "element-plus";
const userStore = useUserStore();
// 编辑器实例，必须用 shallowRef
const editorRef = shallowRef();

// 内容 HTML
const valueHtml = ref("");

const toolbarConfig = {};
const editorConfig = {
  placeholder: "请输入内容...",
  MENU_CONF: {},
};
editorConfig.MENU_CONF["uploadImage"] = {
  server: config.baseUrl + "/file/upload",
  fieldName: "file",
  // 单个文件的最大体积限制，默认为 2M
  maxFileSize: 1 * 1024 * 1024,
  // 最多可上传几个文件，默认为 100
  maxNumberOfFiles: 10,
  // 选择文件时的类型限制，默认为 ['image/*'] 。如不想限制，则设置为 []
  allowedFileTypes: [],
  meta: {
    Authorization: userStore.getToken(),
  },
  onFailed(file, res) {
    // TS 语法
    // JS 语法
    ElMessage.error(`${file.name} 上传失败`);
  },
  customInsert(res, insertFn) {
    const url = config.baseUrl + "/upload/" + res.data;
    const href = config.baseUrl + "/upload/" + res.data;
    const alt = res.data;
    // 从 res 中找到 url alt href ，然后插入图片
    insertFn(url, alt, href);
  },
};
editorConfig.MENU_CONF["uploadVideo"] = {
  server: config.baseUrl + "/file/upload",
  fieldName: "file",
  // 单个文件的最大体积限制，默认为 10M
  maxFileSize: 10 * 1024 * 1024,
  // 最多可上传几个文件，默认为 5
  maxNumberOfFiles: 3,
  // 选择文件时的类型限制，默认为 ['video/*'] 。如不想限制，则设置为 []
  allowedFileTypes: [],
  meta: {
    Authorization: userStore.getToken(),
  },
  onFailed(file, res) {
    // JS 语法
    ElMessage.error(`${file.name} 上传失败`);
  },
  // 上传错误，或者触发 timeout 超时
  onError(file, err, res) {
    // JS 语法
    ElMessage.error(`${file.name} 上传出错`);
  },
  customInsert(res, insertFn) {
    // JS 语法
    // res 即服务端的返回结果
    const url = config.baseUrl + "/upload/" + res.data;
    // 从 res 中找到 url poster ，然后插入视频
    insertFn(url, "");
  },
};
//代码高亮  codeLangs 配置代码语言
editorConfig.MENU_CONF["codeSelectLang"] = {
  // 代码语言
  codeLangs: [
    { text: "CSS", value: "css" },
    { text: "HTML", value: "html" },
    { text: "XML", value: "xml" },
    // 其他
  ],
};
const mode = "default"; // 或 'simple'

// 组件销毁时，也及时销毁编辑器
onBeforeUnmount(() => {
  const editor = editorRef.value;
  if (editor == null) return;
  editor.destroy();
});

const handleCreated = (editor) => {
  editorRef.value = editor; // 记录 editor 实例，重要！
};

const props = defineProps({
  initValue: {
    type: String,
    default: "",
  },
});

onMounted(() => {
  valueHtml.value = props.initValue;
});

const emit = defineEmits(["change"]);
const handleChange = () => {
  let htmlContent = editorRef.value.getHtml();
  // 清理多余的 <p><br></p> 标签
  htmlContent = htmlContent.replace(/<p>\s*<br>\s*<\/p>/g, "");
  emit("change", htmlContent);
};

watch(
  () => props.initValue,
  (newValue) => {
    valueHtml.value = newValue;
  },
  {
    immediate: true,
  }
);
</script>
