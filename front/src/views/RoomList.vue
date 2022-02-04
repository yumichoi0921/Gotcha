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
    </div>
    <b-container class="">
      <b-row cols="3">
        <b-col v-for="(room, index) in rooms" :key="index" v-bind="room">
          <room v-bind="room"
        /></b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import Room from "@/components/RoomList/Room.vue";
import User from "@/components/RoomList/User.vue";
import CreateRoom from "@/components/RoomList/CreateRoom.vue";
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
    console.log(this.rooms);
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
