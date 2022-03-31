<template>
  <div>
    <div class="row mt-5 justify-content-center">
      <div class="col-md-2">
        <div class="form-floating">
          <input type="text" class="form-control" id="cottageName" v-model="nameSearch">
          <label for="cottageName">Adventure name</label>
        </div>
      </div>
      <div class="col-md-2">
        <div class="form-floating">
          <input type="text" class="form-control" id="cottageLocation" v-model="locationSearch">
          <label for="cottageLocation">Location</label>
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
          <input type="number" class="form-control" id="cottageRating" v-model.number="ratingSearch" max="5"
                 min="0">
          <label for="cottageRating">Rating</label>
        </div>
      </div>
      <div class="col-md-1" style="width: 8.7%">
        <div class="form-floating">
          <select v-model="sortBy" class="form-select" id="cottageSort">
            <option value="">None</option>
            <option value="Name">Name</option>
            <option value="Location" selected>Location</option>
            <option value="Rating">Rating</option>
          </select>
          <label for="cottageSort">Sort by</label>
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
    <div class="md-layout md-alignment-center" v-if="this.adventures">
      <div class="md-layout-item md-large-size-30 md-xlarge-size-30"
           v-for="adventure in filteredAdventures.slice((current-1)*perPage,(current-1)*perPage+perPage)" :key=adventure.id>
        <md-card class="md-primary" md-theme="orange-card" md-with-hover>
          <md-ripple>
            <md-card-media md-ratio="4:3">
              <img :src="getImgUrl(adventure.id)" style="height: 100%" alt="Adventure image"
                   @click="adventureProfile(adventure)">
            </md-card-media>
            <md-card-area>
              <md-card-header>
                <p class="md-title">{{ adventure.name }}</p>
                <p class="md-subhead">{{ adventure.address }}, {{ adventure.city }}, {{ adventure.country }}</p>
                <p class="md-subhead">{{ adventure.promoDescription }}</p>
              </md-card-header>
            </md-card-area>
            <md-card-expand>
              <md-card-actions md-alignment="right">
                <md-button v-show="authority==='INSTRUCTOR'||authority==='ADMIN'" class="md-icon-button"
                           @click="confirmDialog(adventure)">
                  <span class="fa fa-trash"></span>
                </md-button>
                <md-button v-show="authority==='INSTRUCTOR'" class="md-icon-button"
                           @click="updateAdventureModal(adventure)">
                  <span class="fa fa-edit"></span>
                </md-button>
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
      <span v-show="authority==='INSTRUCTOR'" class="fa fa-plus-circle fa-3x" style="cursor: pointer" @click="createAdventureModal"></span>
    </div>
    <div class="mt-4" v-if="this.adventures">
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
import CottageUpdate from "@/components/cottage_owner/CottageUpdate";
import AdventureRegistration from "@/components/instructor/AdventureRegistration";
import AdventureUpdate from "@/components/instructor/AdventureUpdate";
import {backend} from "@/env";

const {splitImages} = require('@/_helpers/imageHelpers');

Vue.use(MdCard)
Vue.use(MdRipple)

export default {
  name: "Adventures",
  data() {
    return {
      delete: -1,
      adventures: [],
      total: this.adventures?.size,
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
      authority: '',
      user: null,
    }
  },
  async mounted() {
    this.user = JSON.parse(localStorage.getItem('user'))
    this.authority = this.user?.role.authority;
    (this.authority === 'INSTRUCTOR') ? await this.getOwnerAdventures() : await this.getAdventures()
    this.total = this.adventures.length
    this.$nextTick(() => window.scrollTo(0, document.body.scrollHeight))
  },
  methods: {
    async getAdventures() {
      const response = await axios.get(backend + '/adventure/all')
      if (response.data) {
        this.adventures = response.data
      }
    },
    async getOwnerAdventures() {
      const response = await axios.get(backend + '/adventure/owner?username=' + this.user?.username)
      if (response.data) {
        this.adventures = response.data
      }
    },
    getImgUrl(value) {
      return `${backend}/a${value}.1.jpg`
    },
    adventureProfile(adventure) {
      localStorage.setItem('currentAdventure', JSON.stringify(adventure))
      this.$router.push('/adventureProfile')
    },
    pushAdventure(adventure) {
      this.adventures.push(adventure)
      this.total += 1
    },
    createAdventureModal() {
      this.$buefy.modal.open({
        parent: this,
        component: AdventureRegistration,
        hasModalCard: true,
        trapFocus: true,
        events: {
          'added': this.pushAdventure
        }
      })
    },
    async confirmDialog(adventure) {
      this.delete = adventure.id
      const {result, dialog} = await this.$buefy.dialog.confirm({
        message: 'Are you sure you want to delete? It cannot be undone.',
        closeOnConfirm: true,
        onConfirm: await this.deleteAdventure
      });
    },
    async deleteAdventure() {
      const response = await axios.delete(backend + `/adventure/delete/${this.delete}`)
      if (response.data) {
        this.adventures = this.adventures.filter(c => c.id !== this.delete)
        this.$toasted.success('Adventure successfully deleted!')
        this.total -= 1
      }
    },
    updateAdventureModal(adventure) {
      this.$buefy.modal.open({
        parent: this,
        component: AdventureUpdate,
        hasModalCard: true,
        trapFocus: true,
        props: {adventure},
        events: {
          'updated': this.updateAdventure
        }
      })
    },
    updateAdventure() {
      location.reload()
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
    filteredAdventures() {
      let temp = this.adventures
      let formatAddress = (function (value) {
        return value.address + ", " + value.city + ", " + value.country
      })
      // const types = this.allTypes.filter((type, index) => this.typeCheckboxes[index] === true)
      // const filteredCottages = []
      // for (let i = 0; i < types.length; i++) {
      //   filteredCottages.push(...tempCottages.filter((r) => {
      //     return r.type.toLowerCase().includes(types[i].toLowerCase())
      //   }))
      // }
      //
      // if (filteredCottages.length > 0) tempCottages = filteredCottages

      if (this.nameSearch !== '') {
        temp = temp.filter((r) => {
          return r.name.toLowerCase().includes(this.nameSearch.toLowerCase())
        })
      }

      if (this.locationSearch !== '') {
        temp = temp.filter((c) => {
          return c.address.toLowerCase().includes(this.locationSearch.toLowerCase())
              || c.city.toLowerCase().includes(this.locationSearch.toLowerCase())
              || c.country.toLowerCase().includes(this.locationSearch.toLowerCase())
        })
      }

      // if (this.ratingSearch !== '') {
      //   tempCottages = tempCottages.filter((r) => {
      //     return Math.round(r.rating) === Math.round(this.ratingSearch)
      //   })
      // }

      temp = temp.sort((a, b) => {
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

      //if (this.onlyOpen) tempCottages = tempCottages.filter((r) => { return r.status == 'OPEN' })

      return temp
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