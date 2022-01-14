<template>
  <div>
    <section class="hero is-primary is-medium">
<!--      &lt;!&ndash; Hero head: will stick at the top &ndash;&gt;-->
<!--      <div class="hero-head">-->
<!--        <nav class="navbar">-->
<!--          <div class="container">-->
<!--            <div class="navbar-brand">-->
<!--              <a class="navbar-item">-->
<!--                <img-->
<!--                    src="logo1.png"-->
<!--                    alt="Logo"-->
<!--                />-->
<!--              </a>-->
<!--            </div>-->
<!--          </div>-->
<!--        </nav>-->
<!--      </div>-->

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
              <li @click="setActiveTab('cottages')" v-bind:class="{ 'is-active': isActive === 'cottages' }"><router-link to="/cottages"> Cottages </router-link></li>
              <li @click="setActiveTab('boats')" v-bind:class="{ 'is-active': isActive === 'boats' }"><router-link to="/boats"> Boats </router-link></li>
              <li @click="setActiveTab('instructors')" v-bind:class="{ 'is-active': isActive === 'instructors' }"><router-link to="/adventures"> Adventures </router-link></li>
            </ul>
          </div>
        </nav>
      </div>
    </section>
    <div class="tab-contents">
      <div><router-view></router-view></div>

<!--      <div class="content" v-bind:class="{ 'is-active': isActive === 'cottages' }">-->
<!--        <cottages></cottages>-->
<!--      </div>-->
<!--      <div class="content" v-bind:class="{ 'is-active': isActive === 'boats' }">-->
<!--        <boats></boats>-->
<!--      </div>-->
<!--      <div class="content" v-bind:class="{ 'is-active': isActive === 'instructors' }">-->
<!--        <instructors></instructors>-->

    </div>
  </div>
</template>

<script>
import Cottages from "@/components/Cottages";
import Boats from "@/components/Boats";
import Instructors from "@/components/Instructors";
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
    this.$router.push('/cottages').catch(err => {});
    localStorage.removeItem('user');
    localStorage.removeItem('jwt');
  },
  methods: {
    setActiveTab(tab){
      this.isActive = tab
    },
    loginModal() {
      this.$buefy.modal.open({
        parent: this,
        component: Login,
        hasModalCard: true,
        //customClass: 'custom-class custom-class-2',
        trapFocus: true
      })
    },
    registerModal() {
      this.$buefy.modal.open({
        parent: this,
        component: Registration,
        hasModalCard: true,
        //customClass: 'custom-class custom-class-2',
        trapFocus: true
      })
    }
  }
}
</script>

<style scoped>
.is-primary {
  background-image: linear-gradient(to right, #99A799, #ADC2A9) !important;
}

.title {
  color: #FEF5ED !important;
}

.is-info {
  background-color: #8E806A !important;
}

.tab-contents .content {
  display: none;
}

.tab-contents .content.is-active {
  display: block;
}

.button:hover {
  background-color: #FEF5ED !important;
  color: #8E806A !important;
}

.hero.is-primary .tabs li.is-active a {
  color: #8E806A !important;
  background-color: white;
  opacity: 1;
}
</style>