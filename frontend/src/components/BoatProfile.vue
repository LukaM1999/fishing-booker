<template>
  <div class="tile is-ancestor m-5">
    <div class="tile is-4 is-vertical is-parent sticky">
      <div class="tile is-child box">
        <p class="title"><strong>{{ boat.name }}</strong></p>
        <b-carousel :autoplay="false" indicator-custom :indicator-inside="false" :overlay="gallery"
                    @click="switchGallery(false)">
          <b-carousel-item v-for="(item, i) in items.slice(0, items.length-1)" :key="i" style="height:80%">
            <a class="image ">
              <img :src="backend + '/'+item" alt="'boat_img'">
            </a>
          </b-carousel-item>
          <span v-if="gallery" @click="switchGallery(false)" class="modal-close is-large"/>
          <template #indicators="props">
            <figure class="al image" :draggable="false">
              <img :draggable="false" :src="getImgUrl(props.i)" :title="props.i" alt="'boat_img'">
            </figure>
          </template>
        </b-carousel>
      </div>
      <div class="tile is-child box">
        <nav class="level is-info rounded">
          <div class="level-item has-text-centered">
            <div>
              <p class="heading">per person/day</p>
              <p class="title">{{ boat.price }}$</p>
            </div>
          </div>
          <div class="level-item has-text-centered">
            <div>
              <p class="heading">Capacity</p>
              <p class="title">{{ boat.capacity }}</p>
            </div>
          </div>
          <div class="level-item has-text-centered">
            <div>
              <p class="heading">Length</p>
              <p class="title">{{ boat.length }} m</p>
            </div>
          </div>
        </nav>
        <nav class="level is-info mt-5  rounded">
          <div class="level-item has-text-centered">
            <div>
              <p class="heading">Motors</p>
              <p class="title">{{ boat.motors }}</p>
            </div>
          </div>
          <div class="level-item has-text-centered">
            <div>
              <p class="heading">Max Speed</p>
              <p class="title">{{ boat.maxSpeed }}</p>
            </div>
          </div>
          <div class="level-item has-text-centered">
            <div>
              <p class="heading">Power</p>
              <p class="title">{{ boat.power }}</p>
            </div>
          </div>
        </nav>
        <nav class="level is-info mt-5  rounded">
          <div class="level-item has-text-centered">
            <div>
              <p class="heading">Boat rating</p>
              <p class="title">{{boat.averageRating}}/5</p>
            </div>
          </div>
          <div class="level-item has-text-centered">
            <div>
              <p class="heading">Owner rating</p>
              <p class="title">{{boat.boatOwner.averageRating}}/5</p>
            </div>
          </div>
        </nav>
      </div>
      <div class="tile is-child box">
        <l-map
            :zoom="zoom"
            :center="center"
            style="height: 350px; width: 100%"
        >
          <l-tile-layer
              :url="url"
          />
          <l-marker :lat-lng="marker"/>
        </l-map>
        <p class="subtitle mt-5">{{ boat.address }}, {{ boat.city }}, {{ boat.country }}</p>
      </div>
    </div>
    <div class="tile is-parent">
      <div class="tile is-child box is-dark">

        <div class="my-5"> <p class="is-size-3">More about this boat</p></div>
        <p class="is-size-5">{{ boat.promoDescription }}</p>
        <p class="is-size-5 mt-3">Rules: {{ boat.rules }}</p>
        <p class="is-size-5 mt-3">Fishing Equipment: {{ boat.fishingEquipment }}</p>
        <span class="tag is-primary is-medium my-5" v-if="boat.fishFinder">Fish finder✔</span>
        <span class="tag is-primary is-medium my-5 ml-5" v-if="boat.gps">GPS✔</span>
        <span class="tag is-primary is-medium my-5 ml-5" v-if="boat.radar">Radar✔</span>
        <span class="tag is-primary is-medium my-5 ml-5" v-if="boat.vhfRadio">VHFRadio✔</span>
        <div class="my-lg-5"> <p class="is-size-3"> Additional services </p> </div>
        <div class="row justify-content-left">
          <div class="col-4">
            <div v-for="s in services" :key="s.name">
              <span class="tag is-info is-large mb-5">{{ s.name }}  {{s.price}}€</span>
            </div>
          </div>
        </div>
        <p class="is-size-3 mt-5"> Actions for this boat: </p>
        <p class="subtitle mt-5" v-if="actions.length===0"> Unfortunately there are no special deals for this boat</p>
        <div class="columns mt-5 scrollable mb-5" v-if="actions.length !== 0">
          <div class="col-3 ml-3" v-for="a in actions" v-bind:key="a.id">
            <div class="card" style="background-color: #f5f8f3">
              <div class="card-content">
                <p class="subtitle">
                  <strong> Start Date: {{ a.startTime | formatDate }}</strong>
                </p>
                <p class="subtitle">
                  <strong> End Date: {{ a.endTime | formatDate }}</strong>
                </p>
                <p class="subtitle">
                  <strong><strike>Old price {{(a.price * a.salePercent).toString().substring(0,2)}}€</strike></strong>
                </p>
                <p class="subtitle">
                  <strong>New price {{a.price}}€ </strong>
                </p>

              </div>
              <footer class="card-footer">
                <p class="card-footer-item"  v-if="authority==='CUSTOMER'">
                  <button class="button is-link" @click="reserveAction(a)">
                    Reserve
                  </button>
                </p>
              </footer>
            </div>
          </div>
        </div>
        <div class="mt-5">
          <button class="button is-link my-5" v-if="authority==='BOAT_OWNER'" @click="createAction()">
            Create an action for this boat
          </button>
        </div>
        <button v-if="authority === 'CUSTOMER'" @click="subscribe()" class="button is-primary mb-5">
          {{subscriptionText}}
        </button>
        <div class="row mt-lg-5">
          <div class="col"></div>
          <div class="col">
            <b-field v-show="authority==='BOAT_OWNER'" label="Add available days">
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
            <b-field v-show="authority==='BOAT_OWNER'" label="Add day off">
              <b-datepicker
                  placeholder="Click to select..."
                  v-model="selectedDate"
                  :min-date="minDate">
              </b-datepicker>
              <button class="button ml-5 is-link" @click="addDayOff()">Submit</button>
            </b-field>
          </div>
          <div class="col"></div>
          <div class="col">
            <b-field v-show="authority==='BOAT_OWNER'" label="Check more" class="ml-lg-5">
              <button  class="button is-primary" @click="routeToStatistics()">Statistics</button>
            </b-field>
          </div>
          <div class="col-1"></div>
        </div>
        <div class="row my-5" v-show="authority==='BOAT_OWNER'">
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
import Calendar from "@/components/Calendar";
import {LMap, LMarker, LTileLayer} from "vue2-leaflet";
import {backend} from "@/env";
import {latLng} from "leaflet";
import moment from "moment";
import axios from "axios";
import {Store} from "@/main";

