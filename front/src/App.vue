<template>
  <div id="app">
    <div id="nav">
      <img style="margin-bottom: 10px" src="@/assets/jammin.gif" />
      <div
        id="title"
        class="card shadow p-3 mb-4 rounded-pill MainTitle"
        style="color: #616264; background-color: #fff6a0"
      >
        <h1 class="Jua">GotCha에 오신 것을 환영합니다!</h1>
      </div>
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
  text-align: center;
  color: #2c3e50;
  width: 75%;
  height: 100%;
  margin: auto;
}
.MainTitle {
  width: 60%;
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
