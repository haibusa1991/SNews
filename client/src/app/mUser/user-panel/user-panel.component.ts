import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {PasswordValidators} from "../../utils/validators";

@Component({
  selector: 'app-user-panel',
  templateUrl: './user-panel.component.html',
  styleUrls: ['./user-panel.component.scss']
})
export class UserPanelComponent implements OnInit {
  noFileChosen: string = 'Не е избран файл.'
  imageFilename: string = this.noFileChosen;

  swap: boolean = false;
  private imageFile: File | null = null;
  isChangePassButtonDisabled: boolean = true

  uploadAvatarFormShown: boolean = false;

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
    imageFile: new FormControl()
  })

  changeEmailForm = new FormGroup({
    email: new FormControl('haibusa@abv.b', [
      Validators.email,
      Validators.required
    ])
  });

  constructor() {
  }

  ngOnInit(): void {

    // this.changePasswordForm
    //   .statusChanges
    //   .subscribe(formStatus => this.isChangePassButtonDisabled = formStatus == 'INVALID');
  }


  onFileChange(e: any) {
    this.imageFile = e.target.files[0];
    this.imageFilename = this.imageFile ? this.imageFile.name : this.noFileChosen;
  }

  onPasswordChange() {
    console.log('changing password!')
  }

  onAvatarUpload() {
    console.log('submitting avatar!')
  }

  onEmailChange() {
    console.log('changing email!')
  }
}
