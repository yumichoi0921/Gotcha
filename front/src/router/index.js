import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import RoomList from "../views/RoomList.vue";
import GameRoom from "../views/GameRoom.vue";
import JokerGame from "../views/JokerGame.vue";
import UserLogin from "../views/UserLogin.vue";
import UserJoin from "../views/UserJoin.vue";
import store from "@/store/index.js";
Vue.use(VueRouter);
const onlyAuthUser = async (to, from, next) => {
  const checkUserInfo = store.getters["memberStore/checkUserInfo"];

  if (checkUserInfo === null) {
    alert("로그인이 필요한 페이지입니다..");
    // next({ name: "SignIn" });
    router.push({ name: "UserLogin" });
  } else {
    console.log("로그인 했다.");
    next();
  }
};
const routes = [
  {
    path: "/",
    name: "UserLogin",
    component: UserLogin,
  },
  {
    path: "/join",
    name: "Userjoin",
    component: UserJoin,
  },
  {
    path: "/home",
    name: "Home",
    component: Home,
    beforeEnter: onlyAuthUser,
  },
  {
    path: "/roomList",
    name: "RoomList",
    component: RoomList,
    beforeEnter: onlyAuthUser,
  },
  {
    path: "/room/:roomId",
    name: "GameRoom",
    component: GameRoom,
    beforeEnter: onlyAuthUser,
  },
  {
    path: "/joker",
    name: "JokerGame",
    component: JokerGame,
    beforeEnter: onlyAuthUser,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
