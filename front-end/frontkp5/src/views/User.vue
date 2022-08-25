<script setup>
import UserList from "../components/UserList.vue";
import { ref, onBeforeMount } from "vue";
const users = ref([]);
onBeforeMount(async () => {
  await getUsers();
});
//GET
const getUsers = async () => {
  // const res = await fetch("http://202.44.9.103:8080/kp5/api/users");
  const res = await fetch("http://intproj21.sit.kmutt.ac.th/kp5/api/users");
    // const res = await fetch(`${import.meta.env.LOCAL_URL}/api/users`)
    // const tres = await fetch(`${import.meta.env.LOCAL_URL}`)
  if (res.status === 200) {
    users.value = await res.json();
  } else console.log("Error, cannot get data");
};
//DELETE
const removeUser = async (removeUserId) => {
  let confirmDelete = ref(false);
  confirmDelete.value = confirm(`Are you sure to delete this user?`);
  if (confirmDelete.value) {
    // const res = await fetch(
    //   `http://202.44.9.103:8080/kp5/api/events/${removeEventId}`,
       const res = await fetch(
       `http://intproj21.sit.kmutt.ac.th/kp5/api/users/${removeUserId}`,
      {
        method: "DELETE",
      }
    );
    if (res.status === 200) {
      users.value = users.value.filter((user) => user.id !== removeUserId);
      alert('User removed!')
      console.log("deleted successfullly");
    } else console.log("error, cannot delete");
  } else console.log("Delete was canceled");
};
//PUT
const editUser = async (editingUser) => {
  // const res = await fetch(
  //   `http://202.44.9.103:8080/kp5/api/events/${editingEvent.id}`,
    const res = await fetch(
      `http://intproj21.sit.kmutt.ac.th/kp5/api/users/${editingUser.id}`,
    {
      method: "PUT",
      headers: {
        "content-type": "application/json",
      },
      body: JSON.stringify({
        userName: editingUser.name,
        userEmail: editingUser.email,
        userRole: editingUser.role
      }),
    }
  );

  if (res.status === 200) {
    const moddedUser = await res.json();
    users.value = users.value.map((user) =>
      users.id === moddedUser.id
        ? {
            ...user,
            name: editingUser.name,
            email: editingUser.email,
            role: editingUser.role
          }
        : user
    );
    alert('Edited!')
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
        <UserList
          :users="users" @delete="removeUser" @edit="editUser"
        />
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
