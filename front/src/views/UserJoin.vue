<template>
  <!-- 회원가입 Form -->
  <div class="wrapper">
    <!-- <img style="margin-bottom: 10px" src="@/assets/jammin.gif" /> -->
    <!-- <div
      id="title"
      class="card shadow p-3 mb-4 rounded-pill MainTitle"
      style="color: #616264; background-color: #fff6a0"
    >
      <h1 class="Jua">GotCha에 오신 것을 환영합니다!</h1>
    </div> -->
    <div id="join">
      <div class="container Jua">
        <div class="row justify-content-center align-items-center">
          <div
            class="card shadow"
            style="background-color: #ffcea0"
            id="JoinCard"
          >
            <h3 class="Jua" id="JoinTitle">회원가입</h3>
            <div class="card-body">
              <form
                id="JoinFrom"
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
                        <input
                          type="text"
                          id="inputId"
                          class="form-control"
                          v-model="user.userId"
                          placeholder="아이디를 입력해주세요."
                        /><button
                          type="button"
                          class="btn btn-light Jua"
                          @click="userIdCheck()"
                          style="background-color: #ff8989"
                        >
                          중복 확인
                        </button>
                      </div>
                      <p class="Jua" v-if="errorBag.user.userId">
                        {{ errorBag.user.userId[0] }}
                      </p>
                      <!-- {{ idlength }} -->
                      <div class="help-block with-errors text-danger"></div>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-md-12">
                    <div class="form-group">
                      <label class="Jua">닉네임</label>
                      <div class="input-group">
                        <input
                          type="text"
                          id="inputNickName"
                          v-model="user.nickName"
                          placeholder="닉네임을 입력해주세요."
                          class="form-control"
                        /><button
                          type="button"
                          class="btn btn-light Jua"
                          style="background-color: #ff8989"
                          @click="nickNameCheck()"
                        >
                          중복 확인
                        </button>
                      </div>
                      <p class="Jua" v-if="errorBag.user.nickName">
                        {{ errorBag.user.nickName[0] }}
                      </p>
                      <div class="help-block with-errors text-danger"></div>
                    </div>
                  </div>
                </div>

                <div class="row">
                  <div class="col-md-12">
                    <div class="form-group">
                      <label class="Jua">이메일</label>
                      <div class="input-group">
                        <input
                          type="text"
                          v-model="emailinput.email1"
                          placeholder="이메일을 입력해주세요."
                          class="form-control"
                        />@

                        <select
                          v-model="emailinput.email2"
                          class="custom-select"
                        >
                          <option value="" selected="selected" hidden="hidden">
                            이메일 선택
                          </option>
                          <option value="naver.com">naver.com</option>
                          <option value="hanmail.net">hanmail.net</option>
                          <option value="gmail.com">gmail.com</option>
                          <option value="ssafy.com">ssafy.com</option>
                        </select>
                      </div>
                      <p class="Jua" v-if="errorBag.user.email">
                        {{ errorBag.user.email[0] }}
                      </p>
                      <div class="help-block with-errors text-danger"></div>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-md-12">
                    <div class="form-group">
                      <label class="Jua">비밀번호</label>
                      <div class="input-group">
                        <input
                          type="password"
                          id="inputpassword"
                          v-model="user.password"
                          placeholder="비밀번호를 입력해주세요."
                          class="form-control"
                        />
                        <!-- <div>{{ ing }}</div> -->
                      </div>
                      <p class="Jua" v-if="errorBag.user.password">
                        {{ errorBag.user.password[0] }}
                      </p>
                      <div class="help-block with-errors text-danger"></div>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-md-12">
                    <div class="form-group">
                      <label class="Jua">비밀번호 확인</label>
                      <div class="input-group">
                        <input
                          type="password"
                          v-model="usercheck.passwordCheck"
                          id="passcheck"
                          placeholder="비밀번호를 입력해주세요."
                          class="form-control"
                        />
                      </div>
                      <p class="Jua" v-if="errorBag.user.passwordCheck">
                        {{ errorBag.user.passwordCheck[0] }}
                      </p>
                      <div class="help-block with-errors text-danger"></div>
                    </div>
                  </div>
                </div>
                <div class="row center">
                  <div class="btn">
                    <button
                      type="button"
                      class="btn btn-light btn-lg Jua shadow"
                      style="background-color: hsl(44, 92%, 61%)"
                      @click="join()"
                    >
                      Join</button
                    ><button
                      type="button"
                      class="btn btn-light btn-lg Jua shadow"
                      style="background-color: #ff8989"
                      @click="reset()"
                    >
                      reset
                    </button>
                  </div>
                </div>
              </form>
              <div class="form-group center">
                <div class="clear"></div>
                <br />

                <a class="Jua" style="color: #616264" href="/"
                  >로그인하러 가기</a
                >
                <br />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import validator from "@/api/validator.js";
