<script setup>
import { ref, computed, defineAsyncComponent } from "vue";
import { cookieData } from "../stores/cookieData.js";
import { loginState } from "../stores/loginState.js";
defineEmits(["editCategory"]);
const props = defineProps({
  eventCategories: {
    type: Array,
    default: [],
  },
});
const url = "https://intproj21.sit.kmutt.ac.th/kp5/api"
// const url = "http://202.44.9.103:8080/kp5/api";
const cookie = cookieData();
const isLogin = loginState();
const successInput = "input input-bordered input-success w-full max-w-xs";
const errorInput = "input input-bordered input-error w-full max-w-xs";
const descriptionMax = 500;
const eventCategoryDetails = ref({});
const editEventCategoryName = ref("");
const editEventCategoryDescription = ref("");
const editEventCategoryDuration = ref("");
const getEventCategoryById = async (id) => {
  // const res = await fetch(
  //   `http://202.44.9.103:8080/kp5/api/eventCategories/${id}`
  // );
  const res = await fetch(`${url}/eventCategories/${id}`, {
    method: "GET",
    headers: {
      Authorization: "Bearer " + cookie.getCookie("token"),
    },
  });
  if (res.status === 200) {
    eventCategoryDetails.value = await res.json();
    editEventCategoryName.value = eventCategoryDetails.value.eventCategoryName;
    editEventCategoryDescription.value =
      eventCategoryDetails.value.eventCategoryDescription;
    editEventCategoryDuration.value = eventCategoryDetails.value.eventDuration;
  } else if (res.status === 401) {
    let res = await res.json();
    if (
      res.message
        .toLowerCase()
        .match(
          "please send refresh token to /refresh to refresh token".toLowerCase()
        )
    ) {
      isLogin.refreshToken();
    } else alert("please login");
  } else console.log("Error, cannot get data");
};
// const clearInput = () => {
//     bookingName.value = ''
//     bookingEmail.value = ''
//     eventStartTime.value = ''
//     eventCategory.value = ''
//     eventNotes.value = ''
// }
</script>

<template>
  <div>
    <div class="overflow-x-auto w-full place-items-center">
      <table class="table w-full">
        <thead>
          <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Duration(mins)</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="eventCategory in eventCategories" :key="eventCategory.id">
            <td>{{ eventCategory.eventCategoryName }}</td>
            <td>{{ eventCategory.eventCategoryDescription }}</td>
            <td>{{ eventCategory.eventDuration }}</td>
            <td>
              <div class="px-4">
                <label
                  for="edit-modal"
                  class="btn btn-ghost btn-circle"
                  @click="getEventCategoryById(eventCategory.id)"
                >
                  <img src="/edit.svg" class="h-8 w-8" />
                </label>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
      <input type="checkbox" id="edit-modal" class="modal-toggle" />
      <div class="modal">
        <div class="modal-box">
          <label
            for="edit-modal"
            class="btn btn-sm btn-circle absolute right-2 top-2"
            >✕</label
          >
          <div class="card-body items-center">
            <h3 class="font-bold text-lg">Edit Category</h3>
            Category Name:
            <input
              type="text"
              v-model="editEventCategoryName"
              placeholder="Category name..."
              :class="editEventCategoryName == '' ? errorInput : successInput"
            />
            Duration:
            <input
              type="number"
              v-model="editEventCategoryDuration"
              min="1"
              max="480"
              placeholder="Duration... (1-480 mins)"
              :class="
                editEventCategoryDuration == '' ? errorInput : successInput
              "
            />
            Description:
            <input
              type="text"
              v-model="editEventCategoryDescription"
              placeholder="Description..."
              class="input input-bordered input-success w-full max-w-xs"
            />
            <p class="text-sm">
              {{ editEventCategoryDescription.length }}/{{ descriptionMax }}
            </p>
          </div>
          <div>
            <label
              for="edit-modal"
              class="btn"
              @click="
                $emit('editCategory', {
                  categoryId: eventCategoryDetails.categoryId,
                  eventCategoryName: editEventCategoryName,
                  eventCategoryDescription: editEventCategoryDescription,
                  eventDuration: editEventCategoryDuration,
                })
              "
              >Edit</label
            >
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style></style>
