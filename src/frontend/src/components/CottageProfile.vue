<template>
  <div class="tile is-ancestor m-5">
    <div class="tile is-4 is-vertical is-parent">
      <div class="tile is-child box">
        <p class="title">{{ cottage.name }}</p>
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
      <div class="tile is-child box">
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
        </nav>
        <nav class="level is-info m-3 rounded">
          <div class="level-item has-text-centered">
            <div>
              <p class="heading">per person/day</p>
              <p class="title">{{ cottage.price }}$</p>
            </div>
          </div>
        </nav>
      </div>
    </div>
    <div class="tile is-parent">
      <div class="tile is-child box is-dark">
        <p class="title">Three</p>
        <button class="button is-primary mb-5">Hey there traveler</button>
        <b-field v-show="authority==='COTTAGE_OWNER'" label="Select a free term">
          <b-datepicker
              placeholder="Click to select..."
              v-model="dates"
              range
              :min-date="minDate">
          </b-datepicker>
          <button class="button ml-5 is-link" @click="createFreeTerm()"> Create free term</button>
        </b-field>
        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam semper diam at erat pulvinar, at pulvinar felis blandit. Vestibulum volutpat tellus diam, consequat gravida libero rhoncus ut. Morbi maximus, leo sit amet vehicula eleifend, nunc dui porta orci, quis semper odio felis ut quam.</p>
        <p>Suspendisse varius ligula in molestie lacinia. Maecenas varius eget ligula a sagittis. Pellentesque interdum, nisl nec interdum maximus, augue diam porttitor lorem, et sollicitudin felis neque sit amet erat. Maecenas imperdiet felis nisi, fringilla luctus felis hendrerit sit amet. Aenean vitae gravida diam, finibus dignissim turpis. Sed eget varius ligula, at volutpat tortor.</p>
        <p>Integer sollicitudin, tortor a mattis commodo, velit urna rhoncus erat, vitae congue lectus dolor consequat libero. Donec leo ligula, maximus et pellentesque sed, gravida a metus. Cras ullamcorper a nunc ac porta. Aliquam ut aliquet lacus, quis faucibus libero. Quisque non semper leo.</p>
      </div>
    </div>
  </div>
</template>

<script>
import "buefy/dist/cjs/carousel"
import {Store} from "@/main";
import moment from "moment";

export default {
  name: "CottageProfile",
  data() {
    return {
      cottage: JSON.parse(localStorage.getItem('currentCottage')),
      items: [],
      dates: [],
      gallery: false,
      authority: '',
      user: null,
      type: 'COTTAGE',
      enitityName: '',
      ownerUsername: '',
      startTime: new Date(),
      endTime: new Date(),
      minDate: new Date()
    }
  },
  async mounted() {
    this.items = this.cottage.images.split(';')
    this.user = JSON.parse(localStorage.getItem('user'))
    this.authority = this.user?.role.authority
    this.minDate = moment().add(1, 'days').toDate()
    this.minDate.setHours(0)
    this.minDate.setMinutes(0)
  },
  methods: {
    async createFreeTerm(){
      let sYear = this.dates[0].getFullYear()
      let sMonth = this.formatDateMonth(new Date(this.dates[0]));
      let sDay = this.formatDateDay(new Date(this.dates[0]));
      let eYear = this.dates[this.dates.length - 1].getFullYear()
      let eMonth = this.formatDateMonth(new Date(this.dates.slice(-1)[0]));
      let eDay = this.formatDateDay(new Date(this.dates.slice(-1)[0]));

      const freeTerm = {
        type: this.type,
        entityName: this.cottage.name,
        ownerUsername: this.user.username,
        startTime: sYear + '-' + sMonth + '-' + sDay + ' 00:00',
        endTime: eYear + '-' + eMonth + '-' + eDay + ' 00:00'
      }
      console.log(freeTerm)
      const response = await this.axios.post('/reservation/createFreeTerm', freeTerm)
      if (response.data) {
        this.$toasted.success('Free Term successfully created!')
      } else {
        this.$toasted.error('Error while creating free term.')
      }
    },
    formatDateMonth(date) {
      if (date.getMonth() + 1 < 10)
        return `0${date.getMonth() + 1}`
      else
        return `${date.getMonth() + 1}`
    },
    formatDateDay(date) {
      if (date.getDate() < 10)
        return `0${date.getDate()}`
      else
        return `${date.getDate()}`
    },
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
</style>