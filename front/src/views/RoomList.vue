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

        <!-- <b-modal v-model="showModal"><create-room /></b-modal> -->

        <create-room-v2 v-if="showModal" @close="showModal = false">
        </create-room-v2>
      </div>
      <room></room>
    </div>
  </div>
</template>

<script>
import Room from "@/components/RoomList/Room.vue";
import User from "@/components/RoomList/User.vue";
import CreateRoom from "@/components/RoomList/CreateRoom.vue";
import CreateRoomV2 from "@/components/RoomList/CreateRoomV2.vue";
export default {
  name: "RoomList",
  components: {
    Room,
    User,
    // eslint-disable-next-line vue/no-unused-components
    CreateRoom,
    // eslint-disable-next-line vue/no-unused-components
    CreateRoomV2,
  },
  data() {
    return {
      showModal: false,
    };
  },
};
</script>

<style>
.cardGroup {
  background-color: rgba(255, 216, 110, 0.938);
}
</style>
