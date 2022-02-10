<template>
  <div id="JockerGame">
    <div id="SubscriberSection" class="row row-cols-5 mb-3">
      <b-col
        v-for="sub in subscribers"
        :key="sub.stream.connection.connectionId"
      >
        <div id="PlayerInfo" class="card m-auto">
          <div class="card-header">
            <user-video :stream-manager="sub" />
          </div>
          <div class="card-body" v-if="cardList != null">
            <img :src="require('../assets/poker/miniCard.jpg')" />
            X {{ cardList[getUserId(sub.stream.connection.data)].length }}
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
        <div id="CardInfoSection" class="row">
          <div id="SubCardDeck" class="col CardDeck px-1 mx-1">
            <b-alert show variant="primary">카드를 선택하세요</b-alert>
            <b-row cols="6" class="" v-if="cardList != null">
              <b-col v-for="(card, idx) in cardList[picked]" v-bind:key="idx">
                <img
                  class="cardlist"
                  v-on:click="cardClick(card)"
                  :src="require('../assets/poker/backCard.jpg')"
                /> </b-col
            ></b-row>
            <b-button @click.prevent="sendGameMessage">버튼</b-button>
          </div>
          <div id="PubCardDeck" class="col CardDeck px-1 mx-1">
            <b-alert show variant="primary">내카드덱</b-alert>
            <b-row cols="6" class="" v-if="cardList != null">
              <b-col v-for="(card, idx) in myCard" v-bind:key="idx">
                <img
                  class="cardlist"
                  v-on:click="cardClick(card)"
                  :src="require('@/assets/poker/poker' + card + '.jpg')"
                /> </b-col
            ></b-row>
          </div>
        </div>
      </div>

      <div id="PublisherInfo" class="col">
        <div>
          <user-video :stream-manager="publisher" :userId="userId" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import UserVideo from "@/components/GameRoom/UserVideo.vue";
export default {
  name: "JokerGame",
  props: {
    publisher: Object,
    subscribers: Array,
    userId: String,
    gameMessage: Object,
  },
  components: {
    UserVideo,
  },

  data() {
    return {
      gameSessionId: null,
      hostId: null,
      turn: null,
      picker: null,
      picked: null,
      players: null,
      cardList: null,
      winner: null,
      candidate: null,
      myCard: null,
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
    },
  },
  computed: {},
  methods: {
    cardClick(card) {
      console.log(card);
      alert(card);
    },
    getUserId(data) {
      let clientData = JSON.parse(data);
      return clientData.clientData.userId;
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
      };
      this.$emit("sendGameMessage", message);
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
.CardDeck {
  background-color: rgba(255, 216, 110, 0.938);
}
</style>
