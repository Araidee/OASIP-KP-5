import { defineStore, acceptHMRUpdate } from "pinia";
// use Pinia to help store data
import { cookieData } from "./cookieData";
export const eventData = defineStore("eventDataState", () => {
  // const url = "https://intproj21.sit.kmutt.ac.th/kp5/api";
  const url = "http://202.44.9.103:8080/kp5/api";
  const cookie = cookieData();
  const events = ref([]);
  // const url = "http://202.44.9.103:8080/kp5/api"
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
    } else console.log("Error, cannot get data");
  };
  const getEventById = async (id) => {
    // const res = await fetch(`http://202.44.9.103:8080/kp5/api/events/${id}`);
    const res = await fetch(`${url}/events/${id}`, {
      method: "GET",
      headers: {
        Authorization: "Bearer " + cookie.getCookie("token"),
      },
    });
    if (res.status === 200) {
      EventDetails.value = await res.json();
      editEventNotes.value = EventDetails.value.eventNotes;
      fetchEventCategoryName.value =
        EventDetails.value.eventCategory.eventCategoryName;
    } else console.log("Error, cannot get data");
  };
  const getEventsByCategoryId = async (id) => {
    // const res = await fetch(`http://202.44.9.103:8080/kp5/api/events/category/${id}`);
    const res = await fetch(`${url}/events/category/${id}`, {
      method: "GET",
      headers: {
        Authorization: "Bearer " + cookie.getCookie("token"),
      },
    });
    if (res.status === 200) {
      events.value = await res.json();
    } else console.log("Error, cannot get data");
  };
  const getPastEvents = async (isotime) => {
    // const res = await fetch(`http://202.44.9.103:8080/kp5/api/events/past/${isotime}`);
    const res = await fetch(`${url}/events/past/${isotime}`, {
      method: "GET",
      headers: {
        Authorization: "Bearer " + cookie.getCookie("token"),
      },
    });
    if (res.status === 200) {
      events.value = await res.json();
    } else console.log("Error, cannot get data");
  };
  const getUpcomingEvents = async (isotime) => {
    // const res = await fetch(`http://202.44.9.103:8080/kp5/api/events/upcoming/${isotime}`);
    const res = await fetch(`${url}/events/upcoming/${isotime}`, {
      method: "GET",
      headers: {
        Authorization: "Bearer " + cookie.getCookie("token"),
      },
    });
    if (res.status === 200) {
      events.value = await res.json();
    } else console.log("Error, cannot get data");
  };
  //POST
  const createNewEvent = async (newEvent) => {
    if (
      new Date(newEvent.eventStartTime).getTime() >
      new Date(Date.now()).getTime()
    ) {
      // const res = await fetch("http://202.44.9.103:8080/kp5/api/events", {
      const res = await fetch(`${url}/events/adding`, {
        //  const res = await fetch(`${import.meta.env.LOCAL_URL}/api/events`, {
        method: "POST",
        headers: {
          "content-type": "application/json",
          Authorization: "Bearer " + cookie.getCookie("token"),
        },
        body: JSON.stringify(newEvent),
      });
      if (res.status === 201) {
        const addedEvent = await res.json();
        events.value.push(addedEvent);
        alert("Booked!");
        console.log("created successfully");
      } else console.log("error, cannot create");
    } else alert("Time is not future time");
  };
  //DELETE
  const removeEvent = async (removeEventId) => {
    let confirmDelete = ref(false);
    confirmDelete.value = confirm(`Are you sure to delete this event?`);
    if (confirmDelete.value) {
      // const res = await fetch(
      //   `http://202.44.9.103:8080/kp5/api/events/${removeEventId}`,
      const res = await fetch(`${url}/events/${removeEventId}`, {
        method: "DELETE",
        headers: {
          Authorization: "Bearer " + cookie.getCookie("token"),
        },
      });
      if (res.status === 200) {
        events.value = events.value.filter(
          (event) => event.id !== removeEventId
        );
        alert("Event removed!");
        console.log("deleted successfullly");
      } else console.log("error, cannot delete");
    } else console.log("Delete was canceled");
  };
  //PUT
  const editEvent = async (editingEvent) => {
    // const res = await fetch(
    //   `http://202.44.9.103:8080/kp5/api/events/${editingEvent.id}`,
    const res = await fetch(`${url}/events/${editingEvent.id}`, {
      method: "PUT",
      headers: {
        "content-type": "application/json",
        Authorization: "Bearer " + cookie.getCookie("token"),
      },
      body: JSON.stringify({
        eventStartTime: editingEvent.eventStartTime,
        eventNotes: editingEvent.eventNotes,
      }),
    });

    if (res.status === 200) {
      const moddedEvent = await res.json();
      events.value = events.value.map((event) =>
        event.id === moddedEvent.id
          ? {
              ...event,
              eventStartTime: moddedEvent.eventStartTime,
              eventNotes: moddedEvent.eventNotes,
            }
          : event
      );
      alert("Edited!");
      console.log("edited successfully");
    } else console.log("error, cannot edit");
  };
  return {
    events,
    getEvents,
    getEventById,
    getEventsByCategoryId,
    getPastEvents,
    getUpcomingEvents,
    createNewEvent,
    removeEvent,
    editEvent,
  };
});

if (import.meta.hot) {
  import.meta.hot.accept(acceptHMRUpdate(eventData, import.meta.hot));
}
