<template>
  <div id="JockerGame">
    <div class="modal"></div>
    <div id="SubscriberSection" class="row row-cols-5 mb-3">
      <b-col
        v-for="sub in subscribers"
        :key="sub.stream.connection.connectionId"
      >
        <div id="PlayerInfo" class="card">
          <user-video
            :stream-manager="sub"
            :class="{
              PickerEvent: picker === getUserId(sub.stream.connection.data),
            }"
          />
          <div
            class="alert alert-secondary"
            role="alert"
            v-if="cardList != null"
          >
            <img :src="require('../assets/poker/miniCard.jpg')" /> X
            {{ cardList[getUserId(sub.stream.connection.data)].length }}
          </div>
        </div>
      </b-col>
    </div>
    <div id="PublisherSection" class="row">
      <div id="GameInfoSection" class="col-8">
        <div id="JamminMessage" class="row h-25">
          <b-col>
            <b-alert show variant="secondary"
              >여기는 잼민이의 메시지가 나오는 곳입니다아</b-alert
            ></b-col
          >
        </div>
        <div class="row">
          <h3>{{ timeCounter }}</h3>
        </div>
        <div id="CardInfoSection" class="row">
          <div id="SubCardDeck" class="col CardDeck px-1 mx-1">
            <b-alert show variant="primary"
              >{{ picked }} 카드를 선택하세요</b-alert
            >
            <b-row cols="6" class="" v-if="cardList != null">
              <b-col v-for="(card, idx) in cardList[picked]" v-bind:key="idx">
                <img
                  class="cardlist"
                  v-on:click="picker == userId ? cardClick(card) : ''"
                  :class="{
                    CardEvent: card == selectedCard,
                  }"
                  :src="require('../assets/poker/backCard.jpg')"
                /> </b-col
            ></b-row>
            <b-button v-if="picker == userId" @click.prevent="gameLogic"
              >카드 선택</b-button
            >
          </div>
          <div id="PubCardDeck" class="col CardDeck px-1 mx-1">
            <b-alert show variant="primary">내카드덱</b-alert>
            <b-row cols="6" class="" v-if="cardList != null">
              <b-col v-for="(card, idx) in myCard" v-bind:key="idx">
                <img
                  class="cardlist"
                  v-on:click="cardClick(card)"
                  :class="{
                    CardEvent: card == selectedCard,
                  }"
                  :src="require('@/assets/poker/poker' + card + '.jpg')"
                /> </b-col
            ></b-row>
          </div>
        </div>
      </div>

      <div id="PublisherInfo" class="col">
        <div class="card"></div>
        <user-video
          :stream-manager="publisher"
          :userId="userId"
          :class="{ PickerEvent: picker === userId }"
        />
      </div>
    </div>
  </div>
</template>

<script>
import UserVideo from "@/components/GameRoom/UserVideo.vue";
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
      picked: null,
      players: null,
      cardList: null,
      winner: null,
      candidate: null,
      // eventMessage 관련 data
      eventType: null,
      // picker: null,
      // picked: null,
      selectedCard: "",
      timeCounter: 30,
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
      this.winner = this.gameMessage.winner;
      this.candidate = this.gameMessage.candidate;
      this.myCard = this.cardList[this.userId];
      this.timeCounter = this.gameMessage.timeCounter;
      this.start();
    },
    eventMessage() {
      this.eventType = this.eventMessage.eventType;
      this.selectedCard = this.eventMessage.selectedCard;
    },
  },
  computed: {},
  methods: {
    getUserId(data) {
      let clientData = JSON.parse(data);
      return clientData.clientData.userId;
    },
    cardClick(card) {
      this.eventType = "CARDCLICK";
      this.selectedCard = card;
      this.sendEventMessage();
      alert("선택카드 " + card);
    },
    gameLogic() {
      //  this.timeCounter = 100;
      // 선택한 카드 인덱스
      let selectedCardIdx = this.cardList[this.picked].indexOf(
        this.selectedCard
      );
      // picked의 카드리스트에서 선택한 카드 삭제
      this.cardList[this.picked].splice(selectedCardIdx, 1);
      // 선택한 카드와 picker의 카드리스트 숫자 매칭 확인
      let matched = false; // 매칭플래그
      let cardNum = this.selectedCard.substr(1); // 선택한 카드 숫자
      for (var i = 0; i < this.cardList[this.picker].length; i++) {
        if (cardNum == this.cardList[this.picker][i].substr(1)) {
          // 매칭되면
          alert("중복됨");
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
        // 카드를 다 버렸으면
        this.winner.push(this.picker); // winner에 추가
        this.candidate.splice(this.picker, 1); // candidate에서 삭제
        this.turn.shift();
      } else {
        this.turn.shift();
        this.turn.push(this.picker);
      }
      // picked가 카드를 다 버렸는지 확인
      if (this.cardList[this.picked].length == 0) {
        // 카드를 다 버렸으면
        this.winner.push(this.picked); // winner에 추가
        this.candidate.splice(this.picked, 1); // candidate에서 삭제
        this.turn.shift();
      }
      // 도둑 찾았는지 확인
      if (this.turn.length == 1) {
        alert("게임 끝");
      } else {
        // turn 수정 - turn, picker, picked
        this.picker = this.turn[0];
        this.picked = this.turn[1];
        // 고른 카드 초기화
        this.selectedCard = null;
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
        winner: this.winner,
        candidate: this.candidate,
        timeCounter: 20,
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
    start() {
      console.log("타이머 시작");
      // 1초에 한번씩 start 호출
      var interval = setInterval(() => {
        this.timeCounter--; //1찍 감소
        if (this.timeCounter <= 0) this.timerStop(interval);
      }, 1000);
    },
    timerStop: function (Timer) {
      clearInterval(Timer);
      this.TimeCounter = 0;
      //alert("자동으로 선택됩니다.");
      this.selectedCard = this.cardList[this.picked][0];
      this.gameLogic();
    },
  },
};
</script>

<style>
.cardlist {
  width: 100%;
  margin-bottom: 5px;
}
#PlayerInfo {
  background-color: rgba(255, 216, 110, 0.938);
}
#PublisherInfo {
  background-color: rgba(255, 216, 110, 0.938);
}
.CardDeck {
  background-color: rgba(255, 216, 110, 0.938);
}
.PickerEvent {
  border: 3px #ff0000 solid;
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
</style>
