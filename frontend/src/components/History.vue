<template>
  <div class="row mt-5 d-flex justify-content-center">
    <div class="col-8">
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
                    {{ props.row.startTime | formatDate }}
                  </b-table-column>

                  <b-table-column field="endTime" label="To" sortable v-slot="props">
                    {{ props.row.endTime | formatDate }}
                  </b-table-column>

                  <b-table-column field="guests" label="Guests" sortable numeric v-slot="props">
                    {{ props.row.guests }}
                  </b-table-column>

                  <b-table-column field="price" label="Price" sortable numeric v-slot="props">
                    {{ props.row.price }} €
                  </b-table-column>

                  <b-table-column label="Status" sortable v-slot="props">
                    {{ getStatus(props.row) }}
                  </b-table-column>

                  <b-table-column field="cancelled" label="Cancel" v-slot="props">
                    <button @click="cancelReservation(props.row)" title="Cancel" class="btn btn-danger"
                            :disabled="isCancelDisabled(props.row)"
                            v-if="!props.row.cancelled">
                      <i class="fa fa-ban fa-2x"></i>
                    </button>
                  </b-table-column>

                  <b-table-column field="reviewed" label="Review" v-slot="props">
                    <button title="Review" @click="openReviewModal(props.row)" class="btn btn-warning"
                            :disabled="props.row.reviewed || props.row.cancelled">
                      <i class="fa fa-star fa-2x"></i>
                    </button>
                  </b-table-column>

                  <b-table-column field="complaintExists" label="Complaint" v-slot="props">
                    <button title="Complaint" class="btn btn-danger" :disabled="props.row.complaintExists"
                            v-if="!props.row.complaintExists">
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
                       striped
                       >
                <b-table-column field="name" label="Name" sortable v-slot="props">
                  {{ props.row.name }}
                </b-table-column>

                <b-table-column field="customerUsername" label="Customer" sortable v-slot="props" centered>
                  <b-tooltip
                      type="is-light"
                      :triggers="['click']"
                      :auto-close="['outside', 'escape']"
                      position="is-bottom">
                    <template v-slot:content>
                      <p class="subtitle is-6">Username: <strong>{{ customerProfile.username }}</strong></p>
                      <p class="subtitle is-6">Full name: <strong>{{ customerProfile.name }} {{ customerProfile.surname }}</strong></p>
                      <p class="subtitle is-6 ">Phone: <strong>{{ customerProfile.phone }}</strong></p>
                      <p class="subtitle is-6 ">Email: <strong>{{ customerProfile.email }}</strong></p>
                      <div class="row d-flex justify-content-center text-center">
                        <div class="col">
                          <button class="btn btn-dark btn-sm" @click="openCustomReservationModal">Create new reservation</button>
                        </div>
                      </div>
                    </template>
                    <b-button :label="props.row.customerUsername" @click="getCustomerProfile(props.row)" type="is-light" />
                  </b-tooltip>
                </b-table-column>

                <b-table-column field="startTime" label="From" sortable v-slot="props">
                  {{ props.row.startTime | formatDate }}
                </b-table-column>

                <b-table-column field="endTime" label="To" sortable v-slot="props">
                  {{ props.row.endTime | formatDate }}
                </b-table-column>

                <b-table-column field="guests" label="Guests" sortable numeric v-slot="props">
                  {{ props.row.guests }}
                </b-table-column>

                <b-table-column field="price" label="Price" sortable numeric v-slot="props">
                  {{ props.row.price }} €
                </b-table-column>

                <b-table-column label="Status" sortable v-slot="props">
                  {{ getStatus(props.row) }}
                </b-table-column>

                <b-table-column field="report" label="Report" v-slot="props" centered>
                  <b-button label="Report" @click="openReportModal(props.row)" type="is-dark">
                  </b-button>
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
import CustomReservation from "@/components/CustomReservation";

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
      selected: {},
      customerProfile: {
        username: '',
        name: '',
        surname: '',
        email: '',
        phone: '',
      }
    }
  },
  async mounted() {
    if (this.role === 'CUSTOMER')
      await this.loadTab()
    else await this.getOwnerReservations()
  },
  methods: {
    async loadTab() {
      const response = await axios.post(backend + '/reservation/getReservations',
          {type: this.activeTab.slice(0, -1).toUpperCase(), username: this.username, isCustomer: true})
      if (response.data)
        this.reservations = response.data
    },

    getStatus(reservation) {
      if(reservation.cancelled) return 'Cancelled'
      const now = new Date()
      const start = new Date(reservation.startTime)
      const end = new Date(reservation.endTime)
      if (now < start)
        return 'Upcoming'
      else if (now > end)
        return 'Finished'
      else
        return 'In progress'
    },

    isCancelDisabled(reservation){
      const end = new Date(reservation.endTime)
      return new Date() >= end.addDays(3) || reservation.cancelled
    },

    async cancelReservation(reservation) {
      await axios.patch(backend + '/reservation/cancelReservation/' + reservation.id).catch(error => {
        this.$toasted.error('Cancellation unsuccessful')
        throw error
      })
      this.$toasted.success('Reservation cancelled')
      reservation.cancelled = true
    },

    async getOwnerReservations() {
      const response = await axios.post(backend + '/reservation/getReservations',
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
          rentableName: reservation.name,
          ownerUsername: reservation.ownerUsername
        },
        events: {
          'submit': () => {
            this.$toasted.success('Review submitted!');
          }
        }
      })
    },

    //function that calls the backend to get the customer profile
    async getCustomerProfile(reservation) {
      const response = await axios.get(backend + `/user/${reservation.customerUsername}`)
      if (response.data){
        this.customerProfile = {
          username: response.data.username,
          name: response.data.name,
          surname: response.data.surname,
          email: response.data.email,
          phone: response.data.phone,
        }
      }
      else{
        this.customerProfile = {}
        this.$toasted.error('Error loading customer profile!');
      }
    },

    //function that opens report modal
    openReportModal(reservation) {
      this.$buefy.modal.open({
        parent: this,
        component: ReservationReview,
        props: {
          reservation: reservation,
          isReport: true
        },
        events: {
          'submit': () => {
            this.$toasted.success('Report submitted!');
          }
        }
      })
    },

    //function that opens modal with component CustomReservation and sends customer username as props
    openCustomReservationModal() {
      this.$buefy.modal.open({
        parent: this,
        component: CustomReservation,
        props: {
          username: this.customerProfile.username
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