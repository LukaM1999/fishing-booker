<template>
  <form @submit.prevent="sendDeletionRequest">
    <div class="modal-card" style="width: auto">
      <header class="modal-card-head">
        <p class="modal-card-title is-center">Send deletion request</p>
        <button
            type="button"
            class="delete"
            @click="$emit('close')"/>
      </header>
      <section class="modal-card-body">
        <div class="row mb-3">
          <div class="col">
            <div class="form-floating">
              <textarea type="text" class="form-control" id="deletionReason" v-model="deletionReason" required>
              </textarea>
              <label for="deletionReason">Reason of deletion*</label>
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
  name: "ProfileDeletion",
  data(){
    return {
      deletionReason: '',
      profile: Object.assign({}, JSON.parse(localStorage.getItem('user'))),
    }
  },
  methods: {
    async sendDeletionRequest(){
      const deletionRequest = {
        username: this.profile.username,
        email: this.profile.email,
        deletionReason: this.deletionReason
      }
      const response = await axios.post('/user/sendDeletionRequest', deletionRequest)
      if (response.data){
        this.$toasted.success('Deletion request sent successfully!')
        this.$parent.close()
      }
      else {
        this.$toasted.error('Error sending deletion request')
      }
    }
  }
}
</script>

<style scoped>

</style>