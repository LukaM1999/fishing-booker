<template>
  <div>
    <div class="row">
      <div class="col"></div>
      <div class="col-8">
        <b-table
            :data="users"
            :striped="true"
            :hoverable="true"
            :mobile-cards="true"
            ref="table"
            paginated
            per-page="15"
            detail-key="username"
            aria-next-label="Next page"
            aria-previous-label="Previous page"
            aria-page-label="Page"
            aria-current-label="Current page">

          <b-table-column field="username" label="Username" centered v-slot="props">
            {{ props.row.username }}
          </b-table-column>

          <b-table-column field="first_name" label="First Name" centered v-slot="props">
            {{ props.row.name }}
          </b-table-column>

          <b-table-column field="last_name" label="Last Name" centered v-slot="props">
            {{ props.row.surname }}
          </b-table-column>

          <b-table-column field="date" label="Email" centered v-slot="props">
            {{ props.row.email }}
          </b-table-column>

          <b-table-column label="Role" centered v-slot="props">
            {{ props.row.role.authority }}
          </b-table-column>

          <b-table-column label="Manage account" centered v-slot="props">
            <div class="d-flex justify-content-center" style="display:inline;">
              <button class="btn pagination-link is-current" @click="deleteUser(props.row)">
                Delete
              </button>
            </div>
          </b-table-column>

          <template #empty>
            <div class="has-text-centered">No records</div>
          </template>
        </b-table>
      </div>
      <div class="col"></div>
    </div>
  </div>
</template>

<script>
import {backend} from "@/env";

export default {
  name: "Users",
  data() {
    return {
      users: [],
      filteredUsers: []
    }
  },
  async mounted() {
    await this.axios
        .get(backend + '/user/all')
        .then(response => {
          this.users = response.data.filter(user => user.role.authority !== "ADMIN");
        });
  },
  methods: {
    deleteUser(user) {
      this.axios
          .delete(backend + `/user/delete/${user.username}`)
          .then(response => {
            this.$toasted.success('User deleted successfully!')
            this.users = this.users.filter(u => u.username !== user.username)
          }).catch(error => {
            this.$toasted.error('Error while deleting user!')
          });
    },
  }
}
</script>

<style scoped>

</style>