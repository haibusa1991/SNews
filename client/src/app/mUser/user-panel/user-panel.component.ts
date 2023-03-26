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
  private imageFile: File | null = null;
  isChangePassButtonDisabled: boolean = true

  isUploadAvatarFormShown: boolean = false;
  isUploadAvatarButtonDisabled: boolean = true

  username!: string;
  seeCommentsHref!: string;
  hasOffences: boolean = false;


  // todo proper implementation with user roles
  isModerator:boolean=false;

  passwordErrorMessages: { [key: string]: string } = {
    minlength: "Паролата трябва да бъде с дължина минимум 8 символа.",
    noLowercase: "Паролата трябва да съдържа малка буква.",
    noUppercase: "Паролата трябва да съдържа главна буква.",
    noNumeric: "Паролата трябва да съдържа цифра."
  }

  passwordErrorMessage = this.passwordErrorMessages['minlength'];

  changePasswordForm = new FormGroup({
    password: new FormControl('totally not the password', [
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
    email: new FormControl('haibusa@abv.b', [
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


  onFileChange(e: any) {
    this.imageFile = e.target.files[0];
    this.imageFilename = this.imageFile ? this.imageFile.name : this.noFileChosen;
  }

  onPasswordChange() {
    console.log('changing password!');
  }

  onAvatarUpload() {
    console.log('submitting avatar!');
    this.onAvatarChangeCancel();
  }

  onEmailChange() {
    console.log('changing email!')
  }

  onRemoveAvatar() {
    let dialog = this.confirmationDialog.open(ConfirmationDialogComponent, {
      data: {message: 'Сигурни ли сте, че искате да премахнете аватара?'},
      autoFocus: "dialog",
    });

    dialog.afterClosed().subscribe(result => {
      if (result) {
        console.log('Removing avatar!');
      }
    });
  }

  onAvatarChangeCancel() {
    this.isUploadAvatarFormShown = !this.isUploadAvatarFormShown;
    this.uploadAvatarForm.reset();
    this.imageFilename = this.noFileChosen;
  }
}
