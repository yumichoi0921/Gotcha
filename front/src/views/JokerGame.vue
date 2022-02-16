<template>
  <div id="JockerGame" class="body">
    <div id="Game" v-if="!isGameEnd">
      <div id="TurnList" v-if="turn">
        <h5 class="Jua">순서: {{ turn | join }}</h5>
      </div>
      <div id="SubscriberSection" class="row row-cols-5 mb-3">
        <b-col
          v-for="sub in subscribers"
          :key="sub.stream.connection.connectionId"
        >
          <div id="PlayerInfo">
            <div
              class="card shadow p-1"
              style="color: #616264; background-color: #bcdbff"
            >
              <user-video
                :stream-manager="sub"
                :class="{
                  PickerEvent: picker === getUserId(sub.stream.connection.data),
                }"
              />
              <div
                v-if="
                  cardList != null &&
                  cardList[getUserId(sub.stream.connection.data)].length > 0
                "
              >
                <img :src="require('../assets/poker/miniCard.jpg')" /> X
                {{ cardList[getUserId(sub.stream.connection.data)].length }}
              </div>
              <div
                class="Jua"
                v-else-if="
                  cardList != null &&
                  cardList[getUserId(sub.stream.connection.data)].length == 0
                "
              >
                CLEAR
              </div>
            </div>
          </div>
        </b-col>
      </div>
      <div id="PublisherSection" class="row">
        <div id="GameInfoSection" class="col-8">
          <div id="JamminMessage" class="row h-30">
            <b-alert show variant="danger" class="row mx-1 w-100">
              <b-col cols="2">
                <div class="clock"></div>
              </b-col>
              <b-col class="align-self-center">
                <h3 class="Jua">{{ timeCounter }}</h3>
                <h3 class="Jua">{{ jamminFaceTalk }}</h3>
              </b-col>
              <b-col cols="2"
                ><img :src="require('../assets/jammin.gif')" height="100" />
              </b-col>
            </b-alert>
          </div>
          <div class="row"></div>
          <div id="CardInfoSection" class="row">
            <div
              id="SubCardDeck"
              class="col card shadow p-1 mx-1"
              style="color: #616264; background-color: #fff6a0"
            >
              <b-alert show variant="light"
                ><div class="Jua" v-if="picked == userId">
                  {{ picker }}가 나의 카드를 선택중입니다.
                </div>
                <div class="Jua" v-else-if="picker == userId">
                  {{ picked }}의 카드를 선택하세요
                </div>
                <div class="Jua" v-else-if="picked != null">
                  {{ picked }}의 카드덱입니다.
                </div>
                <div class="Jua" v-else>상대방의 카드덱</div>
              </b-alert>

              <b-row cols="6" v-if="cardList != null && picked != userId">
                <b-col v-for="(card, idx) in cardList[picked]" v-bind:key="idx">
                  <img
                    class="cardlist"
                    v-on:click="picker == userId ? cardClick(card) : ''"
                    :class="{
                      CardEvent: isSameCard(card, selectedCard),
                    }"
                    :src="require('../assets/poker/backCard.jpg')"
                  /> </b-col
              ></b-row>
              <b-button v-if="picker == userId" @click.prevent="gameLogic"
                >카드 선택</b-button
              >
            </div>
            <div
              id="PubCardDeck"
              class="col card shadow p-1 mx-1"
              style="color: #616264; background-color: #fff6a0"
            >
              <b-alert show variant="light" class="Jua">내 카드덱</b-alert>
              <transition-group
                tag="div"
                name="card"
                class="row row-cols-6"
                v-if="cardList != null && myCard.length > 0"
              >
                <b-col v-for="(card, idx) in myCard" v-bind:key="card.number">
                  <img
                    class="cardlist"
                    :class="[
                      {
                        CardEvent: isSameCard(myCard[idx], selectedCard),
                      },
                    ]"
                    v-animate-css="gameStartCardEvent"
                    :src="
                      require('@/assets/poker/poker' +
                        card.shape +
                        card.number +
                        '.jpg')
                    "
                  /> </b-col
              ></transition-group>
              <div
                style="color: #e85858"
                class="Jua"
                v-else-if="cardList != null && myCard.length == 0"
              >
                당신은 탈출했습니다!!
              </div>
            </div>
          </div>
        </div>
        <div id="PublisherInfo" class="col">
          <div
            class="card shadow p-1"
            style="color: #616264; background-color: #dcdbff"
          >
            <user-video
              :stream-manager="publisher"
              :userId="userId"
              :picked="picked"
              :class="{ PickerEvent: picker === userId }"
            />
            <div v-if="cardList != null && myCard.length > 0">
              <img :src="require('../assets/poker/miniCard.jpg')" /> X
              {{ myCard.length }}
            </div>
            <div class="Jua" v-else-if="cardList != null && myCard.length == 0">
              clear!!
            </div>
          </div>
        </div>
      </div>
    </div>
    <div v-else>
      <game-result
        :dodukId="dodukId"
        :publisher="publisher"
        :subscribers="subscribers"
      />
    </div>
  </div>
