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
import Room from "@/components/RoomList/Room.vue";
import User from "@/components/RoomList/User.vue";
export default {
  name: "RoomList",
  components: {
    Room,
    User,
  },
  data() {
    return {};
  },
};
</script>

<style>
.cardGroup {
  background-color: rgba(255, 216, 110, 0.938);
}
</style>
