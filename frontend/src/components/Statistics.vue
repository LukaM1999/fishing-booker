<template>
  <div class="columns ml-5">
    <div class="col-5 m-5 box">
      <p class="subtitle has-text-centered">Number of reservation per rentable</p>
    <Bar
        :chart-options="chartOptions"
        :chart-data="chartData"
        :chart-id="chartId"
        :dataset-id-key="datasetIdKey"
        :plugins="plugins"
        :css-classes="cssClasses"
        :styles="styles"
        :width="width"
        :height="height"
    />
    </div>
    <div class="col-5 m-5 box">
      <p class="subtitle has-text-centered">Distribution of profits per rentable</p>
      <Doughnut
          :chart-options="chartOptionsDoughnut"
          :chart-data="chartDataDoughnut"
          :chart-id="chartId"
          :dataset-id-key="datasetIdKey"
          :plugins="plugins"
          :css-classes="cssClasses"
          :styles="styles"
          :width="width"
          :height="height"
      />
      <b-field class="mt-5" label="Choose earning period">
        <b-datepicker
            placeholder="Click to select..."
            v-model="dates"
            range
            @input="getFinances()"
            >
        </b-datepicker>
      </b-field>
      <p class="subtitle has-text-centered mt-5"> Total earnings in the period  <br> from: {{startTime.substring(0,10)}} to: {{endTime.substring(0,10)}} is <strong>{{totalEarnings}}€</strong></p>
    </div>
  </div>
</template>

<script>
import { Bar, Doughnut } from 'vue-chartjs/legacy'
import { Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale, ArcElement } from 'chart.js'
import {backend} from "@/env";
import axios from "axios";

ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale, ArcElement, CategoryScale)

export default {
  components: { Bar, Doughnut },
  props: {
    chartId: {
      type: String,
      default: 'bar-chart'
    },
    datasetIdKey: {
      type: String,
      default: 'label'
    },
    width: {
      type: Number,
      default: 400
    },
    height: {
      type: Number,
      default: 400
    },
    cssClasses: {
      default: '',
      type: String
    },
    styles: {
      type: Object,
      default: () => {}
    },
    plugins: {
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {
      user: null,
      totalEarnings: 0,
      dates:[],
      startTime: '2022-01-01 00:00',
      endTime: '2023-01-01 00:00',

      chartData: {
        labels: [ 'Cottage' ],
        datasets: [
            {
              label: 'Last Week',
              backgroundColor: '#CAD2C5',
              data: [0]
            },
            {
              label: 'Last Month',
              backgroundColor: '#52796F',
              data: [0]
            },
            {
              label: 'Last Year',
              backgroundColor: '#2F3E46',
              data: [0]
            },
        ]
      },
      chartDataDoughnut: {
        labels: ['Cottage'],
        datasets: [
          {
            backgroundColor: ['#52796F'],
            data: [0]
          }
        ]
      },
      chartOptions: {
        responsive: true,
        scales: {
          y: {
            suggestedMax: 15,
            ticks: {
              stepSize: 2,
              max: 1000
            },
          },
        },
      },
      chartOptionsDoughnut: {
        responsive: true,
        maintainAspectRatio: false
      }
    }
  },
  async mounted() {
    this.user = JSON.parse(localStorage.getItem('user'))
    await this.getNumOfReservations();
    await this.getInitialFinances();
  },
  methods:{
    async getNumOfReservations(){
      const response = await axios.get(backend + '/reservation/getNumberOfReservations/' + this.user.username)
      if (response.data) {
        console.log(response.data)
        this.chartData.labels = response.data.map(data => data.rentableName)
        this.chartData.datasets[0].data = response.data.map(data => data.reservationNumWeek)
        this.chartData.datasets[1].data = response.data.map(data => data.reservationNumMonth)
        this.chartData.datasets[2].data = response.data.map(data => data.reservationNumYear)
      }
    },

    async getFinances(){
      let sYear = this.dates[0].getFullYear()
      let sMonth = this.formatDateMonth(new Date(this.dates[0]));
      let sDay = this.formatDateDay(new Date(this.dates[0]));
      let eYear = this.dates[this.dates.length - 1].getFullYear()
      let eMonth = this.formatDateMonth(new Date(this.dates.slice(-1)[0]));
      let eDay = this.formatDateDay(new Date(this.dates.slice(-1)[0]));

      this.startTime =`${sYear}-${sMonth}-${sDay} 00:00`
      this.endTime= `${eYear}-${eMonth}-${eDay} 00:00`
      const response = await axios.post(backend + '/reservation/finances', {
        username: this.user.username,
        startTime: this.startTime,
        endTime: this.endTime,
      })
      if(response.data){
        console.log(response.data)
        this.chartDataDoughnut.datasets[0].data = response.data.map(data => data.earning)
        this.chartDataDoughnut.datasets[0].backgroundColor = response.data.map(data => this.stringToColor(data.rentableName))
        this.chartDataDoughnut.labels = response.data.map(data => data.rentableName)
        this.totalEarnings = response.data.reduce((acc, cur) => acc + cur.earning, 0)
      }
    },
    async getInitialFinances(){
      const response = await axios.post(backend + '/reservation/finances', {
        username: this.user.username,
        startTime: this.startTime,
        endTime: this.endTime,
      })
      if(response.data){
        console.log(response.data)
        this.chartDataDoughnut.datasets[0].data = response.data.map(data => data.earning)
        this.chartDataDoughnut.datasets[0].backgroundColor = response.data.map(data => this.stringToColor(data.rentableName))
        this.chartDataDoughnut.labels = response.data.map(data => data.rentableName)
        this.totalEarnings = response.data.reduce((acc, cur) => acc + cur.earning, 0)
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
    stringToColor(str) {
      let hash = 0;
      for (let i = 0; i < str.length; i++) {
        hash = str.charCodeAt(i) + ((hash << 5) - hash);
      }
      let colour = '#';
      for (let i = 0; i < 3; i++) {
        let value = (hash >> (i * 8)) & 0xFF;
        colour += ('00' + value.toString(16)).substr(-2);
      }
      return colour;
    }

  }
}
</script>

<style scoped>

</style>