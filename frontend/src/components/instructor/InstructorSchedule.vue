<template>
  <div>
    <div class="row text-center">
      <div class="col">
        <p style="font-size: xx-large;"><strong>Schedule</strong></p>
      </div>
    </div>
    <div class="d-flex justify-content-center">
      <div class="row mt-5 d-flex justify-content-evenly">
        <div class="col">
          <b-field label="Add available days">
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
          <b-field label="Add day off">
            <b-datepicker
                placeholder="Click to select..."
                v-model="selectedDate"
                :min-date="minDate">
            </b-datepicker>
            <button class="button ml-5 is-link" @click="addDayOff()">Submit</button>
          </b-field>
        </div>
      </div>
    </div>
    <div class="mt-4 row d-flex justify-content-center">
      <div class="col-8">
        <calendar :key="key"></calendar>
      </div>
    </div>
  </div>
</template>

<script>
import Calendar from "@/components/Calendar";
import axios from "axios";
import {backend} from "@/env";
import moment from "moment";

export default {
  name: "InstructorSchedule",
  components: {Calendar},
  data() {
    return {
      selectedDate: null,
      user: {},
      key: 0,
      startTime: new Date(),
      endTime: new Date(),
      minDate: new Date(),
      dates: [],
    }
  },
  mounted() {
    this.user = JSON.parse(localStorage.getItem('user'))
    this.minDate = moment().add(1, 'days').toDate()
    this.minDate.setHours(0)
    this.minDate.setMinutes(0)
  },
  methods: {
    async addDayOff() {
      const dayOff = {
        start: this.formatDate(this.selectedDate),
        end: this.formatDate(this.selectedDate),
        username: this.user.username,
        rentableName: "",
        type: this.user.role.authority === "INSTRUCTOR" ? 2 : 1,
      }
      const response = await axios.post(backend + '/reservation/createDayOff', dayOff)
      if (response.data != null) {
        this.$toasted.success('Day off successfully created!')
        this.key = this.key + 1
      } else
        this.$toasted.error('Something went wrong!')
    },
    formatDate(date) {
      return moment(date).format('YYYY-MM-DD HH:mm')
    },
    async createFreeTerm() {
      let sYear = this.dates[0].getFullYear()
      let sMonth = this.formatDateMonth(new Date(this.dates[0]));
      let sDay = this.formatDateDay(new Date(this.dates[0]));
      let eYear = this.dates[this.dates.length - 1].getFullYear()
      let eMonth = this.formatDateMonth(new Date(this.dates.slice(-1)[0]));
      let eDay = this.formatDateDay(new Date(this.dates.slice(-1)[0]));

      const freeTerm = {
        type: 'ADVENTURE',
        entityName: '',
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
  }

}
</script>

<style scoped>

.page {
  display: flex;
  justify-content: space-around;
}

::v-deep .vuecal--green-theme .vuecal__menu {
  background-color: #84A98C;
  color: #fff;
}

</style>