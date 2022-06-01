<template>
  <div>
    <div class="row mt-5 justify-content-center">
      <div class="col-md-2">
        <div class="form-floating">
          <input type="text" class="form-control" id="cottageName" v-model="nameSearch">
          <label for="cottageName">Cottage name</label>
        </div>
      </div>
      <div class="col-md-2">
        <div class="form-floating">
          <input type="text" class="form-control" id="cottageLocation" v-model="locationSearch">
          <label for="cottageLocation">Location</label>
        </div>
      </div>
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
        <button class="button is-primary" @click="setSortOrder">
          <i :class="[ascending ? 'fa fa-sort-up' : 'fa fa-sort-down']"></i>
        </button>
      </div>
    </div>
    <div class="md-layout md-alignment-center" v-if="this.cottages">
      <div class="md-layout-item md-large-size-30 md-xlarge-size-30"
           v-for="cottage in filteredCottages.slice((current-1)*perPage,(current-1)*perPage+perPage)" :key=cottage.id>
        <md-card class="md-primary" md-theme="orange-card" md-with-hover>
          <md-ripple>
            <md-card-media md-ratio="4:3">
              <img :src="getImgUrl(cottage)" style="height: 100%" alt="Cottage image"
                   @click="cottageProfile(cottage)">
            </md-card-media>
            <md-card-area>
              <md-card-header>
                <p class="md-title">{{ cottage.name }}</p>
                <p class="md-subhead">{{ cottage.address }}, {{ cottage.city }}, {{ cottage.country }}</p>
                <p class="md-subhead">{{ cottage.promoDescription }}</p>
                <b-rate v-model="cottage.averageRating" show-score
                        :max="5" disabled :custom-text="` (${cottage.timesRated})`"></b-rate>
              </md-card-header>
            </md-card-area>
            <md-card-expand>
              <md-card-actions md-alignment="right">
                <md-button v-show="authority==='COTTAGE_OWNER'||authority==='ADMIN'" class="md-icon-button"
                           @click="confirmDialog(cottage)">
                  <span class="fa fa-trash-alt"></span>
                </md-button>
                <md-button v-show="authority==='COTTAGE_OWNER'" class="md-icon-button"
                           @click="updateCottageModal(cottage)">
                  <span class="fa fa-edit"></span>
                </md-button>
              </md-card-actions>
            </md-card-expand>
          </md-ripple>
        </md-card>
      </div>
      <span v-show="authority==='COTTAGE_OWNER'" class="fa fa-plus-circle fa-3x" style="cursor: pointer" @click="createCottageModal"></span>
    </div>
    <div class="columns mt-5">
      <div class="col"></div>
      <div class="mt-4 col-3" v-if="this.cottages">
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
      <div class="col"></div>
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
import CottageRegistration from "@/components/cottage_owner/CottageRegistration";
import {backend} from "@/env";

const {splitImages} = require('@/_helpers/imageHelpers');

Vue.use(MdCard)
Vue.use(MdRipple)

export default {
  name: "Cottages",
  data() {
    return {
      delete: -1,
      cottages: [],
      total: this.cottages?.size,
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
    (this.authority === 'COTTAGE_OWNER') ? await this.getOwnerCottages() : await this.getCottages()
    this.total = this.cottages.length
    this.$nextTick(() => window.scrollTo(0, document.body.scrollHeight))
  },
  methods: {
    async getCottages() {
      const response = await axios.get(backend + '/cottage/all')
      if (response.data) {
        this.cottages = response.data
      }
    },
    async getOwnerCottages() {
      const response = await axios.get(backend + '/cottage/owner?username=' + this.user?.username)
      if (response.data) {
        this.cottages = response.data
      }
    },
    getImgUrl(cottage) {
      return `${backend}/${cottage.images?.split(';')[0]}`
    },
    cottageProfile(cottage) {
      localStorage.setItem('currentCottage', JSON.stringify(cottage))
      this.$router.push('cottageProfile')
    },
    pushCottage(cottage) {
      this.cottages.push(cottage)
      this.total += 1
    },
    createCottageModal() {
      this.$buefy.modal.open({
        parent: this,
        component: CottageRegistration,
        hasModalCard: true,
        trapFocus: true,
        events: {
          'added': this.pushCottage
        }
      })
    },
    async confirmDialog(cottage) {
      this.delete = cottage.id
      const {result, dialog} = await this.$buefy.dialog.confirm({
        message: 'Are you sure you want to delete? It cannot be undone.',
        closeOnConfirm: true,
        onConfirm: await this.deleteCottage
      });
    },
    async deleteCottage() {
      const response = await axios.delete(backend + `/cottage/delete/${this.delete}`)
      if (response.data) {
        this.cottages = this.cottages.filter(c => c.id !== this.delete)
        this.$toasted.success('Cottage successfully deleted!')
        this.total -= 1
      }
    },
    updateCottageModal(cottage) {
      this.$buefy.modal.open({
        parent: this,
        component: CottageUpdate,
        hasModalCard: true,
        trapFocus: true,
        props: {cottage},
        events: {
          'updated': this.updateCottage
        }
      })
    },
    updateCottage() {
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
    filteredCottages() {
      let tempCottages = this.cottages
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
        tempCottages = tempCottages.filter((r) => {
          return r.name.toLowerCase().includes(this.nameSearch.toLowerCase())
        })
      }

      if (this.locationSearch !== '') {
        tempCottages = tempCottages.filter((c) => {
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

      tempCottages = tempCottages.sort((a, b) => {
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

      return tempCottages
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