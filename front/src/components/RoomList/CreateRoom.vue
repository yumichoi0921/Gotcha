<template>
  <transition name="modal">
    <div id="CreateRoom" class="modal-mask">
      <div class="modal-wrapper">
        <div class="modal-container" style="background-color: #feced1">
          <div class="modal-body">
            <slot name="body">
              <b-form @submit.prevent="onSubmit" class="text-left">
                <b-form-group
                  id="room-title-group \"
                  label="방제목"
                  label-for="RoomTitle"
                  class="Jua"
                >
                  <b-form-input
                    id="room-title"
                    v-model="form.roomTitle"
                    placeholder="방제목을 입력하세요"
                    required
                  ></b-form-input>
                  <p class="Jua" v-if="errorBag.form.roomTitle">
                    {{ errorBag.form.roomTitle[0] }}
                  </p>
                </b-form-group>

                <b-form-group
                  id="room-password-group"
                  label="비밀번호"
                  label-for="RoomPassword"
                  inline
                  class="Jua"
                >
                  <b-form inline>
                    <b-form-input
                      id="room-password"
                      class="col-10 mr-3"
                      v-model="form.password"
                      placeholder="비공개방 체크를 먼저 해주세요"
                      :readonly="!form.isPrivate"
                      required
                    ></b-form-input>
                    <b-form-checkbox
                      id="room-isPrivate"
                      v-model="form.isPrivate"
                      name="room-isPrivate"
                    >
                      <p class="h3"><b-icon icon="lock"></b-icon></p>
                    </b-form-checkbox>
                  </b-form>
                  <p class="Jua" v-if="errorBag.form.password">
                    {{ errorBag.form.password[0] }}
                  </p>
                </b-form-group>

                <b-form-group id="room-capacity-group" label="인원" class="Jua">
                  <b-form-select
                    id="room-capacity"
                    v-model="form.capacity"
                    :options="capacityOptions"
                    value-field="value"
                    text-field="text"
                    required
                  ></b-form-select>
                </b-form-group>

                <div class="mt-5 mx-5 d-flex justify-content-center">
                  <b-button
                    type="reset"
                    @click="$emit('close')"
                    class="mx-3 Jua"
                  >
                    돌아가기</b-button
                  >
                  <b-button type="submit" variant="danger" class="mx-3 Jua"
                    >방만들기</b-button
                  >
                </div>
              </b-form>
            </slot>
          </div>
        </div>
      </div>
    </div>
  </transition>
</template>

<script>
import { createRoom } from "@/api/room.js";
import validator from "@/api/validator.js";
import { mapState } from "vuex";
const memberStore = "memberStore";
export default {
  name: "CreateRoom",
  components: {},
  data() {
    return {
      form: {
        roomTitle: "",
        password: "",
        isPrivate: false,
        capacity: 4,
      },
      capacityOptions: [
        { text: "4", value: 4 },
        { text: "6", value: 6 },
      ],
      errorBag: {
        form: {
          roomTitle: "",
          password: "",
        },
      },
    };
  },
  computed: {
    ...mapState(memberStore, ["user"]),
  },
  watch: {
    "form.roomTitle"(val) {
      this.errorBag.form.roomTitle = validator.validateRoomTitle("방제목", val);
    },
    "form.password"(val) {
      this.errorBag.form.password = validator.validateRoomPassword(
        "비밀번호",
        val
      );
    },
    "form.isPrivate"() {
      this.form.password = "";
    },
  },
  methods: {
    onSubmit() {
      if (this.errorBag.form.roomTitle[0] || this.errorBag.form.password[0]) {
        alert("방 생성 조건을 만족하지 않았습니다.");
      } else {
        createRoom(
          {
            hostId: this.user.userId,
            roomTitle: this.form.roomTitle,
            password: this.form.password,
            isPrivate: this.form.isPrivate,
            capacity: this.form.capacity,
          },
          (response) => {
            const roomId = response.data.roomId;
            this.$router.push({
              name: "GameRoom",
              params: { roomId: roomId },
            });
          }
        );
      }
    },
  },
};
</script>

<style>
.modal-mask {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: table;
  transition: opacity 0.3s ease;
}

.modal-wrapper {
  display: table-cell;
  vertical-align: middle;
}

.modal-container {
  width: 600px;
  margin: 0px auto;
  padding: 20px 30px;
  background-color: #fff;
  border-radius: 2px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
  transition: all 0.3s ease;
  font-family: Helvetica, Arial, sans-serif;
}

.modal-header h3 {
  margin-top: 0;
  color: #42b983;
}

.modal-body {
  margin: 20px 0;
}

.modal-footer {
  justify-content: center;
}
.modal-default-button {
  float: right;
}

/*
 * The following styles are auto-applied to elements with
 * transition="modal" when their visibility is toggled
 * by Vue.js.
 *
 * You can easily play with the modal transition by editing
 * these styles.
 */

.modal-enter {
  opacity: 0;
}

.modal-leave-active {
  opacity: 0;
}

.modal-enter .modal-container,
.modal-leave-active .modal-container {
  -webkit-transform: scale(1.1);
  transform: scale(1.1);
}
</style>
