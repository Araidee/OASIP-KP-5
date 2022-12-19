<script setup>
import { computed } from "@vue/reactivity";
import { useRouter } from "vue-router";
import { cookieData } from "../stores/cookieData.js";
import { loginState } from "../stores/loginState.js";
import Araideeicon from "./icons/araideeicon.vue";

const appRouter = useRouter();
const cookie = cookieData();
let isLogin = loginState();
const signOut = () => {
  if (confirm("Are you sure you want to sign out?")) {
    isLogin.setLogin();
    isLogin.clearLoginUser();
    cookie.eraseCookie("token");
    cookie.eraseCookie("refreshToken");
    alert("Successfully Sign Out");
    appRouter.push("/login");
  }
};
let btnVisibleClass = "btn btn1 btn-active btnall";
let invisibleClass = "hidden";
const adminNav = computed(() => {
  switch (isLogin.loginUser.role) {
    case "admin":
      return btnVisibleClass;
    case "user":
      return invisibleClass;
    case "lecturer":
      return invisibleClass;
    default:
      return invisibleClass;
  }
});

const lecturerNav = computed(() => {
  switch (isLogin.loginUser.role) {
    case "admin":
      return btnVisibleClass;
    case "user":
      return btnVisibleClass;
    case "lecturer":
      return invisibleClass;
    default:
      return btnVisibleClass;
  }
});

const guestNav = computed(() => {
  switch (isLogin.loginUser.role) {
    case "admin":
      return btnVisibleClass;
    case "user":
      return btnVisibleClass;
    case "lecturer":
      return btnVisibleClass;
    case "Guest":
      return invisibleClass;
    default:
      return btnVisibleClass;
  }
});
</script>

<template>
  <div>
    <div class="bg-base-100">
    <nav class="border-gray-200 mb-10 px-2 sm:px-5 py-5 sticky top-0 z-50">
      <div class="flex flex-wrap justify-between items-center ml-5 mr-5">
        <div class="flex flex-wrap justify-start items-center">
          <Araideeicon />
        </div>
        <div class="w-full order-last md:block md:w-auto" id="mobile-menu">
          <div class="btn-group">
            <button
              @click="$router.push('/')"
              class="btn btn-active btnall btn1"
            >
              Home
            </button>
            <button
              @click="$router.push('Schedules')"
              class="btn btn-active btnall btn1"
            >
              Schedules
            </button>
            <button
              @click="$router.push('EventCategory')"
              :class="guestNav"
            >
              Event Categories
            </button>
            <button
              @click="$router.push('Booking')"
              :class="lecturerNav"
            >
              Booking
            </button>
            <button
              @click="$router.push('Users')"
              :class="adminNav"
            >
              Users
            </button>
            <button
              @click="$router.push('About')"
              class="btn btn-active btnall btn1"
            >
              About us
            </button>
          </div>
        </div>
        <!-- right header section -->
      
        <div class="flex items-center order-last space-x-4 pr-3 border-2 border-gray-500 divide-x-4 rounded-md ">
          <a href="#">
            <svg width="32" height="32" viewBox="0 0 256 256">
              <path fill="currentColor" 
              d="M232 128a104 104 0 1 0-174.2 76.7l1.3 1.2a104 104 0 0 0 137.8 0l1.3-1.2A103.7 103.7 0 0 0 232 128Zm-192 0a88 88 0 1 1 153.8 58.4a79.2 79.2 0 0 0-36.1-28.7a48 48 0 1 0-59.4 0a79.2 79.2 0 0 0-36.1 28.7A87.6 87.6 0 0 1 40 128Zm56-8a32 32 0 1 1 32 32a32.1 32.1 0 0 1-32-32Zm-21.9 77.5a64 64 0 0 1 107.8 0a87.8 87.8 0 0 1-107.8 0Z"/>
            </svg>
          </a>
          
          <h1 class="userText rounded-full">Welcome <span class="font-bold">{{ isLogin.loginUser.name }}</span> </h1></div>
          <div class="items-center order-last">
            <button
              :class="
                isLogin.isLogin == false ? btnVisibleClass  : invisibleClass
              "
              @click="$router.push('Login')"
            >
              Sign in
            </button>
            <button
              :class="
                isLogin.isLogin == true ? btnVisibleClass : invisibleClass
              "
              @click="signOut"
            >
              Sign Out
            </button>
            <button
              :class="adminNav"
              @click="$router.push('AddUser')"
            >
              Add User
            </button>
          </div>
        
        
      </div>
    </nav>
  </div>
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
  background: #e90d0d;
  color: #000000;
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
