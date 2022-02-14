<template>
  <div>
    <div
      class="card shadow p-3 mb-4 rounded-pill MainTitle"
      style="color: #616264; background-color: #fff6a0"
    >
      <img :src="require('../../assets/jammin.gif')" height="100" />
      <h2 class="Jua">도둑은 {{ dodukId }}이쥬</h2>
    </div>

    <div
      v-for="sub in subscribers"
      :key="sub.stream.connection.connectionId"
      class="DodukCam m-auto"
    >
      <user-video
        :stream-manager="sub"
        v-if="dodukId == getUserId(sub.stream.connection.data)"
      />
    </div>
    <div class="DodukCam m-auto">
      <user-video
        :stream-manager="publisher"
        v-if="dodukId == getUserId(publisher.stream.connection.data)"
      />
    </div>
    <b-button @click="refresh">세션으로 돌아가기</b-button>
  </div>
</template>

<script>
import UserVideo from "@/components/GameRoom/UserVideo.vue";
export default {
  name: "GameResult",
  components: {
    UserVideo,
  },
  props: {
    dodukId: String,
    publisher: Object,
    subscribers: Array,
  },
  methods: {
    getUserId(data) {
      let clientData = JSON.parse(data);
      return clientData.clientData.userId;
    },
    refresh() {
      this.$router.go();
    },
  },
};
</script>

<style>
.DodukCam {
  width: 500px;
}
</style>
