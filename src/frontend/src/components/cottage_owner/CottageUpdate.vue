<template>
  <form @submit.prevent="update">
    <div class="modal-card" style="width: auto">
      <header class="modal-card-head">
        <p class="modal-card-title">Update a Cottage</p>
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
                     v-model="capacity" required autofocus style="white-space: pre-line;">
              <label for="floatingCapacity">Capacity*</label>
            </div>
          </div>
          <div class="col">
            <div class="form-floating">
              <input type="number" class="form-control" id="floatingRooms"
                     v-model="rooms" required>
              <label for="floatingRooms">Rooms*</label>
            </div>
          </div>
          <div class="col">
            <div class="form-floating">
              <input type="number" class="form-control" id="floatingBedsPerRoom"
                     v-model="bedsPerRoom" required autofocus style="white-space: pre-line;">
              <label for="floatingBedsPerRoom">Beds per Room*</label>
            </div>
          </div>
          <div class="col">
            <div class="form-floating">
              <input type="number" step="0.1" class="form-control" id="floatingCancellation"
                     v-model="cancellationFee" required autofocus style="white-space: pre-line;">
              <label for="floatingCancellation">Cancellation Fee [%]*</label>
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
              <label for="floatingPrice">Price per person/day [$]</label>
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
              <label for="floatingDescription">Please write a promotional description about the property.</label>
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
                    <p>Add more images here or click to upload.</p>
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
      <footer class="modal-card-foot">
        <div class="row align-content-center">
          <div class="col d-flex justify-content-center">
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
export default {
  name: "CottageUpdate",
  props: {cottage : Object},
  data() {
    return {
      id: this.cottage.id,
      name: this.cottage.name,
      address: this.cottage.address,
      city: this.cottage.city,
      country: this.cottage.country,
      capacity: this.cottage.capacity,
      rooms: this.cottage.rooms,
      bedsPerRoom: this.cottage.bedsPerRoom,
      cancellationFee: this.cottage.cancellationFee,
      rules: this.cottage.rules,
      price: this.cottage.price,
      additionalServices: this.cottage.additionalServices,
      promoDescription: this.cottage.promoDescription,
      dropFiles: []
    }
  },
  methods: {
    async update() {
      const cottage = {
        id: this.id,
        name: this.name,
        address: this.address,
        city: this.city,
        country: this.country,
        capacity: this.capacity,
        rooms: this.rooms,
        bedsPerRoom: this.bedsPerRoom,
        cancellationFee: this.cancellationFee,
        rules: this.rules,
        price: this.price,
        additionalServices: this.additionalServices,
        promoDescription: this.promoDescription,
        ownerUsername: JSON.parse(localStorage.getItem('user')).username
      }
      const formData = new FormData()
      for (let file of this.dropFiles) {
        formData.append('files', file)
      }
      alert(cottage.ownerUsername)
      formData.append('cottage', new Blob([JSON.stringify(cottage)], {type: 'application/json'}))

      const response = await this.axios.post('/cottage/register', formData, {headers: {"Content-Type": "multipart/form-data"}})
      if (response) {
        this.$toasted.success('Cottage successfully registered!')
      } else {
        this.$toasted.error('Error while registering cottage.')
      }
      this.$parent.close()
    },

    deleteDropFile(index) {
      this.dropFiles.splice(index, 1)
    }
  }
}
</script>

<style scoped>

</style>