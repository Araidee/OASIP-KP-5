<script setup>
import { ref, computed } from "vue";
defineEmits(["delete", "edit"]);
const props = defineProps({
  events: {
    type: Array,
    default: [],
  },
});

const myEvent = computed(() => {
  let eventList = [];
  props.events.forEach((element) => {
    eventList.push({
      id: element.id,
      bookingEmail: element.bookingEmail,
      bookingName: element.bookingName,
      eventStartTime: element.eventStartTime,
      eventNotes: element.eventNotes,
      eventCategory: element.eventCategory,
      eventDuration: element.eventDuration,
    });
  });
  return eventList;
});

const editEventStartTime = ref('')
const editEventNotes = ref('')
const clearInput = () => {
  editEventStartTime.value = ''
  editEventNotes.value = ''
}

const EventDetails = ref([]);
const getEventById = async (id) => {
  // const res = await fetch(`http://202.44.9.103:8080/kp5/api/events/${id}`);
  const res = await fetch(
    `http://intproj21.sit.kmutt.ac.th/kp5/api/events/${id}`
  );
  if (res.status === 200) {
    EventDetails.value = await res.json();
  } else console.log("Error, cannot get data");
};
</script>

<template>
  <div>
    <div class="overflow-x-auto w-full place-items-center">
      <table class="table w-full" v-show="!isEmpty">
        <thead>
          <tr>
            <th>Name</th>
            <th>Category</th>
            <th>Date</th>
            <th>Time</th>
            <th>Duration(mins)</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="event in myEvent" :key="event.id">
            <td>{{ event.bookingName }}</td>
            <td>{{ event.eventCategory.eventCategoryName }}</td>
            <td>
              {{ new Date(event.eventStartTime).toLocaleDateString("th-TH") }}
            </td>
            <td>
              {{
                new Date(event.eventStartTime).toLocaleTimeString("en-TH", {
                  hour: "2-digit",
                  minute: "2-digit",
                  hour12: true,
                })
              }}
            </td>
            <td>{{ event.eventDuration }}</td>
            
         <td>
              <div class="flex items-stretch">
              <div>
              <label
                for="detail-modal"
                class="btn modal-button"
                @click="getEventById(event.id)"
                >Details</label
              >
              </div>
              <div class="px-4">
              <label
                for="edit-modal"
                class="btn btn-ghost btn-circle"
                @click="getEventById(event.id)"
              >
                <img src="/edit.svg" class="h-8 w-8">
              </label>
    
              <button class="btn btn-ghost btn-circle"
                @click="$emit('delete', event.id)"
              >
                <img src="/delete.svg" class="h-8 w-8" >
              </button></div>
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
        <p class="py-4">Name: {{ EventDetails.bookingName }}</p>
        <p class="py-4">Email: {{ EventDetails.bookingEmail }}</p>
        <p class="py-4">Category: {{EventDetails.eventCategory}}</p>
        <!-- เกมาก -->
        <p class="py-4">
          Datetime:
          {{
            new Date(EventDetails.eventStartTime).toLocaleDateString("th-TH")
          }},
          {{
            new Date(EventDetails.eventStartTime).toLocaleTimeString("en-TH", {
              hour: "2-digit",
              minute: "2-digit",
              hour12: true,
            })
          }}
        </p>
        <p class="py-4">Duration: {{ EventDetails.eventDuration }} minutes</p>
        <p class="py-4">
          Notes :
          {{ EventDetails.eventNotes === null ? "-" : EventDetails.eventNotes }}
        </p>
        <div class="modal-action">
          <label for="detail-modal" class="btn">Yay!</label>
        </div>
      </div>
    </div>
    <input type="checkbox" id="edit-modal" class="modal-toggle" />
    <div class="modal">
      <div class="modal-box">
         <label for="edit-modal" class="btn btn-sm btn-circle absolute right-2 top-2" @click="clearInput()">✕</label>
        <h3 class="font-bold text-lg">Reschedules</h3>
        <label for="appt">Select a time:</label>
        <!-- <input type="date" value="2022-05-08T" min="2022-05-10" max="2022-12-31" required> -->
        <input class="badge badge-green badge-outline" type="datetime-local" id="meeting-time"
       name="meeting-time" 
       min="2022-05-10T00:00" max="2022-12-31T00:00" required v-model="editEventStartTime"><br>
       <div class="card-body items-center  ">  
        Notes: <input type="text" v-model="editEventNotes" placeholder="Note here... (Optional)" class="input input-bordered input-success w-full max-w-xs"/><br>
        </div>
        <div>
          <label for="edit-modal" class="btn" @click="$emit('edit', {id: EventDetails.id ,eventStartTime: new Date(editEventStartTime), eventNotes: editEventNotes});clearInput()">Edit</label>
        </div>
      </div>
    </div>
  </div>
</template>

<style></style>
