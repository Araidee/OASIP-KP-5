<script setup>
import { ref , computed} from 'vue'

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
})
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
                <tr v-for="(event, index) in events" :key="index">
                    <td>{{event.bookingName}}</td>
                    <td>{{event.eventCategory.eventCategoryName}}</td>
                    <td>{{event.eventStartTime}}</td>
                    <td>{{event.eventDuration}}</td>
                    <td><label for="detail-modal" class="btn modal-button">Details</label></td>
                </tr>
            </tbody>
        </table>
    </div>
    <div v-show="showDetail">
        <p>details {id}</p>
    </div>
    <input type="checkbox" id="detail-modal" class="modal-toggle">
        <div class="modal">
  <div class="modal-box">
    <h3 class="font-bold text-lg">Details {id}</h3>
    <p class="py-4">{}</p>
    <div class="modal-action">
      <label for="detail-modal" class="btn">Yay!</label>
    </div>
  </div>
</div>
</div>
</template>
 
<style>

</style>