export default {
  name: "Join",
  data() {
    return {
      emailinput: {
        email1: "",
        email2: "",
      },
      user: {
        userId: "",
        nickName: "",
        email: "",
        password: "",
      },
      usercheck: {
        passwordCheck: "",
        nickNameCheck: false,
        userIdCheck: false,
      },
      errorBag: {
        user: {
          userId: "",
          nickName: "",
          email: "",
          password: "",
          passwordCheck: "",
        },
      },
    };
  },

  watch: {
    "user.userId"(val) {
      this.errorBag.user.userId = validator.validateId("아이디", val);
    },
    "user.nickName"(val) {
      this.errorBag.user.nickName = validator.validateNickname("닉네임", val);
    },
    "emailinput.email1"(val) {
      this.errorBag.user.email = validator.validateEmail("이메일", val);
    },
    "user.password"(val) {
      this.errorBag.user.password = validator.validatePassword("비밀번호", val);
    },
    "usercheck.passwordCheck"(val) {
      this.errorBag.user.passwordCheck = validator.validatePwCheck(
        "비밀번호",
        val,
        this.user.password
      );
    },
  },

  methods: {
    join() {
      this.user.email = this.emailinput.email1 + "@" + this.emailinput.email2;
      if (
        this.errorBag.user.userId[0] ||
        this.errorBag.user.nickName[0] ||
        this.errorBag.user.email[0] ||
        this.errorBag.user.password[0] ||
        this.errorBag.user.passwordCheck[0] ||
        this.usercheck.userIdCheck == false ||
        this.usercheck.nickNameCheck == false
      ) {
        alert("회원가입 조건을 만족하지 않았습니다.");
      } else {
        axios
          .post(
            `http://localhost:8080/api/users/signup`,
            // `https://i6b102.p.ssafy.io/api/users/signup`,
            JSON.stringify(this.user)
          )
          .then((response) => {
            console.log(response);
            let msg = "회원가입에 실패했습니다.";
            if (response.data.message === "Success") {
              msg = "회원가입 되었습니다.";
            }
            alert(msg);
            this.$router.push("/");
          })
          .catch(() => {});
      }
    },
    userIdCheck() {
      if (this.user.userId == "") {
        alert("아이디를 입력하세요.");
      } else if (this.usercheck.userIdCheck == true) {
        alert("이미 확인되었습니다.");
      } else {
        axios({
          url: "http://localhost:8080/api/users/userIdCheck",
          // url: "https://i6b102.p.ssafy.io/api/users/userIdCheck",
          method: "GET",
          params: {
            userId: this.user.userId,
          },
        })
          .then((response) => {
            const uniqueUserIde = response.data;
            if (uniqueUserIde == false) {
              alert("중복된 아이디 입니다.");
              document.getElementById("nickcheck").value = "";
              this.usercheck.userIdCheck = false;
            } else {
              alert("사용 가능한 아이디 입니다.");
              this.usercheck.userIdCheck = true;
              document.getElementById("inputId").readOnly = true;
            }

            //   this.checkForm();
          })
          .catch(() => {});
      }
    },
    nickNameCheck() {
      if (this.user.nickName == "") {
        alert("닉네임을 입력하세요.");
      } else if (this.usercheck.nickNameCheck == true) {
        alert("이미 확인되었습니다.");
      } else {
        axios({
          url: "http://localhost:8080/api/users/nickNameCheck",
          // url: "https://i6b102.p.ssafy.io/api/users/nickNameCheck",
          method: "GET",
          params: {
            nickName: this.user.nickName,
          },
        })
          .then((response) => {
            const uniqueNickname = response.data;
            if (uniqueNickname == false) {
              alert("중복된 닉네임 입니다.");
              document.getElementById("nickcheck").value = "";
              this.usercheck.nickNameCheck = false;
            } else {
              alert("사용 가능한 닉네임 입니다.");
              this.usercheck.nickNameCheck = true;
              document.getElementById("inputNickName").readOnly = true;
            }

            //   this.checkForm();
          })
          .catch(() => {});
      }
    },
    reset() {
      document.getElementById("JoinFrom").reset();
      document.getElementById("inputId").readOnly = false;
      document.getElementById("inputNickName").readOnly = false;
    },
  },
};
</script>

<style>
#JoinFrom {
  width: 700px;
  margin: auto;
}
#JoinCard {
  width: 800px;
}
#JoinTitle {
  padding-top: 8%;
  padding-left: 5%;
}
.btn {
  margin: auto;
}
#join {
  text-align: left;
  margin-top: 120px;
}
.center {
  text-align: center;
}

p {
  color: red;
}
</style>
