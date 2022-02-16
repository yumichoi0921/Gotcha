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
    // console.log("비디오:" + this.$el);
    // console.log("myWebCam:" + this.$refs.myWebCam);
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
      // clearInterval(this.timerId);
      await faceapi.nets.faceExpressionNet.load("../models");
      await faceapi.loadTinyFaceDetectorModel("../models");
    },
    startExpressDetection() {
      clearInterval(this.timerId);
      this.timerId = setInterval(async () => {
        if (!this.isGameEnd) {
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
      }, 700);
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
