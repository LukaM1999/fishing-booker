<template>
  <div class="modal-card" style="width: auto">
    <div class="modal-card-head">
      <p class="modal-card-title">Send complaint</p>
      <button class="delete" aria-label="close" @click="$emit('close')"></button>
    </div>
    <div class="modal-card-body">
      <div class="content">
        <div class="columns">
          <div class="column">
            <div class="field">
              <label class="label">Complaint</label>
              <div class="control">
                <textarea class="textarea" :disabled="disabled" v-model="complaint" placeholder="Write complaint here"></textarea>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="modal-card-foot">
      <button class="button is-success" :disabled="disabled" @click="submit">Send</button>
      <button class="button" @click="$emit('close')">Cancel</button>
    </div>
  </div>
</template>

<script>

import {backend} from "@/env";
import axios from "axios";

export default {
  name: "Complaint",
  data () {
    return {
      complaint: '',
      disabled: false,
    }
  },
  props: {
    reservation: {
      type: Object,
      required: true
    },
  },
  async mounted() {
    await this.getComplaint();
  },
  methods: {
    async getComplaint() {
      const {data} = await axios.get(backend + '/complaint/customer/' + this.reservation.id).catch(err => {
        throw err;
      })
      if(data) {
        this.complaint = data.complaint;
        this.disabled = true;
      }
    },
    async submit () {
      const complaint = {
        complaint: this.complaint,
        reservationId: this.reservation.id,
        issuerUsername: this.reservation.customerUsername,
        subjectUsername: this.reservation.ownerUsername,
        fromCustomer: true,
      }
      await axios.post(`${backend}/complaint/add`, complaint)
        .then(() => {
          this.$emit('close')
          this.$toasted.success('Complaint sent!')
        })
        .catch(() => {
          this.$toasted.error('Failed to send complaint')
        })
    }
  }
}
</script>

<style scoped>

</style>