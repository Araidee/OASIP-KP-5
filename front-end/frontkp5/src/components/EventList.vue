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
        <table>
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
                    <td><button class="btn">details</button></td>
                </tr>
            </tbody>
        </table>
    </div>
</template>
 
<style>
table, th, td {
  border: 1px solid;
  border-collapse: collapse;
  width: 50%;
}
</style>