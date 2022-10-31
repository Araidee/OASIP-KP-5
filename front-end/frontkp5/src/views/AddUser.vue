<script setup>
import { ref, onBeforeMount } from "vue";
import { cookieData } from "../stores/cookieData.js";
import AddUser from "../components/AddUser.vue";
const url = "https://intproj21.sit.kmutt.ac.th/kp5/api"
// const url = "http://202.44.9.103:8080/kp5/api";
const users = ref([]);
const cookie = cookieData();
onBeforeMount(async () => {
  await getUsers();
});
//GET
const getUsers = async () => {
  // const res = await fetch("http://202.44.9.103:8080/kp5/api/users");
  const res = await fetch(`${url}/users`, {
    method: "GET",
    headers: {
      Authorization: "Bearer " + cookie.getCookie("token"),
    },
  });
  // const res = await fetch(`${import.meta.env.LOCAL_URL}/api/events`)
  if (res.status === 200) {
    users.value = await res.json();
  } else if (res.status === 401) {
    alert("You are not authorized to access this page.");
  } else if (res.status === 403) {
    alert("You are not authorized to access this page.")
  }else console.log("Error, cannot get data");
};

//POST
const createNewUser = async (newUser) => {
  // const res = await fetch("http://202.44.9.103:8080/kp5/api/users", {
  const res = await fetch(`${url}/users`, {
    method: "POST",
    headers: {
      Authorization: "Bearer " + cookie.getCookie("token"),
      "content-type": "application/json",
    },
    body: JSON.stringify(newUser),
  });
  if (res.status === 201) {
    const addedUser = await res.json();
    users.value.push(addedUser);
    alert("Registered!");
    console.log("registered successfully");
  } else console.log("error, cannot register");
};
</script>

<template>
  <div>
    <AddUser :users="users" @addUser="createNewUser" />
  </div>
</template>