</template>

<script>
import UserVideo from "@/components/GameRoom/UserVideo.vue";
import GameResult from "@/components/GameRoom/GameResult.vue";
import { mapState, mapGetters, mapMutations } from "vuex";
const gameStore = "gameStore";
const memberStore = "memberStore";

export default {
  name: "JokerGame",
  components: {
    UserVideo,
    GameResult,
  },
  props: {
    publisher: Object,
    subscribers: Array,
    userId: String,
    gameMessage: Object,
    eventMessage: Object,
    statusMessage: Object,
  },

  data() {
    return {
      // user 관련 data
      myCard: null,
      // gameMessage 관련 data
      gameSessionId: null,
      hostId: null,
      turn: null,
      picker: null,
      picked: null,
      players: null,
      cardList: null,
      timeCounter: null,
      // eventMessage 관련 data
      eventType: null,
      // 게임 로직 관련 data
      selectedCard: { number: null, shape: null },
      matchedCard: { number: null, shape: null },
      timer: null,
      jamminFaceTalk: "조금만 기다려봐~~",
      // 게임 시작 카드 이벤트 관련 data
      gameStartCardEvent: {
        classes: "slideInDown",
        delay: 100,
        duration: 1500,
      },
      // 게임 결과 관련 data
      isGameEnd: false,
      dodukId: null,
    };
  },
  watch: {
    gameMessage() {
      this.gameSessionId = this.gameMessage.gameSessionId;
      this.hostId = this.gameMessage.hostId;
      this.turn = this.gameMessage.turn;
      this.picker = this.gameMessage.picker;
      this.picked = this.gameMessage.picked;
      this.players = this.gameMessage.players;
      this.cardList = this.gameMessage.cardList;
      this.timeCounter = this.gameMessage.timeCounter;
      this.myCard = this.cardList[this.userId];
      this.selectedCard = { number: null, shape: null };
      this.matchedCard = { number: null, shape: null };
      this.timerStop(this.timer); // 게임메시지 받을 때마다 타이머 멈추고
      this.timerStart(); // 타이머 다시 시작
    },
    eventMessage() {
      this.eventType = this.eventMessage.eventType;
      this.selectedCard = this.eventMessage.selectedCard;
    },
    statusMessage() {
      if (this.statusMessage.type == "END") {
        this.timerStop(this.timer);
        this.isGameEnd = true;
        this.dodukId = this.statusMessage.content;
      }
    },
    emotion() {
      this.emotionCheck();
    },
  },
  computed: {
    ...mapState(gameStore, ["emotion"]),
    ...mapGetters(gameStore, ["emotion"]),
    ...mapState(memberStore, ["user"]),
  },
  filters: {
    join: function (array) {
      return array.join(" -> ");
    },
  },
  methods: {
    ...mapMutations(gameStore, ["SET_EMOTION"]),
    getUserId(data) {
      let clientData = JSON.parse(data);
      return clientData.clientData.userId;
    },
    cardClick(card) {
      this.eventType = "CARDCLICK";
      this.selectedCard = card;
      this.sendEventMessage();
    },
    gameLogic() {
      // 타이머 종료
      this.timerStop(this.timer);

      // picked의 카드리스트에서 선택한 카드 삭제
      for (let i = 0; i < this.cardList[this.picked].length; i++) {
        let card = this.cardList[this.picked][i];
        if (
          this.selectedCard.number === card.number &&
          this.selectedCard.shape === card.shape
        ) {
          this.cardList[this.picked].splice(i, 1);
        }
      }

      // 선택한 카드와 picker의 카드리스트 숫자 매칭 확인
      let matched = false; // 매칭플래그
      for (let i = 0; i < this.cardList[this.picker].length; i++) {
        if (this.selectedCard.number == this.cardList[this.picker][i].number) {
          // 매칭되면
          this.matchedCard = this.cardList[this.picker][i]; // 매칭되는 카드
          this.cardList[this.picker].splice(i, 1); // picker 카드리스트에서 삭제
          matched = true; // 매칭플래그 true;
          break;
        }
      }
      // 매칭된 카드가 없었다면 picker의 카드리스트에 선택한 카드 추가
      if (!matched) {
        this.cardList[this.picker].push(this.selectedCard);
      }
      // picker가 카드를 다 버렸는지 확인
      if (this.cardList[this.picker].length == 0) {
        // 카드를 다 버렸으면 turn에서 삭제
        this.turn.shift();
      } else {
        // 카드가 남았으면 마지막 순서로 넣어주기
        this.turn.shift();
        this.turn.push(this.picker);
      }
      // picked가 카드를 다 버렸는지 확인
      if (this.cardList[this.picked].length == 0) {
        // 카드를 다 버렸으면 turn에서 삭제
        this.turn.shift();
      }
      // 도둑 찾았는지 확인
      if (this.turn.length == 1) {
        this.timerStop(this.timer);
        // 게임 종료 메시지 브로드캐스팅하기
        // let content = { dodukId: this.turn[0] };
        this.sendStatusMessage("END", this.turn[0]);
      } else {
        // picker, picked 변경
        this.picker = this.turn[0];
        this.picked = this.turn[1];
        // 고른 카드 초기화
        this.selectedCard = { number: null, shape: null };
        // 매칭되는 카드 초기화
        this.matchedCard = { number: null, shape: null };
        // 타이머 초기화
        this.timeCounter = 30;
        // cardList 셔플
        for (const key in this.cardList) {
          this.shuffle(this.cardList[key]);
        }
        this.sendGameMessage();
      }
    },
    emotionCheck() {
      if (this.picked == this.user.userId) {
        switch (this.emotion) {
          case "angry":
            this.jamminFaceTalk =
              " 지금 화났죠? 개킹받죠? 때리고 싶죠? 어차피 내가 사는 곳 모르죠? 응~ 못떄리죠? 어~ 또빡치죠? 그냥 화났죠? 냬~ 알걨섑니댸~ 아무도 안물 안궁~";
            break;
          case "disgusted":
            this.jamminFaceTalk = "아무도 조커 안가져가서 빡치쥬? ";
            break;
          case "fearful":
            this.jamminFaceTalk =
              "쫄았죠? 눈물나죠? 엄마한테 이를거죠? 근데 엄마도 공감 안해주죠? 또 빡치죠? 응~ 눈물찔끔~ ";
            break;
          case "happy":
            this.jamminFaceTalk =
              "마치 상대방이 조커를 가져간 표정인데? 방심하면 너가 다시 가져온다? ㅋㅋ루삥뽕";
            break;
          case "neutral":
            this.jamminFaceTalk =
              "호오 표정관리좀 친다? 계속 유지 못하면 게임 지쥬? ";
            break;
          case "sad":
            this.jamminFaceTalk =
              "조커 가져왔어? 표정관리 못하면 너가 패배자야 응 어쩔티비 저쩔티비~";
            break;
          case "surprised":
            this.jamminFaceTalk =
              "놀랐쥬? 뜨끔했쥬? 게임 질거같쥬? 이거 하나 못이기쥬?";
            break;
          default:
            this.jamminFaceTalk = "조금만 기다려봐~~~";
            break;
        }
      } else {
        this.jamminFaceTalk = "조금만 기다려봐~~~";
      }
    },
    sendGameMessage() {
      let message = {
        gameSessionId: this.gameSessionId,
        hostId: this.hostId,
        turn: this.turn,
        picker: this.picker,
        picked: this.picked,
        players: this.players,
        cardList: this.cardList,
        timeCounter: 30,
      };
      this.$emit("sendGameMessage", message);
    },
    sendEventMessage() {
      let message = {
        eventType: this.eventType,
        picker: this.picker,
        picked: this.picked,
        selectedCard: this.selectedCard,
      };
      this.$emit("sendEventMessage", message);
    },
    sendStatusMessage(type, content) {
      this.$emit("sendStatusMessage", type, content);
    },
    timerStart() {
      // 1초에 한번씩 start 호출
      this.timer = setInterval(() => {
        this.timeCounter--; //1찍 감소
        if (this.timeCounter <= 0) {
          this.timerStop(this.timer);
          if (this.picker == this.userId) {
            this.selectedCard = this.cardList[this.picked][0];
            this.gameLogic();
          }
        }
      }, 1000);
    },
    timerStop: function (Timer) {
      clearInterval(Timer);
    },
    shuffle: function (array) {
      array.sort(() => Math.random() - 0.5);
    },
    isSameCard: function (card1, card2) {
      if (card1.number === card2.number && card1.shape === card2.shape) {
        return true;
      } else {
        return false;
      }
    },
  },
};
</script>

