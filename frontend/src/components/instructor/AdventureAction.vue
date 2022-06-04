<template>
  <div class="box m-5">
    <b-field label="Choose your adventure">
      <b-select placeholder="Select your adventure" v-model="selectedRentable" @input="getServices()">
        <option
            v-for="adventure in adventures"
            :value="adventure"
            :key="adventure.id">
          {{adventure.name}}
        </option>
      </b-select>
    </b-field>
    <b-field label="Choose action period">
      <b-datepicker
          placeholder="Click to select..."
          v-model="startTime"
          :min-date="minDate">
      </b-datepicker>
    </b-field>
    <label class="label mr-3">Select start hour</label>
    <input class="input" type="number" min=8 max=19 v-model="startHours">
    <label class="label mr-3">Add hours</label>
    <input class="input" type="number" min=1 max=12 v-model="endHours">
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
import moment from "moment";
import axios from "axios";
import {backend} from "@/env";

export default {
  name: "AdventureAction",
  data() {
    return {
      adventures: [],
      selectedRentable: JSON.parse(localStorage.getItem('currentAdventure')),
      user: JSON.parse(localStorage.getItem('user')),
      type: 'ADVENTURE',
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
      startTime: null,
      minDate: new Date(),
      price: 0,
      salePercent: 0,
      startHours: 8,
      endHours: 1
    }
  },
  mounted(){
    this.getOwnerAdventures();
    this.getServices();
    this.minDate = moment().add(1, 'days').toDate()
    this.minDate.setHours(0)
    this.minDate.setMinutes(0)

  },
  methods: {
    async getOwnerAdventures() {
      const response = await axios.get(backend + '/adventure/owner?username=' + this.user?.username)
      if (response.data) {
        this.adventures = response.data
      }
    },
    async createAction() {
      const date1 = moment(this.startTime).add(this.startHours, 'hours').toDate()
      const date2 = moment(this.startTime).add(this.startHours, 'hours').add(this.endHours, 'hours').toDate()
      console.log(date2)
      const reservation = {
        type: this.type,
        name: this.selectedRentable.name,
        ownerUsername: JSON.parse(localStorage.getItem('user') || '{}')?.username,
        customerUsername: null,
        startTime: this.formatDate(date1),
        endTime: this.formatDate(date2),
        additionalServices: this.selectedServices.reduce((prev, current) => prev + current.name + '=' + current.price + ";", ''),
        cancelled: false,
        deal: true,
        salePercent: this.salePercent,
        price: this.selectedRentable.price * (100-this.salePercent) / 100,
        complaintExists: false
      }
      console.log(reservation)
      const response = await axios.post(backend + `/reservation/createAction/${this.selectedRentable.id}`, reservation)
      if (response.data) {
        this.$toasted.success('Sale made successfully!')
        this.$router.push('/instructor/schedule')
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
    formatDate(date) {
      return moment(date).format('YYYY-MM-DD HH:mm')
    },
  }
}
</script>

<style scoped>

</style>