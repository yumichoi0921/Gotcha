<template>
  <div id="JockerGame">
    <div id="SubscriberSection" class="row row-cols-5 mb-3">
      <b-col
        v-for="sub in subscribers"
        :key="sub.stream.connection.connectionId"
      >
        <div id="PlayerInfo">
          <user-video :stream-manager="sub" class="mb-0" />
          <div>닉네임:</div>
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
          <div id="SubCardDeck" class="col">
            <b-alert show variant="primary">카드를 선택하세요</b-alert>
            <b-row cols="6" class="">
              <b-col v-for="(card, idx) in cards[myturn]" v-bind:key="idx">
                <b-card
                  v-on:click="cardClick(card)"
                  :img-src="require('../assets/poker/backCard.jpg')"
                  class="cardlist my-3"
                >
                  <!-- {{ card.shape }} {{ card.number }} -->
                </b-card>
              </b-col></b-row
            >
          </div>
          <div id="PubCardDeck" class="col">
            <b-alert show variant="primary">내카드덱</b-alert>
            <!-- <b-button variant="primary" v-on:click="makeDeck()"
                >Primary</b-button
              > -->
            <b-row cols="6" class="">
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
      cards: [
        ["CA", "C2", "H3", "D5"],
        ["CA", "C2", "H3", "D5"],
        ["CA", "C2", "H3", "D5"],
        ["CA", "C2", "H3", "D5"],
      ],
      myturn: 3,
      hostId: null,
      turn: null,
      nowTurn: null,
      players: null,
      cardList: null,
      winner: null,
      candidate: null,
      myCard: null,
    };
  },
  watch: {
    gameMessage() {
      this.hostId = this.gameMessage.hostId;
      this.turn = this.gameMessage.turn;
      this.nowTurn = this.gameMessage.nowTurn;
      this.players = this.gameMessage.players;
      this.cardList = this.gameMessage.cardList;
      this.winner = this.gameMessage.winner;
      this.candidate = this.gameMessage.candidate;
      this.myCard = this.cardList[this.userId];
    },
  },
  methods: {
    cardClick(card) {
      console.log(card);
      alert(card);
    },
  },
};
</script>

<style>
.cardlist {
  width: 50px;
  height: 70px;
  margin: 5px;
  float: left;
}
#PlayerInfo {
  background-color: rgba(255, 216, 110, 0.938);
}
</style>
