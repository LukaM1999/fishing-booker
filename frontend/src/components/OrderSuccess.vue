<template>
  <div class="order-success">
    <h1>Order Success</h1>
    <p>Thank you for your order.</p>
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
        orderId: "",
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
    const {data} = await axios.put(`${process.env.VUE_APP_BACKEND}/order/${this.$route.params.orderId}?status=SUCCESSFUL`);
    this.order = data;
  }
}
</script>

<style scoped>
.order-success {
  text-align: center;
}

.order-success h1 {
  font-size: 2em;
  font-weight: bold;
  margin-bottom: 0;
}

.order-success p {
  font-size: 1.5em;
  margin-top: 0;
}

.order-success p:first-child {
  margin-top: 1em;
}

</style>