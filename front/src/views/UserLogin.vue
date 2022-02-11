<template>
  <div class="wrapper">
    <div id="login">
      <div class="container">
        <div class="row justify-content-center align-items-center">
          <div class="col-sm-6">
            <div class="card">
              <h4
                class="card-header Jua center"
                style="background-color: rgb(224, 224, 221)"
              >
                로그인
              </h4>
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
                        <label class="Jua">ID</label>
                        <div class="input-group">
                          <div class="input-group-prepend">
                            <span class="input-group-text">
                              <i class="fa fa-user" aria-hidden="true" />
                            </span>
                          </div>
                          <input
                            type="text"
                            class="form-control"
                            v-model="user.userId"
                            placeholder="아이디를 입력해주세요."
                          />
                        </div>
                        <div class="help-block with-errors text-danger"></div>
                      </div>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-md-12">
                      <div class="form-group">
                        <label class="Jua">Password</label>
                        <div class="input-group">
                          <div class="input-group-prepend">
                            <span class="input-group-text">
                              <i class="fa fa-lock" aria-hidden="true" />
                            </span>
                          </div>
                          <input
                            type="password"
                            v-model="user.password"
                            placeholder="비밀번호를 입력해주세요."
                            class="form-control"
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
                        class="btn btn-primary btn-lg btn-block Jua"
                        @click="login()"
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
                  <a class="Jua fontsize18" href="/join">회원가입</a>
                  <br />
                </div>
              </div>
            </div>
          </div>
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
      await this.userConfirm(this.user);
      sessionStorage.getItem("access-token");
      if (this.isLogin) {
        alert("로그인 성공, 대기실로 이동합니다.");
        this.$router.push({ name: "Home" });
      }
    },
  },
};
</script>

<style>
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
</style>
