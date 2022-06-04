<template>
  <div>
    <b-navbar class="is-primary">
      <template #brand>
        <b-navbar-item tag="router-link" :to="{ path: '/admin/statistics' }">
          <img
              src="../../../public/fishy.png"
              alt="fishy ma boi"
              class="logo ml-3"
              style="max-height: 2.5rem"
          >
        </b-navbar-item>
      </template>
      <template #start>
        <b-navbar-dropdown label="Browse">
          <b-navbar-item href="/admin/cottages">
            Cottages
          </b-navbar-item>
          <b-navbar-item href="/admin/boats">
            Boats
          </b-navbar-item>
          <b-navbar-item href="/admin/adventures">
            Adventures
          </b-navbar-item>
        </b-navbar-dropdown>
        <b-navbar-item href="/admin/users">
          Users
        </b-navbar-item>
        <b-navbar-dropdown label="Manage">
          <b-navbar-item href="/admin/approve">
            New users
          </b-navbar-item>
          <b-navbar-item href="/admin/reviews">
            Reviews
          </b-navbar-item>
          <b-navbar-item href="/admin/complaints">
            Complaints
          </b-navbar-item>
          <b-navbar-item href="/admin/requests">
            Requests
          </b-navbar-item>
        </b-navbar-dropdown>
        <b-navbar-item href="/admin/profile">
          Profile information
        </b-navbar-item>
        <b-navbar-item @click="registerAdminModal">
          Register admin
        </b-navbar-item>
        <b-navbar-item href="/admin/statistics">
          Statistics
        </b-navbar-item>
      </template>

      <template #end>
        <b-navbar-item tag="div">
          <div class="buttons">
            <a class="button is-link" @click="logOut">
              Log Out
            </a>
          </div>
        </b-navbar-item>
      </template>
    </b-navbar>
    <div>
      <router-view style="margin-top: 75px"></router-view>
    </div>
  </div>

</template>

<script>

import Registration from "@/components/Registration";
import RegisterAdminModal from "@/components/admin/RegisterAdminModal";
import axios from "axios";
import ChangePasswordModal from "@/components/admin/ChangePasswordModal";
import {backend} from "@/env";

export default {
  name: "AdminHomepage",
  data() {
    return {
      user: {},
    }
  },
  async mounted() {
    this.user = JSON.parse(localStorage.getItem('user'))
    const response = await this.axios.put(backend + '/user/checkPassword', this.user.username)
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
.my-component-wrapper .tabs a {
  border-bottom-style: none;
}
</style>