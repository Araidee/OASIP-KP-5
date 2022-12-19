<script setup>
import { onBeforeMount } from "vue";
import jwt_decode from "jwt-decode";
import { cookieData } from "./stores/cookieData.js";
import NavBar from "./components/NavBar.vue";
import { loginState } from "./stores/loginState";


const cookie = cookieData();
const isLogin = loginState();
onBeforeMount(() => {
  isLogin.checkLogin();
  if (isLogin.isLogin) {
    let decoded = jwt_decode(cookie.getCookie("token"));
    isLogin.setLoginUser(decoded);
  }
});




</script>

<template>
  <div>
    <NavBar />

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
  background-color: #e5e7e5;
}

h1 {
  color: black;
  text-align: center;
  font-family: "Lucida Console";
}

p {
  font-family: verdana;
  font-size: 20px;
}
</style>
