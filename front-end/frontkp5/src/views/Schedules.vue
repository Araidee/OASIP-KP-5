<script setup>
import { ref, onBeforeMount, computed } from "vue";
import EventList from "../components/EventList.vue";
const events = ref([]);

onBeforeMount(async () => {
  await getEvents();
});

let isEmpty = ref(true);

const checkEmptyArr = (arr) => {
  if (arr.length == 0 || arr == null) return true
  else return false
}
//GET
const getEvents = async () => {
  //const res = await fetch("http://202.44.9.103:8080/kp5/api/events")
  const res = await fetch("http://intproj21.sit.kmutt.ac.th/kp5/api/events")
  if (res.status === 200) {
    events.value = await res.json()
  } else console.log("Error, cannot get data");
}
//DELETE
const removeEvent = async (removeEventId) => {
  let confirmDelete = ref(false)
  confirmDelete.value = confirm(`Are you sure to delete this event?`)
  if(confirmDelete.value){
    const res = await fetch(`http://intproj21.sit.kmutt.ac.th/kp5/api/events/${removeEventId}`, {
      method: 'DELETE'
    })
    if (res.status === 200) {
      events.value = events.value.filter((event) => event.id !== removeEventId)
      console.log('deleted successfullly')
    } else console.log('error, cannot delete')
  }else console.log('Delete was canceled')
}

isEmpty = checkEmptyArr(events)
</script>

<template>
  <div>
    <div tabindex="0" class="collapse">
      <input type="checkbox" class="peer" />
      <div
        class="collapse-title bg-primary text-primary-content peer-checked:bg-secondary peer-checked:text-secondary-content"
      >
        <h1 class="text-3xl font-bold">Schedules</h1>
      </div>
      <div
        class="collapse-content bg-primary text-primary-content peer-checked:bg-secondary peer-checked:text-secondary-content"
      >
        <EventList :events="events" @delete="removeEvent" v-show="!isEmpty"/>
        <div class="overflow-x-auto w-4/5 place-items-center" v-show="isEmpty">
          <h2>No schedules</h2>
        </div>
      </div>
    </div>
  </div>
</template>

<style></style>
