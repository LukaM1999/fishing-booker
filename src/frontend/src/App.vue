<template>
  <div id="app">
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

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

.my-label {
  display: inline-block !important;
  position: absolute !important;
  top: 0 !important;
  left: 0 !important;
  height: 100% !important;
  padding: 1rem 0.75rem !important;
  pointer-events: none !important;
  border: 1px solid transparent !important;
  transform-origin: 0 0 !important;
  transition: opacity .1s ease-in-out, transform .1s ease-in-out !important;
  opacity: .65;
  transform: scale(.85) translateY(-0.5rem) translateX(0.15rem);
  font-weight: 400 !important;
  font-size: 14px !important;
}

.md-layout-item {
  min-width: 15% !important;
  max-width: 18% !important;
}
</style>
