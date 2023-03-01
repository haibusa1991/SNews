import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators} from "@angular/forms";
import {passwordValidator} from "../../utils/validators";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  isPasswordHidden = true;
  isRepassHidden = true;
  isRegisterButtonDisabled = true;


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
      passwordValidator()
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
      })

    this.registerForm
      .statusChanges
      .subscribe(formStatus => this.isRegisterButtonDisabled = formStatus == 'INVALID');
  }


  onSubmit() {
    console.log(this.registerForm.value);
  }
}
