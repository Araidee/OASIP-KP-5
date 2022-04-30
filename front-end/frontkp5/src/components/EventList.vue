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
            "bookName": element.bookName,
            "category": element.category,
            "duration": element.duration,
            "dateTime": element.dateTime
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
                    <th>Duration</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="(event, index) in events" :key="index">
                    <td>{{event.bookName}}</td>
                    <td>{{event.category}}</td>
                    <td>{{event.dateTime}}</td>
                    <td>{{event.duration}}</td>
                    <td><button>details</button></td>
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