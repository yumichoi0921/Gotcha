<template>
  <div>
    <div class="m-5">
      <div class="cardGroup pt-3 pb-5">
        <user></user>

        <b-button
          pill
          variant="danger"
          class="float-right mr-4"
          @click="showModal = true"
          >makingRoom</b-button
        >
        <create-room v-if="showModal" @close="showModal = false"> </create-room>
      </div>
      <b-card-group deck>
        <each-room
          v-for="(room, index) in rooms"
          :key="index"
          :room="room"
        ></each-room>
      </b-card-group>
    </div>
  </div>
</template>

<script>
//import Room from "@/components/RoomList/Room.vue";
import User from "@/components/RoomList/User.vue";
import CreateRoom from "@/components/RoomList/CreateRoom.vue";
import { mapActions, mapState } from "vuex";
import EachRoom from "../components/RoomList/EachRoom.vue";
const roomStore = "roomStore";

export default {
  name: "RoomList",
  components: {
    // Room,
    User,
    CreateRoom,
    EachRoom,
  },
  data() {
    return {
      showModal: false,
    };
  },

  created() {
    this.getRoomList();
  },
  computed: {
    ...mapState(roomStore, ["rooms"]),
  },
  methods: {
    ...mapActions(roomStore, ["getRoomList"]),
  },
};
</script>

<style>
.cardGroup {
  background-color: rgba(255, 216, 110, 0.938);
}
</style>
