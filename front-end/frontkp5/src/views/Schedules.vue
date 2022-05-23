<script setup>
import { ref, onBeforeMount, computed } from "vue";
import EventList from "../components/EventList.vue";
const events = ref([]);
const categoryIdTemp = ref('')
const eventCategories = ref([]);
// const eventsByCategoryId = ref([]);
onBeforeMount(async () => {
  await getEvents();
  await getEventCategories();
});
//GET
const getEvents = async () => {
  // const res = await fetch("http://202.44.9.103:8080/kp5/api/events");
  const res = await fetch("http://intproj21.sit.kmutt.ac.th/kp5/api/events")
  if (res.status === 200) {
    events.value = await res.json();
  } else console.log("Error, cannot get data");
};
const getEventsByCategoryId = async (id) => {
  // const res = await fetch(`http://202.44.9.103:8080/kp5/api/events/category/${id}`);
  const res = await fetch(`http://intproj21.sit.kmutt.ac.th/kp5/api/events/category/${id}`);
  if (res.status === 200) {
    events.value = await res.json();
  } else console.log("Error, cannot get data");
};
const getEventCategories = async () => {
  // const res = await fetch("http://202.44.9.103:8080/kp5/api/eventCategories");
   const res = await fetch("http://intproj21.sit.kmutt.ac.th/kp5/api/eventCategories")
  if (res.status === 200) {
    eventCategories.value = await res.json();
  } else console.log("Error, cannot get data");
};
//DELETE
const removeEvent = async (removeEventId) => {
  let confirmDelete = ref(false);
  confirmDelete.value = confirm(`Are you sure to delete this event?`);
  if (confirmDelete.value) {
    // const res = await fetch(
    //   `http://202.44.9.103:8080/kp5/api/events/${removeEventId}`,
      const res = await fetch(
        `http://intproj21.sit.kmutt.ac.th/kp5/api/events/${removeEventId}`,
      {
        method: "DELETE",
      }
    );
    if (res.status === 200) {
      events.value = events.value.filter((event) => event.id !== removeEventId);
      alert('Event removed!')
      console.log("deleted successfullly");
    } else console.log("error, cannot delete");
  } else console.log("Delete was canceled");
};
//PUT
const editEvent = async (editingEvent) => {
  // const res = await fetch(
  //   `http://202.44.9.103:8080/kp5/api/events/${editingEvent.id}`,
    const res = await fetch(
      `http://intproj21.sit.kmutt.ac.th/kp5/api/events/${editingEvent.id}`,
    {
      method: "PUT",
      headers: {
        "content-type": "application/json",
      },
      body: JSON.stringify({
        eventStartTime: editingEvent.eventStartTime,
        eventNotes: editingEvent.eventNotes,
      }),
    }
  );

  if (res.status === 200) {
    const moddedEvent = await res.json();
    events.value = events.value.map((event) =>
      event.id === moddedEvent.id
        ? {
            ...event,
            eventStartTime: moddedEvent.eventStartTime,
            eventNotes: moddedEvent.eventNotes,
          }
        : event
    );
    alert('Edited!')
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
        <h1 class="text-3xl font-bold">Schedules</h1>
      </div>
      <div
        class="collapse-content bg-primary text-primary-content peer-checked:bg-secondary peer-checked:text-secondary-content"
      >
        <div class="card-body items-right">
        <span>Filter by :
        <select
          class="select select-success w-full max-w-xs"
          v-model="categoryIdTemp"
          required
          @change="categoryIdTemp=='all'? getEvents():getEventsByCategoryId(categoryIdTemp)"
        >
          <option disabled selected>-- Choose to filter --</option>
          <option value="all">All</option>
          <option
            v-for="eventCategory in eventCategories"
            :value="eventCategory.id"
          >
            {{ eventCategory.eventCategoryName }}
          </option>
        </select>
        </span>
      </div>
        <EventList :events="events" @delete="removeEvent" @edit="editEvent" />
        <div
          class="overflow-x-auto w-4/5 place-items-center"
          v-show="events.length == 0"
        >
          <h2>No schedules</h2>
        </div>
      </div>
    </div>
  </div>
</template>

<style></style>
