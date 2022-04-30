<script setup>

</script>

<template>
<div>
<router-link :to="{ name: 'Home'}">Home | </router-link> 
<router-link :to="{ name: 'About'}">About us</router-link>

<h1>Yo wtf why nigga</h1>
</div>
<div>
  <router-view></router-view>
</div>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
