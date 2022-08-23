<script setup>
import { ref, onBeforeMount } from "vue";
import AddUser from "../components/AddUser.vue";

const users = ref([]);
onBeforeMount(async () => {
  await getUsers();
});
//GET
const getUsers = async () => {
  // const res = await fetch("http://202.44.9.103:8080/kp5/api/users");
  const res = await fetch("http://intproj21.sit.kmutt.ac.th/kp5/api/events")
  // const res = await fetch(`${import.meta.env.LOCAL_URL}/api/events`)
  if (res.status === 200) {
    events.value = await res.json();
  } else console.log("Error, cannot get data");
};

//POST
const createNewUser = async (newUser) => {
    // const res = await fetch("http://202.44.9.103:8080/kp5/api/users", {
     const res = await fetch(`http://intproj21.sit.kmutt.ac.th/kp5/api/events`, {
    method: "POST",
    headers: {
      "content-type": "application/json",
    },
    body: JSON.stringify(newUser),
  });
  if (res.status === 201) {
    const addedUser = await res.json();
    users.value.push(addedUser);
    alert('Registered!')
    console.log("registered successfully");
  } else console.log("error, cannot register");
};
</script>

<template>
  <div>
    <AddUser
      :users="users"
      @addUser="createNewUser"
    />
  </div>
</template>