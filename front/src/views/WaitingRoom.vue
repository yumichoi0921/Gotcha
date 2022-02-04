<template>
  <div>웨이팅룸 아이디:{{ roomId }}</div>
</template>

<script>
export default {
  name: "WaitingRoom",
  data() {
    return {
      roomId: "",
    };
  },
  created() {
    this.roomId = this.$route.params.roomId;
  },
};
</script>

<style></style>
