import Vue from 'vue'
import App from './App.vue'
import VueRouter from "vue-router";
import Registration from "@/components/Registration";

import axios from 'axios'
import VueAxios from 'vue-axios'
import Toasted from 'vue-toasted';


Vue.config.productionTip = false
Vue.config.devtools
Vue.use(VueRouter)
Vue.use(Toasted, {
    position: 'top-right',
    duration: 5000,
    keepOnHover: true,
})

Vue.use(VueAxios, axios)

const routes = [{
    path: '/registration',
    name: 'registration',
    component: Registration
}]

const router = new VueRouter({
    routes,
    mode: 'history'
})

new Vue({
    render: h => h(App),
    router
}).$mount('#app')
