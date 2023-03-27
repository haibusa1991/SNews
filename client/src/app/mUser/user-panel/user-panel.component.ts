import {Component, Inject, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {PasswordValidators} from "../../utils/validators";
import {MatDialog, MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {ConfirmationDialogComponent} from "../confirmation-dialog/confirmation-dialog.component";
import {UserService} from "../../core/user-service/user.service";
import {userEndpoints} from "../../../environments/environment";

@Component({
  selector: 'app-user-panel',
  templateUrl: './user-panel.component.html',
  styleUrls: [
    './user-panel-shared.component.scss',
    './user-panel-avatar.component.scss',
    './user-panel-moderation.component.scss',
    './user-panel-credentials.component.scss']
})
export class UserPanelComponent implements OnInit {
  noFileChosen: string = 'Не е избран файл.'
  imageFilename: string = this.noFileChosen;

  hasCustomAvatar: boolean = false;
  isChangePassButtonDisabled: boolean = true

  isUploadAvatarFormShown: boolean = false;
  isUploadAvatarButtonDisabled: boolean = true

  username!: string;
  seeCommentsHref!: string;
  hasOffences: boolean = false;

  isChangePasswordFormVisible: boolean = false
  isChangeEmailFormVisible: boolean = false


  // todo proper implementation with user roles
  // todo move section to moderation panel
  isModerator: boolean = false;

  passwordErrorMessages: { [key: string]: string } = {
    minlength: "Паролата трябва да бъде с дължина минимум 8 символа.",
    noLowercase: "Паролата трябва да съдържа малка буква.",
    noUppercase: "Паролата трябва да съдържа главна буква.",
    noNumeric: "Паролата трябва да съдържа цифра."
  }

  passwordErrorMessage = this.passwordErrorMessages['minlength'];

  changePasswordForm = new FormGroup({
    password: new FormControl('', [
      Validators.required,
      Validators.minLength(8),
      PasswordValidators.uppercase(),
      PasswordValidators.lowercase(),
      PasswordValidators.numeric(),
    ]),
  });

  uploadAvatarForm = new FormGroup({
    imageFile: new FormControl('', Validators.required)
  })

  changeEmailForm = new FormGroup({
    email: new FormControl('', [
      Validators.email,
      Validators.required
    ])
  });

  constructor(public confirmationDialog: MatDialog, private userService: UserService) {
  }

  ngOnInit(): void {
    this.uploadAvatarForm.statusChanges.subscribe(formStatus => this.isUploadAvatarButtonDisabled = formStatus != 'VALID');
    this.username = this.userService.getCurrentUsername();
    this.seeCommentsHref = `${userEndpoints['allUserComments']}/${this.username}`

    // this.changePasswordForm
    //   .statusChanges
    //   .subscribe(formStatus => this.isChangePassButtonDisabled = formStatus == 'INVALID');
  }


  onPasswordChange() {
    console.log('changing password!');
  }



  onEmailChange() {
    console.log('changing email!')
  }


  onPasswordChangeCancel() {
    this.isChangePasswordFormVisible = false;
    this.changePasswordForm.reset();
  }

  onEmailChangeCancel() {
    this.isChangeEmailFormVisible = false;
    this.changeEmailForm.reset();
  }

}
