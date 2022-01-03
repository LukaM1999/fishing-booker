import Vue from 'vue'
import App from './App.vue'
import VueRouter from "vue-router";
import Registration from "@/components/Registration";
import ApproveUser from "@/components/ApproveUser";
import Buefy from 'buefy'
import 'buefy/dist/buefy.css'

import axios from 'axios'
import VueAxios from 'vue-axios'
import Toasted from 'vue-toasted';
import Login from "@/components/Login";
import {jwtInterceptor} from "@/_helpers/jwt.interceptor";
import CottageProfile from "@/components/CottageProfile";
import CustomerHomepage from "@/components/CustomerHomepage";
import LandingPage from "@/components/LandingPage";
import Navbar from "@/components/Navbar";
import Cottages from "@/components/Cottages";

Vue.config.productionTip = false
Vue.config.devtools

jwtInterceptor();

Vue.use(VueRouter)
Vue.use(Toasted, {
    position: 'top-right',
    duration: 5000,
    keepOnHover: true,
})
Vue.use(VueAxios, axios)
Vue.use(Buefy)

const routes = [
    {
        path: '/',
        name: 'landingPage',
        component: LandingPage,
        children: [
            {
                path: '/cottages',
                name: 'cottages',
                component: Cottages
            },
            {
                path: ''
            }
        ]

    },
    {
        path: '/registration',
        name: 'registration',
        component: Registration
    },
    {
        path: '/login',
        name: 'login',
        component: Login
    },
    {
        path: '/cottageProfile',
        name: 'cottageProfile',
        component: CottageProfile
    },
    {
        path: '/approve',
        name: 'approveUser',
        component: ApproveUser
    },
    {
        path: '/customer',
        name: 'customerHomepage',
        component: CustomerHomepage,
        children: [
            {
                path: '/profile',
            }
        ]
    },
    {
        path: '/navbar',
        name: 'navbar',
        component: Navbar
    }
]

export const router = new VueRouter({
    routes,
    mode: 'history'
})

new Vue({
    render: h => h(App),
    router
}).$mount('#app')