<style>
.cardlist {
  width: 100%;
  margin-bottom: 5px;
}
.body {
  background-color: rgba(255, 255, 255, 0.452);
  width: 100%;
}
#TurnList {
  position: relative;
  bottom: 45px;
}
.PickerEvent {
  border: 4px #ff0000 solid;
  animation: blink 1s;
  animation-iteration-count: infinite;
}
.CardEvent {
  border: 3px #ff0000 solid;
  animation: blink 1s;
  animation-iteration-count: infinite;
}
@keyframes blink {
  50% {
    border-color: #fff;
  }
}
.clock {
  border-radius: 60px;
  border: 3px solid #fff;
  height: 80px;
  width: 80px;
  position: relative;

  top: 28%;
  top: -webkit-calc(50% - 43px);
  top: calc(50% - 43px);
  left: 35%;
  left: -webkit-calc(50% - 43px);
  left: calc(50% - 43px);
}
.clock:after {
  content: "";
  position: absolute;
  background-color: #fff;
  top: 2px;
  left: 48%;
  height: 38px;
  width: 4px;
  border-radius: 5px;
  -webkit-transform-origin: 50% 97%;
  transform-origin: 50% 97%;
  -webkit-animation: grdAiguille 2s linear infinite;
  animation: grdAiguille 2s linear infinite;
}

