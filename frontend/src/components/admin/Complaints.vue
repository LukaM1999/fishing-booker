<template>
  <div>
    <div class="row">
      <div class="col"></div>
      <div class="col-8" :key="key">
        <b-table
            :data="complaints"
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

          <b-table-column field="issuer" label="Issuer" centered v-slot="props">
            {{ props.row.issuerUsername }}
          </b-table-column>

          <b-table-column field="subject" label="Subject" centered v-slot="props">
            {{ props.row.subjectUsername }}
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
            <div v-if="props.row.fromCustomer" class="d-flex justify-content-center" style="display:inline;">
              <button class="btn btn-warning is-current"
                      @click="openAnswerModal(props.row)">Answer
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
                    {{ props.row.complaint }}
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
import DeclineDeletion from "@/components/admin/DeclineDeletion";
import AnswerComplaint from "@/components/admin/AnswerComplaint";
import axios from "axios";

export default {
  name: "Complaints",
  data() {
    return {
      complaints: [],
      key: 0
    }
  },
  async mounted() {
    this.axios
        .get(backend + '/complaint/all')
        .then(response => {
          this.complaints = response.data
        });
  },
  methods: {
    async approve(complaint) {
      const response = await this.axios.put(backend + '/complaint/update', {
        id: complaint.id,
        reservationId: complaint.reservationId,
        issuerUsername: complaint.issuerUsername,
        subjectUsername: complaint.subjectUsername,
        complaint: complaint.complaint,
        reviewed: true,
        fromCustomer: complaint.fromCustomer,
        forPenalty: complaint.forPenalty
      }).then(response => {
        this.sendEmail(`Complaint approved, `+ complaint.subjectUsername + ` will get penalty.`, complaint);
        this.$toasted.success("Complaint approved!");
        this.removeComplaint(complaint)
      }).catch(error => {
        this.$toasted.error("Error while approving complaint");
      });
    },
    async decline(complaint){
      const response = await this.axios.put(backend + '/complaint/update', {
        id: complaint.id,
        reservationId: complaint.reservationId,
        issuerUsername: complaint.issuerUsername,
        subjectUsername: complaint.subjectUsername,
        complaint: complaint.complaint,
        reviewed: true,
        fromCustomer: complaint.fromCustomer,
        forPenalty: complaint.forPenalty
      }).then(response => {
        this.sendEmail(`Complaint declined, `+ complaint.subjectUsername + ` will not get penalty.`, complaint);
        this.$toasted.success("Complaint declined!");
        this.removeComplaint(complaint)
      }).catch(error => {
        this.$toasted.error("Error while declining complaint");
      });
    },
    openAnswerModal(complaint) {
      this.$buefy.modal.open({
        parent: this,
        component: AnswerComplaint,
        hasModalCard: true,
        trapFocus: true,
        props: {
          complaint
        },
        events: {
          'declined': this.removeComplaint
        }
      });
    },
    removeComplaint(complaint){
      this.complaints = this.complaints.filter(c => c.id !== complaint.id)
    },
    async sendEmail(content, complaint){
      const response = await this.axios.get(backend + `/user/${complaint.issuerUsername}`)
      const subject = await this.axios.get(backend + `/user/${complaint.subjectUsername}`)
      if (response.data !== null && subject.data !== null) {
        console.log(response.data)
        const email1 = {
          service_id: 'service_approve',
          template_id: 'template_approved',
          user_id: 'user_62WYz6KIasgbXlUB5EEGw',
          template_params: {
            'to_name': complaint.issuerUsername,
            'to_email': response.data.email,
            'subject': 'Reservation complaint',
            'message': 'Description: ' + content
          }
        };
        const email2 = {
          service_id: 'service_approve',
          template_id: 'template_approved',
          user_id: 'user_62WYz6KIasgbXlUB5EEGw',
          template_params: {
            'to_name': complaint.subjectUsername,
            'to_email': subject.data.email,
            'subject': 'Reservation complaint',
            'message': 'Description: ' + content
          }
        };
        await axios.post('https://api.emailjs.com/api/v1.0/email/send', email1);
        await axios.post('https://api.emailjs.com/api/v1.0/email/send', email2);
        this.$toasted.success('Email sent successfully')
      } else
        this.$toasted.error('Error while sending Email')
    },
  }
}
</script>

<style scoped>

</style>