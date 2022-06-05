<template>
  <div>
    <b-navbar class="is-primary">
      <template #brand>
        <b-navbar-item tag="router-link" :to="{ path: '/customer' }">
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
          <b-navbar-item href="/customer/browse/cottages">
            Cottages
          </b-navbar-item>
          <b-navbar-item href="/customer/browse/boats">
            Boats
          </b-navbar-item>
          <b-navbar-item href="/customer/browse/adventures">
            Adventures
          </b-navbar-item>
        </b-navbar-dropdown>
        <b-navbar-item href="/customer/profile">
          Profile Information
        </b-navbar-item>
        <b-navbar-item href="/customer/history">
          History
        </b-navbar-item>
        <b-navbar-item href="/customer/penalties">
          Penalties
        </b-navbar-item>
        <b-navbar-item href="/customer/saleSubscriptions">
          Sales Subscriptions
        </b-navbar-item>
        <b-navbar-item href="/customer/reservation">
          Make a Reservation!
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
.my-component-wrapper .tabs a {
  border-bottom-style: none;
}
</style>