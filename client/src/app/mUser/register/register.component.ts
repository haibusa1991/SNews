import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators} from "@angular/forms";
import {PasswordValidators} from "../../utils/validators";
import {UserService} from "../../core/user-service/user.service";
import {Router} from "@angular/router";
import {ConfigurationService} from "../../core/configuration/configuration.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  isRegisterButtonDisabled = true;
  isRegistrationOpen: boolean = true;

  passwordErrorMessages: { [key: string]: string } = {
    minlength: "Паролата трябва да бъде с дължина минимум 8 символа.",
    noLowercase: "Паролата трябва да съдържа малка буква.",
    noUppercase: "Паролата трябва да съдържа главна буква.",
    noNumeric: "Паролата трябва да съдържа цифра."
  }

  formErrorMessages: { [key: string]: string } = {
    usernameAlreadyRegistered: "Вече съществува потребител с това потребителско име.",
    emailAlreadyRegistered: "Вече съществува потребител с този email адрес.",
    registrationClosed: "Регистрацията на нови потребители е забранена.",
  }

  passwordErrorMessage = this.passwordErrorMessages['minlength'];
  formErrorMessage = '';
  hasRegistrationIssue: boolean = false;


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
    email: new FormControl('haibusa@abv.b', [
      Validators.email,
      Validators.required
    ]),
    username: new FormControl('haibusa', [
      Validators.required,
      Validators.minLength(3)
    ]),
    password: new FormControl('123456Aa', [
      Validators.required,
      Validators.minLength(8),
      PasswordValidators.uppercase(),
      PasswordValidators.lowercase(),
      PasswordValidators.numeric(),
    ]),
    repass: new FormControl('123456Aa', [
      Validators.required,
      this.passwordsMatchValidator()
    ])
  })


  constructor(private userService: UserService,
              private router: Router,
              private configurationService: ConfigurationService) {


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

    this.configurationService.isRegistrationEnabled$().subscribe(isOpen => {
      this.isRegistrationOpen = isOpen;
    });
  }


  onSubmit() {
    let form = this.registerForm.value;

    this.userService.register$(form.email!, form.username!, form.password!).subscribe({
      next: response => {
        if (response.isEmailTaken) {
          this.formErrorMessage = this.formErrorMessages['emailAlreadyRegistered'];
          this.hasRegistrationIssue = true;
          return;
        }

        if (response.isUsernameTaken) {
          this.formErrorMessage = this.formErrorMessages['usernameAlreadyRegistered'];
          this.hasRegistrationIssue = true;
          return;
        }

        if (response.isRegistrationClosed) {
          this.formErrorMessage = this.formErrorMessages['registrationClosed'];
          this.hasRegistrationIssue = true;
          return;
        }

        this.router.navigateByUrl('/user/login')
      },
      error: () => {  }
    })
  }

  updatePasswordError() {
    if (this.registerForm.controls.password.errors) {
      let firstError = Object.keys(this.registerForm.controls.password.errors)[0]
      this.passwordErrorMessage = this.passwordErrorMessages[firstError]
    }

  }
}
