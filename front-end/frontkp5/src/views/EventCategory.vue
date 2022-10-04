<script setup>
import EventCategoryList from "../components/EventCategoryList.vue";
import { ref, onBeforeMount } from "vue";
const eventCategories = ref([]);
onBeforeMount(async () => {
  await getEventCategories();
});
//GET
const getEventCategories = async () => {
  // const res = await fetch("http://202.44.9.103:8080/kp5/api/eventCategories");
  const res = await fetch(
    "https://intproj21.sit.kmutt.ac.th/kp5/api/eventCategories"
  );
  // const res = await fetch(`${import.meta.env.LOCAL_URL}/api/eventCategories`)
  if (res.status === 200) {
    eventCategories.value = await res.json();
  } else console.log("Error, cannot get data");
};
//PUT
const editEventCategory = async (editingEventCategory) => {
  // const res = await fetch(
  //   `http://202.44.9.103:8080/kp5/api/eventCategories/${editingEventCategory.categoryId}`,
  const res = await fetch(
    `https://intproj21.sit.kmutt.ac.th/kp5/api/eventCategories/${editingEventCategory.categoryId}`,
    {
      method: "PUT",
      headers: {
        "content-type": "application/json",
      },
      body: JSON.stringify({
        eventCategoryName: editingEventCategory.eventCategoryName,
        eventCategoryDescription: editingEventCategory.eventCategoryDescription,
        eventDuration: editingEventCategory.eventDuration,
      }),
    }
  );

  if (res.status === 200) {
    const moddedEventCategory = await res.json();
    eventCategories.value = eventCategories.value.map((eventCategory) =>
      eventCategory.categoryId === moddedEventCategory.categoryId
        ? {
            ...eventCategory,
            eventCategoryName: moddedEventCategory.eventCategoryName,
            eventCategoryDescription:
              moddedEventCategory.eventCategoryDescription,
            eventDuration: moddedEventCategory.eventDuration,
          }
        : eventCategory
    );
    alert("Edited!");
    console.log("edited successfully");
  } else console.log("error, cannot edit");
};
</script>

<template>
  <div>
    <div tabindex="0" class="collapse">
      <input type="checkbox" class="peer" />
      <div
        class="collapse-title bg-primary text-primary-content peer-checked:bg-secondary peer-checked:text-secondary-content"
      >
        <h1 class="text-3xl font-bold">Event Categories</h1>
      </div>
      <div
        class="collapse-content bg-primary text-primary-content peer-checked:bg-secondary peer-checked:text-secondary-content"
      >
        <EventCategoryList
          :eventCategories="eventCategories"
          @editCategory="editEventCategory"
        />
        <div
          class="overflow-x-auto w-4/5 place-items-center"
          v-show="eventCategories.length == 0"
        >
          <h2>No Category</h2>
        </div>
      </div>
    </div>
  </div>
</template>

<style></style>
