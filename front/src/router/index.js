import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import RoomList from "../views/RoomList.vue";
import WaitingRoom from "../views/WaitingRoom.vue";
import JokerGame from "../views/JokerGame.vue";
//import JokerGame2 from "../views/JokerGame2.vue";
Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/roomList",
    name: "RoomList",
    component: RoomList,
  },
  {
    path: "/room/:roomId",
    name: "WaitingRoom",
    component: WaitingRoom,
  },
  {
    path: "/jocker",
    name: "JokerGame",
    component: JokerGame,
  },
  /* {
    path: "/jocker2",
    name: "JokerGame2",
    component: JokerGame2,
  },*/
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
