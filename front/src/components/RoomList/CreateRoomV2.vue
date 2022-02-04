<template>
  <transition name="modal">
    <div class="modal-mask">
      <div class="modal-wrapper">
        <div class="modal-container">
          <div class="modal-header">
            <slot name="header"></slot>
          </div>
          <div class="modal-body">
            <slot name="body">
              <!-- <b-form @submit="onSubmit" @reset="onReset"> -->
              <b-form @submit.prevent="onSubmit" class="text-left">
                <b-form-group
                  id="room-title-group"
                  label="방제목"
                  label-for="RoomTitle"
                >
                  <b-form-input
                    id="room-title"
                    v-model="form.roomTitle"
                    placeholder="방제목을 입력하세요"
                    :state="roomTitleValidation"
                    required
                  ></b-form-input>
                  <b-form-invalid-feedback :state="roomTitleValidation">
                    방제목은 2-10자까지 가능합니다.
                  </b-form-invalid-feedback>
                </b-form-group>

                <b-form-group
                  id="room-password-group"
                  label="비밀번호"
                  label-for="RoomPassword"
                  inline
                >
                  <b-form inline>
                    <b-form-input
                      id="room-password"
                      class="col-10 mr-3"
                      v-model="form.roomPassword"
                      placeholder="비공개방 체크를 먼저 해주세요"
                      :readonly="!form.isPrivate"
                      :state="roomPwdValidation"
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
                  <b-form-invalid-feedback :state="roomPwdValidation">
                    비밀번호는 2-4자까지 가능합니다.
                  </b-form-invalid-feedback>
                </b-form-group>

                <b-form-group
                  id="room-capacity-group"
                  label="인원"
                  label-for="RoomCapacity"
                >
                  <b-form-select
                    id="room-capacity"
                    v-model="form.roomCapacity"
                    :options="capacityOptions"
                    value-field="value"
                    text-field="text"
                    required
                  ></b-form-select>
                </b-form-group>

                <div class="mt-5 mx-5 d-flex justify-content-center">
                  <b-button type="reset" @click="$emit('close')" class="mx-3">
                    돌아가기</b-button
                  >
                  <b-button type="submit" variant="danger" class="mx-3"
                    >방만들기</b-button
                  >
                </div>
              </b-form>
            </slot>
          </div>

          <div class="modal-footer">
            <slot name="footer"> </slot>
          </div>
        </div>
      </div>
    </div>
  </transition>
</template>

<script>
import axios from "axios";
export default {
  name: "CreateRoom",
  components: {},
  data() {
    return {
      form: {
        roomTitle: "",
        roomPassword: "",
        isPrivate: false,
        roomCapacity: 4,
      },
      capacityOptions: [
        { text: "4", value: 4 },
        { text: "6", value: 6 },
      ],
    };
  },
  computed: {
    roomTitleValidation() {
      return (
        this.form.roomTitle.length >= 2 && this.form.roomTitle.length <= 10
      );
    },
    roomPwdValidation() {
      return (
        this.form.roomPassword.length >= 2 && this.form.roomPassword.length <= 4
      );
    },
  },
  watch: {
    "form.isPrivate"() {
      this.form.roomPassword = "";
    },
  },
  methods: {
    onSubmit() {
      axios
        .post("/books", { title: "1984" })
        .then((reponse) => {
          console.log(reponse);
        })
        .catch((error) => {
          console.log(error);
        });

      console.log(this.form);
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
