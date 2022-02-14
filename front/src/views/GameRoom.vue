<template>
  <div class="GameRoom">
    <!-- <div id="setting-dialog" class="card" v-if="!session">
      <h5 class="card-header">Join a video session</h5>
      <div class="card-body">
        <h5 class="card-title">아이디를 적으세요</h5>
        <div class="card-text form-group">
          <p>
            <input v-model="userId" class="form-control" type="text" required />
          </p>
          <p class="text-center">
            <button class="btn btn-lg btn-success" @click="joinSession()">
              Join!
            </button>
          </p>
        </div>
      </div>
    </div> -->
    <div id="GameSession" v-if="session" class="gs">
      <div id="GameSession-header">
        <b-row class="alert alert-secondary">
          <b-col
            ><h3>{{ room.roomTitle }}</h3></b-col
          >
          <b-col>
            <b-button
              v-if="userId == hostId"
              @click="sendStatusMessage('START', 'START')"
              variant="danger"
              >시작</b-button
            >
          </b-col>
          <b-col>
            <input
              class="btn btn-large btn-danger"
              type="button"
              id="buttonLeaveSession"
              @click="leaveSession"
              value="Leave session" /></b-col
        ></b-row>
      </div>
      <div id="GameSession-body">
        <!-- <div id="main-video" class="col-3">
          <user-video :stream-manager="mainStreamManager" />
        </div> -->
        <joker-game
          :publisher="publisher"
          :subscribers="subscribers"
          :userId="userId"
          :gameMessage="gameMessage"
          :eventMessage="eventMessage"
          :statusMessage="statusMessage"
          v-on:sendGameMessage="sendGameMessage"
          v-on:sendEventMessage="sendEventMessage"
          v-on:sendStatusMessage="sendStatusMessage"
        ></joker-game>
      </div>
    </div>
  </div>
</template>

<script>
import JokerGame from "@/views/JokerGame.vue";

import axios from "axios";
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";
import { OpenVidu } from "openvidu-browser";
import { room } from "@/api/room.js";
import { mapState } from "vuex";
const memberStore = "memberStore";

