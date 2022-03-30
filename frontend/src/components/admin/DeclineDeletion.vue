<template>
  <form @submit.prevent="decline">
    <div class="modal-card" style="width: auto">
      <header class="modal-card-head">
        <p class="modal-card-title is-center">Decline deletion request</p>
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
import axios from "axios";

export default {
  name: "DeclineDeletion",
  data() {
    return {
      reason: ''
    }
  },
  props: {
    user: Object,
  },
  methods: {
    async decline(){
      //console.log(this.user)
      const response = await this.axios.delete(`/user/deleteRequest/${this.user.username}`)
      if (response.data) {
        const email = {
          service_id: 'service_approve',
          template_id: 'template_approved',
          user_id: 'user_62WYz6KIasgbXlUB5EEGw',
          template_params: {
            'to_name': this.user.username,
            'to_email': this.user.email,
            'subject': 'Profile deletion declined',
            'message': 'Your profile deletion was declined with the following reason: ' + this.reason
          }
        };
        this.$parent.close()
        this.$emit('declined', this.user)
        this.$toasted.success('Profile deletion declined successfully')
        await axios.post('https://api.emailjs.com/api/v1.0/email/send', email);
      } else
        this.$toasted.error('Error while declining profile deletion')
    }
  }
}
</script>

<style scoped>

</style>