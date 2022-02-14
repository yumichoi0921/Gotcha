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
          v-if="!room.isFull && !room.isPrivate"
          :to="{
            name: 'GameRoom',
            params: { roomId: room.roomId },
          }"
          ><b-button pill class="col-6 mx-auto Jua">Enter</b-button>
        </router-link>
        <b-button
          @click="secretModal"
          v-else-if="!room.isFull && room.isPrivate"
          >Enter</b-button
        >
        <b-button @click="gameRunning" v-else pill class="col-6 mx-auto Jua"
          >게임진행중</b-button
        >
      </div>
    </b-card>
    <div v-show="isShow">
      <b-form @submit.prevent="onSubmit">
        <b-form-input label="비밀번호" v-model="password"></b-form-input>
        <b-button type="submit" variant="danger" class="mx-3 Jua"
          >확인</b-button
        ></b-form
      >
    </div>
  </div>
</template>
<script>
export default {
  name: "Room",
  props: {
    room: Object,
  },
  data() {
    return {
      isShow: false,
      password: "",
    };
  },
  methods: {
    gameRunning() {
      alert("게임진행중입니다.");
    },
    secretModal() {
      this.isShow = !this.isShow;
    },
    onSubmit() {
      if (this.password != this.room.password) {
        alert("비번이 다름니다");
      } else {
        alert("비번 맞음");
        this.$router.push({
          name: "GameRoom",
          params: { roomId: this.room.roomId },
        });
      }
    },
  },
};
</script>
<style></style>
