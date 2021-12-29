import Vue from 'vue'
import App from './App.vue'
import VueRouter from "vue-router";
import Registration from "@/components/Registration";

import axios from 'axios'
import VueAxios from 'vue-axios'
import Toasted from 'vue-toasted';
import Login from "@/components/Login";
import {jwtInterceptor} from "@/_helpers/jwt.interceptor";
import CottageProfile from "@/components/CottageProfile";

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

const routes = [
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
