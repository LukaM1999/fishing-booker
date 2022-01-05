<template>
  <div>
    <nav class="navbar is-primary">
      <div class="navbar-brand">
        <a class="navbar-item">
          <img :src="'../logo1.png'" alt="logo">
        </a>
        <div class="navbar-burger" data-bs-target="navbarExampleTransparentExample">
          <span></span>
          <span></span>
          <span></span>
        </div>
      </div>

      <div id="navbarExampleTransparentExample" class="navbar-menu">
        <div class="navbar-start">
          <a class="navbar-item">
            Home
          </a>
          <div class="navbar-item has-dropdown is-hoverable">
            <a class="navbar-link">
              Browse
            </a>
            <div class="navbar-dropdown is-boxed">
              <router-link to="/customer/cottages" class="navbar-item">
                Cottages
              </router-link>
              <router-link to="/customer/boats" class="navbar-item">
                Boats
              </router-link>
              <router-link to="/customer/instructors" class="navbar-item">
                Adventures
              </router-link>
            </div>
          </div>
          <div class="navbar-item has-dropdown is-hoverable">
            <a class="navbar-link">
              History
            </a>
            <div class="navbar-dropdown is-boxed">
              <a class="navbar-item">
                Cottage reservations
              </a>
              <a class="navbar-item">
                Boat reservations
              </a>
              <a class="navbar-item">
                Adventure reservations
              </a>
            </div>
          </div>
          <a class="navbar-item">
            Upcoming reservations
          </a>
          <a class="navbar-item">
            Send complaint
          </a>
        </div>
        <div class="navbar-end">
          <div class="navbar-item has-dropdown is-hoverable">
            <a class="navbar-link is-arrowless fa fa-user-alt fa-3x"></a>

            <div class="navbar-dropdown is-right is-boxed">
              <router-link to="/customer/profile" class="navbar-item">
                Profile information
              </router-link>
              <hr class="navbar-divider">
              <a @click="logOut" class="navbar-item">
                Log out
              </a>
            </div>
          </div>
        </div>
      </div>
    </nav>
    <div>
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import {Store} from "@/main";

export default {
  name: "CustomerHomepage",
  data() {
    return {}
  },
  methods: {
    logOut() {
      Store.user = null
      localStorage.removeItem('jwt')
      this.$router.push('/')
    }
  },
}
</script>

<style scoped>
.is-primary {
  background-image: linear-gradient(to right, #99A799, #ADC2A9) !important;
}

nav {
  padding: 0px !important;
}

.navbar-item:hover {
  color: #8E806A !important;
  background-color: white !important;
  opacity: 1;
}

.navbar-link:hover {
  color: #8E806A !important;
  background-color: white !important;
  opacity: 1;
}

a.navbar-item:hover {
  color: #8E806A !important;
  background-color: white;
  opacity: 1;
}

a.navbar-link:hover {
  color: #8E806A !important;
  background-color: white;
  opacity: 1;
}

a.navbar-link:active {
  color: #8E806A !important;
  background-color: white;
  opacity: 1;
}

.navbar-divider {
  background-color: gray;
}

</style>