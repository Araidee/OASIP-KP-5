<script setup>
import { useRouter } from "vue-router";
import Araideeicon from "./icons/araideeicon.vue";

const appRouter = useRouter();
const GoSearch = () => appRouter.push({ name: "Home" });
</script>

<template>
  <div>
    <nav class="border-gray-200 mb-10 px-2 sm:px-5 py-5 sticky top-0 z-50">
      <div class="flex flex-wrap justify-between items-center ml-5 mr-5">
        <div class="flex flex-wrap justify-start items-center">
          <Araideeicon />
        </div>
        <div class="w-full order-last md:block md:w-auto" id="mobile-menu">
          <ul
            class="flex flex-col mt-4 md:flex-row md:space-x-8 md:mt-0 md:text-sm md:font-medium"
          >
            <li class="text-xl">
              <router-link :to="{ name: 'Home' }">Home | </router-link>
            </li>
            <li class="text-xl">
              <router-link :to="{ name: 'Schedules' }"
                >Schedules |
              </router-link>
            </li>
            <li class="text-xl">
              <router-link :to="{ name: 'EventCategory' }"
                >Event category |
              </router-link>
            </li>
            <li class="text-xl">
              <router-link :to="{ name: 'Booking' }">Booking | </router-link>
            </li>
            <li class="text-xl">
              <router-link :to="{ name: 'About' }">About us</router-link>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  </div>
</template>

<style>
body {
  font: "Helvetica Neue", Helvetica, Arial, sans-serif;
  background-color: #e5e7e5;
  color: #4d4d4d;
  -moz-osx-font-smoothing: grayscale;
  -webkit-font-smoothing: antialiased;
}
/* #app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  Color: #E5E7E5;
} */
</style>
