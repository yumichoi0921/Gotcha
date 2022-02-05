<template>
  <b-card bg-variant="light" class="col-lg-4 rounded-lg shadow row">
    <b-alert show variant="dark" class="rounded-pill">
      <div class="float-right col-5">
        <span class="col-8">{{ room.roomId }}}</span>
        <span class="col-2"><b-icon icon="lock"></b-icon></span>
        <span class="col-2">{{ room.capacity }} </span>
      </div>
    </b-alert>
    <b-button pill class="col-6 mx-auto">Enter</b-button>
  </b-card>
</template>

<script>
import { mapState } from "vuex";
const roomStore = "roomStore";
export default {
  name: "EachRow",

  props: {
    room: Object,
  },

  methods: {
    ...mapState(roomStore, ["rooms"]),
  },
};
</script>

<style></style>
