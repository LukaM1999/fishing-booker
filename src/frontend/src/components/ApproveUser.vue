<template>
  <div>
    <div class="row">
      <div class="col"></div>
      <div class="col-8">
        <table class="table table-bordered table-hover">
          <thead>
            <tr class="text-center">
              <th>Username</th>
              <th>Name</th>
              <th>Surname</th>
              <th>Email</th>
              <th>Role</th>
              <th>Letter of intent</th>
              <th>Manage account</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="u in users" :key="u.username">
              <td>{{ u.username }}</td>
              <td>{{ u.name }}</td>
              <td>{{ u.surname }}</td>
              <td>{{ u.email }}</td>
              <td>{{ u.role}}</td>
              <td>{{ u.letterOfIntent }}</td>
              <td>
                <div class="d-flex justify-content-center" style="display:inline;">
                  <button v-if="u.enabled !== true"  class="btn btn-warning" style="margin-right: 5%;"
                          @click="approve(u)">Approve
                  </button>
                  <button v-if="u.enabled !== true"  class="btn btn-warning" style="margin-right: 5%;"
                          @click="approve(u)">Approve
                  </button>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <div class="col"></div>
    </div>
  </div>

</template>

<script>
export default {
  name: "ApproveUser",
  data() {
    return {
      users: [],
      filteredUsers: []
    }
  },
  async mounted() {
    this.axios
        .get('/user/all')
        .then(response => {
          this.users = response.data
        });
    //this.users = this.users.filter(u => u.role !== "CUSTOMER" && u.enabled === false)
  },
  methods: {
    async approve() {

      /* const user = {
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
       const response = await this.axios.post('http://localhost:8090/registration/registerUser', user)
       console.log(response.data)*/
    }
  }
}
</script>

<style scoped>

</style>