<template>
  <div v-if="streamManager">
    <ov-video
      :stream-manager="streamManager"
      :picked="picked"
      :userId="userId"
      @maxEmotion="maxEmotion"
      class="rounded-top"
    />
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
  // emits:['maxEmotion']
  props: {
    streamManager: Object,
    userId: String,
    picked: String,
  },
  data() {
    return {
      maxEmo: "",
    };
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
  watch: {
    maxEmotion() {
      console.log("maxEmotion:" + this.maxEmotion);
      this.maxEmo = this.maxEmotion;
    },
  },
};
</script>
<style></style>
