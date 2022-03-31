<template>
  <form @submit.prevent="isPasswordValid">
    <div class="modal-card" style="width: auto">
      <header class="modal-card-head">
        <p class="modal-card-title">Confirm password</p>
        <button
            type="button"
            class="delete"
            @click="$emit('close')"/>
      </header>
      <section class="modal-card-body">
        <div class="row mb-3">
          <div class="col">
            <div class="form-floating">
              <input type="password" class="form-control" id="loginPassword" v-model="passwordConfirmation" required>
              <label for="loginPassword">Confirm password*</label>
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
import {backend} from "@/env";

export default {
  name: "PasswordConfirmation",
  data(){
    return {
      passwordConfirmation: '',
    }
  },
  methods: {
    async isPasswordValid(){
      const response = await axios.post(backend + '/auth/confirmPassword',
          {username: JSON.parse(localStorage.getItem('user')).username, password: this.passwordConfirmation})
      if (response.data) {
        this.$emit('success')
        this.$parent.close()
        this.$toasted.success('Successfully edited profile!')
      }
      else {
        this.$toasted.error('Incorrect password')
      }
    }
  }
}
</script>

<style scoped>

</style>