<template>
  <div>
    <div class="row text-center mt-3">
      <div class="col">
        <p style="font-size: xx-large;"><strong>Schedule</strong></p>
      </div>
    </div>
    <div class="row mt-4">
      <div class="col">
        <calendar></calendar>
      </div>
      <div class="col">
        <div class="row mt-5">
          <div class="col">
            <form onsubmit="createFreeTerm">
              <div class="row">
                <b-field label="Add available days">
                  <b-datepicker
                      v-model="selectedDate"
                      placeholder="Click to select..."
                      icon="calendar-today"
                  ></b-datepicker>
                </b-field>
              </div>
              <div>
                <button class="btn btn-dark mt-3" type="submit">Submit</button>
              </div>
            </form>
          </div>
          <div class="col">
            <form>
              <div class="row">
                <b-field label="Add day off">
                  <b-datepicker
                      v-model="selectedDate"
                      placeholder="Click to select..."
                      icon="calendar-today"
                      ></b-datepicker>
                </b-field>
              </div>
              <div>
                <button class="btn btn-dark mt-3" @click="addDayOff">Submit</button>
              </div>
            </form>
          </div>
        </div>
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
      selectedDate: new Date(),
      user: {},
      key: 0
    }
  },
  mounted() {
    this.user = JSON.parse(localStorage.getItem('user'))
  },
  methods: {
    async addDayOff(){
      const dayOff = {
        start: this.formatDate(this.selectedDate),
        end: this.formatDate(this.selectedDate),
        username: this.user.username,
        rentableName: "",
        type: this.user.role.authority === "INSTRUCTOR" ? 2 : 1,
      }
      const response = await axios.post(backend + '/reservation/createDayOff', dayOff)
      if(response.data != null){
        //this.key += 1
        this.$toasted.success('Day off successfully created!')
      }
      else
        this.$toasted.error('Something went wrong!')
    },
    formatDate(date){
      return moment(date).format('YYYY-MM-DD HH:mm')
    },
  }

}
</script>

<style scoped>

</style>