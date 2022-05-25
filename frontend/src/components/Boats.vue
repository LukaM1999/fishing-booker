<template>
  <div>
    <div class="row mt-5 justify-content-center">
      <div class="col-md-2">
        <div class="form-floating">
          <input type="text" class="form-control" id="boatName" v-model="nameSearch">
          <label for="boatName">Boat name</label>
        </div>
      </div>
      <div class="col-md-2 align-self-center">
        <b-field label="Navigation equipment"
                 label-position="inside"
                 custom-class="my-label">
          <b-taginput v-model="equipment"
                      autocomplete
                      open-on-focus
                      icon="label"
                      :data="filteredEquipment"
                      placeholder="Select equipment"
                      @typing="getFilteredEquipment">
          </b-taginput>
        </b-field>
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
          <input type="number" class="form-control" id="boatLength" v-model.number="lengthSearch" max="500"
                 min="1">
          <label for="boatLength">Length [m]</label>
        </div>
      </div>
      <div class="col-md-1">
        <div class="form-floating">
          <input type="number" class="form-control" id="boatMotors" v-model.number="motorSearch" max="10"
                 min="1">
          <label for="boatMotors">Motors</label>
        </div>
      </div>
      <div class="col-md-1">
        <div class="form-floating">
          <input type="number" class="form-control" id="boatPower" v-model.number="powerSearch" max="2500"
                 min="1" step="100">
          <label for="boatPower">Power [HP]</label>
        </div>
      </div>
      <div class="col-md-1" style="width: 8.7%">
        <div class="form-floating">
          <select v-model="sortBy" class="form-select" id="boatSort">
            <option value="">None</option>
            <option value="Name">Name</option>
            <option value="Location" selected>Location</option>
            <option value="Length">Length</option>
            <option value="Motors">Motors</option>
            <option value="Power">Power</option>
            <option value="Speed">Speed</option>
          </select>
          <label for="boatSort">Sort by</label>
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
    <div class="md-layout md-alignment-center" v-if="this.boats">
      <div class="md-layout-item md-large-size-30 md-xlarge-size-30"
           v-for="boat in filteredBoats.slice((current-1)*perPage,(current-1)*perPage+perPage)" :key=boat.id>
        <md-card class="md-primary" md-theme="orange-card" md-with-hover>
          <md-ripple>
            <md-card-media md-ratio="4:3">
              <img src="" alt="Skyscraper">
            </md-card-media>
            <md-card-area>
              <md-card-header>
                <p class="md-title">{{ boat.name }}</p>
                <p class="md-subhead">{{ boat.address }}, {{ boat.city }}, {{ boat.country }}</p>
                <p class="md-subhead">{{ boat.promoDescription }}</p>
                <b-rate v-model="boat.averageRating" show-score
                        :max="5" disabled :custom-text="` (${boat.timesRated})`"></b-rate>
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
                  <p class="md-subhead">For a maximum of {{ boat.capacity }} people</p>
                  <p class="md-subhead">{{ boat.boatType }} type</p>
                  <p class="md-subhead">Length of {{ boat.length.toFixed(2) }} meters</p>
                  <p class="md-subhead">Has {{ boat.motors }} motors, {{ boat.power.toFixed(0) }} horsepower</p>
                  <p class="md-subhead">Maximum speed of {{ boat.maxSpeed.toFixed(2) }} km/h</p>
                  <p class="md-subhead is-inline">
                    Has <span v-if="boat.gps">GPS, </span>
                    <span v-if="boat.radar">radar, </span>
                    <span v-if="boat.vhfRadio">VHF radio, </span>
                    <span v-if="boat.fishfinder">fishfinder</span>
                  </p>
                </md-card-content>
              </md-card-expand-content>
            </md-card-expand>
          </md-ripple>
        </md-card>
      </div>
    </div>
    <div class="mt-4" v-if="boats">
      <b-pagination
          :total="total"
          v-model="current"
          :range-before="rangeBefore"
          :range-after="rangeAfter"
          :simple="isSimple"
          :rounded="isRounded"
          :order="order"
          :per-page="perPage"
          :icon-prev="prevIcon"
          :icon-next="nextIcon"
          aria-next-label="Next page"
          aria-previous-label="Previous page"
          aria-page-label="Page"
          aria-current-label="Current page">
      </b-pagination>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import Vue from "vue";
import MdCard from "vue-material"
import MdRipple from "vue-material"
import "vue-material/dist/vue-material.min.css"
import "bootstrap/dist/css/bootstrap-grid.min.css"
import "@fortawesome/fontawesome-free/css/all.min.css"
import {backend} from "@/env";

Vue.use(MdCard)
Vue.use(MdRipple)

