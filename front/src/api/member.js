import { apiInstance } from "./index.js";

const api = apiInstance();

async function login(user, success, fail) {
  console.log(JSON.stringify(user));
  await api
    .post(`/api/users/login`, JSON.stringify(user))
    .then(success)
    .catch(fail);
}
async function findById(userid, success, fail) {
  api.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
  await api.get(`/user/info/${userid}`).then(success).catch(fail);
}
export { login, findById };
