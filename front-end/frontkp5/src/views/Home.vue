<script setup>
import { ref , onBeforeMount} from 'vue'
import EventList from '../components/EventList.vue'
const events = ref([])

onBeforeMount(async() => {
    await getEvents()
})

const getEvents = async() => {
    const res = await fetch('http://localhost:5000/events')
    if(res.status === 200){
        events.value = await res.json()
    }else console.log('Error, cannot get data')
}

</script>
 
<template>
    <div>
        <h1>Home</h1>
        <EventList :events="events"/>
    </div>
</template>
 
<style scoped>
body {
    background-color: aqua;
}
</style>