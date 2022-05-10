<script setup>
import { ref, onBeforeMount } from 'vue'
import AddEvent from '../components/AddEvent.vue';

const events = ref([])
const eventCategories = ref([])
onBeforeMount(async () => {
  await getEvents();
  await getEventCategories();
});
//GET
const getEvents = async () => {
  //const res = await fetch("http://202.44.9.103:8080/kp5/api/events")
   const res = await fetch("http://intproj21.kmutt.ac.th/kp5/api/events")
  if (res.status === 200) {
    events.value = await res.json()
  } else console.log("Error, cannot get data");
}
const getEventCategories = async () => {
  //const res = await fetch("http://202.44.9.103:8080/kp5/api/eventCategories")
   const res = await fetch("http://intproj21.kmutt.ac.th/kp5/api/eventCategories")
  if (res.status === 200) {
    eventCategories.value = await res.json()
  } else console.log("Error, cannot get data");
}
//POST
const createNewEvent = async (newEvent) => {
  // const res = await fetch("http://202.44.9.103:8080/kp5/api/events", {
     const res = await fetch("http://intproj21.kmutt.ac.th/kp5/api/events", {
    method: "POST",
    headers: {
      "content-type": "application/json"
    },
    body: JSON.stringify(newEvent),
  })
  if (res.status === 201) {
    const addedEvent = await res.json()
    events.value.push(addedEvent)
    console.log('created successfully')
  } else console.log('error, cannot create')
}

</script>
 
<template>
    <div>
        <AddEvent :events="events" :eventCategories="eventCategories" @addEvent="createNewEvent"/>
    </div>
</template>
 
<style>

</style>