axios.defaults.headers.post["Content-Type"] = "application/json";
// const OPENVIDU_SERVER_URL = "https://" + location.hostname + ":4443";
// docker run -p 4443:4443 --rm -e OPENVIDU_SECRET=MY_SECRET openvidu/openvidu-server-kms:2.20.0
const OPENVIDU_SERVER_URL = "https://" + "i6b102.p.ssafy.io" + ":9443";
const OPENVIDU_SERVER_SECRET = "MY_SECRET";
export default {
  name: "GameRoom",
  components: {
    JokerGame,
  },
  data() {
    return {
      // user 관련 data
      userId: "",
      // room 관련 data
      room: null,
      // Message 관련 data
      type: "",
      content: "",
      gameMessage: null,
      eventMessage: null,
      statusMessage: null,
      // stomp관련 data
      stompClient: null,
      connected: false,
      // OpenVidu관련 data
      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      subscribers: [],
      mySessionId: "",
      hostId: "",
    };
  },
  created() {
    let roomId = this.$route.params.roomId;
    room(roomId, (response) => {
      console.log("room", response.data);
      this.room = response.data;
      this.mySessionId = this.room.roomId;
      this.hostId = this.room.hostId;
      this.userId = this.user.userId;
      this.joinSession();
    });
  },
  computed: {
    ...mapState(memberStore, ["user"]),
  },
  methods: {
    connect() {
      const serverURL = "http://localhost:8080/ws";
      //const serverURL = "https://i6b102.p.ssafy.io/ws"; // 배포용
      let socket = new SockJS(serverURL);
      this.stompClient = Stomp.over(socket);
      this.stompClient.connect(
        { roomId: this.room.roomId, userId: this.userId },
        this.onConnected,
        this.onError
      );
    },
    onConnected(frame) {
      // 소켓 연결 성공
      console.log("소켓 연결 성공", frame);
      this.connected = true;
      this.stompClient.subscribe(
        "/sub/" + this.room.roomId,
        this.onMessageReceived
      );
    },
    onError(error) {
      // 소켓 연결 실패
      console.log("소켓 연결 실패", error);
      this.connected = false;
    },
    sendMessage() {
      let message = {
        roomId: this.room.roomId,
        userId: this.userId,
        content: this.content,
        type: this.type,
      };
      this.stompClient.send("/pub/message", JSON.stringify(message), {});
    },
    onMessageReceived(payload) {
      // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
      let jsonBody = JSON.parse(payload.body);
      if (jsonBody.type == "GAME") {
        this.gameMessageParser(jsonBody.content);
      } else if (jsonBody.type == "EVENT") {
        this.eventMessageParser(jsonBody.content);
      } else if (jsonBody.type == "START" || jsonBody.type == "END") {
        this.statusMessageParser(jsonBody);
      }
    },
    sendStatusMessage(type, content) {
      this.type = type;
      this.content = content;
      this.sendMessage();
    },
    // gameEnd() {
    //   // TODO: 게임 끝나는 부분에서 호출해주세요!!!!
    //   this.type = "END";
    //   this.content = "";
    //   this.sendMessage();
    // },
    sendGameMessage(message) {
      this.type = "GAME";
      this.content = JSON.stringify(message);
      this.sendMessage();
    },
    sendEventMessage(message) {
      this.type = "EVENT";
      this.content = JSON.stringify(message);
      this.sendMessage();
    },
    gameMessageParser(content) {
      this.gameMessage = JSON.parse(content);
      console.log("게임 메시지", this.gameMessage);
    },
    eventMessageParser(content) {
      this.eventMessage = JSON.parse(content);
      console.log("이벤트 메시지", this.eventMessage);
    },
    statusMessageParser(content) {
      this.statusMessage = content;
      console.log("게임상태 메시지", this.statusMessage);
    },
    joinSession() {
      // --- Get an OpenVidu object ---
      this.OV = new OpenVidu();

      // --- Init a session ---
      this.session = this.OV.initSession();

      // --- Specify the actions when events take place in the session ---

      // On every new Stream received...
      this.session.on("streamCreated", ({ stream }) => {
        const subscriber = this.session.subscribe(stream);
        this.subscribers.push(subscriber);
      });

      // On every Stream destroyed...
      this.session.on("streamDestroyed", ({ stream }) => {
        const index = this.subscribers.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          this.subscribers.splice(index, 1);
        }
      });

      // On every asynchronous exception...
      this.session.on("exception", ({ exception }) => {
        console.warn(exception);
      });

      // --- Connect to the session with a valid user token ---

      // 'getToken' method is simulating what your server-side should do.
      // 'token' parameter should be retrieved and returned by your own backend
      this.getToken(this.mySessionId).then((token) => {
        this.session
          .connect(token, {
            clientData: { userId: this.userId },
          })
          .then(() => {
            // --- Get your own camera stream with the desired properties ---

            let publisher = this.OV.initPublisher(undefined, {
              // audioSource: undefined, // The source of audio. If undefined default microphone
              videoSource: undefined, // The source of video. If undefined default webcam
              publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
              publishVideo: true, // Whether you want to start publishing with your video enabled or not
              resolution: "640x480", // The resolution of your video
              frameRate: 30, // The frame rate of your video
              insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
              mirror: false, // Whether to mirror your local video or not
            });

            this.mainStreamManager = publisher;
            this.publisher = publisher;

            // --- Publish your stream ---

            this.session.publish(this.publisher);
            this.connect();
          })
          .catch((error) => {
            console.log(
              "There was an error connecting to the session:",
              error.code,
              error.message
            );
          });
      });

      window.addEventListener("beforeunload", this.leaveSession);
    },

    leaveSession() {
      // --- Leave the session by calling 'disconnect' method over the Session object ---
      if (this.session) this.session.disconnect();

      this.session = undefined;
      this.mainStreamManager = undefined;
      this.publisher = undefined;
      this.subscribers = [];
      this.OV = undefined;

      window.removeEventListener("beforeunload", this.leaveSession);
    },

    updateMainVideoStreamManager(stream) {
      if (this.mainStreamManager === stream) return;
      this.mainStreamManager = stream;
    },

    /**
     * --------------------------
     * SERVER-SIDE RESPONSIBILITY
     * --------------------------
     * These methods retrieve the mandatory user token from OpenVidu Server.
     * This behavior MUST BE IN YOUR SERVER-SIDE IN PRODUCTION (by using
     * the API REST, openvidu-java-client or openvidu-node-client):
     *   1) Initialize a Session in OpenVidu Server	(POST /openvidu/api/sessions)
     *   2) Create a Connection in OpenVidu Server (POST /openvidu/api/sessions/<SESSION_ID>/connection)
     *   3) The Connection.token must be consumed in Session.connect() method
     */

    getToken(mySessionId) {
      return this.createSession(mySessionId).then((sessionId) =>
        this.createToken(sessionId)
      );
    },

    // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessions
    createSession(sessionId) {
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions`,
            JSON.stringify({
              customSessionId: sessionId,
            }),
            {
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET,
              },
            }
          )
          .then((response) => response.data)
          .then((data) => resolve(data.id))
          .catch((error) => {
            if (error.response.status === 409) {
              resolve(sessionId);
            } else {
              console.warn(
                `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`
              );
              if (
                window.confirm(
                  `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`
                )
              ) {
                location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
              }
              reject(error.response);
            }
          });
      });
    },

    // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessionsltsession_idgtconnection
    createToken(sessionId) {
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`,
            {},
            {
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET,
              },
            }
          )
          .then((response) => response.data)
          .then((data) => resolve(data.token))
          .catch((error) => reject(error.response));
      });
    },
  },
};
</script>

<style>
.gs {
  margin-top: 80px;
}
</style>
