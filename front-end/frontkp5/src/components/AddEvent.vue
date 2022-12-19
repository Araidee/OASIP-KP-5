<script setup>
import { ref } from "vue";
import { loginState } from "../stores/loginState.js";
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
const isLogin = loginState();
const bookingName = ref("");
const bookingEmail = ref("");
const eventStartTime = ref("");
const eventNotes = ref("");
const eventCategory = ref({});
const attachment = ref("")
const notesMax = 500;
const bookingNameMax = 100;
const successInput = "input input-bordered input-success w-full inputwdt";
const errorInput = "input input-bordered input-error w-full inputwdt";
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

const checkFile = () => {
  let currentTime = new Date(new Date().toISOString()).getTime();
  attachment.value = currentTime + "_" + document.getElementById("uploadFile").files[0].name
  alert(attachment.value)
  console.log(document.getElementById("uploadFile").files[0].size)
  document.getElementById("uploadFile").disabled = true;
}
const clearFile = () => {
  attachment.value = ""
  document.getElementById("uploadFile").disabled = false;
  document.getElementById("uploadFile").value = null;
}
</script>

<template>
  <div>
    <div class="card-compact w-3/4 bg-base-100 shadow-xl position1">
      <div class="card-body items-center">
        <h1 class="text-2xl font-bold center">Booking</h1>
      </div>
      <div class="center font-bold">
        Name: <span style="color: red">*</span><br />
        <input
          type="text"
          v-model="bookingName"
          placeholder="Type Name here"
          :class="bookingName == '' ? errorInput : successInput"
          maxlength="100"
          required
        />
        <p class="text-sm">{{ bookingName.length }}/{{ bookingNameMax }}</p>
      </div>
      <div class="center font-weight-bold">
        Email: <span style="color: red">*</span><br />
        <input
          type="text"
          id="email"
          v-model="bookingEmail"
          placeholder="Type Email here"
          :class="
            bookingEmail != '' &&
            (bookingEmail == isLogin.loginUser.email ||
              isLogin.loginUser.role == 'Guest')
              ? successInput
              : errorInput
          "
          required
        />
        <p
          :class="
            bookingEmail == isLogin.loginUser.email ||
            isLogin.loginUser.role == 'Guest'
              ? 'hidden'
              : 'text-sm text-red-500'
          "
        >
          Booking email must be the same email as the student's email
        </p>
      </div>
      <div class="center font-weight-bold">
        Category: <span style="color: red">*</span><br />
        <select
          :class="
            Object.keys(eventCategory).length == 0 ? errorInput : successInput
          "
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
      <div class="center">
        <label for="appt">Select a time: </label
        ><span style="color: red">*</span><br />
        <!-- <input type="date" value="2022-05-08T" min="2022-05-10" max="2022-12-31" required> -->
        <input
          class="badge badge-green badge-outline"
          type="datetime-local"
          id="meeting-time"
          name="meeting-time"
          v-model="eventStartTime"
          min="2022-05-24T13:00"
          max="2022-12-31T00:00"
          required
        /><br /><br />
        <!-- <input type="time" id="appt" name="appt"
       value="22:00" required>
        </div> -->

        <!-- อยากให้เวลาadd จะเด้งdurationให้ว่ากี่นาที (ดึงจาก eventCategory.eventDuration) -->
        <!-- <div>
    Duration: <select class="select select-success w-full max-w-xs"  v-model="eventDuration">
    <option disabled selected>Duration:</option>
    </select><br>
    </div> -->
        <div class="inputwdt">
          Notes:
          <!-- <input
            type="text"
            placeholder="Note here... (Optional)"
            class="input input-bordered input-success w-full max-w-xs"
            v-model="eventNotes"
            maxlength="500"
          /> -->
          <textarea
            class="textarea textarea-success textareacss"
            placeholder="Note here... (Optional)"
            v-model="eventNotes"
            maxlength="500"
          ></textarea>
          <p class="text-sm">{{ eventNotes.length }}/{{ notesMax }}</p>
        </div>
        <div class="flex">
          <div class="mb-3 w-96">
            <label
              for="uploadFile"
              class="form-label inline-block mb-2 text-gray-700"
              >Upload File</label
            >
            <input
              class="form-control block w-full px-3 py-1.5 text-base font-normal text-gray-700 bg-white bg-clip-padding border border-solid border-gray-300 rounded transition ease-in-out m-0 focus:text-gray-700 focus:bg-white focus:border-blue-600 focus:outline-none"
              type="file"
              @change="checkFile"
              id="uploadFile"
              multiple
            />
            <button
                  class="btn bg-red-400 my-2"
                  @click="clearFile">Clear File</button>
        </div>
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
                    attachment: attachment
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
.position1 {
  position: relative;
  left: 15%;
}

.textareacss {
  width: 500px;
  height: 180px;
}

.inputwdt {
  width: 500px;
}

.center {
  margin: auto;
  width: 48%;
  padding: 10px;
  font-weight: bold;
}
</style>
