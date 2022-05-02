<script setup>
import { ref , onBeforeMount} from 'vue'
import EventList from '../components/EventList.vue'
const events = ref([])

onBeforeMount(async() => {
    await getEvents()
})

const getEvents = async() => {
    const res = await fetch('http://10.4.56.88:8080/api/events')
    if(res.status === 200){
        events.value = await res.json()
    }else console.log('Error, cannot get data')
}

</script>
 
<template>
    <div>
        <div tabindex="0" class="collapse">
  <input type="checkbox" class="peer"> 
  <div class="collapse-title bg-primary text-primary-content peer-checked:bg-secondary peer-checked:text-secondary-content">
    <h1 class='text-3xl font-bold'>Schedules</h1>
  </div>
  <div class="collapse-content bg-primary text-primary-content peer-checked:bg-secondary peer-checked:text-secondary-content"> 
    <EventList :events="events"/>
  </div>
</div>
    </div>
</template>
 
<style>

</style>