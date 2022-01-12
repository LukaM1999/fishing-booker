<template>
  <div>
    <div class="row mt-5 justify-content-center">
      <div class="col-md-2">
        <div class="form-floating">
          <input type="text" class="form-control" id="instructorName" v-model="nameSearch">
          <label for="instructorName">Instructor name</label>
        </div>
      </div>
      <div class="col-md-2">
        <div class="form-floating">
          <input type="text" class="form-control" id="instructorLocation" v-model="locationSearch">
          <label for="instructorLocation">Location</label>
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
          <input type="number" class="form-control" id="instructorRating" v-model.number="ratingSearch" max="5"
                 min="0">
          <label for="instructorRating">Rating</label>
        </div>
      </div>
      <div class="col-md-1" style="width: 8.7%">
        <div class="form-floating">
          <select v-model="sortBy" class="form-select" id="instructorSort">
            <option value="">None</option>
            <option value="Name">Name</option>
            <option value="Location" selected>Location</option>
            <option value="Rating">Rating</option>
          </select>
          <label for="instructorSort">Sort by</label>
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
    <div class="md-layout md-alignment-center" v-if="this.instructors">
      <div class="md-layout-item md-large-size-30 md-xlarge-size-30"
           v-for="instructor in filteredInstructors.slice((current-1)*perPage,(current-1)*perPage+perPage)" :key=instructor.id>
        <md-card class="md-primary" md-theme="orange-card" md-with-hover>
          <md-ripple>
              <md-card-media md-ratio="4:3">
                <img src="" alt="Skyscraper">
              </md-card-media>
              <md-card-area>
                <md-card-header>
                  <p class="md-title">{{ instructor.name }}</p>
                  <p class="md-subhead">{{ instructor.address }}, {{ instructor.city }}, {{ instructor.country }}</p>
                  <p class="md-subhead">{{ instructor.promoDescription }}</p>
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
                  <!--                  <p class="md-subhead">For a maximum of {{ boat.capacity }} people</p>-->
                  <!--                  <p class="md-subhead">{{ boat.boatType }} type</p>-->
                  <!--                  <p class="md-subhead">Length of {{ boat.length.toFixed(2) }} meters</p>-->
                  <!--                  <p class="md-subhead">Has {{ boat.motors }} motors, {{ boat.power.toFixed(0) }} horsepower</p>-->
                  <!--                  <p class="md-subhead">Maximum speed of {{ boat.maxSpeed.toFixed(2) }} km/h</p>-->
                  <!--                  <p class="md-subhead is-inline">-->
                  <!--                    Has <span v-if="boat.gps">GPS, </span>-->
                  <!--                    <span v-if="boat.radar">radar, </span>-->
                  <!--                    <span v-if="boat.vhfRadio">VHF radio, </span>-->
                  <!--                    <span v-if="boat.fishfinder">fishfinder</span>-->
                  <!--                  </p>-->
                </md-card-content>
              </md-card-expand-content>
            </md-card-expand>
          </md-ripple>
        </md-card>
      </div>
    </div>
    <div class="mt-4" v-if="instructors">
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

Vue.use(MdCard)
Vue.use(MdRipple)

export default {
  name: "Instructors",
  data() {
    return {
      instructors: [],
      total: this.instructors?.size,
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
      ascending: true,
      sortBy: '',
      ratingSearch: '',
    }
  },
  async mounted() {
    await this.getInstructors()
    this.total = this.instructors.length
    this.$nextTick(() => window.scrollTo(0,document.body.scrollHeight))
    // this.$nextTick(() => {
    //   const expands = document.getElementsByClassName('fa-arrow-circle-down')
    //   for (const expand of expands) {
    //     expand.click()
    //   }
    // })
  },
  methods: {
    async getInstructors() {
      const response = await axios.get('/instructor/all')
      if (response) {
        this.instructors = response.data
      }
    },
    setSortOrder() {
      this.ascending = !this.ascending
    },
  },
  filters: {
    addressFormat(value) {
      return value.address + ", " + value.city + ", " + value.country
    },
  },
  computed: {
    filteredInstructors() {
      let tempInstructors = this.instructors
      let formatAddress = (function (value){
        return value.address + ", " + value.city + ", " + value.country
      })
      // const types = this.allTypes.filter((type, index) => this.typeCheckboxes[index] === true)
      // const filteredInstructors = []
      // for (let i = 0; i < types.length; i++) {
      //   filteredInstructors.push(...tempInstructors.filter((r) => {
      //     return r.type.toLowerCase().includes(types[i].toLowerCase())
      //   }))
      // }
      //
      // if (filteredInstructors.length > 0) tempInstructors = filteredInstructors

      if (this.nameSearch !== '') {
        tempInstructors = tempInstructors.filter((r) => {
          return r.name.toLowerCase().includes(this.nameSearch.toLowerCase())
        })
      }

      if (this.locationSearch !== '') {
        tempInstructors = tempInstructors.filter((c) => {
          return c.address.toLowerCase().includes(this.locationSearch.toLowerCase())
              || c.city.toLowerCase().includes(this.locationSearch.toLowerCase())
              || c.country.toLowerCase().includes(this.locationSearch.toLowerCase())
        })
      }

      // if (this.ratingSearch !== '') {
      //   tempInstructors = tempInstructors.filter((r) => {
      //     return Math.round(r.rating) === Math.round(this.ratingSearch)
      //   })
      // }

      tempInstructors = tempInstructors.sort((a, b) => {
        if (this.sortBy === 'Name') {
          let result = 0
          if (a.name < b.name) result = -1
          if (a.name > b.name) result = 1
          if (this.ascending) return result
          return result * -1
        }
        else if (this.sortBy === 'Location') {
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

      //if (this.onlyOpen) tempInstructors = tempInstructors.filter((r) => { return r.status == 'OPEN' })

      return tempInstructors
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