<template>
  <div class="tile is-ancestor m-5">
    <div class="tile is-4 is-vertical is-parent">
      <div class="tile is-child box">
        <p class="title">{{ cottage.name }}</p>
        <b-carousel :autoplay="false" indicator-custom :indicator-inside="false" :overlay="gallery"
                    @click="switchGallery(true)">
          <b-carousel-item v-for="(item, i) in items" :key="i" style="height:80%">
            <a class="image ">
              <img :src="backend + '/'+item" alt="'cottage_img'">
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
        <button v-if="authority === 'CUSTOMER'" @click="subscribe()" class="button is-primary mb-5">
          {{subscriptionText}}
        </button>
        <div class="row">
          <div class="col">
            <b-field v-show="authority==='COTTAGE_OWNER'" label="Add available days">
              <b-datepicker
                  placeholder="Click to select..."
                  v-model="dates"
                  range
                  :min-date="minDate">
              </b-datepicker>
              <button class="button ml-5 is-link" @click="createFreeTerm()">Submit</button>
            </b-field>
          </div>
          <div class="col">
            <b-field v-show="authority==='COTTAGE_OWNER'" label="Add day off">
              <b-datepicker
                  placeholder="Click to select..."
                  v-model="selectedDate"
                  :min-date="minDate">
              </b-datepicker>
              <button class="button ml-5 is-link" @click="addDayOff()">Submit</button>
            </b-field>
          </div>
        </div>

        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam semper diam at erat pulvinar, at pulvinar felis blandit. Vestibulum volutpat tellus diam, consequat gravida libero rhoncus ut. Morbi maximus, leo sit amet vehicula eleifend, nunc dui porta orci, quis semper odio felis ut quam.</p>
        <p>Suspendisse varius ligula in molestie lacinia. Maecenas varius eget ligula a sagittis. Pellentesque interdum, nisl nec interdum maximus, augue diam porttitor lorem, et sollicitudin felis neque sit amet erat. Maecenas imperdiet felis nisi, fringilla luctus felis hendrerit sit amet. Aenean vitae gravida diam, finibus dignissim turpis. Sed eget varius ligula, at volutpat tortor.</p>
        <p>Integer sollicitudin, tortor a mattis commodo, velit urna rhoncus erat, vitae congue lectus dolor consequat libero. Donec leo ligula, maximus et pellentesque sed, gravida a metus. Cras ullamcorper a nunc ac porta. Aliquam ut aliquet lacus, quis faucibus libero. Quisque non semper leo.</p>
        <div class="row mt-5" v-show="authority==='COTTAGE_OWNER'">
          <div class="col"></div>
          <div class="col-8">
            <calendar :key="key">
            </calendar>
          </div>
          <div class="col"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import "buefy/dist/cjs/carousel"
import {Store} from "@/main";
import moment from "moment";
import {backend} from "@/env";
import Calendar from "@/components/Calendar";
import axios from "axios";

export default {
  name: "CottageProfile",
  components: {Calendar},
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
      minDate: new Date(),
      backend: backend,
      key: 0,
      selectedDate: null,
      subscriptionText: 'Subscribed',
    }
  },
  async mounted() {
    this.items = this.cottage.images.split(';')
    this.user = JSON.parse(localStorage.getItem('user'))
    this.authority = this.user?.role.authority
    this.minDate = moment().add(1, 'days').toDate()
    this.minDate.setHours(0)
    this.minDate.setMinutes(0)
    await this.getIsSubscribed()
  },
  methods: {
    async getIsSubscribed() {
      if (this.authority !== 'CUSTOMER') {
        return false
      }
      const {data} = await axios.get(`${backend}/saleSubscription/isSubscribed`, {params: {entityName: this.cottage.name
      ,ownerUsername: this.cottage.ownerUsername, customerUsername: this.user.username}})
      if(data) {
        this.subscriptionText = 'Unsubscribe'
        return true
      }
      this.subscriptionText = 'Subscribe'
      return false
    },
    async subscribe(){
      if (this.authority !== 'CUSTOMER') {
        return
      }
      const isSubscribed = await this.getIsSubscribed()
      if(isSubscribed) {
        await axios.delete(`${backend}/saleSubscription`, {params: {entityName: this.cottage.name
        ,ownerUsername: this.cottage.ownerUsername, customerUsername: this.user.username}}).catch(e => {
          this.$toasted.error('Error occurred while unsubscribing')
          throw e
        })
        this.subscriptionText = 'Subscribe'
        this.$toasted.success('Unsubscribed successfully')
        return
      }
      await axios.post(`${backend}/saleSubscription`, {entityName: this.cottage.name
      ,ownerUsername: this.cottage.ownerUsername, customerUsername: this.user.username}).catch(e => {
        this.$toasted.error('Failed to subscribe')
        throw e
      })
      this.subscriptionText = 'Unsubscribe'
      this.$toasted.success('Subscribed successfully!')
    },
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
      const response = await this.axios.post(backend + '/reservation/createFreeTerm', freeTerm)
      if (response.data) {
        this.$toasted.success('Free Term successfully created!')
        this.key = this.key + 1
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
      return `${backend}/c${this.cottage.id}.${value}.jpg`
    },
    switchGallery(value) {
      this.gallery = value
      if (value) {
        return document.documentElement.classList.add('is-clipped')
      } else {
        return document.documentElement.classList.remove('is-clipped')
      }
    },
    async addDayOff(){
      const dayOff = {
        start: this.formatDate(this.selectedDate),
        end: this.formatDate(this.selectedDate),
        username: this.user.username,
        rentableName: this.cottage.name,
        type: this.user.role.authority === "INSTRUCTOR" ? 2 : 0,
      }
      const response = await axios.post(backend + '/reservation/createDayOff', dayOff)
      if(response.data != null){
        this.$toasted.success('Day off successfully created!')
        this.key = this.key + 1
      }
      else
        this.$toasted.error('Something went wrong!')
    },
    formatDate(date){
      return moment(date).format('YYYY-MM-DD HH:mm')
    },
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