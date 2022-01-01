<template>
  <div>
    <section class="hero is-primary is-medium">
      <!-- Hero head: will stick at the top -->
      <div class="hero-head">
        <nav class="navbar">
          <div class="container">
            <div class="navbar-brand">
              <a class="navbar-item">
                <img
                    src="assets/logo_white.png"
                    alt="Logo"
                />
              </a>
            </div>
          </div>
        </nav>
      </div>

      <!-- Hero content: will be in the middle -->
      <div class="hero-body">
        <div class="container has-text-centered">
          <p class="title is-size-1">Cura Infinita</p>
          <p class="subtitle">We are glad you are here</p>
          <button class="button is-info mr-5" onclick="window.location.href='/patientLogin'">Log in
          </button>

          <button class="button is-info" onclick="window.location.href='/patientRegistraion'">Register
          </button>
        </div>
      </div>

      <!-- Hero footer: will stick at the bottom -->
      <div class="hero-foot">
        <nav class="tabs">
          <div class="container">
            <ul>
              <li><a class="" href="">Home</a></li>
              <li><a class="" href="/record">Patient Record</a></li>
              <li><a class="" href="/standardAppointment">Standard appointment</a></li>
              <li><a class="" href="/priorityBasedAppointment">Advanced appointment</a></li>
              <li><a class="" href="/feedback">Leave Feedback</a></li>
            </ul>
          </div>
        </nav>
      </div>
    </section>
  </div>
</template>

<script>
export default {
  name: "LandingPage"
}
</script>

<style scoped>

</style>