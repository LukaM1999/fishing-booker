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
        <div class="row mb-3">
          <div class="col">
            <div class="form-floating">
              <input type="password" class="form-control" id="floatingPassword"
                     v-model="password" required style="white-space: pre-line;">
              <label for="floatingPassword">Password*</label>
            </div>
          </div>
          <div class="col">
            <div class="form-floating">
              <input type="password" class="form-control" id="floatingPasswordConfirm"
                     v-model="passwordConfirmed" required style="white-space: pre-line;">
              <label for="floatingPasswordConfirm">Confirm Password*</label>
            </div>
          </div>
        </div>
        <div class="row mb-3">
          <div class="col">
            <div class="form-floating">
              <select class="form-select" id="floatingRole" v-model="role" required>
                <option selected value="CUSTOMER">Customer</option>
                <option value="COTTAGE_OWNER">Cottage owner</option>
                <option value="BOAT_OWNER">Boat owner</option>
                <option value="INSTRUCTOR">Fishing instructor</option>
              </select>
              <label for="floatingRole">Role*</label>
            </div>
          </div>
        </div>
        <div v-if="this.role !== 'CUSTOMER'" class="row mb-3">
          <div class="col">
            <div class="form-floating">
                <textarea class="form-control" id="letter"
                          v-model="letterOfIntent" required style="white-space: pre-line;">
                </textarea>
              <label for="letter">Please explain the intent of creating an account*</label>
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
  name: "Registration",
  data() {
    return {
      username: '',
      password: '',
      passwordConfirmed: '',
      name: '',
      surname: '',
      email: '',
      address: '',
      city: '',
      country: '',
      phone: '',
      role: 'CUSTOMER',
      letterOfIntent: ''
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

      /*
      const userCopy = JSON.parse(JSON.stringify(user));
      userCopy.name = "someName"

      await axios.all([
        axios.post(backend + '/auth/signup', user),
        axios.post(backend + '/auth/signup', userCopy)
      ])
       */

      const response = await this.axios.post(backend + '/auth/signup', user)
      if (response.data) {
        if (response.data.role.authority === 'CUSTOMER') {
          const email = {
            service_id: 'service_fb',
            template_id: 'template_verification',
            user_id: 'user_62WYz6KIasgbXlUB5EEGw',
            template_params: {
              'to_name': response.data.name,
              'to_email': response.data.email,
              'verification_link': `<a href="http://localhost:8090/auth/verify?token=${response.data.verificationToken}">here</a>`
            }
          };
          const emailResponse = await axios.post('https://api.emailjs.com/api/v1.0/email/send', email);
          if (emailResponse) {
            this.$toasted.success('Verification email sent!')
          } else {
            this.$toasted.error('Error sending verification email')
          }
        } else {
          this.$toasted.success('Registration request successfully sent!')
        }
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