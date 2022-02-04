<template>
  <b-card bg-variant="light" class="rounded-lg shadow mx-3 my-4">
    <b-alert show variant="dark" class="rounded-pill">
      <div class="row">
        <span class="col-6">{{ roomTitle }}</span>
        <span class="col-2"
          ><b-icon icon="lock" v-if="isPrivate"></b-icon>
          <b-icon icon="unlock" v-else></b-icon
        ></span>
        <span class="col-4">{{ participant }} / {{ capacity }}</span>
      </div>
    </b-alert>

    <router-link :to="{ name: 'WaitingRoom', params: { roomId: roomId } }"
      ><b-button pill class="col-6 mx-auto">Enter</b-button></router-link
    >
  </b-card>
</template>
<script>
export default {
  name: "Room",
  props: {
    roomTitle: String,
    isPrivate: Boolean,
    participant: Number,
    capacity: Number,
  },
  data() {
    return {
      roomId: "695d2660-0168-4372-9525-4dba334d5bbe",
    };
  },
};
</script>
<style></style>
