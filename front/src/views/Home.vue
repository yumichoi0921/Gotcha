<template>
  <div class="home">
    <Room />
    <!--     
    <img alt="Vue logo" src="../assets/logo.png" />
    <HelloWorld msg="Welcome to Your Vue.js App" /> 
    -->
  </div>
</template>

<script>
// @ is an alias to /src
// import HelloWorld from "@/components/HelloWorld.vue";
import Room from "@/components/RoomList/Room.vue";

export default {
  name: "Home",
  components: {
    // HelloWorld,
    Room,
  },
};
</script>
