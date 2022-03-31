<template>
  <form @submit.prevent="register">
    <div class="modal-card" style="width: auto">
      <header class="modal-card-head">
        <p class="modal-card-title">Register</p>
        <button
            type="button"
            class="delete"
            @click="$emit('close')"/>
      </header>
      <section class="modal-card-body">
        <div class="row mb-3">
          <div class="col">
            <div class="form-floating">
              <input type="text" class="form-control" id="floatingName"
                     v-model="name" required>
              <label for="floatingName">Name*</label>
            </div>
          </div>
          <div class="col">
            <div class="form-floating">
              <input type="text" class="form-control" id="floatingSurname"
                     v-model="surname" required>
              <label for="floatingSurname">Last name*</label>
            </div>
          </div>
        </div>
        <div class="row mb-3">
          <div class="col">
            <div class="form-floating">
              <input type="text" class="form-control" id="floatingAddress"
                     v-model="address" required>
              <label for="floatingAddress">Address*</label>
            </div>
          </div>
          <div class="col">
            <div class="form-floating">
              <input type="text" class="form-control" id="floatingCity"
                     v-model="city" required>
              <label for="floatingCity">City*</label>
            </div>
          </div>
        </div>
        <div class="row mb-3">
          <div class="col">
            <div class="form-floating">
              <input type="text" class="form-control" id="floatingCountry"
                     v-model="country" required>
              <label for="floatingCountry">Country*</label>
            </div>
          </div>
          <div class="col">
            <div class="form-floating">
              <input type="text" class="form-control" id="floatingPhone"
                     v-model="phone" required>
              <label for="floatingPhone">Phone*</label>
            </div>
          </div>
        </div>
        <div class="row mb-3">
          <div class="col">
            <div class="form-floating">
              <input type="text" class="form-control" id="floatingUsername"
                     v-model="username" required autofocus style="white-space: pre-line;">
              <label for="floatingUsername">Username*</label>
            </div>
          </div>
          <div class="col">
            <div class="form-floating">
              <input type="text" class="form-control" id="floatingEmail"
                     v-model="email" required>
              <label for="floatingEmail">Email*</label>
            </div>
          </div>
        </div>
      </section>
      <footer class="modal-card-foot">
        <div class="row align-content-center">
          <div class="col d-flex justify-content-center">
            <button type="submit" class="btn btn-dark btn-lg">
              Register
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
  name: "RegisterAdminModal",
  data() {
    return {
      username: '',
      password: 'admin',
      passwordConfirmed: 'admin',
      name: '',
      surname: '',
      email: '',
      address: '',
      city: '',
      country: '',
      phone: '',
      role: 'ADMIN',
      letterOfIntent: 'New Admin'
    }
  },
  methods: {
    async register() {
      const user = {
        username: this.username,
        password: this.password,
        name: this.name,
        surname: this.surname,
        email: this.email,
        address: this.address,
        city: this.city,
        country: this.country,
        phone: this.phone,
        role: this.role,
        letterOfIntent: this.letterOfIntent
      }
      const response = await this.axios.post(backend + '/auth/signup', user)

      if (response.data) {
        this.$toasted.success('Registration request successfully sent!')
        this.$parent.close()
      } else {
        this.$toasted.error('Error sending registration request')
      }
    }
  }

}
</script>

<style scoped>

</style>