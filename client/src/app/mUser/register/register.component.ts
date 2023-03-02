import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators} from "@angular/forms";
import {PasswordValidators} from "../../utils/validators";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  isRegisterButtonDisabled = true;
  passwordErrorMessages: { [key: string]: string } = {
    minlength: "Паролата трябва да бъде с дължина минимум 8 символа.",
    noLowercase: "Паролата трябва да съдържа малка буква.",
    noUppercase: "Паролата трябва да съдържа главна буква.",
    noNumeric: "Паролата трябва да съдържа цифра."
  }

  passwordErrorMessage = this.passwordErrorMessages['minlength'];

  passwordsMatchValidator(): ValidatorFn {
    return (c: AbstractControl): ValidationErrors | null => {
      let group = c.parent as FormGroup;
      let password = group?.controls?.['password'].value;
      let repass = c.value;
      let doMatch = password === repass;

      return !doMatch ? {passwordsMatch: true} : null;
    }
  }

  registerForm = new FormGroup({
    email: new FormControl('', [
      Validators.email,
      Validators.required
    ]),
    username: new FormControl('', [
      Validators.required,
      Validators.minLength(3)
    ]),
    password: new FormControl('', [
      Validators.required,
      Validators.minLength(8),
      PasswordValidators.uppercase(),
      PasswordValidators.lowercase(),
      PasswordValidators.numeric(),
    ]),
    repass: new FormControl('', [
      Validators.required,
      this.passwordsMatchValidator()
    ])
  })

  constructor() {
  }

  ngOnInit(): void {
    this.registerForm
      .controls
      .password
      .valueChanges
      .subscribe(() => {
        this.registerForm.controls.repass.updateValueAndValidity()
      });

    this.registerForm
      .statusChanges
      .subscribe(formStatus => this.isRegisterButtonDisabled = formStatus == 'INVALID');

    this.registerForm.controls.password.valueChanges.subscribe(() => this.updatePasswordError())
  }


  onSubmit() {
    console.log(this.registerForm.value);
  }

  updatePasswordError() {
    if (this.registerForm.controls.password.errors) {
      let firstError = Object.keys(this.registerForm.controls.password.errors)[0]
      this.passwordErrorMessage = this.passwordErrorMessages[firstError]
    }

  }
}
