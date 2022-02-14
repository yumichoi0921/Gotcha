<template>
  <video autoplay width="100%" ref="myWebCam" />
  <!-- <button @click="startExpressDetection">버튼</button> -->
</template>

<script>
import * as faceapi from "face-api.js";

//import * as tf from "@tensorflow/tfjs";
export default {
  name: "OvVideo",
  data() {
    return {
      //video: "",
      emotionModel: null,
      timerId: 0,
      pickedName: "",
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
    // this.$emit("video");
    // this.startExpressDetection();
  },
  methods: {
    async init() {
      console.log("왜안나와");

      await faceapi.nets.faceExpressionNet.load("../models");
      await faceapi.loadTinyFaceDetectorModel("../models");
      console.log("뭔데:" + faceapi.nets);
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
