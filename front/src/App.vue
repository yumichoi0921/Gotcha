<template>
  <div id="app">
    <audio autoplay loop controls class="rightTop">
      <source src="@/assets/River_Changes.mp3" type="audio/mp3" />
    </audio>
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
.rightTop {
  position: fixed;
  right: 20px;
  top: 50px;
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
