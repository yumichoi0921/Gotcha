export default {
  validateId(key, val) {
    const errors = [];
    if (!val) {
      errors.push(`${key}가 비어있습니다.`);
    } else {
      if (val.length < 4) {
        errors.push(`${key}는 4 자 이상이어야 합니다.`);
      }
    }
    return errors;
  },

  validateNickname(key, val) {
    const errors = [];
    if (!val) {
      errors.push(`${key}이 비어있습니다.`);
    } else {
      if (val.length < 4) {
        errors.push(`${key}는 4 자 이상이어야 합니다.`);
      }
    }
    return errors;
  },

  validatePassword(key, val) {
    const errors = [];
    if (!val) {
      errors.push(`${key}가 비어있습니다.`);
    } else {
      if (val.length < 4) {
        errors.push(`${key}는 4자 이상이어야 합니다.`);
      }
    }
    return errors;
  },
  validateEmail(key, val) {
    const errors = [];
    if (!val) {
      errors.push(`${key}이 비어있습니다.`);
    }
    return errors;
  },
  validatePwCheck(key, val1, val2) {
    const errors = [];
    if (val1 != val2) {
      errors.push(`${key}를 다시 확인해 주세요.`);
    }
    return errors;
  },
  validateRoomTitle(key, val) {
    const errors = [];
    if (!val) {
      errors.push(`${key}이 비어있습니다.`);
    } else {
      if (val.length < 2 || val.length > 10) {
        errors.push(`${key}은 2자 이상, 4자 이하여야 합니다.`);
      }
    }
    return errors;
  },
  validateRoomPassword(key, val) {
    const errors = [];
    if (val.length < 2 || val.length > 4) {
      errors.push(`${key}는 2자 이상, 4자 이하여야 합니다.`);
    }
    return errors;
  },
};
