<script setup>
import EventCategoryList from '../components/EventCategoryList.vue';
import { ref, onBeforeMount } from 'vue'
const eventCategories = ref([])
onBeforeMount(async () => {
  await getEventCategories();
});
//GET
const getEventCategories = async () => {
  // const res = await fetch("http://202.44.9.103:8080/kp5/api/eventCategories")
   const res = await fetch("http://intproj21.sit.kmutt.ac.th/kp5/api/eventCategories")
  if (res.status === 200) {
    eventCategories.value = await res.json()
  } else console.log("Error, cannot get data");
}
//PUT
const editEventCategory = async (editingEventCategory) => {
  const res = await fetch(
    `http://intproj21.sit.kmutt.ac.th/kp5/api/eventCategories/${editingEventCategory.id}`,
    {
      method: "PUT",
      headers: {
        "content-type": "application/json",
      },
      body: JSON.stringify({
        eventCategoryName: editingEventCategory.eventCategoryName,
        eventCategoryDescription: editingEventCategory.eventCategoryDescription,
        eventDuration: editingEventCategory.eventDuration
      }),
    }
  );

  if (res.status === 200) {
    const moddedEventCategory = await res.json();
    eventCategories.value = eventCategories.value.map((eventCategory) =>
      eventCategory.id === moddedEventCategory.id
        ? {
            ...eventCategory,
            eventCategoryName: moddedEventCategory.eventCategoryName,
            eventCategoryDescription: moddedEventCategory.eventCategoryDescription,
            eventDuration: moddedEventCategory.eventDuration
          }
        : eventCategory
    );

    console.log("edited successfully");
  } else console.log("error, cannot edit");
};
</script>
 
<template>
<div>
    <EventCategoryList :eventCategories="eventCategories" @editCategory="editEventCategory"/>
</div>
</template>
 
<style>

</style>