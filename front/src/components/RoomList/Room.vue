<template>
  <b-card bg-variant="light" class="rounded-lg shadow m-1">
    <b-alert show variant="dark" class="rounded-pill">
      <div class="row">
        <span class="col-6">{{ roomTitle }}</span>
        <span class="col-2"
          >{{ isPrivate }}<b-icon icon="lock" v-if="isPrivate"></b-icon>
          <b-icon icon="unlock" v-else></b-icon
        ></span>
        <span class="col-4">{{ participant }} / {{ capacity }}</span>
      </div>
    </b-alert>
    <b-button pill class="col-6 mx-auto">Enter</b-button>
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
};
</script>

<style></style>
