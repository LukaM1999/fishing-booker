<template>
  <form @submit.prevent="decline">
    <div class="modal-card" style="width: auto">
      <header class="modal-card-head">
        <p class="modal-card-title is-center">Send answer</p>
        <button
            type="button"
            class="delete"
            @click="$emit('close')"/>
      </header>
      <section class="modal-card-body">
        <div class="row mb-3">
          <div class="col">
            <div class="form-floating">
              <textarea type="text" class="form-control" id="deletionReason" v-model="reason" required>
              </textarea>
              <label for="deletionReason">Description*</label>
            </div>
          </div>
        </div>
      </section>
      <footer class="modal-card-foot">
        <div class="row align-content-center">
          <div class="col d-flex justify-content-center">
            <button type="submit" class="btn btn-dark btn-lg" data-bs-dismiss="modal">
              Send
            </button>
          </div>
        </div>
      </footer>
    </div>
  </form>
</template>

<script>
import {backend} from "@/env";
import axios from "axios";

export default {
  name: "AnswerComplaint",
  data() {
    return {
      reason: '',
      issuerEmail: '',
      subjectEmail: '',
    }
  },
  props: {
    complaint: Object,
  },
  methods: {
    async decline(){
      const response = await this.axios.get(backend + `/user/${this.complaint.issuerUsername}`)
      const subject = await this.axios.get(backend + `/user/${this.complaint.subjectUsername}`)
      if (response.data !== null && subject.data !== null) {
        const email1 = {
          service_id: 'service_approve',
          template_id: 'template_approved',
          user_id: 'user_62WYz6KIasgbXlUB5EEGw',
          template_params: {
            'to_name': this.complaint.issuerUsername,
            'to_email': response.data.email,
            'subject': 'Reservation complaint',
            'message': 'Answer: ' + this.reason
          }
        };
        const email2 = {
          service_id: 'service_approve',
          template_id: 'template_approved',
          user_id: 'user_62WYz6KIasgbXlUB5EEGw',
          template_params: {
            'to_name': this.complaint.subjectUsername,
            'to_email': subject.data.email,
            'subject': 'Reservation complaint',
            'message': 'Answer: ' + this.reason
          }
        };
        await this.updateComplaint(this.complaint)
        await axios.post('https://api.emailjs.com/api/v1.0/email/send', email1);
        await axios.post('https://api.emailjs.com/api/v1.0/email/send', email2);
        this.$parent.close()
        this.$emit('declined', this.complaint)
        this.$toasted.success('Answer sent successfully')
      } else
        this.$toasted.error('Error while sending answer')
    },
    async updateComplaint(complaint){
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
        this.$toasted.success('Complaint updated successfully')
      }).catch(error => {
        this.$toasted.error("Error while updating complaint");
      });
    },

  }
}
</script>

<style scoped>

</style>