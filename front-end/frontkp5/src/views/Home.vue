<script setup>
import { useRouter } from "vue-router";
import { loginState } from "../stores/loginState";
const isLogin = loginState();
const appRouter = useRouter();
</script>

<template>
  <div>
    <div class="tooltip tooltip-secondary" data-tip="Hello We're from KP05 :D">
      <button>
        <h1 class="text-9xl font-bold">
          Welcome to <br />
          OASIP
        </h1>
      </button>
      <h3 class="text-lg">
        Online Appointment Scheduling System for Integrated Project Clinics
      </h3>
      <br />
    </div>
    <div class="button-space">
      <button
        :class="isLogin.loginUser.role == 'lecturer'? 'hidden':'btn btn-primary'"
        @click="appRouter.push({ path: '/booking', replace: true })"
      >
        Booking Now!
      </button>
      <button
        class="btn btn-secondary"
        @click="appRouter.push({ path: '/schedules', replace: true })"
      >
        Check schedules
      </button>
    </div>
  </div>
  <img src="../../public/booking.gif" alt="gif" />
</template>

<style scoped>


img {
  width: 300px;
  height: 220px;
  position: absolute;
  right: 10%;
  top: 30%;
}

h1 {
  position: relative;
  left: 10%;
}

h3 {
  position: relative;
  left: 10%;
}

button {
  position: relative;
  left: 10%;
}

.button-space > button:not(:last-child) {
  margin-right: 10px;
}
</style>
