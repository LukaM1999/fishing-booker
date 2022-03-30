<template>
<div>
  <nav class="navbar is-primary">
    <div class="navbar-brand">
      <a class="navbar-item">
        <img src="logo1.png" alt="logo" width="50" height="200">
      </a>
      <div class="navbar-burger" data-target="navbarExampleTransparentExample">
        <span></span>
        <span></span>
        <span></span>
      </div>
    </div>

    <div id="navbarExampleTransparentExample" class="navbar-menu">
      <div class="navbar-start">
        <a class="navbar-item" >
          Home
        </a>
        <div class="navbar-item has-dropdown is-hoverable">
          <a class="navbar-link" >
            Docs
          </a>
          <div class="navbar-dropdown is-boxed">
            <a class="navbar-item" >
              Overview
            </a>
            <a class="navbar-item" >
              Modifiers
            </a>
            <a class="navbar-item" >
              Columns
            </a>
            <a class="navbar-item" >
              Layout
            </a>
            <a class="navbar-item" >
              Form
            </a>
            <hr class="navbar-divider">
            <a class="navbar-item">
              Elements
            </a>
            <a class="navbar-item is-active">
              Components
            </a>
          </div>
        </div>
      </div>

      <div class="navbar-end">

        <button class="button"> Profile </button>
      </div>
    </div>
  </nav>
  <div>
    a <br> a <br> a <br> a <br> a <br> a <br> a <br> a <br> a <br> a <br> a <br> a <br>
  </div>
</div>
</template>

<script>
export default {
  name: "Navbar"
}
</script>

<style scoped>
.is-primary{
  background-image: linear-gradient(to right, #99A799 , #ADC2A9) !important;
}
nav{
  padding: 0px !important;
}
.navbar-item:hover{
  color: #8E806A !important;
  background-color: white !important;
  opacity: 1;
}
.navbar-link:hover{
  color: #8E806A !important;
  background-color: white !important;
  opacity: 1;
}
a.navbar-item:hover{
  color: #8E806A !important;
  background-color: white;
  opacity: 1;
}
a.navbar-link:hover{
  color: #8E806A !important;
  background-color: white;
  opacity: 1;
}
a.navbar-link:active{
  color: #8E806A !important;
  background-color: white;
  opacity: 1;
}
</style>