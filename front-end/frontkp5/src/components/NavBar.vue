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
          <div class="btn-group">
  <button @click="$router.push('/')" class="btn btn-active btnall btn1">Home</button>
  <button @click="$router.push('Schedules')" class="btn btn-active btnall btn1">Schedules</button>
  <button @click="$router.push('EventCategory')" class="btn btn-active btnall btn1">Event Categories </button>
  <button @click="$router.push('Booking')" class="btn btn-active btnall btn1">Booking </button>
  <button @click="$router.push('Users')" class="btn btn-active btnall btn1">Users </button>
  <button @click="$router.push('Register')" class="btn btn-active btnall btn1">Register </button>
  <button @click="$router.push('About')" class="btn btn-active btnall btn1">About us </button>
</div>
        </div>
      </div>
    </nav>
  </div>
</template>

<style scoped>
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

.custom-btn {
  width: 130px;
  padding: 5px 25px;
  border: 2px #5C7F67;
  font-family: 'Lato', sans-serif;
  font-weight: 500;
  background: transparent;
  cursor: pointer;
  transition: all 0.3s ease;
  position: relative;
  display: inline-block;
}

.btn-10 {
  transition: all 0.3s ease;
  overflow: hidden;
}
.btn-10:after {
  position: absolute;
  content: " ";
  top: 0;
  left: 0;
  z-index: -1;
  width: 100%;
  height: 100%;
  transition: all 0.3s ease;
  -webkit-transform: scale(.1);
  transform: scale(.1);
}
.btn-10:hover {
  color: #fff;
}
.btn-10:hover:after {
  background: #5C7F67;
  -webkit-transform: scale(1);
  transform: scale(1);
}

p:hover input {
text-decoration:underline;
}

.btnall {
  position: relative;
  background: #e9e7e7;
  color: #5C7F67;
  width: 170px;
  height: 50px;
  border: none;
  outline: none;
  border-radius: 10px;
  

  box-shadow:
  0px 0.7px 4px rgba(0, 0, 0, 0.045),
  0px 1.9px 11.1px rgba(0, 0, 0, 0.065),
  0px 4.5px 26.8px rgba(0, 0, 0, 0.085),
  0px 15px 89px rgba(0, 0, 0, 0.13)
;
}
 
.btnall::after{
  content: '';
  position: absolute;
  width: 100%;
  height: 0.175rem;
  background: white;
  left: 0;
  bottom: 0;
  cursor: pointer;
}

.btn1::after{
  transform: scale(0, 1);
  transition: transform 0.3s ease;
}

.btn1:hover::after {
  transform: scale(1, 1);
}

</style>

