<template>
  <form @submit.prevent="userLogin">
    <div class="modal-card" style="width: auto">
      <header class="modal-card-head">
        <p class="modal-card-title">Login</p>
        <button
            type="button"
            class="delete"
            @click="$emit('close')"/>
      </header>
      <section class="modal-card-body">
        <div class="row mb-3">
          <div class="col">
            <div class="form-floating">
              <input type="text" class="form-control" id="loginUsername" v-model="usernameLogin" required autofocus>
              <label for="loginUsername">Username*</label>
            </div>
          </div>
        </div>
        <div class="row mb-3">
          <div class="col">
            <div class="form-floating">
              <input type="password" class="form-control" id="loginPassword" v-model="passwordLogin" required>
              <label for="loginPassword">Password*</label>
            </div>
          </div>
        </div>
      </section>
      <footer class="modal-card-foot">
        <div class="row align-content-center">
          <div class="col d-flex justify-content-center">
            <button type="submit" class="btn btn-dark btn-lg" data-bs-dismiss="modal"
                    :disabled="!usernameLogin || !passwordLogin" id="login">
              Login
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
  name: "Login",
  data: function () {
    return {
      usernameLogin: '',
      passwordLogin: '',
    }
  },
  methods: {
    async userLogin() {
      const loginDto = {username: this.usernameLogin, password: this.passwordLogin}
      const response = await axios.post(backend + '/auth/login', loginDto).catch(e => {
        this.$toasted.error('Wrong username or password!')
      })
      if (response.data) {
        localStorage.setItem('jwt', response.data.accessToken)
        localStorage.setItem('user', JSON.stringify(response.data.user))
        this.$parent.close();
        if (response.data.user.role.authority === 'CUSTOMER')
          await this.$router.push('/customer')
        else if (response.data.user.role.authority === 'COTTAGE_OWNER')
          await this.$router.push('/cottageOwner/cottages')
        else if (response.data.user.role.authority === 'BOAT_OWNER')
          await this.$router.push('/boatOwner/boats')
        else if (response.data.user.role.authority === 'INSTRUCTOR')
          await this.$router.push('/instructor')
        else
          await this.$router.push('/admin')
      } else
        this.$toasted.error('Wrong username or password!')
    },
  }
}
</script>

<style scoped>

</style>