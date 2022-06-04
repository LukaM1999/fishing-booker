<template>
  <div class="row">
    <div class="col text-center">
      <h2 style="font-size: x-large">Travel more and save more!
      </h2>
      <h2 class="mt-3" style="font-size: large">Membership:
        <strong v-if="points >= silver && points < gold" style="color: limegreen;">Emerald</strong>
        <strong v-else-if="points >=  gold" style="color:goldenrod;">Gold</strong>
        <strong v-else style="color: darkred">Ruby</strong>
      </h2>
      <b-progress
          :type="points >= silver && points < gold ? 'is-success' : points >= gold ? 'is-warning' : 'is-danger'"
          :value="points"
          show-value
          size="is-large"
      ></b-progress>
      <h2 v-if="user.role.authority === 'CUSTOMER'" class="mt-3" style="font-size: large">Membership bonus:
        <strong v-if="points >= silver && points < gold" style="color: limegreen;">10%</strong>
        <strong v-else-if="points >=  gold" style="color:goldenrod;">20%</strong>
        <strong v-else style="color: darkred">0%</strong>
        off on any reservation you make.
      </h2>
      <h2 v-else class="mt-3" style="font-size: large">Membership bonus:
        <strong v-if="points >= silver && points < gold" style="color: limegreen;">3%</strong>
        <strong v-else-if="points >=  gold" style="color:goldenrod;">5%</strong>
        <strong v-else style="color: darkred">0%</strong>
        income bonus after every reservation.
      </h2>
    </div>
  </div>

</template>

<script>
import {backend} from "@/env";
import axios from "axios";

export default {
  name: "ProgressBar",
  data() {
    return {
      user: JSON.parse(localStorage.getItem('user')),
      points: 0,
      silver: 500,
      gold: 1000,
    }
  },
  async mounted() {
    const response = await axios.get(`${backend}/loyalty/getUserPoints/${this.user.username}`).catch(err => {
      console.log(err)
    })
    if (response.data) {
      this.points = response.data.points/10
    }
    const loyalty = await axios.get(`${backend}/loyalty/getPoints`).catch(err => {
      console.log(err)
    })
    if (loyalty.data) {
      this.silver = loyalty.data[0].silver/10
      this.gold = loyalty.data[0].gold/10
    }
  },
  methods: {

  }
}
</script>

<style scoped>

</style>