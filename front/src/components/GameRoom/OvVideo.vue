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
      emotions: {
        angry: 0,
        disgusted: 0,
        fearful: 0,
        happy: 0,
        neutral: 0,
        sad: 0,
        surprised: 0,
      },
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
  },
  methods: {
    ...mapMutations(gameStore, ["SET_EMOTION"]),
    ...mapGetters(gameStore, ["emotion"]),

    async init() {
      await faceapi.nets.faceExpressionNet.load("../models");
      await faceapi.loadTinyFaceDetectorModel("../models");
    },
    startExpressDetection() {
      console.log("얼굴인식되니");
      //    console.log(this.picked + this.$refs.myWebCam);

      this.timerId = setInterval(async () => {
        console.log(this.picked + this.$el);
        this.detections = await faceapi
          .detectSingleFace(this.$el, new faceapi.TinyFaceDetectorOptions())
          .withFaceExpressions();
        console.log(this.picked + " /" + this.user.userId);
        if (this.detections && this.picked == this.user.userId) {
          let maxval = 0;
          for (let emo in this.detections.expressions) {
            if (this.detections.expressions[emo] > maxval) {
              maxval = this.detections.expressions[emo];
              this.maxEmotion = emo;
            }
          }

          this.SET_EMOTION(this.maxEmotion);
        }
      }, 2500);

      // setTimeout(() => {
      //   console.log("끝내자");
      //   clearInterval(this.timerId);
      // }, 5000);
    },
  },
  watch: {
    picked() {
      clearInterval(this.timerId);
      if (this.userId == this.picked) {
        this.startExpressDetection();
      }
    },
  },
};
</script>
