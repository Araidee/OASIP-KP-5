import {createRouter, createWebHistory} from 'vue-router'
import Home from '../views/Home.vue'
import About from '../views/About.vue'
import NotFound from '../views/NotFound.vue'
import Schedules from '../views/Schedules.vue'
import Booking from '../views/Booking.vue'

const history = createWebHistory('/kp5/');
const routes = [
    {
        path: '/',
        name: 'Home',
        component: Home
    },
    {
        path: '/about',
        name: 'About',
        component: About
    },
    {
        path: '/schedules',
        name: 'Schedules',
        component: Schedules
    },
    {
        path: '/booking',
        name: 'Booking',
        component: Booking
    },
    {
        path: '/:catchNotMatchPath(.*)',
        name: 'NotFound',
        component: NotFound
    }
]

const router = createRouter({history,routes})
export default router