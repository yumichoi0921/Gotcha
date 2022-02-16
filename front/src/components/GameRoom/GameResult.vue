<template>
  <div>
    <div
      class="card shadow p-3"
      style="color: #616264; background-color: #fff6a0"
    >
      <div class="row mx-auto">
        <img :src="require('../../assets/jammin.gif')" height="100" />
      </div>
      <div class="row mx-auto">
        <h2 class="Jua align-middle">도둑은 "{{ dodukId }}"쥬</h2>
      </div>
      <div class="row mx-auto mb-3">
        <b-button pill class="Jua float-right" @click="refresh"
          >세션으로 돌아가기</b-button
        >
      </div>
      <div class="row mx-auto">
        <div id="dodukCam" class="col">
          <b-alert show variant="danger" class="Jua">도둑</b-alert>
          <div class="DodukCam m-auto">
            <user-video :stream-manager="doduk" />
          </div>
        </div>

        <div id="playerCam" class="col">
          <b-alert show variant="primary" class="Jua">플레이어</b-alert>
          <div class="row row-cols-2">
            <div
              v-for="player in players"
              :key="player.stream.connection.connectionId"
              class="PlayerCam col"
            >
              <user-video :stream-manager="player" />
            </div>
          </div>
        </div>
      </div>
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
  data() {
    return {
      allUser: [],
      doduk: null,
      players: [],
    };
  },
  created() {
    for (let i = 0; i < this.subscribers.length; i++) {
      this.allUser.push(this.subscribers[i]);
    }
    this.allUser.push(this.publisher);
    for (let i = 0; i < this.allUser.length; i++) {
      if (
        this.dodukId == this.getUserId(this.allUser[i].stream.connection.data)
      ) {
        this.doduk = this.allUser[i];
      } else {
        this.players.push(this.allUser[i]);
      }
    }
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
  width: 90%;
}
.PlayerCam {
  width: 60%;
}
</style>
