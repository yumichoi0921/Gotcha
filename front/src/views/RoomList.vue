<template>
  <div id="RoomList">
    <div
      id="title"
      class="card shadow p-3 mb-4 rounded-pill MainTitle title"
      style="color: #616264; background-color: #fff6a0"
    >
      <h1 class="Jua">Gotcha 방 대기실</h1>
    </div>
    <div id="RoomList-header" class="header py-3">
      <b-row>
        <b-col><user class="float-left ml-2"></user></b-col>
        <b-col>
          <b-row>
            <b-col></b-col>
            <b-col>
              <b-button
                class="float-right mr-3 Jua"
                pill
                variant="primary"
                @click="refresh()"
                >새로고침</b-button
              >
              <b-button
                pill
                variant="danger"
                @click="showModal = true"
                class="float-right mr-3 Jua"
                >Create Room</b-button
              ></b-col
            >
          </b-row>
        </b-col>
      </b-row>
      <create-room v-if="showModal" @close="showModal = false"> </create-room>
    </div>
    <div id="RoomList-body">
      <b-row cols="3">
        <b-col v-for="(room, index) in rooms" :key="index">
          <room :room="room"
        /></b-col>
      </b-row>
    </div>
  </div>
</template>

<script>
import User from "@/components/RoomList/User.vue";
import CreateRoom from "@/components/RoomList/CreateRoom.vue";
import Room from "@/components/RoomList/Room.vue";

import { mapGetters, mapActions } from "vuex";

const roomStore = "roomStore";

export default {
  name: "RoomList",
  components: {
    Room,
    User,
    CreateRoom,
  },
  data() {
    return {
      showModal: false,
    };
  },
  computed: {
    ...mapGetters(roomStore, ["rooms"]),
  },
  created() {
    this.getRoomList();
  },

  methods: {
    ...mapActions(roomStore, ["getRoomList"]),
    refresh() {
      this.$router.go();
    },
  },
};
</script>

<style>
.header {
  background-color: rgba(255, 216, 110, 0.938);
  width: 96.5%;
  margin: auto;
  border-radius: 10px;
}
.title {
  margin-top: 40px;
}
</style>
