<script setup>
import { ref, computed, defineAsyncComponent } from "vue";
defineEmits(["editCategory"]);
const props = defineProps({
  eventCategories: {
    type: Array,
    default: [],
  },
});
const eventCategoryDetails = ref({});
const editEventCategoryName = ref('');
const editEventCategoryDescription = ref('');
const editEventCategoryDuration = ref('')
const getEventCategoryById = async (id) => {
  // const res = await fetch(
  //   `http://202.44.9.103:8080/kp5/api/eventCategories/${id}`
  // );
  const res = await fetch(
    `http://intproj21.sit.kmutt.ac.th/kp5/api/eventCategories/${id}`
  );
  if (res.status === 200) {
    eventCategoryDetails.value = await res.json();
    editEventCategoryName.value = eventCategoryDetails.value.eventCategoryName
    editEventCategoryDescription.value = eventCategoryDetails.value.eventCategoryDescription
    editEventCategoryDuration.value = eventCategoryDetails.value.eventDuration
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
      <table class="table w-full" v-show="!isEmpty">
        <thead>
          <tr>
            <th>Name</th>
            <th>Notes</th>
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
            class="input input-bordered input-success w-full max-w-xs"
          />
          Duration:
          <input
            type="number"
            v-model="editEventCategoryDuration"
            min="0"
            max="480"
            placeholder="duration... (Optional)"
            class="input input-bordered input-success w-full max-w-xs"
          />
            Notes:
            <input
              type="text"
              v-model="editEventCategoryNotes"
              placeholder="Note here... (Optional)"
              class="input input-bordered input-success w-full max-w-xs"
            /><br />
          </div>
          <div>
            <label
              for="edit-modal"
              class="btn"
              @click="
                $emit('editCategory', {
                  eventCategoryId: eventCategoryDetails.eventCategoryId,
                  eventCategoryName: editEventCategoryName,
                  eventCategoryDescription: editEventCategoryDescription,
                  eventDuration: editEventCategoryDuration
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
