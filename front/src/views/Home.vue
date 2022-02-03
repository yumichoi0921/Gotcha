<template>
  <div class="home">
    <room-list />
    <!--     
    <img alt="Vue logo" src="../assets/logo.png" />
    <HelloWorld msg="Welcome to Your Vue.js App" /> 
    -->
  </div>
</template>

<script>
// @ is an alias to /src
// import HelloWorld from "@/components/HelloWorld.vue";
// import Room from "@/components/RoomList/Room.vue";
import RoomList from "@/views/RoomList.vue";

export default {
  name: "Home",
  components: {
    // HelloWorld,
    // Room,
    RoomList,
  },
};
</script>
