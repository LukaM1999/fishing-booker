<template>
  <form @submit.prevent="register">
    <div class="modal-card" style="width: auto">
      <header class="modal-card-head">
        <p class="modal-card-title">Register an Adventure</p>
        <button
            type="button"
            class="delete"
            @click="$emit('close')"/>
      </header>
      <section class="modal-card-body">
        <div class="row mb-3">
          <div class="col">
            <div class="form-floating">
              <input type="text" class="form-control" autofocus id="floatingName"
                     v-model="name" required>
              <label for="floatingName">Name*</label>
            </div>
          </div>
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
              <input type="number" class="form-control" id="floatingCapacity"
                     v-model="capacity" required style="white-space: pre-line;">
              <label for="floatingCapacity">Capacity*</label>
            </div>
          </div>
          <div class="col">
            <div class="form-floating">
              <input type="number" step="0.1" class="form-control" id="floatingCancellation"
                     v-model="cancellationFee" required style="white-space: pre-line;">
              <label for="floatingCancellation">Cancellation Fee [%]*</label>
            </div>
          </div>
        </div>

        <div class="row mb-3">
          <div class="col">
            <div class="form-floating">
                <textarea class="form-control" id="floatingBiography"
                          v-model="biography" required style="white-space: pre-line;">
                </textarea>
              <label for="floatingBiography">Add your biography*</label>
            </div>
          </div>
        </div>
        <div class="row mb-3">
          <div class="col">
            <div class="form-floating">
                <textarea class="form-control" id="floatingRules"
                          v-model="rules" required style="white-space: pre-line;">
                </textarea>
              <label for="floatingRules">Please declare rules that your guest should follow during the visit.</label>
            </div>
          </div>
        </div>

        <div class="row mb-3">
          <div class="col">
            <div class="form-floating">
              <input type="number" step="0.1" class="form-control" id="floatingPrice"
                     v-model="price" required style="white-space: pre-line;">
              <label for="floatingPrice">Price per person/hour [$]</label>
            </div>
          </div>
          <div class="col">
            <div class="form-floating">
                <textarea class="form-control" id="floatingAdditionalServices"
                          v-model="additionalServices" required style="white-space: pre-line;">
                </textarea>
              <label for="floatingAdditionalServices">Please declare additional services.</label>
            </div>
          </div>
        </div>

        <div class="row mb-3">
          <div class="col">
            <div class="form-floating">
                <textarea class="form-control" id="floatingDescription"
                          v-model="promoDescription" required style="white-space: pre-line;">
                </textarea>
              <label for="floatingDescription">Please write a promotional description about the adventure.</label>
            </div>
          </div>
          <div class="col">
            <div class="form-floating">
                <textarea class="form-control" id="fishingEquipment"
                          v-model="fishingEquipment" required style="white-space: pre-line;">
                </textarea>
              <label for="fishingEquipment">Please define available fishing equipment.</label>
            </div>
          </div>
        </div>

        <div class="row mb-3">
          <div class="col d-flex justify-content-start">
            <b-field>
              <b-upload v-model="dropFiles"
                        name="images"
                        multiple
                        drag-drop
                        accept="image/*">
                <section class="section">
                  <div class="content has-text-centered">
                    <p>
                      <b-icon
                          icon="upload"
                          size="is-large">
                      </b-icon>
                    </p>
                    <p>Add images here or click to upload.</p>
                  </div>
                </section>
              </b-upload>
            </b-field>
          </div>
          <div class="col">
            <div class="tags">
              <span v-for="(file, index) in dropFiles"
                    :key="index"
                    class="tag is-primary">
                  {{ file.name }}
                  <button class="delete is-small"
                          type="button"
                          @click="deleteDropFile(index)">
                  </button>
              </span>
            </div>
          </div>
        </div>
      </section>
      <footer class="modal-card-foot buttons is-centered">
        <div class="row align-content-center">
          <div class="col justify-content-center">
            <button type="submit" class="btn btn-dark btn-lg">
              Finish
            </button>
          </div>
        </div>
      </footer>
    </div>
  </form>
</template>

<script>
import {backend} from "@/env";

export default {
  name: "adventureRegistration",
  data(){
    return{
      user : null,
      name: '',
      address: '',
      city: '',
      country: '',
      capacity: 1,
      biography: '',
      cancellationFee: 0,
      rules: '',
      price: 1,
      additionalServices: '',
      promoDescription: '',
      fishingEquipment: '',
      dropFiles: []
    }
  },
  mounted() {
    this.user = JSON.stringify(localStorage.getItem('user'))
  },
  methods: {
    async register(){
      const adventure = {
        name: this.name,
        country: this.country,
        city: this.city,
        address: this.address,
        promoDescription: this.promoDescription,
        capacity: this.capacity,
        rules: this.rules,
        price: this.price,
        additionalServices: this.additionalServices,
        cancellationFee: this.cancellationFee,
        instructorBio: this.biography,
        fishingEquipment: this.fishingEquipment,
        images:'',
        ownerUsername: JSON.parse(localStorage.getItem('user')).username
      }
      const formData = new FormData()
      for (let file of this.dropFiles) {
        formData.append('files', file)
      }
      formData.append('adventure', new Blob([JSON.stringify(adventure)], {type: 'application/json'}))

      const response = await this.axios.post(backend + '/adventure/register', formData, {headers: {"Content-Type": "multipart/form-data"}})
      if (response) {
        this.$toasted.success('Adventure successfully registered!')
      } else {
        this.$toasted.error('Error while registering adventure.')
      }
      this.$parent.close()
      this.$emit('added', response.data)
    },
    deleteDropFile(index) {
      this.dropFiles.splice(index, 1)
    },
  }
}
</script>

<style scoped>

</style>