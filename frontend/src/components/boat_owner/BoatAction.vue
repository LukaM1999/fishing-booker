<template>
  <div class="box m-5">
    <b-field label="Choose your boat">
      <b-select placeholder="Select your boat" v-model="selectedRentable" @input="getServices()">
        <option
            v-for="boat in boats"
            :value="boat"
            :key="boat.id">
          {{boat.name}}
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
import moment from "moment";
import axios from "axios";
import {backend} from "@/env";

export default {
  name: "BoatAction",
  data(){
    return {
      boats: [],
      selectedRentable: JSON.parse(localStorage.getItem('currentBoat')),
      user:JSON.parse(localStorage.getItem('user')),
      type: 'BOAT',
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
      salePercent: 0,
      subscribers: []
    }
  },
  mounted(){
    this.getOwnerBoats();
    this.getServices();
    this.minDate = moment().add(1, 'days').toDate()
    this.minDate.setHours(0)
    this.minDate.setMinutes(0)

  },
  methods:{
    async getOwnerBoats() {
      const response = await axios.get(backend + '/boat/owner?username=' + this.user?.username)
      if (response.data) {
        this.boats = response.data
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
        price: this.selectedRentable.price * (100-this.salePercent) / 100,
        rating: this.selectedRentable.rating,
        complaintExists: false
      }

      const response = await axios.post(backend + `/reservation/createAction/${this.selectedRentable.id}`, reservation)
      if (response.data) {
        this.$toasted.success('Action created successfully!')
        await this.sendMail(reservation)
        this.$router.push('/boatOwner/boatProfile')
      } else {
        this.$toasted.error('Action creation was unsuccessful')
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
    async sendMail(reservation){
      const response = await axios.get(backend + '/saleSubscription/entity/' + this.selectedRentable.name)
      if (response.data) {
        this.subscribers = response.data
      }
      for(let subscriber of this.subscribers){
        const email = {
          service_id: 'service_approve',
          template_id: 'template_approved',
          user_id: 'user_62WYz6KIasgbXlUB5EEGw',
          template_params: {
            'to_name': subscriber.customerUsername,
            'to_email': subscriber.email,
            'subject': 'New Sale!!!',
            'message': `<p>New sale for <strong>${this.selectedRentable.name}</strong> has been created!</p>
          <p><u>Start time</u>: ${reservation.startTime}</p>
          <p><u>End time</u>: ${reservation.endTime}</p>
          <p><u>Included additional services</u>: ${reservation.additionalServices}</p>
          <p><u>Sale percent</u>: ${reservation.salePercent}</p>
          <br>
          <p><u><strong>OLD PRICE</strong></u>: ${reservation.price + reservation.price * reservation.salePercent / 100} EUR</p>
          <p><u><strong>NEW PRICE</strong></u>: ${reservation.price} EUR</p>`
          }
        };
        await axios.post('https://api.emailjs.com/api/v1.0/email/send', email).catch(error => {
          this.$toasted.error('Reservation email could not be sent')
          throw error
        })
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
  }
}
</script>

<style scoped>

</style>