<template>
  <div id="JockerGame">
    <div class="modal"></div>
    <div id="SubscriberSection" class="row row-cols-5 mb-3">
      <b-col
        v-for="sub in subscribers"
        :key="sub.stream.connection.connectionId"
      >
        <div id="PlayerInfo">
          <div class="Info rounded">
            <user-video
              :stream-manager="sub"
              :class="{
                PickerEvent: picker === getUserId(sub.stream.connection.data),
              }"
            />
            <div v-if="cardList != null">
              <img :src="require('../assets/poker/miniCard.jpg')" /> X
              {{ cardList[getUserId(sub.stream.connection.data)].length }}
            </div>
          </div>
        </div>
      </b-col>
    </div>
    <div id="PublisherSection" class="row">
      <div id="GameInfoSection" class="col-8">
        <div id="JamminMessage" class="row h-30">
          <b-alert show variant="secondary" class="row mx-1 w-100">
            <b-col cols="2">
              <div class="clock"></div>
            </b-col>
            <b-col class="align-self-center">
              <h3>{{ timeCounter }}</h3>
              여기는 잼민이의 메시지가 나오는 곳입니다아
            </b-col>
            <b-col cols="2"
              ><img :src="require('../assets/jammin.gif')" height="100" />
            </b-col>
          </b-alert>
        </div>
        <div class="row"></div>
        <div id="CardInfoSection" class="row">
          <div id="SubCardDeck" class="col CardDeck p-1 mx-1">
            <b-alert show variant="primary"
              >{{ picked }} 카드를 선택하세요</b-alert
            >
            <b-row cols="6" class="" v-if="cardList != null">
              <b-col v-for="(card, idx) in cardList[picked]" v-bind:key="idx">
                <img
                  class="cardlist"
                  v-on:click="picker == userId ? cardClick(card) : ''"
                  :class="{
                    CardEvent:
                      card.number === selectedCard.number &&
                      card.shape === selectedCard.shape,
                  }"
                  :src="require('../assets/poker/backCard.jpg')"
                /> </b-col
            ></b-row>
            <b-button v-if="picker == userId" @click.prevent="gameLogic"
              >카드 선택</b-button
            >
          </div>
          <div id="PubCardDeck" class="col CardDeck p-1 mx-1">
            <b-alert show variant="primary">내카드덱</b-alert>
            <b-row cols="6" class="" v-if="cardList != null">
              <b-col v-for="(card, idx) in myCard" v-bind:key="idx" class="">
                <img
                  class="cardlist"
                  v-on:click="cardClick(card)"
                  :class="[
                    {
                      CardEvent:
                        card.number === selectedCard.number &&
                        card.shape === selectedCard.shape,
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
            ></b-row>
          </div>
        </div>
      </div>
      <div id="PublisherInfo" class="col">
        <div class="Info rounded">
          <user-video
            :stream-manager="publisher"
            :userId="userId"
            :picked="picked"
            :class="{ PickerEvent: picker === userId }"
          />
          <div v-if="cardList != null">
            <img :src="require('../assets/poker/miniCard.jpg')" /> X
            {{ myCard.length }}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import UserVideo from "@/components/GameRoom/UserVideo.vue";
import { mapGetters, mapState } from "vuex";
const gameStore = "gameStore";
const memberStore = "memberStore";
export default {
  name: "JokerGame",
  components: {
    UserVideo,
  },
  props: {
    publisher: Object,
    subscribers: Array,
    userId: String,
    gameMessage: Object,
    eventMessage: Object,
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
      picked: "before",
      players: null,
      cardList: null,
      timeCounter: null,
      // eventMessage 관련 data
      eventType: null,
      // 게임 로직 관련 data
      selectedCard: { number: null, shape: null },
      matchedCard: { number: "", shape: "" },
      // 게임 시작 카드 이벤트 관련 data
      gameStartCardEvent: {
        classes: "slideInDown",
        delay: 100,
        duration: 2500,
      },
      timer: null,
      jamminMessag: "",
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
      this.timerStop(this.timer); // 게임메시지 받을 때마다 타이머 멈추고
      this.timerStart(); // 타이머 다시 시작
    },
    eventMessage() {
      this.eventType = this.eventMessage.eventType;
      this.selectedCard = this.eventMessage.selectedCard;
    },
    "$store.state.emotion": function () {
      console.log(this.$store.state.emotion);
    },
    emotion() {
      console.log("이모션 인식!!!!");
      if (this.picked == this.user.userId) {
        console.log("emotion 바뀌고 내차례-> " + this.emotion);
        this.jamminMessag = "열받쮸";
      }
    },
  },
  computed: {
    ...mapGetters(gameStore, ["emotion"]),
    ...mapState(memberStore, ["user"]),
  },
  methods: {
    getUserId(data) {
      let clientData = JSON.parse(data);
      return clientData.clientData.userId;
    },
    cardClick(card) {
      this.eventType = "CARDCLICK";
      this.selectedCard = card;
      this.sendEventMessage();
      // alert("선택카드 " + card.shape + card.number);
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
          // alert("중복됨");
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
        alert("게임 끝");
      } else {
        // picker, picked 변경
        this.picker = this.turn[0];
        this.picked = this.turn[1];
        // 고른 카드 초기화
        this.selectedCard = { number: null, shape: null };
        // 매칭되는 카드 초기화
        this.matchedCard = { number: "", shape: "" };
        // 타이머 초기화
        this.timeCounter = 30;
        // 이벤트 메시지 보내기
        // this.sendEventMessage();
      }
      this.sendGameMessage();
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
    timerStart() {
      console.log("타이머 시작");
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
      console.log("타이머 종료");
    },
  },
};
</script>

<style>
.cardlist {
  width: 100%;
  margin-bottom: 5px;
}
.Info {
  background-color: rgba(255, 216, 110, 0.938);
}
.CardDeck {
  background-color: rgba(255, 216, 110, 0.938);
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
</style>
