<script setup>
import { ref , computed} from 'vue'

const props = defineProps({
    events: {
        type: Array,
        default: []
    }
})
let showDetail
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
})

const EventDetails = ref({})
const getEventById = async (id) => {
  const res = await fetch(`http://10.4.56.88:8080/api/events/${id}`);
  if (res.status === 200) {
    EventDetails.value = await res.json();
  } else console.log("Error, cannot get data");
};
</script>
 
<template>
<div>
    <div class="overflow-x-auto w-4/5 place-items-center " >
        <table class="table w-full">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Category</th>
                    <th>Date</th>
                    <th>Duration(mins)</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="event in events" :key="event.id"> 
                    <td>{{event.bookingName}}</td>
                    <td>{{event.eventCategory.eventCategoryName}}</td>
                    <td>{{event.eventStartTime}}</td>
                    <td>{{event.eventDuration}}</td>
                    <td><label for="detail-modal" class="btn modal-button" @click="getEventById(event.id)">Details</label></td>
                </tr>
            </tbody>
        </table>
    </div>
    <div v-show="showDetail">
        <p>details</p>
    </div>
    <input type="checkbox" id="detail-modal" class="modal-toggle">
        <div class="modal">
            <div class="modal-box">
                <h3 class="font-bold text-lg">Details</h3>
                <p class="py-4" >Name: {{EventDetails.bookingName}}</p>
                <p class="py-4" >Email: {{EventDetails.bookingEmail}}</p>
                <p class="py-4" >Category: {{EventDetails.eventCategory}}</p>
                <p class="py-4" >Datetime: {{EventDetails.eventStartTime}}</p>
                <p class="py-4" >Duration: {{EventDetails.eventDuration}}</p>
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