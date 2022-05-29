<template>
  <div class="row">
    <div class="col"></div>
    <div class="col">
      <div class="row-6">
        <div class="col mt-5">
          <b-field label="Define points client gets after successful reservation">
            <b-numberinput v-model="customerPoints" rounded></b-numberinput>
          </b-field>
        </div>
        <div class="col mt-5">
          <b-field label="Define points owner gets after successful reservation">
            <b-numberinput v-model="ownerPoints" rounded></b-numberinput>
          </b-field>
        </div>
      </div>
      <div class="row-6">
        <div class="col mt-5">
          <b-field label="Percentage system gets from reservations">
            <b-numberinput v-model="systemTax" rounded></b-numberinput>
          </b-field>
        </div>
        <div class="col mt-5">
          <b-field label="Minimum points required for silver membership">
            <b-numberinput v-model="silver" rounded></b-numberinput>
          </b-field>
        </div>
        <div class="col mt-5">
          <b-field label="Minimum points required for gold membership">
            <b-numberinput v-model="gold" rounded></b-numberinput>
          </b-field>
        </div>
      </div>
      <div class="row-6 text-center mt-5">
        <b-button type="is-primary" @click="save">Save</b-button>
      </div>
    </div>
    <div class="col"></div>

  </div>
</template>

<script>
import {backend} from "@/env";
import axios from "axios";

export default {
  name: "Loyalty",
  data() {
    return {
      silver: 0,
      gold: 0,
      customerPoints: 0,
      ownerPoints: 0,
      systemTax: 0,
    }
  },
  methods: {
    async save() {
      const response = await axios.put(backend + '/loyalty/updatePoints', {
        id: 1,
        silver: this.silver,
        gold: this.gold,
        customerPoints: this.customerPoints,
        ownerPoints: this.ownerPoints,
        systemTax: this.systemTax,
      })
      .then(response => {
        this.$toasted.success("Successfully saved!");
      })
      .catch(error => {
        console.log(error);
      });
    }
  },
  async mounted() {
    const response = await axios.get(backend + '/loyalty/getPoints').catch(error => {
      console.log(error);
    });
    if(response.data !== null) {
      this.silver = response.data[0].silver;
      this.gold = response.data[0].gold;
      this.customerPoints = response.data[0].customerPoints;
      this.ownerPoints = response.data[0].ownerPoints;
      this.systemTax = response.data[0].systemTax;
    }
  },
}
</script>

<style scoped>

</style>