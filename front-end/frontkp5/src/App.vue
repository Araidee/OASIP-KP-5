<script setup>
import NavBar from './components/NavBar.vue'
</script>

<template>
<div>
<NavBar/>




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
  color: #2d353d;
}
body {
  background-color: #E5E7E5;
}

h1 {
  color: black;
  text-align: center;
  font-family:"Lucida Console";
}

p {
  font-family: verdana;
  font-size: 20px;
}
</style>