export default {
  name: "Boats",
  data() {
    return {
      boats: [],
      total: this.boats?.size,
      current: 1,
      perPage: 4,
      rangeBefore: 3,
      rangeAfter: 3,
      order: 'is-centered',
      isSimple: false,
      isRounded: false,
      prevIcon: 'chevron-left',
      nextIcon: 'chevron-right',
      paginationKey: 0,
      nameSearch: '',
      locationSearch: '',
      lengthSearch: '',
      motorSearch: '',
      powerSearch: '',
      ascending: true,
      sortBy: '',
      ratingSearch: '',
      equipment: [],
      filteredEquipment: ['GPS', 'Radar', 'VHF radio', 'Fishfinder'],
    }
  },
  async mounted() {
    await this.getBoats()
    this.total = this.boats.length
    this.$nextTick(() => window.scrollTo(0,document.body.scrollHeight))
    // const cards = document.getElementsByClassName('md-card')
    // for (const card of cards) {
    //   card.classList.remove('md-expand-active')
    // }
    // const expandContents = document.getElementsByClassName('md-card-expand-content')
    // for (const expandContent of expandContents) {
    //   expandContent.style.marginTop = '-232px'
    //   expandContent.style.opacity = 0
    // }
    // this.$nextTick(() => {
    //   const expands = document.getElementsByClassName('fa-arrow-circle-down')
    //   for (const expand of expands) {
    //     expand.click()
    //   }
    // })

  },
  methods: {
    async getBoats() {
      const response = await axios.get(backend + '/boat/all')
      if (response) {
        this.boats = response.data
      }
    },
    setSortOrder() {
      this.ascending = !this.ascending
    },
    getFilteredEquipment(text) {
      let allEquipment = ['GPS', 'Radar', 'VHF radio', 'Fishfinder']
      this.filteredEquipment = allEquipment.filter((option) => {
        return option
            .toString()
            .toLowerCase()
            .indexOf(text.toLowerCase()) >= 0
      })
    },
  },
  filters: {},
  computed: {
    filteredBoats() {
      let tempBoats = this.boats
      let formatAddress = (function (value) {
        return value.address + ", " + value.city + ", " + value.country
      })
      // const types = this.allTypes.filter((type, index) => this.typeCheckboxes[index] === true)
      // const filteredBoats = []
      // for (let i = 0; i < types.length; i++) {
      //   filteredBoats.push(...tempBoats.filter((b) => {
      //     return b.type.toLowerCase().includes(types[i].toLowerCase())
      //   }))
      // }
      //
      // if (filteredBoats.length > 0) tempBoats = filteredBoats
      if (this.equipment) {
        const hasGps = this.equipment.includes('GPS')
        const hasRadar = this.equipment.includes('Radar')
        const hasRadio = this.equipment.includes('VHF radio')
        const hasFishfinder = this.equipment.includes('Fishfinder')

        if (hasGps) {
          tempBoats = tempBoats.filter(b => {
            return b.gps === hasGps
          })
        }
        if (hasRadar) {
          tempBoats = tempBoats.filter(b => {
            return b.radar === hasRadar
          })
        }
        if (hasRadio) {
          tempBoats = tempBoats.filter(b => {
            return b.vhsRadio === hasRadio
          })
        }
        if (hasFishfinder) {
          tempBoats = tempBoats.filter(b => {
            return b.fishfinder === hasFishfinder
          })
        }
      }

      if (this.nameSearch !== '') {
        tempBoats = tempBoats.filter((b) => {
          return b.name.toLowerCase().includes(this.nameSearch.toLowerCase())
        })
      }

      if (this.locationSearch !== '') {
        tempBoats = tempBoats.filter((c) => {
          return c.address.toLowerCase().includes(this.locationSearch.toLowerCase())
              || c.city.toLowerCase().includes(this.locationSearch.toLowerCase())
              || c.country.toLowerCase().includes(this.locationSearch.toLowerCase())
        })
      }

      if (this.lengthSearch !== '') {
        tempBoats = tempBoats.filter((b) => {
          return Math.round(b.length) <= Math.round(this.lengthSearch)
        })
      }

      if (this.motorSearch !== '') {
        tempBoats = tempBoats.filter((b) => {
          return Math.round(b.motors) <= Math.round(this.motorSearch)
        })
      }

      if (this.powerSearch !== '') {
        tempBoats = tempBoats.filter((b) => {
          return Math.round(b.power) <= Math.round(this.powerSearch)
        })
      }

      tempBoats = tempBoats.sort((a, b) => {
        if (this.sortBy === 'Name') {
          let result = 0
          if (a.name < b.name) result = -1
          if (a.name > b.name) result = 1
          if (this.ascending) return result
          return result * -1
        } else if (this.sortBy === 'Type') {
          let result = 0
          if (a.boatType < b.boatType) result = -1
          if (a.boatType > b.boatType) result = 1
          if (this.ascending) return result
          return result * -1
        } else if (this.sortBy === 'Location') {
          let result = 0
          if (formatAddress(a) < formatAddress(b)) result = -1
          if (formatAddress(a) > formatAddress(b)) result = 1
          if (this.ascending) return result
          return result * -1
        } else if (this.sortBy === 'Length') {
          if (this.ascending) return a.length - b.length
          return b.length - a.length
        } else if (this.sortBy === 'Motors') {
          if (this.ascending) return a.motors - b.motors
          return b.motors - a.motors
        } else if (this.sortBy === 'Power') {
          if (this.ascending) return a.power - b.power
          return b.power - a.power
        } else if (this.sortBy === 'Speed') {
          if (this.ascending) return a.maxSpeed - b.maxSpeed
          return b.maxSpeed - a.maxSpeed
        }
      })

      //if (this.onlyOpen) tempBoats = tempBoats.filter((b) => { return b.status == 'OPEN' })

      return tempBoats
    }
  },
}
</script>

<style scoped>
.md-layout {
  margin-top: 3vh;
}

.md-layout-item {
  padding: 1%;
}

</style>