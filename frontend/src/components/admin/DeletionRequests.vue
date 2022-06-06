<template>
  <div>
    <div class="row">
      <div class="col"></div>
      <div class="col-8">
        <b-table
            :data="requests"
            :striped="true"
            :hoverable="true"
            :mobile-cards="true"
            ref="table"
            paginated
            per-page="5"
            detailed
            detail-key="username"
            detail-transition="fade"
            :show-detail-icon="true"
            aria-next-label="Next page"
            aria-previous-label="Previous page"
            aria-page-label="Page"
            aria-current-label="Current page">

          <b-table-column field="username" label="Username" centered v-slot="props">
            {{ props.row.username }}
          </b-table-column>

          <b-table-column field="email" label="Email" centered v-slot="props">
            {{ props.row.email }}
          </b-table-column>

          <b-table-column label="Manage account" centered v-slot="props">
            <div class="d-flex justify-content-center" style="display:inline;">
              <button class="btn pagination-link is-current" style="margin-right: 5%;"
                      @click="approve(props.row)">Approve
              </button>
              <button class="btn btn-danger" style="margin-right: 5%;"
                      @click="openDeclineModal(props.row)">Decline
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
                    {{ props.row.deletionReason }}
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
import axios from "axios";
import DeclineRegistration from "@/components/admin/DeclineRegistration";
import DeclineDeletion from "@/components/admin/DeclineDeletion";
import {backend} from "@/env";

export default {
  name: "DeletionRequests",
  data() {
    return {
      requests: [],
    }
  },
  async mounted() {
    this.axios
        .get(backend + '/user/getDeletionRequests')
        .then(response => {
          this.requests = response.data
        });
  },
  methods: {
    async approve(user) {
      //await this.testDelete(user)
      const response = await this.axios.delete(backend + `/user/delete/${user.username}`)
      if (response.data) {
        this.removeUser(user)
        const email = {
          service_id: 'service_approve',
          template_id: 'template_approved',
          user_id: 'user_62WYz6KIasgbXlUB5EEGw',
          template_params: {
            'to_name': user.username,
            'to_email': user.email,
            'subject': 'Deletion request approved',
            'message': 'You have successfully deleted account from FishingBooker.'
          }
        };
        this.$toasted.success('Request approved successfully!')
        await axios.post('https://api.emailjs.com/api/v1.0/email/send', email);
      } else
        this.$toasted.error('Approving request failed')
    },
    async testDelete(user){
      await axios.all([
        axios.delete(backend + `/user/delete/${user.username}`),
        axios.delete(backend + `/user/delete/${user.username}`)
      ])
    },
    openDeclineModal(user) {
      this.$buefy.modal.open({
        parent: this,
        component: DeclineDeletion,
        hasModalCard: true,
        trapFocus: true,
        props: {
          user
        },
        events: {
          'declined': this.removeUser
        }
      });
    },
    removeUser(user){
      this.requests = this.requests.filter(u => u.username !== user.username)
    }
  }
}
</script>

<style scoped>

</style>