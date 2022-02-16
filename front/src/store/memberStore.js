// import jwt_decode from "jwt-decode";
import { login } from "@/api/member.js";
// import { findById } from "@/api/member.js";
const memberStore = {
  namespaced: true,
  state: {
    isLogin: false,
    isLoginError: false,
    user: {
      userId: "",
    },
    token: "",
  },
  getters: {
    checkUserInfo: function (state) {
      return state.user;
    },
  },
  mutations: {
    SET_IS_LOGIN: (state, isLogin) => {
      state.isLogin = isLogin;
    },
    SET_IS_LOGIN_ERROR: (state, isLoginError) => {
      state.isLoginError = isLoginError;
    },
    SET_USER_INFO: (state, user) => {
      state.isLogin = true;
      state.user = user;
    },
    SET_ACCESS_TOKEN: (state, token) => {
      state.token = token;
    },
  },
  actions: {
    async userConfirm({ commit }, user) {
      await login(
        user,
        (response) => {
          if (response.status === 200) {
            // eslint-disable-next-line no-unused-vars
            let token = response.data["token"];
            commit("SET_IS_LOGIN", true);
            commit("SET_IS_LOGIN_ERROR", false);
            commit("SET_USER_INFO", user);
          }
        },
        // eslint-disable-next-line no-unused-vars
        (response) => {
          commit("SET_IS_LOGIN", false);
          commit("SET_IS_LOGIN_ERROR", true);
          alert("아이디, 비밀번호를 다시 확인해 주세요");
        }
      );
    },
    // getUserInfo({ commit }, token) {
    //   let decode_token = jwt_decode(token);
    //   findById(
    //     decode_token.userid,
    //     (response) => {
    //       if (response.data.message === "success") {
    //         console.log(response);
    //         commit("SET_USER_INFO", response.data.userInfo);
    //       } else {
    //         console.log("유저 정보를 찾을 수 없습니다.");
    //       }
    //     },
    //     (error) => {
    //       console.log(error);
    //     }
    //   );
    // },
    // ChangeUserInfo({}) {},
  },
};

export default memberStore;
