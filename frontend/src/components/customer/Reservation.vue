<template>
  <div class="row mt-5">
    <div class="col">
      <div class="row" v-if="reservationBlocked">
        <div class="col">
          <h1 class="text-center">Booking new reservations denied.</h1>
        </div>
      </div>
      <b-steps
          v-model="activeStep"
          :animated="true"
          :rounded="true"
          :has-navigation="true" v-if="!reservationBlocked">
        <b-step-item step="1" label="Type and date" :clickable="false">
          <form @submit.prevent="nextStep">
            <div class="row mb-3 mt-4 justify-content-center">
              <div class="col-md-3">
                <div class="form-floating">
                  <select class="form-select" id="floatingType"
                          @change="setDatetime"
                          v-model="type" required>
                    <option selected value="COTTAGE">Cottage</option>
                    <option value="BOAT">Boat</option>
                    <option value="ADVENTURE">Adventure</option>
                  </select>
                  <label for="floatingType">Type*</label>
                </div>
              </div>
            </div>
            <div class="row mb-3 mt-4 justify-content-center">
              <div class="col-md-2">
                <b-field label="Select date">
                  <b-datepicker
                      :min-date="minDate"
                      v-model="selectedDate">
                  </b-datepicker>
                </b-field>
              </div>
              <div class="col-md-2" v-if="type === 'ADVENTURE'">
                <b-field label="Select hours">
                  <b-timepicker
                      :hour-format="'24'"
                      :increment-minutes="60"
                      :min-time="new Date(2022, 1, 9, 8, 0, 0)"
                      :max-time="new Date(2022, 1, 9, 19, 0, 0)"
                      v-model="selectedTime"
                      @input="duration = 1">
                  </b-timepicker>
                </b-field>
              </div>
              <div class="col-md-2">
                <b-field :label="type === 'ADVENTURE' ? 'Select duration (hours)' : 'Select duration (days)'">
                  <b-numberinput v-if="type === 'ADVENTURE'"
                                 :max="setMaxDuration()"
                                 :min="1"
                                 v-model="duration">
                  </b-numberinput>
                  <b-numberinput v-if="type !== 'ADVENTURE'"
                                 :min="1"
                                 v-model="duration">
                  </b-numberinput>
                </b-field>
              </div>
              <div class="col-md-2">
                <b-field :label="'Select number of people'">
                  <b-numberinput
                      :max="100"
                      :min="1"
                      v-model="people">
                  </b-numberinput>
                </b-field>
              </div>
            </div>
          </form>
          <div>
            <hr class="w-100"/>
            <section class="section">
              <h1 class="title">Total price: {{ calculateTotal() }} euros</h1>
            </section>
          </div>
        </b-step-item>

        <b-step-item step="2" label="Search, filter and select" :clickable="false">
          <div>
            <div class="row mt-5 justify-content-center">
              <div class="col-md-2">
                <div class="form-floating">
                  <input type="text" class="form-control" id="rentableName" v-model="nameSearch">
                  <label for="rentableName">Name</label>
                </div>
              </div>
              <div class="col-md-2">
                <div class="form-floating">
                  <input type="text" class="form-control" id="rentableLocation" v-model="locationSearch">
                  <label for="rentableLocation">Location</label>
                </div>
              </div>
              <!--      <div class="col-md-2 d-flex justify-content-center">-->
              <!--        <button class="btn btn-lg btn-primary dropdown-toggle" type="button" id="dropdownMenu1"-->
              <!--                data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="true">-->
              <!--          Select types-->
              <!--        </button>-->
              <!--        <ul class="dropdown-menu checkbox-menu allow-focus keep-open" aria-labelledby="dropdownMenu1">-->
              <!--          <li v-for="(type, i) in allTypes">-->
              <!--            <label>-->
              <!--              <input type="checkbox" v-model="typeCheckboxes[i]">-->
              <!--              {{ type }}-->
              <!--            </label>-->
              <!--          </li>-->
              <!--        </ul>-->
              <!--      </div>-->
              <div class="col-md-1">
                <div class="form-floating">
                  <input type="number" class="form-control" id="rentableRating" v-model.number="ratingSearch" max="5"
                         min="0">
                  <label for="rentableRating">Rating</label>
                </div>
              </div>
              <div class="col-md-1" style="width: 8.7%">
                <div class="form-floating">
                  <select v-model="sortBy" class="form-select" id="rentableSort">
                    <option value="">None</option>
                    <option value="Name">Name</option>
                    <option value="Location" selected>Location</option>
                    <option value="Rating">Rating</option>
                  </select>
                  <label for="rentableSort">Sort by</label>
                </div>
              </div>
              <div class="col-md-1 align-self-center d-flex justify-content-center">
                <button class="btn btn-primary" @click="setSortOrder">
                  <i :class="[ascending ? 'fa fa-sort-up' : 'fa fa-sort-down']"></i>
                </button>
              </div>
              <!--        <div class="col-md-1 align-self-center">-->
              <!--          <input type="checkbox" class="form-check-input" id="onlyOpen" v-model="onlyOpen">-->
              <!--          <label for="onlyOpen" style="color:white;">Only open</label>-->
              <!--        </div>-->
            </div>
            <div class="md-layout md-alignment-center mt-5" v-if="rentables.length > 0">
              <div class="md-layout-item md-large-size-30 md-xlarge-size-30"
                   v-for="rentable in filteredRentables.slice((current-1)*perPage,(current-1)*perPage+perPage)"
                   :key=rentable.id
                   @click="setSelectedRentable(rentable)"
                   :class="{ 'is-active': selectedRentable && selectedRentable.id === rentable.id }">
                <md-card class="md-primary" md-theme="orange-card" md-with-hover>
                  <md-ripple>
                    <md-card-media md-ratio="4:3">
                      <img :src="rentable.images ? backend + '/' + rentable.images[0].toLowerCase() + rentable.id + '.1.jpg' : ''"
                           style="height: 100%"
                           alt="Rentable image">
                    </md-card-media>
                    <md-card-area>
                      <md-card-header>
                        <p class="md-title">{{ rentable.name }}</p>
                        <p class="md-subhead">{{ rentable.address }}, {{ rentable.city }}, {{ rentable.country }}</p>
                        <p class="md-subhead">{{ rentable.promoDescription }}</p>
                      </md-card-header>
                    </md-card-area>
                    <md-card-expand>
                      <md-card-actions md-alignment="right">
                        <md-card-expand-trigger>
                          <md-button class="md-icon-button">
                            <span class="fa fa-arrow-circle-down fa-2x"></span>
                          </md-button>
                        </md-card-expand-trigger>
                      </md-card-actions>
                      <md-card-expand-content>
                        <md-card-content>
                        </md-card-content>
                      </md-card-expand-content>
                    </md-card-expand>
                  </md-ripple>
                </md-card>
              </div>
            </div>
            <div class="mt-4" v-if="rentables.length > 0">
              <b-pagination
                  :total="total"
                  v-model="current"
                  :range-before="3"
                  :range-after="3"
                  :simple="false"
                  :rounded="false"
                  :order="'is-centered'"
                  :per-page="perPage"
                  :icon-prev="'chevron-left'"
                  :icon-next="'chevron-right'"
                  aria-next-label="Next page"
                  aria-previous-label="Previous page"
                  aria-page-label="Page"
                  aria-current-label="Current page">
              </b-pagination>
            </div>
            <div class="row mt-5 mb-5" v-if="rentables.length === 0">
              <div class="col is-center">
                <h1 class="title has-text-centered">No available {{ this.type.toLowerCase() }}s for selected time and
                  filters</h1>
              </div>
            </div>
          </div>
          <div>
            <hr class="w-100"/>
            <section class="section">
              <h1 class="title">Total price: {{ calculateTotal() }} euros</h1>
            </section>
          </div>
        </b-step-item>

        <b-step-item step="3" label="Additional services" :clickable="false">
          <div class="row justify-content-center">
            <div class="col-4">
              <b-table class="mt-4 mb-4" v-if="services.length > 0"
                       :data="services"
                       :columns="columns"
                       :checked-rows.sync="selectedServices"
                       checkable
                       hoverable
                       striped
                       :checkbox-position="'left'">
              </b-table>
            </div>
            <div>
              <hr class="w-100"/>
              <section class="section">
                <h1 class="title">Total price: {{ calculateTotal() }} euros</h1>
              </section>
            </div>
          </div>
        </b-step-item>

        <b-step-item step="4" label="Confirm reservation" :clickable="false">
          <div class="row justify-content-center">
            <div class="col-5" v-if="selectedRentable">
              <div class="md-layout md-alignment-center mt-5">
                <div class="md-layout-item md-large-size-30 md-xlarge-size-30" style="min-width: 50% !important;">
                  <md-card class="md-primary" md-theme="orange-card" md-with-hover>
                    <md-ripple>
                      <md-card-media md-ratio="4:3">
                        <img :src="backend + '/' + type[0].toLowerCase() + selectedRentable.id + '.1.jpg'" style="height: 100%"
                             alt="Rentable image">
                      </md-card-media>
                      <md-card-area>
                        <md-card-header>
                          <p class="md-title">{{ selectedRentable.name }}</p>
                          <p class="md-subhead">{{ setSelectedRentable.address }}, {{ selectedRentable.city }},
                            {{ selectedRentable.country }}</p>
                          <p class="md-subhead">{{ selectedRentable.promoDescription }}</p>
                        </md-card-header>
                      </md-card-area>
                      <md-card-expand>
                        <md-card-actions md-alignment="right">
                          <md-card-expand-trigger>
                            <md-button class="md-icon-button">
                              <span class="fa fa-arrow-circle-down fa-2x"></span>
                            </md-button>
                          </md-card-expand-trigger>
                        </md-card-actions>
                        <md-card-expand-content>
                          <md-card-content>
                          </md-card-content>
                        </md-card-expand-content>
                      </md-card-expand>
                    </md-ripple>
                  </md-card>
                </div>
              </div>
            </div>
            <div class="col-4">
              <b-table class="" v-if="selectedServices.length > 0"
                       :data="selectedServices"
                       :columns="columns"
                       hoverable
                       striped
                       :checkbox-position="'left'">
              </b-table>
              <div class="row mt-2">
                <div class="col">
                  <h1 class="subtitle">Additional services total:</h1>
                </div>
                <div class="col justify-content-end d-flex mr-2">
                  <h1 class="subtitle">{{ calculateServicesTotal() }}</h1>
                </div>
              </div>
              <div class="row mt-5">
                <div class="col">
                  <h1 class="title">Total price: {{ calculateTotal() }} euros</h1>
                </div>
              </div>
              <div class="row mt-4">
                <div class="col">
                  <div class="buttons">
                    <b-button type="is-primary" @click="reserveRentable" expanded>Confirm reservation</b-button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </b-step-item>
        <template #navigation="{previous, next}">
          <b-button
              outlined
              type="is-danger"
              icon-pack="fas"
              icon-left="backward"
              :disabled="previous.disabled"
              @click.prevent="previousStep">
          </b-button>
          <b-button
              outlined
              type="is-success"
              icon-pack="fas"
              icon-right="forward"
              :disabled="isNextDisabled(next)"
              @click.prevent="nextStep">
          </b-button>
        </template>
      </b-steps>
    </div>
  </div>
