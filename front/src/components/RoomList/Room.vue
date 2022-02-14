<template>
  <div id="Room">
    <b-card bg-variant="light" class="rounded-lg shadow m-4">
      <div id="Room-body">
        <b-alert show variant="dark" class="rounded-pill">
          <b-row
            ><b-col class="Jua">{{ room.roomTitle }}</b-col>
            <b-col cols="1" class="Jua"
              ><b-icon icon="lock" v-if="room.isPrivate"></b-icon>
              <b-icon icon="unlock" v-else></b-icon
            ></b-col>
            <b-col cols="4" class="Jua"
              >{{ room.participant }} / {{ room.capacity }}</b-col
            ></b-row
          >
        </b-alert>
      </div>
      <div id="Room-footer">
        <router-link
          :to="{
            name: 'GameRoom',
            params: { roomId: room.roomId },
          }"
          ><b-button pill class="col-6 mx-auto Jua"
            >Enter</b-button
          ></router-link
        >
      </div>
    </b-card>
  </div>
</template>
<script>
export default {
  name: "Room",
  props: {
    room: Object,
  },
  data() {
    return {};
  },
};
</script>
<style></style>
