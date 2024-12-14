<template>
<!--  <div v-loading="loading" :style="'height:' + height">-->
<!--    <iframe -->
<!--      :src="url" -->
<!--      frameborder="no" -->
<!--      style="width: 100%; height: 100%" -->
<!--      scrolling="auto" />-->
<!--  </div>-->
  <div :style="'height:' + height">
    <iframe
        :src="url"
        frameborder="no"
        style="width: 100%; height: 100%;border-radius: 3px"
        scrolling="auto" />
  </div>
</template>

<script setup>

import { ElLoading } from 'element-plus'
import {onMounted} from "vue";

const props = defineProps({
  src: {
    type: String,
    required: true
  }
})

const height = ref(document.documentElement.clientHeight - 60 + "px;")
// const loading = ref(true)
const url = computed(() => props.src)


const openFullScreen2 = () => {
  const loading = ElLoading.service({
    lock: true,
    text: '加载监管中...',
    background: 'rgba(0, 0, 0, 0.7)',
  })
  setTimeout(() => {
    loading.close()
  }, 500)
}

onMounted(() => {
  // setTimeout(() => {
  //   // loading.value = false;
  //   loading.close() ;
  // }, 2000);
  openFullScreen2();
  window.onresize = function temp() {
    height.value = document.documentElement.clientHeight - 94.5 + "px;";
  };
})
</script>
