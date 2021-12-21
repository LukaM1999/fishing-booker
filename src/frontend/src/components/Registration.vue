<template>
  <div>
    <div class="row">
      <div class="col">
      </div>
      <div class="col">
        <h1 style="color: black; text-align: center;">Register user</h1>
        <form @submit.prevent="register">
          <div class="row mb-3">
            <div class="col">
              <div class="form-floating">
                <input type="text" class="form-control" id="floatingName"
                       v-model="name" required>
                <label for="floatingName">Name*</label>
              </div>
            </div>
          </div>
          <div class="row mb-3">
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
                <input type="text" class="form-control" id="floatingEmail"
                       v-model="email" required>
                <label for="floatingEmail">Email*</label>
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
          </div>
          <div class="row mb-3">
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
          </div>
          <div class="row mb-3">
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
          </div>
          <div class="row mb-3">
            <div class="col">
              <div class="form-floating">
                <input type="password" class="form-control" id="floatingPassword"
                       v-model="password" required style="white-space: pre-line;">
                <label for="floatingPassword">Password*</label>
              </div>
            </div>
          </div>
          <div class="row mb-3">
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
                  <option selected value="COTTAGE_OWNER">Cottage owner</option>
                  <option value="BOAT_OWNER">Boat owner</option>
                  <option value="INSTRUCTOR">Fishing instructor</option>
                </select>
                <label for="floatingRole">Role*</label>
              </div>
            </div>
          </div>
          <div class="row mb-3">
            <div class="col">
              <div class="form-floating">
                <textarea class="form-control" id="letter"
                       v-model="letterOfIntent" required style="white-space: pre-line;">
                </textarea>
                <label for="letter">Please explain the intent of creating an account*</label>
              </div>
            </div>
          </div>
          <div class="row align-content-center">
            <div class="col d-flex justify-content-center">
              <button type="submit" class="btn btn-dark btn-lg">
                Register
              </button>
            </div>
          </div>
        </form>
      </div>
      <div class="col">
      </div>
    </div>
  </div>

</template>

<script>
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
      role: 1,
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
      const response = await this.axios.post('/auth/signup', user)
      console.log(response.data)
    }
  }
}
</script>

<style scoped>

</style>