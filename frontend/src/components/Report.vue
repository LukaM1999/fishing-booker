<template>
  <div class="modal-card" style="width: auto">
    <div class="modal-card-head">
      <p class="modal-card-title">Reservation report</p>
      <button class="delete" aria-label="close" @click="$emit('close')"></button>
    </div>
    <div class="modal-card-body">
      <div class="content">
        <div class="columns">
          <div class="column">
            <div class="field">
              <label class="label">Report</label>
              <div class="control">
                <textarea class="textarea" :disabled="!canSend" v-model="review" placeholder="Write your report here"></textarea>
              </div>
            </div>
          </div>
          <div class="column is-2 align-self-center">
            <div class="field">
              <label class="label">Ask for penalty</label>
              <div class="control">
                <b-checkbox :disabled="!canSend" v-model="forPenalty"></b-checkbox>
              </div>
            </div>
          </div>
          <div v-if="canSend" class="column is-3 align-self-center">
            <div class="field">
              <label class="label">Customer did not come</label>
              <div class="control">
                <b-checkbox v-model="didNotCome"></b-checkbox>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div v-if="canSend" class="modal-card-foot">
      <button class="button is-success" @click="submit">Submit</button>
      <button class="button" @click="$emit('close')">Cancel</button>
    </div>
  </div>
</template>

<script>
import {backend} from "@/env";

export default {
  name: "Report",
  data () {
    return {
      review: '',
      forPenalty: false,
      didNotCome: false,
      canSend: true
    }
  },
  props: {
    reservation: {
      type: Object,
      required: true
    },
  },
  async mounted() {
    const response = await this.axios.get(`${backend}/complaint/${this.reservation.id}`);
    if (response.data !== null) {
      if(!response.data.fromCustomer){
        this.review = response.data.complaint;
        this.forPenalty = response.data.forPenalty;
        this.canSend = false;
      }
    }
    },
  methods: {
    //add new review
    async submit () {
      if(this.didNotCome){
        const response = await this.axios.post(`${backend}/penalty/add`, {
          customerUsername: this.reservation.customerUsername
        });
        if(response.data !== null){
          this.$toasted.success('Penalty successfully created!')
        } else {
          this.$toasted.error('Penalty creation failed!')
        }
      }
      let data = {
        reservationId: this.reservation.id,
        issuerUsername: this.reservation.ownerUsername,
        subjectUsername: this.reservation.customerUsername,
        complaint: this.review,
        fromCustomer: false,
        forPenalty: this.forPenalty,
        reviewed: false
      }
      try {
        const response = await this.axios.post(`${backend}/complaint/add`, data);
        if (response.data !== null) {
          this.$emit('close')
          this.$emit('submit')
        } else {
          this.$toasted.error('Something went wrong!')
        }
      } catch (error) {
        console.log(error)
      }
    },

  }
}
</script>

<style scoped>

</style>