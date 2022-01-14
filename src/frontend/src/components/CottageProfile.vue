<template>
  <div>
    <nav class="navbar is-primary">
      <div class="navbar-brand">
        <a class="navbar-item">
          <img :src="'/logo1.png'" alt="logo">
        </a>
        <div class="navbar-burger" data-bs-target="navbarExampleTransparentExample">
          <span></span>
          <span></span>
          <span></span>
        </div>
      </div>

      <div id="navbarExampleTransparentExample" class="navbar-menu">
        <div class="navbar-start">
          <a class="navbar-item">
            <a href="cottageOwner/cottages">
              My Cottages
            </a>
          </a>
        </div>
        <div class="navbar-end">
          <div class="navbar-item has-dropdown is-hoverable">
            <a class="navbar-link is-arrowless fa fa-user-alt fa-3x"></a>

            <div class="navbar-dropdown is-right is-boxed">
              <router-link to="/cottageOwner  /profile" class="navbar-item">
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

    <div class="row">
      <nav class="level is-info m-3 rounded">
        <div class="level-item has-text-left ml-5 is-justify-content-left">
          <div>
            <p class="heading">Name</p>
            <p class="title">{{ cottage.name }}</p>
          </div>
        </div>
      </nav>
    </div>
    <div class="row justify-content-center">
      <div class="col"></div>
      <div class="col-10">
        <b-carousel :autoplay="false" indicator-custom :indicator-inside="false" :overlay="gallery"
                    @click="switchGallery(true)">
          <b-carousel-item v-for="(item, i) in items" :key="i" style="height:80%">
            <a class="image ">
              <img :src="'/'+item" alt="'cottage_img'">
            </a>
          </b-carousel-item>
          <span v-if="gallery" @click="switchGallery(false)" class="modal-close is-large"/>
          <template #indicators="props">
            <figure class="al image" :draggable="false">
              <img :draggable="false" :src="getImgUrl(props.i+1)" :title="props.i+1" alt="'cottage_img'">
            </figure>
          </template>
        </b-carousel>
      </div>
      <div class="col"></div>
    </div>
    <div class="row">
      <div class="col">
        <nav class="level is-info m-3 rounded">
          <div class="level-item has-text-centered">
            <div>
              <p class="heading">Capacity</p>
              <p class="title">{{ cottage.capacity }}</p>
            </div>
          </div>
          <div class="level-item has-text-centered">
            <div>
              <p class="heading">Rooms</p>
              <p class="title">{{ cottage.rooms }}</p>
            </div>
          </div>
          <div class="level-item has-text-centered">
            <div>
              <p class="heading">Beds per room</p>
              <p class="title">{{ cottage.bedsPerRoom }}</p>
            </div>
          </div>
          <div class="level-item has-text-centered">
            <div>
              <p class="heading">per person/day</p>
              <p class="title">{{ cottage.price }}$</p>
            </div>
          </div>
        </nav>
      </div>
      <div class="col m-5">

      </div>

    </div>
  </div>
</template>

<script>
import "buefy/dist/cjs/carousel"
import {Store} from "@/main";

export default {
  name: "CottageProfile",
  data() {
    return {
      cottage: JSON.parse(localStorage.getItem('currentCottage')),
      items: [],
      gallery: false
    }
  },
  async mounted() {
    this.items = this.cottage.images.split(';')
  },
  methods: {
    logOut() {
      Store.user = null
      localStorage.removeItem('jwt')
      this.$router.push('/')
    },
    getImgUrl(value) {
      return `/c${this.cottage.id}.${value}.jpg`
    },
    switchGallery(value) {
      this.gallery = value
      if (value) {
        return document.documentElement.classList.add('is-clipped')
      } else {
        return document.documentElement.classList.remove('is-clipped')
      }
    }
  }
}
</script>

<style scoped>
.is-active .al img {
  filter: grayscale(0%);
}

.al img {
  filter: grayscale(100%);
}

.carousel-item {
  display: block;
}
.is-primary {
  background-image: linear-gradient(to right, #99A799, #ADC2A9) !important;
}
.is-info{
  background-image: linear-gradient(to bottom, ghostwhite, white) !important;
}
nav {
  padding: 0 !important;
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