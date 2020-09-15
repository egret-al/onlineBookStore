<template>
  <div class="main">
    <!-- <registry-main :tip="tip" :questions="questions" :submit="submit" @submit="submitHandler"></registry-main> -->
    <registry-header></registry-header>
    <registry-main></registry-main>
  </div>
</template>

<script>
import RegistryHeader from './components/RegistryHeader'
import RegistryMain from './components/RegistryMain'

export default {
  components: {
    RegistryHeader,
    RegistryMain
  },
  data() {
    return {
      
    }
  },
  methods: {
    
  },
  mounted() {},
}
</script>
<style lang='scss' scoped></style>
