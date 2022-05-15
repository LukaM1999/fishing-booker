<template>
  <div>
    <b-navbar class="is-primary">
      <template #brand>
        <b-navbar-item tag="router-link" :to="{ path: '/cottageOwner/cottages' }">
          <img
              src="https://raw.githubusercontent.com/buefy/buefy/dev/static/img/buefy-logo.png"
              alt="Lightweight UI components for Vue.js based on Bulma"
          >
        </b-navbar-item>
      </template>
      <template #start>
        <b-navbar-item href="/cottageOwner/cottages">
          My Cottages
        </b-navbar-item>
        <b-navbar-item href="/cottageOwner/profile">
          Profile information
        </b-navbar-item>
        <b-navbar-item href="/cottageOwner/history">
          History
        </b-navbar-item>
        <b-navbar-item href="/cottageOwner/action">
          Make a sale
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
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import {Store} from "@/main";

export default {
  name: "CottageOwnerHomepage",
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
</style>