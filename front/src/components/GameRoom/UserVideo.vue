<template>
  <div v-if="streamManager">
    <div class="card my-2" style="width: 270px">
      <ov-video :stream-manager="streamManager" class="mx-auto mt-2" />
      <div class="card-body">
        <div class="card-title alert alert-dark" role="alert">
          닉네임: {{ clientData.userId }}
        </div>
        <div
          class="card-text alert alert-dark"
          role="alert"
          v-if="clientData.userId == userId"
        >
          카드: {{ card }}
        </div>
        <div class="card-text alert alert-dark" role="alert" v-else>
          <b-icon icon="file-fill" scale="1"></b-icon> x
          {{ cardLength }}
        </div>
      </div>
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
    myCard: Array,
  },
  data() {
    return {
      isHovered: false,
      card: null,
      cardLength: null,
    };
  },
  computed: {
    clientData() {
      const { clientData } = this.getConnectionData();
      return clientData;
    },
  },
  watch: {
    myCard() {
      this.card = this.myCard;
      this.cardLength = this.myCard.length;
    },
  },

  methods: {
    getConnectionData() {
      const { connection } = this.streamManager.stream;
      return JSON.parse(connection.data);
    },
    handleHover(hovered) {
      this.isHovered = hovered;
    },
  },
};
</script>
