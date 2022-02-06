<template>
  <div class="home">
    <room-list />
  </div>
</template>

<script>
// @ is an alias to /src
import RoomList from "@/views/RoomList.vue";
export default {
  name: "Home",
  components: {
    RoomList,
  },
};
</script>
