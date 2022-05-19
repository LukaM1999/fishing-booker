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
          <router-link to="/instructor/schedule" class="navbar-item">
            Schedule
          </router-link>
          <router-link to="/instructor/adventures" class="navbar-item">
            My Adventures
          </router-link>
          <router-link to="/instructor/history" class="navbar-item">
            Adventure reservation history
          </router-link>
          <router-link to="" class="navbar-item">
            Business Report and Income
          </router-link>
        </div>
        <div class="navbar-end">
          <div class="navbar-item has-dropdown is-hoverable">
            <a class="navbar-link is-arrowless fa fa-user-alt fa-3x"></a>
            <div class="navbar-dropdown is-right is-boxed">
              <router-link to="/instructor/profile" class="navbar-item">
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

export default {
  name: "InstructorHomepage",
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