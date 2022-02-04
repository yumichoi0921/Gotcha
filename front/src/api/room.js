import { apiInstance } from "./index.js";

const api = apiInstance();

function createRoom(room, success, fail) {
  api.post(`/api/rooms`, JSON.stringify(room)).then(success).catch(fail);
}

function roomList(succes, fail) {
  api.get(`api/rooms`).then(succes).catch(fail);
}

export { createRoom, roomList };
