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
  const res = await fetch("http://10.4.56.88:8080/api/events")
  if (res.status === 200) {
    events.value = await res.json()
  } else console.log("Error, cannot get data");
}
//DELETE
const removeEvent = async (removeEventId) => {
  const res = await fetch(`http://localhost:5000/events/${removeEventId}`, {
    method: 'DELETE'
  })
  if (res.status === 200) {
    events.value = events.value.filter((event) => event.id !== removeEventId)
    console.log('deleted successfullly')
  } else console.log('error, cannot delete')
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
        <EventList :events="events" v-show="!isEmpty"/>
        <div class="overflow-x-auto w-4/5 place-items-center" v-show="isEmpty">
          <h2>No schedules</h2>
        </div>
      </div>
    </div>
  </div>
</template>

<style></style>
