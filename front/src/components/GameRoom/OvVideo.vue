<template>
  <video autoplay width="100%" ref="myWebCam" />
</template>

<script>
import * as faceapi from "face-api.js";

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
      maxEmotion: "",
    };
  },
  props: {
    streamManager: Object,
    picked: String,
    userId: String,
  },
  emits: ["maxEmotion"],

  mounted() {
    this.streamManager.addVideoElement(this.$el);
    console.log("비디오:" + this.$el);
    console.log("myWebCam:" + this.$refs.myWebCam);
    this.init();
  },
  methods: {
    async init() {
      await faceapi.nets.faceExpressionNet.load("../models");
      await faceapi.loadTinyFaceDetectorModel("../models");
    },
    startExpressDetection() {
      console.log("얼굴인식되니");
      console.log(this.picked + this.$refs.myWebCam);

      this.timerId = setInterval(async () => {
        console.log(this.picked + this.$refs.myWebCam);
        this.detections = await faceapi
          .detectSingleFace(
            this.$refs.myWebCam,
            new faceapi.TinyFaceDetectorOptions()
          )
          .withFaceExpressions();
        if (this.detections) {
          let maxval = 0;
          for (let emo in this.detections.expressions) {
            if (this.detections.expressions[emo] > maxval) {
              maxval = this.detections.expressions[emo];
              this.maxEmotion = emo;
            }
          }
        }
        this.$emit("maxEmotion", this.maxEmotion);
        console.log(this.detections);
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
