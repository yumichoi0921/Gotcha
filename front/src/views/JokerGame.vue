<template>
  <div id="JockerGame">
    <b-row cols="5" id="SubscriberSection" class="mb-3">
      <b-col
        v-for="sub in subscribers"
        :key="sub.stream.connection.connectionId"
      >
        <user-video :stream-manager="sub"
      /></b-col>
    </b-row>
    <b-row id="PublisherSection">
      <b-col cols="8" id="CardSection">
        <!-- <b-alert show variant="secondary"
          >여기는 잼민이의 메시지가 나오는 곳입니다아</b-alert
        > -->
        <b-row id="JamminMessage" class="h-25">
          <b-col>
            <b-alert show variant="secondary"
              >여기는 잼민이의 메시지가 나오는 곳입니다아</b-alert
            ></b-col
          >
        </b-row>
        <b-row>
          <b-col>
            <b-alert show variant="primary">카드를 선택하세요</b-alert>
            <b-col v-for="(card, idx) in cards[myturn]" v-bind:key="idx">
              <b-card
                v-on:click="cardClick(card)"
                :img-src="require('../assets/poker/backCard.jpg')"
                class="cardlist my-3"
              >
                <!-- {{ card.shape }} {{ card.number }} -->
              </b-card>
            </b-col>
          </b-col>
          <!--  </b-card-group> -->
          <b-col>
            <b-alert show variant="primary">내카드덱</b-alert>
            <!-- <b-button variant="primary" v-on:click="makeDeck()"
                >Primary</b-button
              > -->
            <b-col v-for="(card, idx) in cards[myturn]" v-bind:key="idx">
              <img
                class="cardlist"
                v-on:click="cardClick(card)"
                :src="require('@/assets/poker/poker' + card + '.jpg')"
              />
            </b-col>
          </b-col>
        </b-row>
      </b-col>
      <b-col cols="4" id="PublisherUserInfo">
        <div>
          <user-video
            :stream-manager="publisher"
            :userId="userId"
            :myCard="myCard"
          />
        </div>
      </b-col>
    </b-row>
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
    myCard: Array,
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
    };
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
</style>
