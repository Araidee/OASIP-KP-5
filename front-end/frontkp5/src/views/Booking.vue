<script setup>
import { ref } from 'vue'
//POST
const createNewEvent = async (newEvent) => {
  const res = await fetch('http://localhost:5000/events', {
    method: 'POST',
    headers: {
      'content-type': 'application/json'
    },
    body: JSON.stringify(newEvent)
  })
  if (res.status === 201) {
    const addedEvent = await res.json()
    movies.value.push(addedEvent)
    console.log('created successfully')
  } else console.log('error, cannot create')
}

</script>
 
<template>
    <div>
        <h1 class="text-3xl font-bold">Booking Pages</h1>
    </div>
</template>
 
<style>

</style>