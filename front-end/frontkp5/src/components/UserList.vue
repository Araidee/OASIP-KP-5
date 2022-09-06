<script setup>
import { ref, computed } from "vue";
defineEmits(["delete, edit"]);
const props = defineProps({
    users: {
    type: Array,
    default: [],
  },
});

const myUser = computed(() => {
  let userList = [];
  props.users.forEach((element) => {
    userList.push({
      id: element.id,
      name: element.name,
      email: element.email,
      role: element.role,
      createdOn: element.createdOn,
      updatedOn: element.updatedOn,
    });
  });
  return userList;
});

const editName = ref("");
const editEmail = ref("");
const editRole = ref("");
const nameMax = 100;
const UserDetails = ref({});
const clearInput = () => {
  editName.value = "";
  editEmail.value = "";
  editRole.value = "";
};
const getUserById = async (id) => {
  // const res = await fetch(`http://202.44.9.103:8080/kp5/api/users/${id}`);
  const res = await fetch(`https://intproj21.sit.kmutt.ac.th/kp5/api/users/${id}`);
  if (res.status === 200) {
    UserDetails.value = await res.json();
    console.log(UserDetails.value)
  } else console.log("Error, cannot get data");
};
</script>

<template>
  <div>
    <div class="overflow-x-auto w-full place-items-center">
      <table class="table w-full">
        <thead>
          <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Role</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="user in myUser" :key="user.id">
            <td>{{ user.name }}</td>
            <td>{{ user.email }}</td>
            <td>{{ user.role }}</td>
            <td>
              <div class="flex items-stretch">
                <div>
                  <label
                    for="detail-modal"
                    class="btn modal-button"
                    @click="getUserById(user.id)"
                    >Details</label>
                </div>
              </div>
              <div class="px-4">
                  <label
                    for="edit-modal"
                    class="btn btn-ghost btn-circle"
                    @click="getUserById(user.id)"
                  >
                    <img src="/edit.svg" class="h-8 w-8" />
                  </label>

                  <button
                    class="btn btn-ghost btn-circle"
                    @click="$emit('delete', user.id)"
                  >
                    <img src="/delete.svg" class="h-8 w-8" />
                  </button>
                </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <input type="checkbox" id="detail-modal" class="modal-toggle" />
    <div class="modal">
      <div class="modal-box">
        <h3 class="font-bold text-lg">Details</h3>
        <p class="py-4">Name: {{ UserDetails.name }}</p>
        <p class="py-4">Email: {{ UserDetails.email }}</p>
        <p class="py-4">Role: {{ UserDetails.role }}</p>
        <p class="py-4">
          Created On:
          {{
            new Date(UserDetails.createdOn).toLocaleDateString("th-TH")
          }},
          {{
            new Date(UserDetails.createdOn).toLocaleTimeString("en-CA", {
              timeZoneName: 'short',
              hour: "2-digit",
              minute: "2-digit",
              hour12: true,
            })
          }}
        </p>
        <p class="py-4">
          Updated On:
          <!-- {{
            new Date(UserDetails.updatedOn).toLocaleTimeString("en-CA",{timeZoneName: 'short'})
          }} -->
          {{
            new Date(UserDetails.updatedOn).toLocaleDateString("th-TH")
          }},
          {{
            new Date(UserDetails.updatedOn).toLocaleTimeString("en-CA", {
              timeZoneName: 'short',
              hour: "2-digit",
              minute: "2-digit",
              hour12: true
            })
          }}
        </p>
        <div class="modal-action">
          <label for="detail-modal" class="btn">Yay!</label>
        </div>
      </div>
    </div>
    <input type="checkbox" id="edit-modal" class="modal-toggle" />
    <div class="modal">
      <div class="modal-box">
        <label
          for="edit-modal"
          class="btn btn-sm btn-circle absolute right-2 top-2"
          @click="clearInput()"
          >✕</label>
        <h3 class="font-bold text-lg">Edit UserDetails</h3>
        <div class="card-body items-center">
          Name:
          <input
            type="text"
            v-model="editName"
            placeholder="name... (Optional)"
            class="input input-bordered input-success w-full max-w-xs"
          />
          <p class="text-sm">{{editName.length}}/{{nameMax}}</p>
        </div>
        <div class="card-body items-center">
          Email:
          <input
            type="text"
            v-model="editEmail"
            placeholder="email... (Optional)"
            class="input input-bordered input-success w-full max-w-xs"
          />
          <!-- <p class="text-sm">{{editEventNotes.length}}/{{notesMax}}</p> -->
        </div>
        <div class="card-body items-center">
          Role:
          <input
            type="text"
            v-model="editRole"
            placeholder="role... (Optional)"
            class="input input-bordered input-success w-full max-w-xs"
          />
          <!-- <p class="text-sm">{{editEventNotes.length}}/{{notesMax}}</p> -->
        </div>
        <div>
          <label
            for="edit-modal"
            class="btn"
            @click="
              $emit('edit', {
                id: UserDetails.id,
                name: editName,
                email: editEmail,
                role: editRole
              });
              clearInput();
            "
            >Edit</label
          >
        </div>
      </div>
    </div>
  </div>
</template>

<style></style>