<template>
  <div>
    <section class="hero is-primary is-medium">

      <!-- Hero content: will be in the middle -->
      <div class="hero-body">
        <div class="container has-text-centered">
          <p class="title is-size-1">Catchy Fishing Booker</p>
          <p class="subtitle">Gooone fishin'</p>
          <button @click="loginModal" class="button is-info mr-5">Login
          </button>
          <button @click="registerModal" class="button is-info">Register
          </button>
        </div>
      </div>

      <!-- Hero footer: will stick at the bottom -->
      <div class="hero-foot">
        <nav class="tabs">
          <div class="container">
            <ul>
              <li @click="setActiveTab('cottages')" v-bind:class="{ 'is-active': isActive === 'cottages' }" style="text-decoration: none;">
                <router-link to="/cottages" style="text-decoration: none; color: inherit;">
                  <div class="box is-info" v-if="isActive === 'cottages'"> Cottages </div>
                  <div class="tag is-info" v-if="isActive !== 'cottages'" > Cottages </div>
                </router-link>
              </li>
              <li @click="setActiveTab('boats')" v-bind:class="{ 'is-active': isActive === 'boats' }">
                <router-link to="/boats">
                  <div class="box is-info" v-if="isActive === 'boats'"> Boats </div>
                  <div class="tag is-info" v-if="isActive !== 'boats'"> Boats </div>
                </router-link>
              </li>
              <li @click="setActiveTab('instructors')" v-bind:class="{ 'is-active': isActive === 'instructors' }">
                <router-link to="/adventures" style="text-decoration: none; color: inherit;">
                  <div class="box is-info" v-if="isActive === 'instructors'"> Adventures </div>
                  <div class="tag is-info" v-if="isActive !== 'instructors'"> Adventures </div>
                </router-link>
              </li>
            </ul>
          </div>
        </nav>
      </div>
    </section>
    <div class="tab-contents">
      <div>
        <router-view style="margin-top:6rem"></router-view>
      </div>
    </div>
    <div :class="[$route.path.includes('Profile') ? 'big': 'smool']"> </div>
    <footer class="footer" style="">
      <div class="content has-text-centered">
        <p>
          <strong>Fishing Booker</strong> Full Stack <strong>Vue & Spring Boot</strong> demonstration by <a href="https://github.com/LukaM1999">Luka Miletić</a>,
          <a href="https://github.com/KisicM">Mihajlo Kisić</a> and  <a href="https://github.com/piwneuh">Filip Pinjuh</a>.
        </p>
      </div>
    </footer>
  </div>
</template>

<script>
import Login from "@/components/Login";
import Registration from "@/components/Registration";

export default {
  name: "LandingPage",
  data() {
    return {
      isActive: 'cottages'
    }
  },
  mounted() {
    this.$router.push('/cottages').catch(err => {
    });
    localStorage.removeItem('user');
    localStorage.removeItem('jwt');
  },
  methods: {
    setActiveTab(tab) {
      this.isActive = tab
    },
    loginModal() {
      this.$buefy.modal.open({
        parent: this,
        component: Login,
        hasModalCard: true,
        trapFocus: true
      })
    },
    registerModal() {
      this.$buefy.modal.open({
        parent: this,
        component: Registration,
        hasModalCard: true,
        trapFocus: true
      })
    }
  }
}
</script>
<style lang="scss">
.my-component-wrapper .tabs a {
  border-bottom-style: none;
}

.big{
  height: 50rem;
}

.smool{
  height: 6rem;
  }
</style>