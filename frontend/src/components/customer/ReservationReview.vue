<template>
  <div class="modal-card" style="width: auto">
    <div class="modal-card-head">
      <p class="modal-card-title">Review and rate your reservation</p>
      <button class="delete" aria-label="close" @click="$emit('close')"></button>
    </div>
    <div class="modal-card-body">
      <div class="content">
        <div class="columns">
          <div class="column">
            <div class="field">
              <label class="label">Review</label>
              <div class="control">
                <textarea class="textarea" v-model="review" placeholder="Write your review here"></textarea>
              </div>
            </div>
          </div>
          <div class="column is-2 align-self-center">
            <div class="field">
              <label class="label">{{reservation.type | capitalize}} rating</label>
              <div class="control">
                <b-rate v-model="rentableRating" :max="5"></b-rate>
              </div>
            </div>
          </div>
          <div class="column is-2 align-self-center">
            <div class="field">
              <label class="label">Owner rating</label>
              <div class="control">
                <b-rate v-model="ownerRating" :max="5"></b-rate>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="modal-card-foot">
      <button class="button is-success" @click="submit">Submit</button>
      <button class="button" @click="$emit('close')">Cancel</button>
    </div>
  </div>
</template>

<script>
import {backend} from "@/env";

export default {
  name: "ReservationReview",
  data () {
    return {
      review: '',
      rentableRating: 3,
      ownerRating: 3,
    }
  },
  props: {
    reservation: {
      type: Object,
      required: true
    }
  },

  filters: {
    capitalize (value) {
      if (!value) return ''
      value = value.toString()
      value = value.toLowerCase()
      return value.charAt(0).toUpperCase() + value.slice(1)
    }
  },

  methods: {
    async submit () {
      try {
        const response = await this.axios.post(`${backend}/reservation/review`, {
          reservationId: this.reservation.id,
          comment: this.review,
          rentableRating: this.rentableRating,
          ownerRating: this.ownerRating
        })
        this.reservation.reviewed = true
        this.$emit('close')
        this.$emit('submit')
      } catch (error) {
        console.log(error)
      }
    },
  }
}
</script>

<style scoped>

</style>