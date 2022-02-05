<template>
  <div>
    웨이팅룸 아이디:{{ roomId }}
    <b-form @submit.prevent="sendMessage" class="text-left">
      <b-form-group id="sender" label="sender" label-for="sender">
        <b-form-input id="sender" v-model="sender" required></b-form-input>
      </b-form-group>
      <b-form-group id="content" label="content" label-for="content">
        <b-form-input id="content" v-model="content" required></b-form-input>
      </b-form-group>
      <div class="mt-5 mx-5 d-flex justify-content-center">
        <b-button type="submit" variant="danger" class="mx-3"
          >방만들기</b-button
        >
      </div>
    </b-form>
    <div v-for="(m, index) in msg" :key="index" v-bind="m">{{ m }}</div>
  </div>
</template>

<script>
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";
export default {
  name: "WaitingRoom",
  data() {
    return {
      roomId: "",
      sender: "",
      content: "",
      stompClient: null,
      msg: [],
    };
  },
  created() {
    this.roomId = this.$route.params.roomId;
    this.connect();
  },
  methods: {
    connect() {
      const serverURL = "http://localhost:8080/ws";
      let socket = new SockJS(serverURL);
      this.stompClient = Stomp.over(socket);
      console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`);
      this.stompClient.connect(
        {},
        (frame) => {
          // 소켓 연결 성공
          // this.connected = true;
          console.log("소켓 연결 성공", frame);
          // 서버의 메시지 전송 endpoint를 구독합니다.
          // 이런형태를 pub sub 구조라고 합니다.
          this.stompClient.subscribe("/sub/" + this.roomId, (res) => {
            console.log("구독으로 받은 메시지 입니다.", res.body);
            // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
            let jsonBody = JSON.parse(res.body);
            let message = {
              roomId: jsonBody.roomId,
              sender: jsonBody.sender,
              content: jsonBody.content,
              type: jsonBody.type,
            };
            this.msg.push(message);
          });
        },
        (error) => {
          // 소켓 연결 실패
          console.log("소켓 연결 실패", error);
          this.connected = false;
        }
      );
    },
    sendMessage() {
      let message = {
        roomId: this.roomId,
        sender: this.sender,
        content: this.content,
        type: "JOIN",
      };
      this.stompClient.send("/pub/message", JSON.stringify(message), {});
    },
  },
};
</script>

<style></style>
