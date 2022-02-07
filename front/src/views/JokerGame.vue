<template>
  <div>
    <b-container>
      <b-alert show variant="primary">도둑잡기 게임화면입니다.</b-alert>

      <b-row>
        <b-col sm="4" align-self="start"
          ><div>
            <b-img
              src="https://picsum.photos/125/125/?image=58"
              fluid
              alt="Responsive image"
            ></b-img></div
        ></b-col>
        <b-col sm="4" align-self="center"
          ><div>
            <b-img
              src="https://picsum.photos/125/125/?image=58"
              fluid
              alt="Responsive image"
            ></b-img></div
        ></b-col>
        <b-col sm="4" align-self="end"
          ><div>
            <b-img
              src="https://picsum.photos/125/125/?image=58"
              fluid
              alt="Responsive image"
            ></b-img></div
        ></b-col>
      </b-row>

      <b-row>
        <b-col cols="8">
          <b-row class="text-center">
            <div class="bg-secondary text-light col m-5">
              여기는 잼민이의 메시지가 나오는 곳입니다아
            </div>
          </b-row>
          <b-row>
            <!--  <b-card-group deck class="col-6"> -->
            <b-col>
              <b-alert show variant="primary">카드를 선택하세요</b-alert>
              <b-col v-for="card in cards[myturn]" v-bind:key="card">
                <b-card
                  v-on:click="cardClick(card)"
                  :img-src="require('../assets/backCard.jpg')"
                  class="cardlist my-3"
                >
                  <!-- {{ card.shape }} {{ card.number }} -->
                </b-card>
              </b-col>
            </b-col>
            <!--  </b-card-group> -->
            <b-col>
              <b-alert show variant="primary">내카드덱</b-alert>
              <b-col v-for="card in cards[myturn]" v-bind:key="card">
                <b-card
                  v-on:click="cardClick(card)"
                  :img-src="require('../assets/backCard.jpg')"
                  class="cardlist my-3"
                >
                  <!-- {{ card.shape }} {{ card.number }} -->
                </b-card>
              </b-col>
            </b-col>
          </b-row>
        </b-col>
        <b-col cols="4">
          <b-alert show variant="primary">내카메라</b-alert>
          <div>
            <b-img
              src="https://picsum.photos/1024/400/?image=41"
              fluid
              alt="Responsive image"
            ></b-img>
          </div>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
export default {
  name: "JokerGame",

  data() {
    return {
      cards: [
        [
          { number: 1, shape: "HEART" },
          { number: 2, shape: "CLOVER" },
          { number: 2, shape: "DIAMOND" },
          { number: 4, shape: "HEART" },
        ],

        [
          { number: 1, shape: "HEART" },
          { number: 2, shape: "CLOVER" },
          { number: 2, shape: "DIAMOND" },
          { number: 4, shape: "HEART" },
        ],
        [
          { number: 1, shape: "HEART" },
          { number: 2, shape: "CLOVER" },
          { number: 2, shape: "DIAMOND" },
          { number: 4, shape: "HEART" },
        ],
        [
          { number: 1, shape: "HEART" },
          { number: 2, shape: "CLOVER" },
          { number: 2, shape: "DIAMOND" },
          { number: 4, shape: "HEART" },
          { number: 1, shape: "HEART" },
          { number: 2, shape: "CLOVER" },
          { number: 2, shape: "DIAMOND" },
          { number: 4, shape: "HEART" },
        ],
      ],
      myturn: 3,
    };
  },
  methods: {
    cardClick(card) {
      console.log(card);
      alert(card.shape + card.number);
    },
  },
};
</script>

<style>
.cardlist {
  width: 50px;
  height: 50px;
  float: left;
}
</style>
