<template>
  <div class="order-fail">
    <h1>Order Failed</h1>
    <p>Order ID: {{ order.orderId }}</p>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "OrderFail",
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
    const {data} = await axios.put(`${process.env.VUE_APP_BACKEND}/order/${this.$route.params.orderId}?status=FAILED`, {}, {});
    this.order = data;
  }
}
</script>

<style scoped>
.order-fail {
  text-align: center;
}

.order-fail h1 {
  font-size: 2em;
  font-weight: bold;
  margin-bottom: 0;
}

.order-fail p {
  font-size: 1.5em;
  margin-top: 0;
}

</style>