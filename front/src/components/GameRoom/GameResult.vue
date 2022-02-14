<template>
  <div>
    게임끝 도둑은 {{ dodukId }}
    <div
      v-for="sub in subscribers"
      :key="sub.stream.connection.connectionId"
      class="DodukCam"
    >
      <user-video
        :stream-manager="sub"
        v-if="dodukId == getUserId(sub.stream.connection.data)"
      />
    </div>
    <div class="DodukCam">
      <user-video
        :stream-manager="publisher"
        v-if="dodukId == getUserId(publisher.stream.connection.data)"
      />
    </div>
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
  },
};
</script>

<style>
.DodukCam {
  width: 500px;
}
</style>
