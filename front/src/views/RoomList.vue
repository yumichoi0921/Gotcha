<template>
  <div>
    <div class="m-5">
      <div class="cardGroup pt-3 pb-5">
        <user></user>
        <b-button variant="danger" class="float-right mr-4"
          >makingRoom</b-button
        >
      </div>
      <room></room>
    </div>
  </div>
</template>

<script>
import room from "../components/RoomList/Room.vue";
import user from "../components/RoomList/User.vue";

import { mapActions } from "vuex";
const roomStore = "roomStore";
export default {
  name: "RoomList",
  components: {
    room,
    user,
  },
  data() {
    return {};
  },
  computed: {
    ...mapActions(roomStore, ["getRoomList"]),
  },
  created() {
    this.getRoomList();
  },
};
</script>

<style>
.cardGroup {
  background-color: rgba(255, 216, 110, 0.938);
}
</style>
