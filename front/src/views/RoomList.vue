<template>
  <div id="RoomList">
    <div id="RoomList-header" class="header py-3">
      <b-row>
        <b-col><user class="float-left ml-2"></user></b-col>
        <b-col>
          <b-button
            pill
            variant="danger"
            @click="showModal = true"
            class="float-right mr-3"
            >makingRoom</b-button
          ></b-col
        >
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
  },
};
</script>

<style>
.header {
  background-color: rgba(255, 216, 110, 0.938);
}
</style>