</template>

<script>
import moment from 'moment'
import axios from "axios";
import {backend} from "@/env";


const {splitImages} = require('@/_helpers/imageHelpers');


export default {
  name: "Reservation",
  data() {
    return {
      username: JSON.parse(localStorage.getItem('user') || '{}')?.username,
      role: JSON.parse(localStorage.getItem('user') || '{}')?.role?.authority,
      reservationBlocked: false,
      backend: backend,
      activeStep: 0,
      type: 'COTTAGE',
      selectedDate: new Date(),
      selectedTime: new Date(2022, 1, 9, 8, 0, 0),
      minDate: new Date(),
      duration: 1,
      rentables: [],
      total: this.rentables?.size,
      perPage: 4,
      current: 1,
      paginationKey: 0,
      nameSearch: '',
      locationSearch: '',
      ascending: true,
      sortBy: '',
      ratingSearch: '',
      selectedRentable: null,
      services: [],
      columns: [
        {
          field: 'name',
          label: 'Service',
        },
        {
          field: 'price',
          label: 'Price (euros)',
          numeric: true
        }
      ],
      selectedServices: [],
      people: 1,
      start: '',
      end: ''
    }
  },
  async mounted() {
    this.minDate = moment().add(3, 'days').toDate()
    this.minDate.setHours(0)
    this.minDate.setMinutes(0)
    this.selectedDate = this.minDate
    this.reservationBlocked = await this.isReserveBlocked()
  },
  methods: {
    async isReserveBlocked(){
      if(this.role !== 'CUSTOMER') return false
      const response = await axios.get(`${backend}/penalty/count/${this.username}`)
      return response.data >= 3;
    },
    async nextStep() {
      this.activeStep += 1
      if (this.activeStep === 1)
        await this.getRentables()
      if (this.activeStep === 2) {
        this.services = this.selectedRentable.additionalServices.split(';').flatMap(service => {
          let s = service.split('=')
          if (s[0] === '') return []
          return {name: s[0], price: parseFloat(s[1])}
        })
      }
      this.total = this.rentables.length
    },
    previousStep() {
      if (this.activeStep === 2) {
        this.selectedServices = []
      }
      if (this.activeStep === 1) {
        this.selectedRentable = null
      }
      this.activeStep -= 1
    },
    calculateTotal() {
      let multiplier = 1
      if (this.type === 'ADVENTURE') multiplier = this.people
      if (this.selectedRentable === null) return 0
      if (this.selectedServices.length === 0) return this.selectedRentable.price * this.duration * multiplier
      return this.selectedRentable?.price * this.duration * multiplier + this.selectedServices?.reduce((prev, current) => prev + current.price, 0)
    },
    calculateServicesTotal() {
      if (this.selectedServices.length === 0) return 0
      return this.selectedServices.reduce((prev, current) => prev + current.price, 0)
    },
    setDatetime() {
      if (this.type === 'ADVENTURE') {
        this.minDate.setHours(8)
        this.minDate.setMinutes(0)
      }
    },
    setMaxDuration() {
      return 20 - this.selectedTime.getHours()
    },
    addDays(date, days) {
      let result = new Date(date);
      result.setDate(result.getDate() + days);
      return result;
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
    async getRentables() {
      let month
      let day
      let hour
      let start
      let end
      let endHour
      month = this.formatDateMonth(new Date(this.selectedDate));
      day = this.formatDateDay(new Date(this.selectedDate));
      if (this.type === 'ADVENTURE') {
        if (this.selectedTime.getHours() < 10) {
          hour = `0${this.selectedTime.getHours()}`
          endHour = `0${new Date(this.selectedTime).getHours() + this.duration}`
        } else {
          hour = `${this.selectedTime.getHours()}`
          endHour = `${new Date(this.selectedTime).getHours() + this.duration}`
        }
        if (endHour.length > 2) endHour = endHour.substring(1, 3)
        console.log(endHour)
        start = `${this.selectedDate.getFullYear()}-${month}-${day} ${hour}:00`
        end = `${this.selectedDate.getFullYear()}-${month}-${day} ${endHour}:00`
      } else {
        let endDate = new Date(this.selectedDate)
        endDate.setTime(endDate.getTime() + (this.duration * 24 * 60 * 60 * 1000));
        start = `${this.selectedDate.getFullYear()}-${month}-${day} 00:00`
        end = `${endDate.getFullYear()}-${this.formatDateMonth(endDate)}-${this.formatDateDay(endDate)} 00:00`
      }

      this.start = start
      this.end = end

      const reservationDto = {
        type: this.type,
        start: start,
        end: end,
        capacity: this.people
      }
      const response = await axios.post(backend + '/reservation/getFreeRentables', reservationDto)
      if (response.data) {
        this.rentables = response.data.filter(rentable => rentable.capacity >= this.people)
      }
    },
    async reserveRentable() {
      const reservation = {
        type: this.type,
        name: this.selectedRentable.name,
        ownerUsername: this.selectedRentable.ownerUsername,
        customerUsername: JSON.parse(localStorage.getItem('user') || '{}')?.username,
        startTime: this.start,
        endTime: this.end,
        additionalServices: this.selectedServices.reduce((prev, current) => prev + current.name + '=' + current.price + ";", ''),
        guests: this.people,
        isCancelled: false,
        isDeal: false,
        price: this.calculateTotal(),
        rating: 0,
        complaintExists: false
      }

      const response = await axios.post(backend + `/reservation/reserveRentable/${this.selectedRentable.id}`, reservation)
      if (response.data) {
        //this.$router.push('/customer/upcomingReservations')
        this.$toasted.success('Reservation confirmed successfully!')
      } else {
        this.$toasted.error('Reservation was unsuccessful')
        this.activeStep = 0
      }

    },
    setSelectedRentable(rentable) {
      if (this.selectedRentable === null) {
        this.selectedRentable = rentable
        return
      }
      if (this.selectedRentable.id !== rentable.id) this.selectedRentable = rentable
      else this.selectedRentable = null
    },
    isNextDisabled(next) {
      if (this.activeStep === 1) {
        return this.selectedRentable === null;
      }
      return this.activeStep === 3;

    },
    setSortOrder() {
      this.ascending = !this.ascending
    },
    splitImages(images, folderName) {
      return splitImages(images, folderName)
    }
  },
  filters: {
    addressFormat(value) {
      return value.address + ", " + value.city + ", " + value.country
    },
  },
  computed: {
    filteredRentables() {
      let tempRentables = this.rentables
      let formatAddress = (function (value) {
        return value.address + ", " + value.city + ", " + value.country
      })
      // const types = this.allTypes.filter((type, index) => this.typeCheckboxes[index] === true)
      // const filteredRentables = []
      // for (let i = 0; i < types.length; i++) {
      //   filteredRentables.push(...tempRentables.filter((r) => {
      //     return r.type.toLowerCase().includes(types[i].toLowerCase())
      //   }))
      // }
      //
      // if (filteredRentables.length > 0) tempRentables = filteredRentables

      if (this.nameSearch !== '') {
        tempRentables = tempRentables.filter((r) => {
          return r.name.toLowerCase().includes(this.nameSearch.toLowerCase())
        })
      }

      if (this.locationSearch !== '') {
        tempRentables = tempRentables.filter((c) => {
          return c.address.toLowerCase().includes(this.locationSearch.toLowerCase())
              || c.city.toLowerCase().includes(this.locationSearch.toLowerCase())
              || c.country.toLowerCase().includes(this.locationSearch.toLowerCase())
        })
      }

      // if (this.ratingSearch !== '') {
      //   tempRentables = tempRentables.filter((r) => {
      //     return Math.round(r.rating) === Math.round(this.ratingSearch)
      //   })
      // }

      tempRentables = tempRentables.sort((a, b) => {
        if (this.sortBy === 'Name') {
          let result = 0
          if (a.name < b.name) result = -1
          if (a.name > b.name) result = 1
          if (this.ascending) return result
          return result * -1
        } else if (this.sortBy === 'Location') {
          let result = 0
          if (formatAddress(a) < formatAddress(b)) result = -1
          if (formatAddress(a) > formatAddress(b)) result = 1
          if (this.ascending) return result
          return result * -1
        }
        // else if (this.sortBy === 'Rating') {
        //   if (this.ascending) return a.rating - b.rating
        //   return b.rating - a.rating
        // }
      })

      //if (this.onlyOpen) tempRentables = tempRentables.filter((r) => { return r.status == 'OPEN' })

      return tempRentables
    }
  }
}
</script>

<style scoped>
.is-active {
  outline: solid blue;
}
</style>