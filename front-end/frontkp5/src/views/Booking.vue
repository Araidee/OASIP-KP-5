<script setup>
import { ref, onBeforeMount } from "vue";
import { cookieData } from "../stores/cookieData.js";
import { loginState } from "../stores/loginState";
import AddEvent from "../components/AddEvent.vue";
const url = "https://intproj21.sit.kmutt.ac.th/kp5/api"
// const url = "http://202.44.9.103:8080/kp5/api";
const events = ref([]);
const cookie = cookieData();
const isLogin = loginState();
const eventCategories = ref([]);
onBeforeMount(async () => {
  // await getEvents();
  await getEventCategories();
  await getEvents();
});
//GET
const getEvents = async () => {
  // const res = await fetch("http://202.44.9.103:8080/kp5/api/events");
  const res = await fetch(`${url}/events/all`, {
    method: "GET",
    headers: {
      Authorization: "Bearer " + cookie.getCookie("token"),
    },
  });
  // const res = await fetch(`${import.meta.env.LOCAL_URL}/api/events`)
  if (res.status === 200) {
    events.value = await res.json();
  } else if (res.status === 401) {
    let res = await res.json();
    if (
      res.message
        .toLowerCase()
        .match(
          "please send refresh token to /refresh to refresh token".toLowerCase()
        )
    ) {
      isLogin.refreshToken();
    } else alert("please login");
  } else console.log("Error, cannot get data");
};
const getEventCategories = async () => {
  // const res = await fetch("http://202.44.9.103:8080/kp5/api/eventCategories");
  const res = await fetch(`${url}/eventCategories`, {
    method: "GET",
    headers: {
      Authorization: "Bearer " + cookie.getCookie("token"),
    },
  });
  // const res = await fetch(`${import.meta.env.LOCAL_URL}/api/eventCategories`)
  if (res.status === 200) {
    eventCategories.value = await res.json();
  } else if (res.status === 401) {
    let res = await res.json();
    if (
      res.message
        .toLowerCase()
        .match(
          "please send refresh token to /refresh to refresh token".toLowerCase()
        )
    ) {
      isLogin.refreshToken();
    } else alert("please login");
  } else console.log("Error, cannot get data");
};
//POST
const createNewEvent = async (newEvent) => {
  if (
    new Date(newEvent.eventStartTime).getTime() > new Date(Date.now()).getTime()
  ) {
    // const res = await fetch("http://202.44.9.103:8080/kp5/api/events", {
    const res = await fetch(`${url}/events/adding`, {
      //  const res = await fetch(`${import.meta.env.LOCAL_URL}/api/events`, {
      method: "POST",
      headers: {
        Authorization: "Bearer " + cookie.getCookie("token"),
        "content-type": "application/json",
      },
      body: JSON.stringify(newEvent),
    });
    if (res.status === 201) {
      const addedEvent = await res.json();
      events.value.push(addedEvent);
      alert("Booked!");
      console.log("created successfully");
    } else if (res.status === 400) {
      alert("Booking email must be the same email as the student's email");
      console.log(
        "Booking email must be the same email as the student's email"
      );
    } else if (res.status === 401) {
      let res = await res.json();
      if (
        res.message
          .toLowerCase()
          .match(
            "please send refresh token to /refresh to refresh token".toLowerCase()
          )
      ) {
        isLogin.refreshToken();
      } else alert("please login");
    } else console.log("error, cannot create");
  } else alert("Time is not future time");
};
</script>

<template>
  <div>
    <AddEvent
      :events="events"
      :eventCategories="eventCategories"
      @addEvent="createNewEvent"
    />
  </div>
</template>

<style></style>
