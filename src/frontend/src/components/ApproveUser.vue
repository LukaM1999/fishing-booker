<template>
  <div>
    <div class="row">
      <div class="col"></div>
      <div class="col-8">
        <b-table
            :data="users"
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

          <b-table-column field="first_name" label="First Name" centered v-slot="props">
            {{ props.row.name }}
          </b-table-column>

          <b-table-column field="last_name" label="Last Name" centered v-slot="props">
            {{ props.row.surname }}
          </b-table-column>

          <b-table-column field="date" label="Email" centered v-slot="props">
            {{ props.row.email }}
          </b-table-column>

          <b-table-column label="Role" centered v-slot="props">
            {{ props.row.role }}
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
                    <strong>Letter of intent:</strong>
                    <br>
                    {{ props.row.letterOfIntent }}
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
import ProfileDeletion from "@/components/ProfileDeletion";
import DeclineRegistration from "@/components/DeclineRegistration";

export default {
  name: "ApproveUser",
  data() {
    return {
      users: [],
      filteredUsers: []
    }
  },
  async mounted() {
    this.axios
        .get('/user/waitingApproval')
        .then(response => {
          this.users = response.data
        });
  },
  methods: {
    async approve(user) {
      const response = await this.axios.put('/user/approve', user.username)
      if (response.data) {
        this.removeUser(user)
        const email = {
          service_id: 'service_approve',
          template_id: 'template_approved',
          user_id: 'user_62WYz6KIasgbXlUB5EEGw',
          template_params: {
            'to_name': user.name,
            'to_email': user.email,
            'subject': 'Registration approved!',
            'message': 'You have successfully registered to FishingBooker. We are happy to have you with us!'
          }
        };
        this.$toasted.success('User approved successfully!')
        await axios.post('https://api.emailjs.com/api/v1.0/email/send', email);
      } else
        this.$toasted.error('Approving user failed')
    },
    openDeclineModal(user) {
      this.$buefy.modal.open({
        parent: this,
        component: DeclineRegistration,
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
      this.users = this.users.filter(u => u.username !== user.username)
    }
  }
}
</script>

<style scoped>

</style>