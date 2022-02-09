<template>
  <div v-if="streamManager">
    <div id="userCAM">
      <ov-video :stream-manager="streamManager" />
      <div>닉네임: {{ clientData.userId }}</div>
    </div>
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
