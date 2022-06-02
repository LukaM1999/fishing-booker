import Vue from 'vue'
import App from './App.vue'
import VueRouter from "vue-router";
import Registration from "@/components/Registration";
import ApproveUser from "@/components/admin/ApproveUser";
import Buefy from 'buefy'
import 'buefy/dist/buefy.min.css'
import moment from "moment";
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
import DeletionRequests from "@/components/admin/DeletionRequests";
import History from "@/components/History";
import InstructorSchedule from "@/components/instructor/InstructorSchedule";
import Penalties from "@/components/customer/Penalties";
import SaleSubscriptions from "@/components/customer/SaleSubscriptions";
import Complaints from "@/components/admin/Complaints";

import CottageAction from "@/components/cottage_owner/CottageAction";
import Reviews from "@/components/admin/Reviews";
import Loyalty from "@/components/admin/Loyalty";
//map support
import { LMap, LTileLayer, LMarker } from 'vue2-leaflet';
import 'leaflet/dist/leaflet.css';
import { Icon } from 'leaflet';
import Statistics from "@/components/Statistics";
import AdventureProfile from "@/components/AdventureProfile";
import AdventureAction from "@/components/instructor/AdventureAction";

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

//map support
Vue.component('l-map', LMap);
Vue.component('l-tile-layer', LTileLayer);
Vue.component('l-marker', LMarker);
delete Icon.Default.prototype._getIconUrl;
Icon.Default.mergeOptions({
    iconRetinaUrl: require('leaflet/dist/images/marker-icon-2x.png'),
    iconUrl: require('leaflet/dist/images/marker-icon.png'),
    shadowUrl: require('leaflet/dist/images/marker-shadow.png'),
});


Vue.filter('formatDate', function(value) {
    if (value) {
        return moment(String(value)).format('DD.MM.YYYY. hh:mm')
    }
});

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
        path: '/adventureProfile',
        name: 'adventureProfile',
        component: AdventureProfile,
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
                path: 'browse/cottageProfile',
                name: 'customerCottageProfile',
                component: CottageProfile
            },
            {
                path: 'browse/adventureProfile',
                name: 'customerAdventureProfile',
                component: AdventureProfile,
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
            },
            {
                path: 'penalties',
                name: 'customerPenalties',
                component: Penalties
            },
            {
                path: 'saleSubscriptions',
                name: 'customerSaleSubscriptions',
                component: SaleSubscriptions
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
                path: 'reviews',
                name: 'reviews',
                component: Reviews
            },
            {
                path: 'complaints',
                name: 'complaints',
                component: Complaints
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
                path: 'cottageProfile',
                name: 'adminCottageProfile',
                component: CottageProfile,
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
                path: 'adventureProfile',
                name: 'adminAdventureProfile',
                component: AdventureProfile,
            },
            {
                path: 'profile',
                name: 'adminProfile',
                component: AdminProfile
            },
            {
                path: 'business',
                name: 'business',
                component: Loyalty
            },
            {
                path: 'statistics',
                name: 'statistics',
                component: Statistics
            },
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
                path: 'schedule',
                name: 'instructorSchedule',
                component: InstructorSchedule
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
                path: 'adventureProfile',
                name: 'MyAdventureProfile',
                component: AdventureProfile,
            },
            {
                path: 'action',
                name: 'adventureAction',
                component: AdventureAction
            },
            {
                path: 'history',
                name: 'adventureHistory',
                component: History
            },
            {
                path: 'statistics',
                name: 'statistics',
                component: Statistics
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
            },
            {
                path: 'action',
                name: 'action',
                component: CottageAction
            },
            {
              path: 'statistics',
              name: 'statistics',
              component: Statistics
            },
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