<template>
  <div id="User">
    <b-avatar variant="secondary" class="mx-2"></b-avatar>
    <span class="Jua">{{ user.userId }}님 환영합니다.</span>
    <b-button variant="warning" class="mx-2 Jua" @click="logout()"
      >logout</b-button
    >
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
const memberStore = "memberStore";
export default {
  name: "User",
  computed: {
    ...mapState(memberStore, ["isLogin", "user"]),
  },
  methods: {
    ...mapMutations(memberStore, ["SET_IS_LOGIN", "SET_USER_INFO"]),
    logout() {
      this.SET_IS_LOGIN(false);
      this.SET_USER_INFO(null);
      sessionStorage.removeItem("access-token");
      this.$router.push({ name: "UserLogin" });
    },
  },
};
</script>

<style></style>
