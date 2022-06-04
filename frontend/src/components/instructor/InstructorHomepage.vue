<template>
  <div>
    <b-navbar class="is-primary">
      <template #brand>
        <b-navbar-item tag="router-link" :to="{ path: '/instructor/adventures' }">
          <img
              src="../../../public/fishy.png"
              alt="fishy ma boi"
              class="logo ml-3"
              style="max-height: 2.5rem"
          >
        </b-navbar-item>
      </template>
      <template #start>
        <b-navbar-item href="/instructor/schedule">
          Schedule
        </b-navbar-item>
        <b-navbar-item href="/instructor/adventures">
          My Adventures
        </b-navbar-item>
        <b-navbar-item href="/instructor/profile">
          Profile information
        </b-navbar-item>
        <b-navbar-item href="/instructor/history">
          History
        </b-navbar-item>
        <b-navbar-item href="/instructor/statistics">
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

export default {
  name: "InstructorHomepage",
  components: {},

  data() {
    return {
      user: null
    }
  },
  mounted() {
    this.user = localStorage.getItem('user')
  },
  methods: {
    logOut() {
      this.user = null
      localStorage.removeItem('user');
      localStorage.removeItem('jwt');
      this.$router.push('/')
    },

  },
}
</script>

<style scoped>
.my-component-wrapper .tabs a {
  border-bottom-style: none;
}
</style>