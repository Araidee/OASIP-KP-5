<script setup>
import { useRouter } from "vue-router";
import { cookieData } from "../stores/cookieData.js";
import jwt_decode from "jwt-decode";
import { loginState } from "../stores/loginState.js";
import Login from "../components/Login.vue";
const cookie = cookieData();
const appRouter = useRouter();
const isLogin = loginState();
const url = "https://intproj21.sit.kmutt.ac.th/kp5/api"
// const url = "http://202.44.9.103:8080/kp5/api";
//POST
const login = async (user) => {
  // const res = await fetch("http://localhost:8080/api/jwt/login", {
  const res = await fetch(`${url}/jwt/login`, {
    method: "POST",
    headers: {
      "content-type": "application/json",
    },
    body: JSON.stringify(user),
  });
  if (res.status === 200) {
    const token = await res.json();
    cookie.setCookie(Object.keys(token)[0], Object.values(token)[0], 7);
    cookie.setCookie(Object.keys(token)[1], Object.values(token)[1], 7);
    let decoded = jwt_decode(cookie.getCookie(Object.keys(token)[0]));
    isLogin.setLogin();
    isLogin.setLoginUser(decoded);
    alert("Login successfully!");
    appRouter.push({ path: '/', replace: true });
    console.log("login successfully");
  } else if (res.status === 404) {
    alert("Email doesn't exist");
    console.log("email doesn't exist");
  } else if (res.status === 401) {
    alert("Password not match");
    console.log("password not match");
  } else console.log("error, something went wrong");
};
</script>

<template>
  <div>
    <Login @login="login" />
  </div>
</template>
