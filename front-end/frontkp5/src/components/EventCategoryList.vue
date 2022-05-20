<script setup>
import { ref, computed , defineAsyncComponent  } from 'vue'
defineEmits(["editCategory"]);
const props = defineProps({
    eventCategories: {
        type: Array,
        default: []
    }
})
const editEventCategoryName = ref('')
const editEventCategoryNotes = ref('')
const editEventCategoryDetails = ref('')
const EventCategoryDetails = ref([]);
const getEventCategoryById = async (id) => {
  const res = await fetch(`http://202.44.9.103:8080/kp5/api/eventCategories/${id}`);
  // const res = await fetch(
  //   `http://intproj21.sit.kmutt.ac.th/kp5/api/events/${id}`
  // );
  if (res.status === 200) {
    EventCategoryDetails.value = await res.json();
  } else console.log("Error, cannot get data");
}
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
            <td>{{ eventCategory.eventCategoryName}}</td>
            <td>{{ eventCategory.eventCategoryDescription }}</td>
            <td>{{ eventCategory.eventDuration }}</td>
            <td>
              <div class="px-4">
              <label
                for="edit-modal"
                class="btn btn-ghost btn-circle"
                @click="getEventCategoryById(eventCategory.id)"
              >
                <img src="/edit.svg" class="h-8 w-8">
              </label>
              </div>
              </td>
          </tr>
        </tbody>
      </table>
      <div class="modal">
      <div class="modal-box">
         <label for="edit-modal" class="btn btn-sm btn-circle absolute right-2 top-2">✕</label>
        <h3 class="font-bold text-lg">Edit Category</h3>
        Category Name: <input type="text" v-model="editEventCategoryName" placeholder="category name... (Optional)" class="input input-bordered input-success w-full max-w-xs"/>
       <div class="card-body items-center  ">  
        Notes: <input type="text" v-model="editEventCategoryNotes" placeholder="Note here... (Optional)" class="input input-bordered input-success w-full max-w-xs"/><br>
        </div>
        <div>
          <label for="edit-modal" class="btn" @click="$emit('editCategory', {id: EventDetails.id,eventCategoryName: editEventCategoryName, eventCategoryNotes: editEventCategoryNotes})">Edit</label>
        </div>
      </div>
    </div>
    </div>
    </div>
</template>
 
<style>

</style>