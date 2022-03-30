import Vue from 'vue'
import App from './App.vue'
import VueRouter from "vue-router";
import Registration from "@/components/Registration";
import ApproveUser from "@/components/admin/ApproveUser";
import Buefy from 'buefy'
import 'buefy/dist/buefy.min.css'

import axios from 'axios'
import VueAxios from 'vue-axios'
import Toasted from 'vue-toasted';
import Login from "@/components/Login";
import {jwtInterceptor} from "@/_helpers/jwt.interceptor";
import CottageProfile from "@/components/CottageProfile";
import CustomerHomepage from "@/components/customer/CustomerHomepage";
import LandingPage from "@/components/LandingPage";
import Cottages from "@/components/Cottages";
import Boats from "@/components/Boats";
import Instructors from "@/components/Instructors";
import CustomerProfile from "@/components/customer/CustomerProfile";
import AdminHomepage from "@/components/admin/AdminHomepage";
import AdminProfile from "@/components/admin/AdminProfile";
import InstructorHomepage from "@/components/instructor/InstructorHomepage";
import InstructorProfile from "@/components/instructor/InstructorProfile";
import CottageOwnerHomepage from "@/components/cottage_owner/CottageOwnerHomepage";
import CottageOwnerProfile from "@/components/cottage_owner/CottageOwnerProfile";
import CottageUpdate from "@/components/cottage_owner/CottageUpdate";
import AdventureRegistration from "@/components/instructor/AdventureRegistration"
import Reservation from "@/components/customer/Reservation";
import Adventures from "@/components/Adventures";
import CustomReservation from "@/components/CustomReservation";
import DeletionRequests from "@/components/admin/DeletionRequests";
import History from "@/components/History";


Vue.config.productionTip = false
Vue.config.devtools

jwtInterceptor();

Vue.use(Buefy)
Vue.use(VueRouter)
Vue.use(Toasted, {
    position: 'top-right',
    duration: 5000,
    keepOnHover: true,
})
Vue.use(VueAxios, axios)

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
                path: 'adventures',
                name: 'adventures',
                component: Adventures
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
        component: CottageProfile,
    },
    {
        path: '/customer',
        name: 'customerHomepage',
        component: CustomerHomepage,
        children: [
            {
                path: 'browse/cottages',
                name: 'customerCottages',
                component: Cottages
            },
            {
                path: 'browse/boats',
                name: 'customerBoats',
                component: Boats
            },
            {
                path: 'browse/adventures',
                name: 'customerAdventures',
                component: Adventures
            },
            {
                path: 'history',
                name: 'customerHistory',
                component: History
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
            },
            {
                path: 'reservation',
                name: 'customerReservation',
                component: Reservation
            }
        ]
    },
    {
        path: '/admin',
        name: 'adminHomepage',
        component: AdminHomepage,
        redirect: '/admin/cottages',
        children: [
            {
                path: 'approve',
                name: 'approveUser',
                component: ApproveUser
            },
            {
                path: 'requests',
                name: 'requests',
                component: DeletionRequests
            },
            {
                path: 'cottages',
                name: 'adminCottages',
                component: Cottages
            },
            {
                path: 'boats',
                name: 'adminBoats',
                component: Boats
            },
            {
                path: 'adventures',
                name: 'adminAdventures',
                component: Adventures
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
        redirect: '/instructor/adventures',
        children: [
            {
                path: 'profile',
                name: 'instructorProfile',
                component: InstructorProfile
            },
            {
                path: 'register',
                name: 'adventureRegistration',
                component: AdventureRegistration
            },
            {
                path: 'adventures',
                name: 'instructorAdventures',
                component: Adventures
            },
            {
                path: 'reservation',
                name: 'instructorReservation',
                component: CustomReservation
            },
            {
                path: 'history',
                name: 'adventureHistory',
                component: History
            },
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
                component: Cottages,
            },
            {
                path: 'profile',
                name: 'cottageOwnerProfile',
                component: CottageOwnerProfile
            },
            {
                path: 'history',
                name: 'cottageHistory',
                component: History
            },
            {
                path: 'cottageProfile',
                name: 'cottageProfile',
                component: CottageProfile
            }
        ]
    }
]

export const router = new VueRouter({
    routes,
    mode: 'history'
})

router.beforeEach((to, from, next) => {
    if (to.path.indexOf('customer') !== -1) {
        if (JSON.parse(localStorage.getItem('user') || '{}')?.role?.authority !== 'CUSTOMER') {
            alert('Customer must be logged in!')
            next('/')
        } else next()
    }
    else if (to.path.indexOf('cottageOwner') !== -1) {
        if (JSON.parse(localStorage.getItem('user') || '{}')?.role?.authority !== 'COTTAGE_OWNER') {
            alert('Cottage owner must be logged in!')
            next('/')
        } else next()
    }
    else if (to.path.indexOf('boatOwner') !== -1) {
        if (JSON.parse(localStorage.getItem('user') || '{}')?.role?.authority !== 'BOAT_OWNER') {
            alert('Boat owner must be logged in!')
            next('/')
        } else next()
    }
    else if (to.path.indexOf('instructor') !== -1 && to.path.indexOf('instructors') === -1) {
        if (JSON.parse(localStorage.getItem('user') || '{}')?.role?.authority !== 'INSTRUCTOR') {
            alert('Instructor must be logged in!')
            next('/')
        } else next()
    }
    else if (to.path.indexOf('admin') !== -1) {
        if (JSON.parse(localStorage.getItem('user') || '{}')?.role?.authority !== 'ADMIN') {
            alert('Admin must be logged in!')
            next('/')
        } else next()
    }
    else next()
})

export var Store = {user: null};

export var vue = new Vue({
    render: h => h(App),
    router,
}).$mount('#app')
