<script setup>
import { ref, computed, defineAsyncComponent } from "vue";
defineEmits(["addEvent"]);
const props = defineProps({
  events: {
    type: Array,
    default: [],
  },
  eventCategories: {
    type: Array,
    default: [],
  },
});

const bookingName = ref("");
const bookingEmail = ref("");
const eventStartTime = ref("");
const eventNotes = ref("");
const eventCategory = ref({});

// const eventDuration = computed((id)=> {

// })

const clearInput = () => {
  bookingName.value = "";
  bookingEmail.value = "";
  eventStartTime.value = "";
  eventCategory.value = "";
  eventNotes.value = "";
};

function emailValidate() {
  let regx =
    /^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
  if (bookingEmail.value.match(regx)) {
    return true;
  } else {
    alert("Sorry! Incorrect Email Address");
    return false;
  }
}
</script>

<template>
  <div>
    <div class="card-compact w-3/4 bg-base-100 shadow-xl position1">
      <div class="card-body items-center">
        <h1 class="text-2xl font-bold card-title">Booking</h1>
      </div>
      <div class="card-body items-center">
        Name:
        <input
          type="text"
          v-model="bookingName"
          placeholder="Type Name here"
          class="input input-bordered input-success w-full max-w-xs"
          maxlength="100"
          required
        />
        <small></small>
      </div>
      <div class="card-body items-center">
        Email:
        <input
          type="text"
          id="email"
          v-model="bookingEmail"
          placeholder="Type Email here"
          class="input input-bordered input-success w-full max-w-xs"
          required
        /><br />
      </div>
      <div class="card-body items-center">
        Category:
        <select
          class="select select-success w-full max-w-xs"
          v-model="eventCategory"
          required
          
        >
          <option disabled selected>-- Category --</option>
          <option
            v-for="eventCategory in eventCategories"
            :value="{
              id: eventCategory.id,
              eventDuration: eventCategory.eventDuration,
            }"
          >
            {{ eventCategory.eventCategoryName }}
          </option>
        </select>
      </div>
      <div class="card-body items-center">
        <label for="appt">Select a time:</label>
        <!-- <input type="date" value="2022-05-08T" min="2022-05-10" max="2022-12-31" required> -->
        <input
          class="badge badge-green badge-outline"
          type="datetime-local"
          id="meeting-time"
          name="meeting-time"
          v-model="eventStartTime"
          min="2022-05-12T17:05"
          max="2022-12-31T00:00"
          required
        /><br />
        <!-- <input type="time" id="appt" name="appt"
       value="22:00" required>
        </div> -->

        <!-- อยากให้เวลาadd จะเด้งdurationให้ว่ากี่นาที (ดึงจาก eventCategory.eventDuration) -->
        <!-- <div>
    Duration: <select class="select select-success w-full max-w-xs"  v-model="eventDuration">
    <option disabled selected>Duration:</option>
    </select><br>
    </div> -->
        <div class="card-body items-center">
          Notes:
          <input
            type="text"
            placeholder="Note here... (Optional)"
            class="input input-bordered input-success w-full max-w-xs"
            v-model="eventNotes"
            maxlength="500"
          /><br />
        </div>
        <div class-="card-actions justify-end">
          <button
            class="btn btn-primary btn-success"
            @click="
              emailValidate()
                ? $emit('addEvent', {
                    bookingEmail: bookingEmail,
                    bookingName: bookingName,
                    eventStartTime: new Date(eventStartTime),
                    eventNotes: eventNotes,
                    eventCategory: { id: eventCategory.id },
                    eventDuration: eventCategory.eventDuration,
                  })
                : '';
              clearInput();
            "
          >
            Book
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.position1{
   position: relative;
   left: 15%;
 }</style>
