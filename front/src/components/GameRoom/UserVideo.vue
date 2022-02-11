<template>
  <div v-if="streamManager">
    <ov-video :stream-manager="streamManager" class="rounded-top" />
    <h5 class="card-title">닉네임: {{ clientData.userId }}</h5>
  </div>
</template>

<script>
import OvVideo from "./OvVideo";

export default {
  name: "UserVideo",

  components: {
    OvVideo,
  },
  props: {
    streamManager: Object,
    userId: String,
  },
  data() {
    return {};
  },
  computed: {
    clientData() {
      const { clientData } = this.getConnectionData();
      return clientData;
    },
  },

  methods: {
    getConnectionData() {
      const { connection } = this.streamManager.stream;
      return JSON.parse(connection.data);
    },
  },
};
</script>
<style></style>
