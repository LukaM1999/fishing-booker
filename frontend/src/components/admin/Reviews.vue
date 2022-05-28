<template>
  <div>
    <div class="row">
      <div class="col"></div>
      <div class="col-8">
        <b-table
            :data="reviews"
            :striped="true"
            :hoverable="true"
            :mobile-cards="true"
            ref="table"
            paginated
            per-page="7"
            detailed
            detail-key="id"
            detail-transition="fade"
            :show-detail-icon="true"
            aria-next-label="Next page"
            aria-previous-label="Previous page"
            aria-page-label="Page"
            aria-current-label="Current page">

          <b-table-column field="Owner" label="Owner" centered v-slot="props">
            {{ props.row.ownerUsername }}
          </b-table-column>

          <b-table-column field="Entity" label="Entity" centered v-slot="props">
            {{ props.row.rentableName }}
          </b-table-column>

          <b-table-column field="OwnerRating" label="Owner rating" centered v-slot="props">
            {{ props.row.ownerRating }}
          </b-table-column>

          <b-table-column field="EntityRating" label="Entity rating" centered v-slot="props">
            {{ props.row.rentableRating }}
          </b-table-column>

          <b-table-column label="Manage" centered v-slot="props">
            <div v-if="!props.row.fromCustomer" class="d-flex justify-content-center" style="display:inline;">
              <button class="btn pagination-link is-current"
                      @click="approve(props.row)">Approve
              </button>
              <button class="btn btn-danger"
                      @click="decline(props.row)">Decline
              </button>
            </div>
          </b-table-column>

          <template #empty>
            <div class="has-text-centered">No records</div>
          </template>

          <template #detail="props">
            <article class="media">
              <div class="media-content">
                <div class="content">
                  <p>
                    <strong>Description:</strong>
                    <br>
                    {{ props.row.comment }}
                  </p>
                </div>
              </div>
            </article>
          </template>
        </b-table>
      </div>
      <div class="col"></div>
    </div>
  </div>
</template>

<script>
import {backend} from "@/env";
import axios from "axios";

export default {
  name: "Reviews",
  data() {
    return {
      reviews: [],
    }
  },
  async mounted() {
    this.axios
        .get(backend + '/reservation/getReviews')
        .then(response => {
          this.reviews = response.data
        });
  },
  methods: {
    approve(review) {
      this.axios.put(backend + '/reservation/updateReview', {
        reservationId: review.reservationId,
        rentableName: review.rentableName,
        ownerUsername: review.ownerUsername,
        reservationType: review.reservationType,
        comment: review.comment,
        rentableRating: review.rentableRating,
        ownerRating: review.ownerRating,
        public: true,
        approved: true
      }).then(response => {
        this.sendEmail(`Review approved, updated rating for `+ review.rentableName, review);
        this.$toasted.success("Review approved!");
        this.removeReview(review)
      }).catch(error => {
        this.$toasted.error("Error while approving review");
      });
    },
    decline(review){
      this.axios.put(backend + '/reservation/updateReview', {
        reservationId: review.reservationId,
        rentableName: review.rentableName,
        ownerUsername: review.ownerUsername,
        reservationType: review.reservationType,
        comment: review.comment,
        rentableRating: review.rentableRating,
        ownerRating: review.ownerRating,
        public: false,
        approved: true
      }).then(response => {
        this.$toasted.success("Review declined!");
        this.removeReview(review)
      }).catch(error => {
        this.$toasted.error("Error while declining review");
      });
    },
    removeReview(complaint){
      this.reviews = this.reviews.filter(c => c.reservationId !== complaint.reservationId)
    },
    async sendEmail(content, review){
      const response = await this.axios.get(backend + `/user/${review.ownerUsername}`)
      if (response.data !== null) {
        const email = {
          service_id: 'service_approve',
          template_id: 'template_approved',
          user_id: 'user_62WYz6KIasgbXlUB5EEGw',
          template_params: {
            'to_name': review.ownerUsername,
            'to_email': response.data.email,
            'subject': 'Review approved',
            'message': 'Description: ' + content
          }
        }
        await axios.post('https://api.emailjs.com/api/v1.0/email/send', email);
        this.$toasted.success('Email sent successfully')
      } else
        this.$toasted.error('Error while sending Email')
    },
  }
}
</script>

<style scoped>

</style>