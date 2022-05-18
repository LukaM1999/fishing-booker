<template>
  <div>
    <vue-cal name="calendar"
             :time-from="8 * 60"
             :time-to="21 * 60"
             :events="events"
             style="height: 650px"
             active-view="month"
             class="vuecal--green-theme"
             today-button
             events-on-month-view="short"
             :show-all-day-events="'short'"
             :editable-events="{ delete: false, resize: true}"
    >
    </vue-cal>
  </div>
</template>

<script>
import 'vue-cal/dist/vuecal.css'
import moment from "moment";
import axios from "axios";
import VueCal from "vue-cal";
import 'vue-cal/dist/vuecal.css'
import {backend} from "@/env";
export default {
  name: "Calendar",
  data() {
    return {
      user: {},
      events: [
      ],
      rentableName: JSON.parse(localStorage.getItem('currentCottage')).name,
    }
  },
  mounted() {
    this.user = JSON.parse(localStorage.getItem('user'))
    this.getFreeTerms()
    this.getAllReservations()
    console.log(this.rentableName)
  },
  components: {VueCal},
  methods: {
    async getFreeTerms() {
      const event = {
        username: this.user.username,
        type: this.user.role.authority === "INSTRUCTOR" ? 2 : 1,
        rentableName: this.rentableName
      }
      const response = await axios.post(backend + `/reservation/getFreeTerms`, event)
      if(response.data != null || response.data?.length !== 0){
        this.createEventsFromFreeTerms(response.data)
      }
    },
    createEventsFromFreeTerms(array){
      for (let i = 0; i < array.length; i++) {
        const event = {
          start: `${array[i].startTime}`,
          end: `${array[i].endTime}`,
          title: 'Available',
          content: '',
          class: 'sport',
          allDay: true,
          deletable: true
        }
        this.events.push(event)
      }
    },
    async getAllReservations() {
      const event = {
        username: this.user.username,
        type: this.user.role.authority === "INSTRUCTOR" ? 2 : 1,
        rentableName: this.rentableName
      }
      const response = await axios.post(backend + `/reservation/getAllReservations`, event)
      if(response.data != null || response.data?.length !== 0){
        this.createEventsFromReservations(response.data)
      }
    },
    createEventsFromReservations(array){
      for (let i = 0; i < array.length; i++) {
        let content = `Type: <b>${array[i].type}</b><br>
        Name: <b>${array[i].name}</b><br>
        Customer: <b>${array[i].customerUsername}</b><br>
        Price: <b>${array[i].price} euros</b><br>
        Start: <b>${array[i].startTime}</b><br>
        End: <b>${array[i].endTime}</b><br>`;
          const event = {
          start: `${array[i].startTime}`,
          end: `${array[i].endTime}`,
          title: 'Reserved',
          content: content,
          class: 'leisure',
          deletable: false,
        }
        this.events.push(event)
      }
    },
  },

}
</script>

<style>
.vuecal__cell--disabled {text-decoration: line-through;}

.vuecal--month-view .vuecal__cell-content {
  justify-content: flex-start;
  height: 100%;
  align-items: flex-end;
}
.vuecal--month-view .vuecal__cell-date {padding: 4px;}
.vuecal--month-view .vuecal__no-event {display: none;}

.vuecal__event-title {
  font-size: 1.2em;
  font-weight: bold;
  margin: 4px 0 8px;
}
.vuecal__event-time {
  display: inline-block;
  margin-bottom: 12px;
  padding-bottom: 12px;
  border-bottom: 1px solid rgba(0, 0, 0, 0.2);
}
.vuecal__event-content {
  font-style: italic;
}

.vuecal__event.leisure {background-color: rgba(54, 153, 38, 0.9);border: 1px solid rgb(19, 198, 13);color: #fff;}
.vuecal__event.sport {background-color: rgba(37, 199, 175, 0.9);border: 1px solid rgb(22, 177, 154);color: #fff;}

.vuecal__event.lunch {
  background: repeating-linear-gradient(45deg, transparent, transparent 10px, #f2f2f2 10px, #f2f2f2 20px);/* IE 10+ */
  color: #999;
  display: flex;
  justify-content: center;
  align-items: center;
}
.vuecal__event.lunch .vuecal__event-time {display: none;align-items: center;}
</style>