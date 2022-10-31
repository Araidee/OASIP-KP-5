import { defineStore, acceptHMRUpdate } from "pinia";
// use Pinia to help store data
import { cookieData } from "./cookieData";
export const eventCategoryData = defineStore("eventCategoryDataState", () => {
  const cookie = cookieData();
  const url = "http://202.44.9.103:8080/kp5/api";
  const eventCategories = ref([]);
  //GET
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
    } else console.log("Error, cannot get data");
  };
  //PUT
  const editEventCategory = async (editingEventCategory) => {
    // const res = await fetch(
    //   `http://202.44.9.103:8080/kp5/api/eventCategories/${editingEventCategory.categoryId}`,
    const res = await fetch(
      `${url}/eventCategories/${editingEventCategory.categoryId}`,
      {
        method: "PUT",
        headers: {
          "content-type": "application/json",
          Authorization: "Bearer " + cookie.getCookie("token"),
        },
        body: JSON.stringify({
          eventCategoryName: editingEventCategory.eventCategoryName,
          eventCategoryDescription:
            editingEventCategory.eventCategoryDescription,
          eventDuration: editingEventCategory.eventDuration,
        }),
      }
    );

    if (res.status === 200) {
      const moddedEventCategory = await res.json();
      eventCategories.value = eventCategories.value.map((eventCategory) =>
        eventCategory.categoryId === moddedEventCategory.categoryId
          ? {
              ...eventCategory,
              eventCategoryName: moddedEventCategory.eventCategoryName,
              eventCategoryDescription:
                moddedEventCategory.eventCategoryDescription,
              eventDuration: moddedEventCategory.eventDuration,
            }
          : eventCategory
      );
      alert("Edited!");
      console.log("edited successfully");
    } else console.log("error, cannot edit");
  };
  return { eventCategories, getEventCategories, editEventCategory };
});

if (import.meta.hot) {
  import.meta.hot.accept(acceptHMRUpdate(eventCategoryData, import.meta.hot));
}
