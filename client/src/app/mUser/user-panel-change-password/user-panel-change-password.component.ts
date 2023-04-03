import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {PasswordValidators} from "../../utils/validators";
import {MatDialog} from "@angular/material/dialog";
import {PasswordReenterDialogComponent} from "../password-reenter-dialog/password-reenter-dialog.component";
import {userEndpoints} from "../../../environments/environment";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-user-panel-change-password',
  templateUrl: './user-panel-change-password.component.html',
  styleUrls: ['./user-panel-change-password.component.scss']
})
export class UserPanelChangePasswordComponent implements OnInit {
  @Input()
  isVisible: boolean = true;

  @Output() isActive = new EventEmitter<boolean>();

  isChangePasswordFormVisible: boolean = false
  isValidForm: boolean = false;

  passwordErrorMessages: { [key: string]: string } = {
    minlength: "Паролата трябва да бъде с дължина минимум 8 символа.",
    noLowercase: "Паролата трябва да съдържа малка буква.",
    noUppercase: "Паролата трябва да съдържа главна буква.",
    noNumeric: "Паролата трябва да съдържа цифра."
  }

  passwordErrorMessage = this.passwordErrorMessages['minlength'];
  passwordPlaceholder = 'not the password :)'

  changePasswordForm = new FormGroup({
    password: new FormControl(this.passwordPlaceholder, [
      Validators.required,
      Validators.minLength(8),
      PasswordValidators.uppercase(),
      PasswordValidators.lowercase(),
      PasswordValidators.numeric(),
    ]),
  });


  constructor(public verifyPasswordDialog: MatDialog, private confirmationSnackbar: MatSnackBar) {

  }

  ngOnInit(): void {
    this.changePasswordForm.controls.password.disable()

    this.changePasswordForm.statusChanges.subscribe(isValid => {
      this.isValidForm = isValid == 'VALID'
    });

    this.changePasswordForm.controls.password.valueChanges.subscribe(() => {
      this.updatePasswordError();
    })
  }


  onPasswordChange() {
    let endpoint = userEndpoints['changePassword'];
    let payload = new FormData();
    payload.set('newPassword', this.changePasswordForm.controls.password.value!);

    let dialog = this.verifyPasswordDialog.open(PasswordReenterDialogComponent, {
      data: {endpoint, payload},
      autoFocus: "dialog",
    });

    dialog.afterClosed().subscribe(hasChangedPassword => {
      if (hasChangedPassword) {
        this.confirmationSnackbar.open('Паролата е променена успешно.', 'ОK', {duration: 3000});
      }

      this.onPasswordChangeCancel();
    });
  }

  onPasswordChangeCancel() {
    this.isChangePasswordFormVisible = false;
    this.changePasswordForm.reset();
    this.changePasswordForm.controls.password.setValue(this.passwordPlaceholder)
    this.changePasswordForm.controls.password.disable();
    this.isActive.emit(false);
  }

  updatePasswordError() {
    if (this.changePasswordForm.controls.password.errors) {
      let firstError = Object.keys(this.changePasswordForm.controls.password.errors)[0]
      this.passwordErrorMessage = this.passwordErrorMessages[firstError] ? this.passwordErrorMessages[firstError] : this.passwordErrorMessages['minlength'];
    }
  }

  onPasswordEdit() {
    this.isChangePasswordFormVisible = true;
    this.changePasswordForm.controls.password.enable();
    this.changePasswordForm.reset();
    this.isActive.emit(true);
  }
}
