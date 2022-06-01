<template>
  <div id="app" class="my-component-wrapper">
    <router-view></router-view>
  </div>
</template>

<script>

export default {
  name: 'App',
  data() {
    return {
      user: null
    }
  },
  components: {},
}
</script>

<style lang="scss">
.my-component-wrapper {
  @import "~bulma/sass/utilities/_all";

  // Set your colors
  // Braco moja vamo samo menjate boje
  $primary: #2f3e46;
  $info: #84a98c;
  $link: #84A98C;
  $box-background-color: whitesmoke;
  $footer-background-color: #CAD2C5;

  // Lists and maps
  $custom-colors: null !default;
  $custom-shades: null !default;

  // Setup $colors to use as bulma classes (e.g. 'is-twitter')
  $colors: mergeColorMaps(
          (
              "white": (
                  $white,
                  $black,
              ),
              "black": (
                  $black,
                  $white,
              ),
              "light": (
                  $light,
                  $light-invert,
              ),
              "dark": (
                  $dark,
                  $dark-invert,
              ),
              "primary": (
                  $primary,
                  $primary-invert,
                  $primary-light,
                  $primary-dark,
              ),
              "link": (
                  $link,
                  $link-invert,
                  $link-light,
                  $link-dark,
              ),
              "info": (
                  $info,
                  $info-invert,
                  $info-light,
                  $info-dark,
              ),
              "success": (
                  $success,
                  $success-invert,
                  $success-light,
                  $success-dark,
              ),
              "warning": (
                  $warning,
                  $warning-invert,
                  $warning-light,
                  $warning-dark,
              ),
              "danger": (
                  $danger,
                  $danger-invert,
                  $danger-light,
                  $danger-dark,
              ),
          ),
          $custom-colors
  );

  // Links
  $link: $primary;
  $link-invert: $primary-invert;
  $link-focus-border: $primary;

  // Import Bulma and Buefy styles
  @import "~bulma";
  @import "~buefy/src/scss/buefy";
  }
  #app {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    color: #2c3e50;
  }

  .md-layout-item {
    min-width: 15% !important;
    max-width: 18% !important;
  }
</style>
