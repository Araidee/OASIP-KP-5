<script setup>
import { ref , computed} from 'vue'
defineEmits(['delete'])
const props = defineProps({
    events: {
        type: Array,
        default: []
    }
})

const myEvent = computed(()=> {
    let eventList = []
    props.events.forEach((element)=>{
        eventList.push({
            "id": element.id,
            "bookingEmail": element.bookingEmail,
            "bookingName": element.bookingName,
            "eventStartTime": element.eventStartTime,
            "eventNotes": element.eventNotes,
            "eventCategory": element.eventCategory,
            "eventDuration": element.eventDuration
        })
    })
    return eventList;
})

const EventDetails = ref({})
const getEventById = async (id) => {
  const res = await fetch(`http://202.44.9.103:8080/kp5/api/events/${id}`);
  if (res.status === 200) {
    EventDetails.value = await res.json();
  } else console.log("Error, cannot get data");
};



</script>
 
<template>
<div>
    <div class="overflow-x-auto w-full  place-items-center ">
        <table class="table w-full" v-show="!isEmpty">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Category</th>
                    <th>Date</th>
                    <th>Time</th>
                    <th>Duration(mins)</th>
                    <th></th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="event in myEvent" :key="event.id"> 
                    <td>{{event.bookingName}}</td>
                    <td>{{event.eventCategory.eventCategoryName}}</td>
                    <td>{{new Date(event.eventStartTime).toLocaleDateString('th-TH')}}</td>
                    <td>{{new Date(event.eventStartTime).toLocaleTimeString('en-TH', {hour: '2-digit', minute: '2-digit', hour12: true})}}</td>
                    <td>{{event.eventDuration}}</td>
                    <td><label for="detail-modal" class="btn modal-button" @click="getEventById(event.id)">Details</label></td>
                    <td><button class="btn modal-button" @click="$emit('delete', event.id)">Delete</button></td>
                </tr>
            </tbody>
        </table>

    </div>

    <input type="checkbox" id="detail-modal" class="modal-toggle">
        <div class="modal">
            <div class="modal-box">
                <h3 class="font-bold text-lg">Details</h3>
                <p class="py-4" >Name: {{EventDetails.bookingName}}</p>
                <p class="py-4" >Email: {{EventDetails.bookingEmail}}</p>
                <p class="py-4" >Category: {{EventDetails.eventCategory}}</p>
                <p class="py-4" >Datetime: {{new Date(EventDetails.eventStartTime).toLocaleDateString('th-TH')}}, {{new Date(EventDetails.eventStartTime).toLocaleTimeString('en-TH', {hour: '2-digit', minute: '2-digit', hour12: true})}}</p>
                <p class="py-4" >Duration: {{EventDetails.eventDuration}} minutes</p>
                <p class="py-4" >Notes : {{EventDetails.eventNotes===null? '-':EventDetails.eventNotes}}</p>
                <div class="modal-action">
            <label for="detail-modal" class="btn">Yay!</label>
            </div>
        </div>
    </div>
</div>
</template>
 
<style>

</style>