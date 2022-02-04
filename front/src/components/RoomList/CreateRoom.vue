<template>
  <div>
    <b-form @submit="onSubmit" @reset="onReset">
      <b-form-group id="room-title-group" label="방제목" label-for="RoomTitle">
        <b-form-input
          id="room-title"
          v-model="form.roomTitle"
          placeholder="방제목을 입력하세요"
          required
        ></b-form-input>
      </b-form-group>

      <b-form-group
        id="room-password-group"
        label="비밀번호"
        label-for="RoomPassword"
      >
        <b-form-input
          id="room-password"
          v-model="form.roomPassword"
          placeholder="비공개방 체크를 먼저 해주세요"
        ></b-form-input>
        <b-form-checkbox
          id="room-isPrivate"
          v-model="form.isPrivate"
          name="room-isPrivate"
          value="true"
          unchecked-value="false"
          >체크</b-form-checkbox
        >
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
          required
        ></b-form-select>
      </b-form-group>

      <b-button type="submit" variant="primary">Submit</b-button>
      <b-button type="reset" variant="danger">Reset</b-button>
    </b-form>
    <b-card class="mt-3" header="Form Data Result">
      <pre class="m-0">{{ this.form }}</pre>
    </b-card>
  </div>
</template>

<script>
export default {
  name: "CreateRoom",
  components: {},
  data() {
    return {
      form: {
        roomTitle: "",
        roomPassword: "",
        isPrivate: "",
        roomCapacity: null,
      },
      capacityOptions: [{ text: "4", value: null }, "6"],
    };
  },
  methods: {
    onSubmit(event) {
      event.preventDefault();
      console.log(this.form);
    },
  },
};
</script>

<style></style>
