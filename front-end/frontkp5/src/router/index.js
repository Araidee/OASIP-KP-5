import { createRouter, createWebHistory } from "vue-router";
import Home from "../views/Home.vue";
import About from "../views/About.vue";
import NotFound from "../views/NotFound.vue";
import Schedules from "../views/Schedules.vue";
import Booking from "../views/Booking.vue";
import EventCategory from "../views/EventCategory.vue";
import Users from "../views/User.vue";
import Register from "../views/Register.vue";
import Login from "../views/LoginPage.vue";

const history = createWebHistory("/kp5/");
const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/about",
    name: "About",
    component: About,
  },
  {
    path: "/schedules",
    name: "Schedules",
    component: Schedules,
  },
  {
    path: "/booking",
    name: "Booking",
    component: Booking,
  },
  {
    path: "/eventCategory",
    name: "EventCategory",
    component: EventCategory,
    Comment: "list component",
  },
  ,
  {
    path: "/users",
    name: "Users",
    component: Users,
    Comment: "list component",
  },
  {
    path: "/register",
    name: "Register",
    component: Register,
    Comment: "sign up",
  },
  {
    path: "/login",
    name: "Login",
    component: Login,
    Comment: "sign in",
  },
  {
    path: "/:catchNotMatchPath(.*)",
    name: "NotFound",
    component: NotFound,
  },
];

const router = createRouter({ history, routes });
export default router;
