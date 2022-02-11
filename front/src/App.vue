<template>
  <div id="app">
    <div id="nav">
      <img src="@/assets/jammin.gif" />
      <h1 class="Jua">GotCha에 오신 것을 환영합니다!</h1>
    </div>
    <router-view />
  </div>
</template>
<script>
import "@/css/background.scss";

export default {
  name: "app",
};
</script>
<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  /* text-align: center; */
  color: #2c3e50;
  width: 75%;
  height: 100%;
  margin: auto;
}
/* 
#nav {
  margin-top: 300px;
} */
/* #nav a {
  font-weight: bold;
  color: #2c3e50;
}
#nav a.router-link-exact-active {
  color: #42b983;
} */
</style>
