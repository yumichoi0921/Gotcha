<template>
  <div class="wrapper">
    <img style="margin-bottom: 10px" src="@/assets/jammin.gif" />
    <div>
      <img src="@/assets/welcom.png" />
      <!-- <h1 class="Jua">GotCha에 오신 것을 환영합니다!</h1> -->
    </div>
    <div id="login">
      <div class="container">
        <div class="row justify-content-center align-items-center">
          <b-card
            class="shadow p-3 mb-5 rounded"
            id="card"
            style="color: #616264; background-color: #ffcea0"
          >
            <h2 class="Jua" id="LoginTitle">Login</h2>
            <div class="card-body">
              <form
                data-toggle="validator"
                role="form"
                method="post"
                action="#"
              >
                <div class="row">
                  <div class="col-md-12">
                    <div class="form-group">
                      <h3 class="Jua">ID</h3>
                      <div class="input-group input-group-lg">
                        <input
                          type="text"
                          class="form-control shadow-sm Jua"
                          v-model="user.userId"
                          placeholder="아이디를 입력해주세요."
                          @keyup.enter="login()"
                        />
                      </div>
                      <div class="help-block with-errors text-danger"></div>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-md-12">
                    <div class="form-group">
                      <h3 class="Jua">Password</h3>
                      <div class="input-group input-group-lg shadow-sm">
                        <input
                          type="password"
                          v-model="user.password"
                          placeholder="비밀번호를 입력해주세요."
                          class="form-control shadow-sm Jua"
                          @keyup.enter="login()"
                        />
                      </div>
                      <div class="help-block with-errors text-danger"></div>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="btn">
                    <button
                      type="button"
                      class="btn btn-light btn-lg Jua shadow"
                      @click="login()"
                      style="background-color: #ff8989"
                    >
                      Login
                    </button>
                  </div>
                </div>
              </form>
              <div class="form-group center">
                <div class="clear"></div>
                <br />
                <i class="fa fa-user fa-fw" /><span class="Jua fontsize18"
                  >처음 방문하십니까?
                </span>
                <router-link
                  :to="{
                    name: 'Userjoin',
                  }"
                  class="Jua fontsize18"
                >
                  회원가입
                </router-link>
              </div>
            </div>
          </b-card>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";

const memberStore = "memberStore";
export default {
  name: "Login",
  data() {
    return {
      user: {
        userId: "",
        password: "",
      },
    };
  },

  created() {
    window.onSignIn = this.onSignIn;
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "isLoginError"]),
  },
  methods: {
    ...mapActions(memberStore, ["userConfirm"]),
    async login() {
      if (this.user.userId == "" || this.user.password == "") {
        alert("아이디 또는 비밀번호를 입력해 주세요.");
      } else {
        await this.userConfirm(this.user);
        sessionStorage.getItem("access-token");
        if (this.isLogin) {
          alert("로그인 성공, 대기실로 이동합니다.");
          this.$router.push({ name: "Home" });
        }
      }
    },
  },
};
</script>

<style>
#LoginTitle {
  /* padding-block: 2%;
  padding-left: 5%; */
  text-align: center;
}
#card {
  width: 800px;
  height: 450px;
}
#login {
  text-align: left;
}

.center {
  text-align: center;
}

.asd {
  background-color: rgb(224, 224, 221);
}

.fontsize18 {
  font-size: 18px;
}
#title {
  text-align: center;
}
</style>
