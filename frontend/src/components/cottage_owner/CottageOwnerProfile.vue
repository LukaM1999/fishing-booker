<template>
  <form @submit.prevent="openPasswordConfirmation" class="mt-5">
    <div class="row mb-3 justify-content-center">
      <div class="col-md-3">
        <div class="form-floating">
          <input type="text" class="form-control" id="floatingProfileUsername" v-model="profile.username" readonly>
          <label for="floatingProfileUsername">Username</label>
        </div>
      </div>
      <div class="col-md-3">
        <div class="form-floating">
          <input type="text" class="form-control" id="floatingProfileEmail" v-model="profile.email" readonly>
          <label for="floatingProfileEmail">Email</label>
        </div>
      </div>
    </div>
    <div class="row mb-3 justify-content-center">
      <div class="col-md-3">
        <div class="form-floating">
          <input type="text" class="form-control" id="floatingProfileName" v-model="profile.name"
                 required
                 title="1. No numbers\n2. No special characters, except - and '"
                 style="white-space: pre-line;">
          <label for="floatingProfileName">Name*</label>
        </div>
      </div>
      <div class="col-md-3">
        <div class="form-floating">
          <input type="text" class="form-control" id="floatingProfileSurname" v-model="profile.surname"
                 required
                 title="1. No numbers\n2. No special characters, except - and '"
                 style="white-space: pre-line;">
          <label for="floatingProfileSurname">Last name*</label>
        </div>
      </div>
    </div>
    <div class="row mb-3 justify-content-center">
      <div class="col-md-3">
        <div class="form-floating">
          <input type="text" class="form-control" id="floatingAddress"
                 v-model="profile.address" required>
          <label for="floatingAddress">Address*</label>
        </div>
      </div>
      <div class="col-md-3">
        <div class="form-floating">
          <input type="text" class="form-control" id="floatingCity"
                 v-model="profile.city" required>
          <label for="floatingCity">City*</label>
        </div>
      </div>
    </div>
    <div class="row mb-3 justify-content-center">
      <div class="col-md-3">
        <div class="form-floating">
          <input type="text" class="form-control" id="floatingCountry"
                 v-model="profile.country" required>
          <label for="floatingCountry">Country*</label>
        </div>
      </div>
      <div class="col-md-3">
        <div class="form-floating">
          <input type="text" class="form-control" id="floatingPhone"
                 v-model="profile.phone" required>
          <label for="floatingPhone">Phone*</label>
        </div>
      </div>
    </div>
    <div class="row mb-3 justify-content-center">
      <div class="col-md-3">
        <div class="form-floating">
          <input type="password" class="form-control"
                 id="floatingNewPassword" v-model="profile.password">
          <label for="floatingNewPassword">New password</label>
        </div>
      </div>
    </div>
    <div class="row mb-5 justify-content-center">
      <div class="col-md d-flex justify-content-end">
        <button type="submit" class="btn btn-lg btn-primary">
          Save
        </button>
      </div>
      <div class="col-md d-flex justify-content-start">
        <button type="button" @click="openDeletionRequest" class="btn btn-lg btn-danger">
          Delete profile
        </button>
      </div>
    </div>
  </form>
</template>

<script>
import axios from "axios";
import PasswordConfirmation from "@/components/PasswordConfirmation";
import ProfileDeletion from "@/components/ProfileDeletion";
import {backend} from "@/env";

export default {
  name: "CottageOwnerProfile",
  data() {
    return {
      oldProfile: null,
      profile: Object.assign({}, JSON.parse(localStorage.getItem('user'))),
      //progress: 0,
      //progressColor: '#FF5733',
    }
  },
  mounted() {
    this.oldProfile = JSON.parse(localStorage.getItem('user'))
  },
  methods: {
    openPasswordConfirmation() {
      this.$buefy.modal.open({
        parent: this,
        component: PasswordConfirmation,
        hasModalCard: true,
        //customClass: 'custom-class custom-class-2',
        trapFocus: true,
        events: {
          'success': this.editProfile
        }
      })
    },
    openDeletionRequest(){
      this.$buefy.modal.open({
        parent: this,
        component: ProfileDeletion,
        hasModalCard: true,
        trapFocus: true,
        width: '50%'
      })
    },
    async editProfile() {
      const {authorities, ...editedProfile} = this.profile
      editedProfile.role = {roleName: editedProfile.role.authority, id: editedProfile.role.id}
      const response = await axios.put(backend + '/user/editProfile', {...editedProfile})
      if (response.data) {
        this.oldProfile = response.data
        localStorage.setItem('user', JSON.stringify(response.data))
      } else {
        console.log('Error')
      }
    }
  }
}
</script>

<style scoped>
</style>