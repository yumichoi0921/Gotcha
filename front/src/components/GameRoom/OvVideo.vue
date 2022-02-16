<template>
  <video autoplay width="100%" ref="myWebCam" />
</template>

<script>
import * as faceapi from "face-api.js";
import { mapGetters, mapState, mapMutations } from "vuex";
const gameStore = "gameStore";
const memberStore = "memberStore";

export default {
  name: "OvVideo",
  data() {
    return {
      //video: "",
      emotionModel: null,
      timerId: 0,
      pickedName: "",

      maxEmotion: null,
    };
  },
  props: {
    streamManager: Object,
    picked: String,
    userId: String,
  },

  mounted() {
    this.streamManager.addVideoElement(this.$el);
    console.log("비디오:" + this.$el);
    console.log("myWebCam:" + this.$refs.myWebCam);
    this.init();
  },
  computed: {
    ...mapState(memberStore, ["user"]),
    ...mapGetters(gameStore, ["isGameEnd"]),
  },
  methods: {
    ...mapMutations(gameStore, ["SET_EMOTION"]),
    ...mapGetters(gameStore, ["emotion"]),

    async init() {
      console.log("init");

      await faceapi.nets.faceExpressionNet.load("../models");
      await faceapi.loadTinyFaceDetectorModel("../models");
    },
    startExpressDetection() {
      console.log("얼굴인식되니");

      this.timerId = setInterval(async () => {
        if (!this.isGameEnd) {
          console.log(this.isGameEnd + this.picked + this.$el);
          this.detections = await faceapi
            .detectSingleFace(this.$el, new faceapi.TinyFaceDetectorOptions())
            .withFaceExpressions();
          if (this.detections) {
            let maxval = 0;
            for (let emo in this.detections.expressions) {
              if (this.detections.expressions[emo] > maxval) {
                maxval = this.detections.expressions[emo];
                this.maxEmotion = emo;
              }
            }
            console.log(this.maxEmotion);

            this.SET_EMOTION(this.maxEmotion);
          }
        } else {
          clearInterval(this.timerId);
        }
      }, 1000);
    },
  },
  watch: {
    picked() {
      clearInterval(this.timerId);
      if (this.userId == this.picked && !this.isGameEnd) {
        this.startExpressDetection();
      } else {
        this.SET_EMOTION("");
      }
    },
  },

  // },
};
</script>
