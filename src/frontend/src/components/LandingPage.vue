<template>
  <div>
    <section class="hero is-primary is-medium">
      <!-- Hero head: will stick at the top -->
      <div class="hero-head">
        <nav class="navbar">
          <div class="container">
            <div class="navbar-brand">
              <a class="navbar-item">
                <img
                    src=""
                    alt="Logo"
                />
              </a>
            </div>
          </div>
        </nav>
      </div>

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
              <li v-bind:class="{ 'is-active': isActive == 'cottages' }"><a
                  v-on:click="setActiveTab('cottages')">Cottages</a></li>
              <li v-bind:class="{ 'is-active': isActive == 'boats' }"><a v-on:click="setActiveTab('boats')">Boats</a></li>
              <li v-bind:class="{ 'is-active': isActive == 'instructors' }"><a v-on:click="setActiveTab('instructors')">Instructors </a>
              </li>
            </ul>
          </div>
        </nav>
      </div>
    </section>
    <div class="tab-contents">
      <div class="content" v-bind:class="{ 'is-active': isActive == 'cottages' }">
        <cottages></cottages>
      </div>
      <div class="content" v-bind:class="{ 'is-active': isActive == 'boats' }">
        <boats></boats>
      </div>
      <div class="content" v-bind:class="{ 'is-active': isActive == 'instructors' }">
        <instructors></instructors>
      </div>
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
  components: {Instructors, Boats, Cottages},
  data() {
    return {
      isActive: 'cottages'
    }
  },
  methods: {
    setActiveTab(tab){
      this.isActive = tab
      this.$nextTick(() => window.scrollTo(0,document.body.scrollHeight))
      // this.$nextTick(() => {
      //   const expands = document.getElementsByClassName('fa-arrow-circle-down')
      //   for (const expand of expands) {
      //     expand.click()
      //   }
      // })
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