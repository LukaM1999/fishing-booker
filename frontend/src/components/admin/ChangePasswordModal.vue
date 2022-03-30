<template>
  <form @submit.prevent="changePassword">
    <div class="modal-card" style="width: auto">
      <header class="modal-card-head">
        <p class="modal-card-title">Change password</p>
      </header>
      <section class="modal-card-body">
        <div class="row mb-3">
          <div class="col">
            <div class="form-floating">
              <input type="password" class="form-control" id="loginPassword" v-model="password" required>
              <label for="loginPassword">New password*</label>
            </div>
          </div>
        </div>
      </section>
      <footer class="modal-card-foot">
        <div class="row align-content-center">
          <div class="col d-flex justify-content-center">
            <button type="submit" class="btn btn-dark btn-lg" data-bs-dismiss="modal">
              Confirm
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
  name: "ChangePasswordModal",
  data(){
    return {
      password: '',
      user: {}
    }
  },
  mounted() {
    this.user = JSON.parse(localStorage.getItem('user'))
  },
  methods: {
    async changePassword(){
      const loginDto = {username: this.user.username, password: this.password}
      const response = await axios.put('/user/changePassword', loginDto)
      if (response.data) {
        this.$parent.close()
        this.$toasted.success('Successfully changed password!')
      }
      else {
        this.$toasted.error('Invalid password')
      }
    }
  }
}
</script>

<style scoped>

</style>