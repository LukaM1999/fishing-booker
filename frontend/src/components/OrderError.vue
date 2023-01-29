<template>
  <div class="order-error">
    <h1>Order Error</h1>
    <p>Order ID: {{ order.orderId }}</p>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "OrderSuccess",
  data() {
    return {
      order: {
        orderId: '',
        type: '',
        name: '',
        ownerUsername: '',
        customerUsername: '',
        startTime: '',
        endTime: '',
        additionalServices: [],
        guests: 0,
        price: 0.0,
      }
    };
  },
  async mounted() {
    const {data} = await axios.put(`${process.env.VUE_APP_BACKEND}/order/${this.$route.params.orderId}?status=ERROR`);
    this.order = data;
  }
}
</script>

<style scoped>
.order-error {
  text-align: center;
}

.order-error h1 {
  font-size: 2em;
  font-weight: bold;
  margin-bottom: 0;
}

.order-error p {
  font-size: 1.5em;
  margin-top: 0;
}

</style>