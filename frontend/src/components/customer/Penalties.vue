<template>
  <div class="row mt-5 d-flex justify-content-center">
    <div class="col-3">
      <div class="row">
        <div class="col">
          <h1 class="text-center title">Penalties</h1>
        </div>
      </div>
        <div class="row mt-5">
          <div class="col">
            <b-table class="mt-4 mb-4"
                     :data="penalties"
                     hoverable
                     striped>
              <b-table-column field="issuedAt" label="Issued at" date sortable v-slot="props">
                {{ new Date(props.row.issuedAt) | formatDate }}
              </b-table-column>
              <template #footer>
                <b>Total</b>: {{ penalties.length }}
              </template>
            </b-table>
          </div>
        </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import {backend} from "@/env";
import moment from "moment";

export default {
  name: "Penalties",
  data () {
    return {
      username: JSON.parse(localStorage.getItem('user') || '{}')?.username,
      penalties: []
    }
  },
  async mounted() {
    const response = await axios.get(backend + '/penalty/' + this.username)
    if (response.data)
      this.penalties = response.data
  },
  methods: {
  },
}
</script>

<style scoped>

</style>