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
          <button class="button is-info mr-5">Log in
          </button>

          <button class="button is-info">Register
          </button>
        </div>
      </div>

      <!-- Hero footer: will stick at the bottom -->
      <div class="hero-foot">
        <nav class="tabs">
          <div class="container">
            <ul>
              <li v-bind:class="{ 'is-active': isActive == 'cottages' }"><a v-on:click="isActive = 'cottages'">Cottages</a></li>
              <li v-bind:class="{ 'is-active': isActive == 'boats' }"><a v-on:click="isActive = 'boats'">Boats</a></li>
              <li v-bind:class="{ 'is-active': isActive == 'instructors' }"><a v-on:click="isActive = 'instructors'">Instructors </a></li>

            </ul>
          </div>
        </nav>
      </div>
    </section>
    <div class="tab-contents">
      <div class="content" v-bind:class="{ 'is-active': isActive == 'cottages' }">
        cottages
      </div>
      <div class="content" v-bind:class="{ 'is-active': isActive == 'boats' }">
        boats
      </div>
      <div class="content" v-bind:class="{ 'is-active': isActive == 'instructors' }">
        instructors
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "LandingPage",
  data(){
    return {
      isActive: 'cottages'
    }
  }
}
</script>

<style scoped>
.is-primary{
  background-image: linear-gradient(to right, #99A799 , #ADC2A9) !important;
}
.title{
  color: #FEF5ED !important;
}
.is-info{
  background-color: #8E806A !important;
}
.tab-contents .content {
  display: none;
}
.tab-contents .content.is-active {
  display: block;
}
.button:hover{
  background-color: #FEF5ED !important;
  color: #8E806A !important;
}
.hero.is-primary .tabs li.is-active a {
  color: #8E806A !important;
  background-color: white;
  opacity: 1;
}
</style>