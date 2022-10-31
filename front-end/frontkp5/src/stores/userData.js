import { defineStore, acceptHMRUpdate } from "pinia";
// use Pinia to help store data

export const userData = defineStore("userDataState", () => {
  return {};
});

if (import.meta.hot) {
  import.meta.hot.accept(acceptHMRUpdate(userData, import.meta.hot));
}
