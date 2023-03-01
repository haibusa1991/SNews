import {AbstractControl, ValidationErrors, ValidatorFn} from "@angular/forms";

export function passwordValidator(): ValidatorFn {
  return (c: AbstractControl): ValidationErrors | null => {
    if (!c.value) {
      return null;
    }
    let hasUpperCase = /[A-Z]+/.test(c.value);
    let hasLowerCase = /[a-z]+/.test(c.value);
    let hasNumeric = /[0-9]+/.test(c.value);

    let passwordValid = hasUpperCase && hasLowerCase && hasNumeric;

    return !passwordValid ? {passwordValid: true} : null;
  }
}
