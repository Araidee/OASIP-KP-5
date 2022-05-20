<script setup>
import EventCategoryList from '../components/EventCategoryList.vue';
import { ref, onBeforeMount } from 'vue'
const eventCategories = ref([])
onBeforeMount(async () => {
  await getEventCategories();
});
const getEventCategories = async () => {
  const res = await fetch("http://202.44.9.103:8080/kp5/api/eventCategories")
  //  const res = await fetch("http://intproj21.sit.kmutt.ac.th/kp5/api/eventCategories")
  if (res.status === 200) {
    eventCategories.value = await res.json()
  } else console.log("Error, cannot get data");
}
</script>
 
<template>
<div>
    <EventCategoryList :eventCategories="eventCategories"/>
</div>
</template>
 
<style>

</style>