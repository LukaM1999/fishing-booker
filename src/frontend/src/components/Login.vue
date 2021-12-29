<template>
  <div>
    <h1 style="color: black; text-align: center;">Log in</h1>
    <form @submit.prevent="userLogin">
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
      <div class="row align-content-center">
        <div class="col d-flex justify-content-center">
          <button type="submit" class="btn btn-dark btn-lg" data-bs-dismiss="modal"
                  :disabled="!usernameLogin || !passwordLogin" id="login">
            Log in
          </button>
        </div>
      </div>
    </form>
  </div>
</template>

<script>
import axios from "axios";

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
      const response = await axios.post('/auth/login', loginDto)
      if (response.data) {
        localStorage.setItem('jwt', response.data.accessToken)
        //localStorage.setItem('expiresIn', response.data.expiresIn);
        this.$root.$data.user = response.data.user
        await this.$router.push('/')
      } else
        this.$toasted.error('Wrong username or password!')
    },
  }
}
</script>

<style scoped>

</style>