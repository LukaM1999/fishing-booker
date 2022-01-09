import Vue from 'vue'
import App from './App.vue'
import VueRouter from "vue-router";
import Registration from "@/components/Registration";
import ApproveUser from "@/components/admin/ApproveUser";
import Buefy from 'buefy'
import 'buefy/dist/buefy.css'

import axios from 'axios'
import VueAxios from 'vue-axios'
import Toasted from 'vue-toasted';
import Login from "@/components/Login";
import {jwtInterceptor} from "@/_helpers/jwt.interceptor";
import CottageProfile from "@/components/CottageProfile";
import CustomerHomepage from "@/components/customer/CustomerHomepage";
import LandingPage from "@/components/LandingPage";
import Cottages from "@/components/Cottages";
import CottageRegistration from "@/components/cottage_owner/CottageRegistration";
import Boats from "@/components/Boats";
import Instructors from "@/components/Instructors";
import CustomerProfile from "@/components/customer/CustomerProfile";
import BoatRegistration from "@/components/boat_owner/BoatRegistration";
import AdminHomepage from "@/components/admin/AdminHomepage";
import AdminProfile from "@/components/admin/AdminProfile";
import InstructorHomepage from "@/components/instructor/InstructorHomepage";
import InstructorProfile from "@/components/instructor/InstructorProfile";
import CottageOwnerHomepage from "@/components/cottage_owner/CottageOwnerHomepage";
import CottageOwnerProfile from "@/components/cottage_owner/CottageOwnerProfile";
import CottageUpdate from "@/components/cottage_owner/CottageUpdate";

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
                path: 'cottages',
                name: 'cottages',
                component: Cottages
            },
            {
                path: 'boats',
                name: 'boats',
                component: Boats
            },
            {
                path: 'instructors',
                name: 'instructors',
                component: Instructors
            },
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
        path: '/customer',
        name: 'customerHomepage',
        component: CustomerHomepage,
        children: [
            {
                path: 'cottages',
                name: 'customerCottages',
                component: Cottages
            },
            {
                path: 'boats',
                name: 'customerBoats',
                component: Boats
            },
            {
                path: 'instructors',
                name: 'customerInstructors',
                component: Instructors
            },
            {
                path: 'profile',
                name: 'customerProfile',
                component: CustomerProfile
            },
            {
                path: 'cottageUpdate',
                name: 'cottageUpdate',
                component: CottageUpdate
            }
        ]
    },
    {
        path: '/admin',
        name: 'adminHomepage',
        component: AdminHomepage,
        children: [
            {
                path: 'approve',
                name: 'approveUser',
                component: ApproveUser
            },
            {
                path: 'cottages',
                name: 'customerCottages',
                component: Cottages
            },
            {
                path: 'boats',
                name: 'customerBoats',
                component: Boats
            },
            {
                path: 'instructors',
                name: 'customerInstructors',
                component: Instructors
            },
            {
                path: 'profile',
                name: 'adminProfile',
                component: AdminProfile
            }
        ]
    },
    {
        path: '/instructor',
        name: 'instructorHomepage',
        component: InstructorHomepage,
        children: [
            {
                path: 'profile',
                name: 'instructorProfile',
                component: InstructorProfile
            }
        ]
    },
    {
        path: '/cottageOwner',
        name: 'cottageOwnerHomepage',
        component: CottageOwnerHomepage,
        children: [
            {
                path: 'cottages',
                name: 'customerCottages',
                component: Cottages
            },
            {
                path: 'profile',
                name: 'cottageOwnerProfile',
                component: CottageOwnerProfile
            }
        ]
    }
]

export const router = new VueRouter({
    routes,
    mode: 'history'
})

export var Store = { user: null };

new Vue({
    render: h => h(App),
    router
}).$mount('#app')
