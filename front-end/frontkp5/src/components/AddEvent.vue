<script setup>
import { ref } from 'vue'
defineEmits(['addEvent'])
const props = defineProps({
    events: {
        type: Array,
        default: []
    }
})
const bookingName = ref('')
const bookingEmail = ref('')
const eventStartTime = ref('')
const eventNotes = ref('')
const eventCategory = ref('')
const eventDuration = ref('')
</script>
 
<template>
<div>
    <div>
        <form>
        <h1 class='text-2xl font-bold'>Booking</h1>
        Name: <input type="text" v-model="bookingName" placeholder="Type Name here" class="input input-bordered input-success w-full max-w-xs" />
        Email: <input type="text" v-model="bookingEmail" placeholder="Type Email here" class="input input-bordered input-success w-full max-w-xs" /><br>
        Category: <select class="select select-success w-full max-w-xs" v-model="eventCategory">
        <option disabled selected>Category:</option>
        <option v-for="event in events">{{event.eventCategory.eventCategoryName}}</option>
</select>
  <label for="appt">Select a time:</label>
<input class="badge badge-green badge-outline" type="datetime-local" id="meeting-time"
       name="meeting-time" value="2022-05-08T19:30"
       min="2022-05-10T00:00" max="2022-12-31T00:00"><br>

    Duration: <select class="select select-success w-full max-w-xs" v-model="eventDuration">
    <option disabled selected>Duration:</option>
    
    </select><br>
        Notes: <input type="text" placeholder="Note here" class="input input-bordered input-success w-full max-w-xs" v-model="eventNotes"/><br>
        <button class="btn btn-primary btn-success" @click="$emit('addEvent',{
            bookingEmail: bookingEmail,
            bookingName: bookingName,
            eventStartTime: eventStartTime,
            eventNotes: eventNotes,
            eventCategory: eventCategory,
            eventDuration: eventDuration
            })
            ">Book</button>
            </form>

    </div>
</div>
</template>
 
<style>
.textbox {
    background: #F1F1F1 url(/images/input-text-40.png) no-repeat;
    background-position: 5px -7px !important;
    padding: 10px 10px 10px 25px;
    width: 270px;
    border: 1px solid #CCC;
    -moz-border-radius: 5px;
    -webkit-border-radius: 5px;
    border-radius: 5px;
    -moz-box-shadow: 0 1px 1px #ccc inset, 0 1px 0 #fff;
    -webkit-box-shadow: 0 1px 1px #CCC inset, 0 1px 0 #FFF;
    box-shadow: 0 1px 1px #CCC inset, 0 1px 0 #FFF;
}

</style>