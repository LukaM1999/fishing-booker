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
          <div class="navbar-item has-dropdown is-hoverable">
            <a class="navbar-link">
              Browse
            </a>
            <div class="navbar-dropdown is-boxed">
              <router-link to="/admin/cottages" class="navbar-item">
                Cottages
              </router-link>
              <router-link to="/admin/boats" class="navbar-item">
                Boats
              </router-link>
              <router-link to="/admin/adventures" class="navbar-item">
                Adventures
              </router-link>
            </div>
          </div>
          <div class="navbar-item has-dropdown is-hoverable">
            <a class="navbar-link">
              Approve
            </a>
            <div class="navbar-dropdown is-boxed">
              <router-link to="/admin/approve" class="navbar-item">
                New users
              </router-link>
              <router-link to="" class="navbar-item">
                Comments
              </router-link>
              <router-link to="" class="navbar-item">
                Complaints
              </router-link>
              <router-link to="/admin/requests" class="navbar-item">
                Requests
              </router-link>
            </div>
          </div>
          <router-link to="" class="navbar-item">
            Business report and income
          </router-link>
          <router-link to="" class="navbar-item">
            Transaction fee
          </router-link>
          <a @click="registerAdminModal" class="navbar-item">
            Register admin
          </a>
        </div>
        <div class="navbar-end">
          <div class="navbar-item has-dropdown is-hoverable">
            <a class="navbar-link is-arrowless fa fa-user-alt fa-3x"></a>

            <div class="navbar-dropdown is-right is-boxed">
              <router-link to="/admin/profile" class="navbar-item">
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

import Registration from "@/components/Registration";
import RegisterAdminModal from "@/components/admin/RegisterAdminModal";
import axios from "axios";
import ChangePasswordModal from "@/components/admin/ChangePasswordModal";

export default {
  name: "AdminHomepage",
  data() {
    return {
      user: {},
    }
  },
  async mounted() {
    this.user = JSON.parse(localStorage.getItem('user'))
    const response = await this.axios.put('/user/checkPassword', this.user.username)
    if(response.data === true){
      this.changePasswordModal(this.user.username)
    }
  },
  methods: {
    logOut() {
      this.user = null
      localStorage.removeItem('user');
      localStorage.removeItem('jwt');
      this.$router.push('/')
    },
    registerAdminModal() {
      this.$buefy.modal.open({
        parent: this,
        component: RegisterAdminModal,
        hasModalCard: true,
        //customClass: 'custom-class custom-class-2',
        trapFocus: true
      })
    },
    changePasswordModal(){
      this.$buefy.modal.open({
        parent: this,
        component: ChangePasswordModal,
        hasModalCard: true,
        trapFocus: true,
        canCancel: false
      })
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