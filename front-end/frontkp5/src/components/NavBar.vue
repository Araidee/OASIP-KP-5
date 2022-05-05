<script setup>
import { useRouter } from 'vue-router'
import Araideeicon from './icons/araideeicon.vue';

const appRouter = useRouter()
const GoSearch = () => appRouter.push({ name: 'Home' })
</script>
 
<template>

    <div>
        <nav class="border-gray-200 mb-10 px-2 sm:px-5 py-5 shadow-xl sticky top-0 z-50 bg-white">
            <div class="flex flex-wrap justify-between items-center ml-5 mr-5">
                <div class="flex flex-wrap justify-start items-center">
                    <Araideeicon />
                </div>
                <div class="w-full order-last md:block md:w-auto" id="mobile-menu">
                    <ul class="flex flex-col mt-4 md:flex-row md:space-x-8 md:mt-0 md:text-sm md:font-medium">
                        <li class="text-xl">
                            <router-link :to="{ name: 'Home'}">Home | </router-link> 
                        </li>
                        <li class="text-xl">
                            <router-link :to="{name: 'Schedules'}">Schedules | </router-link>
                        </li>
                        <li class="text-xl">
                            <router-link :to="{name: 'Booking'}">Booking | </router-link>
                        </li>
                        <li class="text-xl">
                            <router-link :to="{ name: 'About'}">About us</router-link>
                        </li>
                        <li class="text-xl">
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    </div>
</template>
 
<style>

</style>
