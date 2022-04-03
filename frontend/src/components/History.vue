<template>
  <div class="row mt-5">
    <div class="col">
      <div class="row" v-if="role === 'CUSTOMER'">
        <div class="col">
          <h1 class="text-center">Reservation history</h1>
        </div>
      </div>
      <b-tabs v-if="role === 'CUSTOMER'" v-model="activeTab" @input="loadTab">
        <template v-for="tab in tabs">
          <b-tab-item
              :key="tab.id"
              :value="tab.id"
              :label="tab.label">
            <div class="row mt-5">
              <div class="col">
                <b-table class="mt-4 mb-4"
                         :data="reservations"
                         hoverable
                         striped>
                  <b-table-column field="name" label="Name" sortable v-slot="props">
                    {{ props.row.name }}
                  </b-table-column>

                  <b-table-column field="ownerUsername" label="Owner" sortable v-slot="props">
                    {{ props.row.ownerUsername }}
                  </b-table-column>

                  <b-table-column field="startTime" label="From" sortable v-slot="props">
                    {{ props.row.startTime }}
                  </b-table-column>

                  <b-table-column field="endTime" label="To" sortable v-slot="props">
                    {{ props.row.endTime }}
                  </b-table-column>

                  <b-table-column field="guests" label="Guests" sortable numeric v-slot="props">
                    {{ props.row.guests }}
                  </b-table-column>

                  <b-table-column field="price" label="Price" sortable numeric v-slot="props">
                    {{ props.row.price }}
                  </b-table-column>

                  <b-table-column field="reviewed" label="Review" v-slot="props">
                    <button title="Review" @click="openReviewModal(props.row)" class="btn btn-warning"
                            :disabled="props.row.reviewed || props.row.cancelled">
                      <i class="fa fa-star fa-2x"></i>
                    </button>
                  </b-table-column>

                  <b-table-column field="complaintExists" label="Complaint" v-slot="props">
                    <button title="Complaint" class="btn btn-danger" :disabled="props.row.complaintExists" v-if="!props.row.complaintExists">
                      <i class="fa fa-exclamation-circle fa-2x"></i>
                    </button>
                  </b-table-column>

                </b-table>
              </div>
            </div>
          </b-tab-item>
        </template>
      </b-tabs>
      <div v-if="role !== 'CUSTOMER'" class="row">
        <div class="col">
          <div class="row">
            <div class="col">
              <h1 v-if="role === 'COTTAGE_OWNER'" class="text-center">Cottage reservation history</h1>
              <h1 v-if="role === 'BOAT_OWNER'" class="text-center">Boat reservation history</h1>
              <h1 v-if="role === 'INSTRUCTOR'" class="text-center">Adventure reservation history</h1>
            </div>
          </div>
          <div class="row">
            <div class="col">
              <b-table class="mt-4 mb-4"
                       :data="reservations"
                       hoverable
                       striped>
                <b-table-column field="name" label="Name" sortable v-slot="props">
                  {{ props.row.name }}
                </b-table-column>

                <b-table-column field="customerUsername" label="Customer" sortable v-slot="props">
                  {{ props.row.customerUsername }}
                </b-table-column>

                <b-table-column field="startTime" label="From" sortable v-slot="props">
                  {{ props.row.startTime }}
                </b-table-column>

                <b-table-column field="endTime" label="To" sortable v-slot="props">
                  {{ props.row.endTime }}
                </b-table-column>

                <b-table-column field="guests" label="Guests" sortable numeric v-slot="props">
                  {{ props.row.guests }}
                </b-table-column>

                <b-table-column field="price" label="Price" sortable numeric v-slot="props">
                  {{ props.row.price }}
                </b-table-column>

              </b-table>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import {backend} from "@/env";
import ReservationReview from "@/components/customer/ReservationReview";

export default {
  name: "History",
  data() {
    return {
      username: JSON.parse(localStorage.getItem('user') || '{}')?.username,
      role: JSON.parse(localStorage.getItem('user') || '{}')?.role?.authority,
      activeTab: 'cottages',
      reservations: [],
      modalRating: false,
      rating: 0,
    }
  },
  async mounted() {
    if(this.role === 'CUSTOMER')
      await this.loadTab()
    else await this.getOwnerReservations()
  },
  methods: {
    async loadTab(){
      const response = await axios.post(backend + '/reservation/getFinishedReservations',
          {type: this.activeTab.slice(0, -1).toUpperCase(), username: this.username, isCustomer: true})
      if(response.data)
        this.reservations = response.data
    },

    async getOwnerReservations(){
      const response = await axios.post(backend + '/reservation/getFinishedReservations',
          {type: null, username: this.username, isCustomer: false})
      if (response.data)
        this.reservations = response.data
    },

    // Function to open buefy reservation review modal with ReservationReview component as parent
    // and pass the reservation to it, with handlers for close and submit
    openReviewModal(reservation) {
      this.$buefy.modal.open({
        parent: this,
        component: ReservationReview,
        props: {
          reservation: reservation,
        },
        events: {
          'submit': () => {
            this.$toasted.success('Review submitted!');
          }
        }
      })
    },


  },
  filters: {
    addressFormat(value) {
      return value.address + ", " + value.city + ", " + value.country
    },
  },
  computed: {
    tabs() {
      return [
        {
          id: 'cottages',
          label: 'Cottages',
        },
        {
          id: 'boats',
          label: 'Boats',
        },
        {
          id: 'adventures',
          label: 'Adventures',
        }
      ]
    },
  }
}
</script>

<style scoped>
.fa-star {
  color: white;
}
</style>