export default {
  name: "BoatProfile",
  components: {Calendar, LMap, LTileLayer, LMarker},
  data() {
    return {
      boat: JSON.parse(localStorage.getItem('currentBoat')),
      items: [],
      dates: [],
      actions: [],
      gallery: false,
      authority: '',
      user: null,
      type: 'BOAT',
      ownerUsername: '',
      startTime: new Date(),
      endTime: new Date(),
      minDate: new Date(),
      backend: backend,
      key: 0,
      selectedDate: null,
      subscriptionText: 'Subscribed',
      zoom: 13,
      services: [],
      center: [47.41322, -1.219482],
      marker: latLng(47.41322,-1.219482),
      url: 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
    }
  },
  async mounted() {
    this.items = this.boat.images.split(';')
    this.user = JSON.parse(localStorage.getItem('user'))
    this.authority = this.user?.role.authority
    this.minDate = moment().add(1, 'days').toDate()
    this.minDate.setHours(0)
    this.minDate.setMinutes(0)
    await this.getIsSubscribed()
    await this.getActions()
    await this.getGeoLocation()
    this.getServices()
  },
  methods: {
    async getGeoLocation(){
      let config = {
        method: 'get',
        url: `https://api.geoapify.com/v1/geocode/search?text=${this.boat.country}' '${this.boat.city}' '${this.boat.address}&lang=en&apiKey=3e759fb569c640dcb289d932c50d4962`,
      };

      const {data} = await axios(config).catch(err => {
        console.log(err)
      })
      this.center = [data.features[0].geometry.coordinates[1], data.features[0].geometry.coordinates[0]]
      this.marker = latLng(data.features[0].properties.lat, data.features[0].properties.lon)

    },
    async getIsSubscribed() {
      if (this.authority !== 'CUSTOMER') {
        return false
      }
      const {data} = await axios.get(`${backend}/saleSubscription/isSubscribed`, {params: {entityName: this.boat.name
          ,ownerUsername: this.boat.ownerUsername, customerUsername: this.user.username}})
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
        await axios.delete(`${backend}/saleSubscription`, {params: {entityName: this.boat.name
            ,ownerUsername: this.boat.ownerUsername, customerUsername: this.user.username}}).catch(e => {
          this.$toasted.error('Error occurred while unsubscribing')
          throw e
        })
        this.subscriptionText = 'Subscribe'
        this.$toasted.success('Unsubscribed successfully')
        return
      }
      await axios.post(`${backend}/saleSubscription`, {entityName: this.boat.name
        ,ownerUsername: this.boat.ownerUsername, customerUsername: this.user.username, email: this.user.email}).catch(e => {
        this.$toasted.error('Failed to subscribe')
        throw e
      })
      this.subscriptionText = 'Unsubscribe'
      this.$toasted.success('Subscribed successfully!')
    },
    async createFreeTerm() {
      let sYear = this.dates[0].getFullYear()
      let sMonth = this.formatDateMonth(new Date(this.dates[0]));
      let sDay = this.formatDateDay(new Date(this.dates[0]));
      let eYear = this.dates[this.dates.length - 1].getFullYear()
      let eMonth = this.formatDateMonth(new Date(this.dates.slice(-1)[0]));
      let eDay = this.formatDateDay(new Date(this.dates.slice(-1)[0]));

      const freeTerm = {
        type: this.type,
        entityName: this.boat.name,
        ownerUsername: this.user.username,
        startTime: sYear + '-' + sMonth + '-' + sDay + ' 00:00',
        endTime: eYear + '-' + eMonth + '-' + eDay + ' 00:00'
      }
      const response = await this.axios.post(backend + '/reservation/createFreeTerm', freeTerm)
      if (response.data) {
        this.$toasted.success('Free Term successfully created!')
        this.key = this.key + 1
      } else {
        this.$toasted.error('Error while creating free term.')
      }
    },
    async getActions() {
      const response = await this.axios.post(backend + '/reservation/getActions', {
        name: this.boat.name,
        ownerUsername: this.boat.ownerUsername
      })
      if (response.data) {
        this.actions = response.data;
      }
    },
    async reserveAction(action){
      await this.axios.patch(backend + '/reservation/reserveAction', {
        id: action.id,
        customerUsername: this.user.username
      }).catch(e => {
        this.$toasted.error('Error while reserving action.')
        throw e
      })
      this.$toasted.success('Action successfully reserved!')
      this.actions.splice(this.actions.findIndex(a => a.id === action.id), 1)
      const email = {
        service_id: 'service_approve',
        template_id: 'template_approved',
        user_id: 'user_62WYz6KIasgbXlUB5EEGw',
        template_params: {
          'to_name': this.user.username,
          'to_email': JSON.parse(localStorage.getItem('user') || '{}')?.email,
          'subject': 'Reservation confirmed',
          'message': `<p>Your reservation for <strong>${action.name}</strong> has been confirmed.</p>
          <p><u>Start time</u>: ${action.startTime}</p>
          <p><u>End time</u>: ${action.endTime}</p>
          <p><u>Number of guests</u>: ${action.guests}</p>
          <p><u>Additional services</u>: ${action.additionalServices}</p>
          <p><u>Discount</u>: ${action.salePercent}%</p>
          <br>
          <p><u><strong>TOTAL PRICE</strong></u>: ${action.price} EUR</p>`
        }
      };
      await axios.post('https://api.emailjs.com/api/v1.0/email/send', email).catch(error => {
        this.$toasted.error('Reservation email could not be sent')
        throw error
      })
      await this.$router.push('/customer/history')
    },
    async createAction() {
      await this.$router.push({path: '/boatOwner/action', params: {boatId: this.boat.id}})
    },
    routeToStatistics() {
      this.$router.push('/boatOwner/statistics')
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
      return `${backend}/${this.boat.images.split(';')[value]}`
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
        rentableName: this.boat.name,
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
    getServices(){
      this.services = [];
      this.services = this.boat.additionalServices.split(';').flatMap(service => {
        let s = service.split('=')
        if (s[0] === '') return []
        return {name: s[0], price: parseFloat(s[1])}
      })
    }
  }
}
</script>

<style scoped lang="scss">
.is-active .al img {
  filter: grayscale(0%);
}

.al img {
  filter: grayscale(100%);
}

.carousel-item {
  display: block;
}

.sticky{
  max-height: 600px;
}

::v-deep .vuecal--green-theme .vuecal__menu {
  background-color: #84A98C;
  color: #fff;
}
</style>