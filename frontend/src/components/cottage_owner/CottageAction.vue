<template>
  <div class="box m-5">
  <b-field label="Choose your cottage">
    <b-select placeholder="Select your cottage" v-model="selectedRentable" @input="getServices()">
      <option
          v-for="cottage in cottages"
          :value="cottage"
          :key="cottage.id">
          {{cottage.name}}
      </option>
    </b-select>
  </b-field>
    <b-field label="Choose action period">
      <b-datepicker
          placeholder="Click to select..."
          v-model="dates"
          range
          :min-date="minDate">
      </b-datepicker>
    </b-field>
    <label class="label mr-3">Sale percent %</label>
    <input class="input" type="number" v-model="salePercent">

    <div class="row justify-content-left">
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
    </div>

    <button class="button is-primary mt-5" @click="createAction()">Create action</button>
  </div>
</template>

<script>
import axios from "axios";
import {backend} from "@/env";
import moment from "moment";

export default {
  name: "CottageAction",
  data(){
    return {
      cottages: [],
      selectedRentable: JSON.parse(localStorage.getItem('currentCottage')),
      user:JSON.parse(localStorage.getItem('user')),
      type: 'COTTAGE',
      dates: [],
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
      startTime: new Date(),
      endTime: new Date(),
      minDate: new Date(),
      price: 0,
      salePercent: 0
    }
  },
  mounted(){
    this.getOwnerCottages();
    this.getServices();
    this.minDate = moment().add(1, 'days').toDate()
    this.minDate.setHours(0)
    this.minDate.setMinutes(0)

  },
  methods:{
    async getOwnerCottages() {
      const response = await axios.get(backend + '/cottage/owner?username=' + this.user?.username)
      if (response.data) {
        this.cottages = response.data
      }
    },
    async createAction() {
      let sYear = this.dates[0].getFullYear()
      let sMonth = this.formatDateMonth(new Date(this.dates[0]));
      let sDay = this.formatDateDay(new Date(this.dates[0]));
      let eYear = this.dates[this.dates.length - 1].getFullYear()
      let eMonth = this.formatDateMonth(new Date(this.dates.slice(-1)[0]));
      let eDay = this.formatDateDay(new Date(this.dates.slice(-1)[0]));

      const reservation = {
        type: this.type,
        name: this.selectedRentable.name,
        ownerUsername: JSON.parse(localStorage.getItem('user') || '{}')?.username,
        customerUsername: null,
        startTime: sYear + '-' + sMonth + '-' + sDay + ' 00:00',
        endTime: eYear + '-' + eMonth + '-' + eDay + ' 00:00',
        additionalServices: this.selectedServices.reduce((prev, current) => prev + current.name + '=' + current.price + ";", ''),
        cancelled: false,
        deal: true,
        salePercent: this.salePercent,
        price: this.selectedRentable.price * this.salePercent / 100,
        rating: this.selectedRentable.rating,
        complaintExists: false
      }

      const response = await axios.post(backend + `/reservation/createAction/${this.selectedRentable.id}`, reservation)
      if (response.data) {
        this.$toasted.success('Sale made successfully!')
      } else {
        this.$toasted.error('Sale creation was unsuccessful')
      }
    },
    getServices(){
      this.services = [];
      this.services = this.selectedRentable.additionalServices.split(';').flatMap(service => {
        let s = service.split('=')
        if (s[0] === '') return []
        return {name: s[0], price: parseFloat(s[1])}
      })
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
  }
}
</script>

<style scoped>

</style>