@-webkit-keyframes grdAiguille {
  0% {
    -webkit-transform: rotate(0deg);
  }
  100% {
    -webkit-transform: rotate(360deg);
  }
}

@keyframes grdAiguille {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}

.clock:before {
  content: "";
  position: absolute;
  background-color: #fff;
  top: 6px;
  left: 48%;
  height: 35px;
  width: 4px;
  border-radius: 5px;
  -webkit-transform-origin: 50% 94%;
  transform-origin: 50% 94%;
  -webkit-animation: ptAiguille 12s linear infinite;
  animation: ptAiguille 12s linear infinite;
}

@-webkit-keyframes ptAiguille {
  0% {
    -webkit-transform: rotate(0deg);
  }
  100% {
    -webkit-transform: rotate(360deg);
  }
}

@keyframes ptAiguille {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}

.card-enter-active,
.card-leave-active,
.card-move {
  transition: opacity 1s, transform 1s;
}
.card-leave-active {
  position: absolute;
}
.card-enter {
  opacity: 0;
  transform: translateY(-20px);
}
.card-leave-to {
  opacity: 0;
  transform: translateY(20px);
}
#GameInfoSection {
  padding-left: 4%;
}

#SubscriberSection {
  /* padding-left: 4%;
  padding-top: 2%; */
  padding: 2%;
}
#PublisherInfo {
  padding-right: 4%;

  padding-bottom: 2%;
}
</style>
