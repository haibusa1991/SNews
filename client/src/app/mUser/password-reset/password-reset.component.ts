import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators} from "@angular/forms";
import {PasswordValidators} from "../../utils/validators";
import {UserService} from "../../core/user-service/user.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-password-reset',
  templateUrl: './password-reset.component.html',
  styleUrls: ['./password-reset.component.scss']
})
export class PasswordResetComponent implements OnInit {

  //todo DRY candidate - register/reset/change
  passwordErrorMessages: { [key: string]: string } = {
    minlength: "Паролата трябва да бъде с дължина минимум 8 символа.",
    noLowercase: "Паролата трябва да съдържа малка буква.",
    noUppercase: "Паролата трябва да съдържа главна буква.",
    noNumeric: "Паролата трябва да съдържа цифра."
  }

  isChangePasswordButtonDisabled = true;
  hasError: boolean = false;
  passwordErrorMessage: string = this.passwordErrorMessages['minlength'];

  passwordResetForm = new FormGroup({
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

//todo DRY candidate - register/reset/change
  passwordsMatchValidator(): ValidatorFn {
    return (c: AbstractControl): ValidationErrors | null => {
      let group = c.parent as FormGroup;
      let password = group?.controls?.['password'].value;
      let repass = c.value;
      let doMatch = password === repass;

      return !doMatch ? {passwordsMatch: true} : null;
    }
  }

  constructor(private userService: UserService, private router: Router, private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.passwordResetForm
      .controls
      .password
      .valueChanges
      .subscribe(() => {
        this.passwordResetForm.controls.repass.updateValueAndValidity()
      });

    this.passwordResetForm
      .statusChanges
      .subscribe(formStatus => this.isChangePasswordButtonDisabled = formStatus == 'INVALID');

    this.passwordResetForm.controls.password.valueChanges.subscribe(() => this.updatePasswordError())
  }

  onSubmit() {
    let form = this.passwordResetForm.value;
    let token = this.activatedRoute.snapshot.paramMap.get('passwordResetToken');

    this.userService.resetPassword$(form.password!, token!).subscribe(response => {
      if (!response) {
        this.hasError = true;
        return;
      }

      this.router.navigateByUrl('/user/login')
    })
  }

  updatePasswordError() {
    if (this.passwordResetForm.controls.password.errors) {
      let firstError = Object.keys(this.passwordResetForm.controls.password.errors)[0]
      this.passwordErrorMessage = this.passwordErrorMessages[firstError]
    }
  }

}
