<template>
  <div>
    웨이팅룸 아이디:{{ roomId }}
    <b-button @click="sendMessage">send</b-button>
    <div v-for="(room, index) in rooms" :key="index" v-bind="room"></div>
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
            this.sendMessage();
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
            console.log(message);
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
        sender: "A",
        content: "입장",
        type: "Chat",
      };
      this.stompClient.send("/pub/message", {}, JSON.stringify(message));
    },
  },
};
</script>

<style></style>
