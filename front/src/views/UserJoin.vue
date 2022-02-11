<template>
  <!-- 회원가입 Form -->
  <div class="wrapper">
    <div id="join">
      <div class="container">
        <div class="row justify-content-center align-items-center">
          <div class="col-sm-6">
            <div class="card">
              <h4
                class="card-header Jua center"
                style="color: #616264; background-color: #ffc0cb"
              >
                회원가입
              </h4>
              <div class="card-body" style="background-color: #fff0f5">
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
                          <div class="input-group-prepend">
                            <span class="input-group-text">
                              <i class="fa fa-user" aria-hidden="true" />
                            </span>
                          </div>
                          <input
                            type="text"
                            id="inputId"
                            class="form-control"
                            v-model="user.userId"
                            placeholder="아이디를 입력해주세요."
                          /><button
                            type="button"
                            class="btn btn-outline-light Jua"
                            @click="userIdCheck()"
                            style="background-color: #bea3c9"
                          >
                            중복 확인
                          </button>
                        </div>
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
                          <div class="input-group-prepend">
                            <span class="input-group-text">
                              <i class="fa fa-lock" aria-hidden="true" />
                            </span>
                          </div>
                          <input
                            type="text"
                            id="inputNickName"
                            v-model="user.nickName"
                            placeholder="닉네임을 입력해주세요."
                            class="form-control"
                          /><button
                            type="button"
                            class="btn btn-outline-light Jua"
                            style="background-color: #bea3c9"
                            @click="nickNameCheck()"
                          >
                            중복 확인
                          </button>
                        </div>
                        <div class="help-block with-errors text-danger"></div>
                      </div>
                    </div>
                  </div>

                  <div class="row">
                    <div class="col-md-12">
                      <div class="form-group">
                        <label class="Jua">이메일</label>
                        <div class="input-group">
                          <div class="input-group-prepend">
                            <span class="input-group-text">
                              <i class="fa fa-lock" aria-hidden="true" />
                            </span>
                          </div>
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
                            <option
                              value=""
                              selected="selected"
                              hidden="hidden"
                            >
                              이메일 선택
                            </option>
                            <option value="naver.com">naver.com</option>
                            <option value="hanmail.net">hanmail.net</option>
                            <option value="gmail.com">gmail.com</option>
                            <option value="ssafy.com">ssafy.com</option>
                          </select>
                        </div>
                        <div class="help-block with-errors text-danger"></div>
                      </div>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-md-12">
                      <div class="form-group">
                        <label class="Jua">비밀번호</label>
                        <div class="input-group">
                          <div class="input-group-prepend">
                            <span class="input-group-text">
                              <i class="fa fa-lock" aria-hidden="true" />
                            </span>
                          </div>
                          <input
                            type="password"
                            id="inputpassword"
                            v-model="user.password"
                            placeholder="비밀번호를 입력해주세요."
                            class="form-control"
                          />
                          <!-- <div>{{ ing }}</div> -->
                        </div>
                        <div class="help-block with-errors text-danger"></div>
                      </div>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-md-12">
                      <div class="form-group">
                        <label class="Jua">비밀번호 확인</label>
                        <div class="input-group">
                          <div class="input-group-prepend">
                            <span class="input-group-text">
                              <i class="fa fa-lock" aria-hidden="true" />
                            </span>
                          </div>
                          <input
                            type="password"
                            v-model="usercheck.passwordCheck"
                            id="passcheck"
                            placeholder="비밀번호를 입력해주세요."
                            class="form-control"
                          />
                        </div>
                        <div class="help-block with-errors text-danger"></div>
                      </div>
                    </div>
                  </div>
                  <div class="row center">
                    <div class="btn">
                      <button
                        type="button"
                        class="btn btn-outline-light Jua"
                        style="background-color: #ba4160"
                        @click="join()"
                      >
                        Join</button
                      ><button
                        type="button"
                        class="btn btn-outline-light Jua"
                        style="background-color: #ba4160"
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

                  <a class="Jua" style="color: #616264" href="/login"
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
  </div>
</template>
<script>
import axios from "axios";
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
    };
  },

  methods: {
    join() {
      this.user.email = this.emailinput.email1 + "@" + this.emailinput.email2;
      if (this.user.userId == "") {
        alert("아이디를 입력하세요.");
      } else if (this.user.nickName == "") {
        alert("닉네임을 입력하세요.");
      } else if (this.emailinput.email1 == "" || this.emailinput.email2 == "") {
        alert("이메일을 입력하세요.");
      } else if (this.user.password == "") {
        alert("비밀번호를 입력하세요.");
      } else if (this.user.password != this.usercheck.passwordCheck) {
        alert("비밀번호가 일치하지 않습니다.");
        document.getElementById("inputpassword").value = "";
        document.getElementById("passcheck").value = "";
      } else if (
        this.usercheck.userIdCheck == false ||
        this.usercheck.nickNameCheck == false
      ) {
        alert("중복 확인을 해주세요.");
      } else {
        axios
          .post(
            `http://localhost:8080/api/users/signup`,
            JSON.stringify(this.user)
          )
          .then((response) => {
            console.log(response);
            let msg = "회원가입에 실패했습니다.";
            if (response.data.message === "Success") {
              msg = "회원가입 되었습니다.";
            }
            alert(msg);
            this.$router.push("/login");
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
    },
  },
};
</script>

<style>
.btn {
  margin: auto;
}
#join {
  text-align: left;
}
.center {
  text-align: center;
}
</style>
