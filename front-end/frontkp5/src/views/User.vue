<script setup>
import UserList from "../components/UserList.vue";
import { cookieData } from "../stores/cookieData";
import { ref, onBeforeMount } from "vue";
const url = "https://intproj21.sit.kmutt.ac.th/kp5/api"
// const url = "http://202.44.9.103:8080/kp5/api";
const cookie = cookieData();
const users = ref([]);
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
  // const res = await fetch(`${import.meta.env.LOCAL_URL}/api/users`)
  // const tres = await fetch(`${import.meta.env.LOCAL_URL}`)
  if (res.status === 200) {
    users.value = await res.json();
  } else if (res.status === 401) {
    alert("You are not authorized to access this page.");
  } else if (res.status === 403) {
    alert("You are not authorized to access this page.");
  } else console.log("Error, cannot get data");
};
//DELETE
const removeUser = async (removeUserId) => {
  let confirmDelete = ref(false);
  confirmDelete.value = confirm(`Are you sure to delete this user?`);
  if (confirmDelete.value) {
    // const res = await fetch(
    //   `http://202.44.9.103:8080/kp5/api/users/${removeUserId}`,
    const res = await fetch(`${url}/users/${removeUserId}`, {
      method: "DELETE",
      headers: {
        Authorization: "Bearer " + cookie.getCookie("token"),
      },
    });
    if (res.status === 200) {
      users.value = users.value.filter((user) => user.id !== removeUserId);
      alert("User removed!");
      console.log("deleted successfullly");
    } else console.log("error, cannot delete");
  } else console.log("Delete was canceled");
};
//PUT
const editUser = async (editingUser) => {
  // const res = await fetch(
  //   `http://202.44.9.103:8080/kp5/api/users/${editingUser.id}`,
  const res = await fetch(`${url}/users/${editingUser.id}`, {
    method: "PUT",
    headers: {
      Authorization: "Bearer " + cookie.getCookie("token"),
      "content-type": "application/json",
    },
    body: JSON.stringify({
      name: editingUser.name,
      email: editingUser.email,
      role: editingUser.role,
    }),
  });

  if (res.status === 200) {
    const moddedUser = await res.json();
    users.value = users.value.map((user) =>
      users.id === moddedUser.id
        ? {
            ...user,
            name: editingUser.name,
            email: editingUser.email,
            role: editingUser.role,
          }
        : user
    );
    alert("Edited!");
    console.log("edited successfully");
  } else console.log("error, cannot edit");
};
</script>

<template>
  <div>
    <div tabindex="0" class="collapse">
      <input type="checkbox" class="peer" />
      <div
        class="collapse-title bg-primary text-primary-content peer-checked:bg-secondary peer-checked:text-secondary-content"
      >
        <h1 class="text-3xl font-bold">Users</h1>
      </div>
      <div
        class="collapse-content bg-primary text-primary-content peer-checked:bg-secondary peer-checked:text-secondary-content"
      >
        <UserList :users="users" @delete="removeUser" @edit="editUser" />
        <div
          class="overflow-x-auto w-4/5 place-items-center"
          v-show="users.length == 0"
        >
          <h2>No users</h2>
        </div>
      </div>
    </div>
  </div>
</template>

<style></style>
