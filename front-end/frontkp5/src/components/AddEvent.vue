<script setup>
import { ref } from 'vue'
defineEmits(['addEvent'])
const bookingName = ref('')
const bookingEmail = ref('')
const eventStartTime = ref('')
const eventNotes = ref('')
const eventCategory = ref('')
const eventDuration = ref('')
</script>
 
<template>
<div>
    <div>
        <h1>Booking</h1>
        Name: <input type="text" v-model="bookingName"><br>
        Email: <input type="text" v-model="bookingEmail"><br>
        Category: <input type="text" v-model="eventCategory"><br>
        Time: <input type="text" v-model="eventStartTime"><br>
        Duration: <input type="number" v-model="eventDuration"><br>
        Notes: <input type="text" v-model="eventNotes"><br>
        <button @click="$emit('addEvent',{
            bookingEmail: bookingEmail,
            bookingName: bookingName,
            eventStartTime: eventStartTime,
            eventNotes: eventNotes,
            eventCategory: eventCategory,
            eventDuration: eventDuration
            })
            ">Book</button>
    </div>
</div>
</template>
 
<style>

</style>