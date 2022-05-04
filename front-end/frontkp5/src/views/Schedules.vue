<script setup>
import { ref, onBeforeMount } from "vue";
import EventList from "../components/EventList.vue";
const events = ref([]);
const isEmpty = ref(true);

const checkEmptyArr = () => {
  if (events.lenght === 0) return true;
  else return false;
};

onBeforeMount(async () => {
  await getEvents();
});

const getEvents = async () => {
  const res = await fetch("http://10.4.56.88:8080/api/events")
  if (res.status === 200) {
    events.value = await res.json()
    // checkEmptyArr()
  } else console.log("Error, cannot get data");
};
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
        <EventList :events="events"/>
        <div class="overflow-x-auto w-4/5 place-items-center">
          <h2>No schedules</h2>
        </div>
      </div>
    </div>
  </div>
</template>

<style></style>
