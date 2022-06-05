<template>
  <div class="row mt-5 d-flex justify-content-center">
    <div class="col-3">
      <div class="row">
        <div class="col">
          <h1 class="text-center title">Sale subscriptions</h1>
        </div>
      </div>
      <div class="row mt-5">
        <div class="col">
          <b-table class="mt-4 mb-4"
                   :data="subscriptions"
                   hoverable
                   striped>
            <b-table-column field="entityName" label="Name" sortable v-slot="props">
              {{ props.row.entityName }}
            </b-table-column>

            <b-table-column field="ownerUsername" label="Owner" sortable v-slot="props">
              {{ props.row.ownerUsername }}
            </b-table-column>

            <b-table-column label="Unsubscribe" sortable v-slot="props">
              <button class="button is-danger is-small" @click="unsubscribe(props.row)">Unsubscribe</button>
            </b-table-column>
          </b-table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import {backend} from "@/env";

export default {
  name: "SaleSubscriptions",
  data () {
    return {
      username: JSON.parse(localStorage.getItem('user') || '{}')?.username,
      subscriptions: []
    }
  },
  async mounted() {
    const response = await axios.get(backend + '/saleSubscription/customer/' + this.username)
    if (response.data)
      this.subscriptions = response.data
  },
  methods: {
    async unsubscribe(subscription) {
      await axios.delete(`${backend}/saleSubscription`, {params: {entityName: subscription.entityName
          ,ownerUsername: subscription.ownerUsername, customerUsername: subscription.customerUsername}}).catch(e => {
        this.$toasted.error('Error occurred while unsubscribing')
        throw e
      })
      this.$toasted.success('Unsubscribed successfully')
      this.subscriptions = this.subscriptions.filter(s => s.id !== subscription.id)
    }
  },
}
</script>

<style scoped>

</style>