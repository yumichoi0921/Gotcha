<template>
  <div id="JockerGame">
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
            <b-col class="align-self-center"
              >여기는 잼민이의 메시지가 나오는 곳입니다아
            </b-col>
            <b-col cols="2"
              ><img :src="require('../assets/jammin.gif')" height="100" />
            </b-col>
          </b-alert>
        </div>
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
                    CardEvent: card == selectedCard,
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
              <b-col v-for="(card, idx) in myCard" v-bind:key="idx">
                <img
                  class="cardlist focus-in-contract-bck"
                  v-on:click="cardClick(card)"
                  :class="[
                    { CardEvent: card === selectedCard },
                    { blurOutContract: card === matchedCard },
                  ]"
                  :src="require('@/assets/poker/poker' + card + '.jpg')"
                /> </b-col
            ></b-row>
          </div>
        </div>
      </div>
      <!-- :class="[{ CardEvent: card === 'selectedCard' }, { blur-out-contract: card
      === 'matchedCard' }]" -->
      <!-- blur-out-contract -->
      <!-- :class="{ CardEvent: card == selectedCard, }"  -->
      <div id="PublisherInfo" class="col">
        <div class="Info rounded">
          <user-video
            :stream-manager="publisher"
            :userId="userId"
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
      // eventMessage 관련 data
      eventType: null,
      // picker: null,
      // picked: null,
      selectedCard: null,
      matchedCard: null,
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
      this.myCard = this.cardList[this.userId];
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
        alert("게임 끝");
      } else {
        // picker, picked 변경
        this.picker = this.turn[0];
        this.picked = this.turn[1];
        // 고른 카드 초기화
        this.selectedCard = null;
        // 매칭되는 카드 초기화
        this.matchedCard = null;
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

.focus-in-contract-bck {
  animation: focus-in-contract-bck 2s cubic-bezier(0.25, 0.46, 0.45, 0.94) both;
}

/* ----------------------------------------------
 * Generated by Animista on 2022-2-11 11:12:17
 * Licensed under FreeBSD License.
 * See http://animista.net/license for more info. 
 * w: http://animista.net, t: @cssanimista
 * ---------------------------------------------- */

/**
 * ----------------------------------------
 * animation focus-in-contract-bck
 * ----------------------------------------
 */
@keyframes focus-in-contract-bck {
  0% {
    letter-spacing: 1em;
    transform: translateZ(300px);
    filter: blur(12px);
    opacity: 0;
  }
  100% {
    transform: translateZ(12px);
    filter: blur(0);
    opacity: 1;
  }
}

.blurOutContract {
  animation: blurOutContract 1s cubic-bezier(0.55, 0.085, 0.68, 0.53) both;
}

/* ----------------------------------------------
 * Generated by Animista on 2022-2-11 11:20:39
 * Licensed under FreeBSD License.
 * See http://animista.net/license for more info. 
 * w: http://animista.net, t: @cssanimista
 * ---------------------------------------------- */

/**
 * ----------------------------------------
 * animation blur-out-contract
 * ----------------------------------------
 */
@keyframes blurOutContract {
  0% {
    filter: blur(0.01);
  }
  100% {
    letter-spacing: -0.5em;
    filter: blur(12px) opacity(0%);
  }
}
</style>
