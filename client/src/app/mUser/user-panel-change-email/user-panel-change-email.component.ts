import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {MatDialog} from "@angular/material/dialog";
import {MatSnackBar} from "@angular/material/snack-bar";
import {userEndpoints} from "../../../environments/environment";
import {PasswordReenterDialogComponent} from "../password-reenter-dialog/password-reenter-dialog.component";
import {UserService} from "../../core/user-service/user.service";

@Component({
  selector: 'app-user-panel-change-email',
  templateUrl: './user-panel-change-email.component.html',
  styleUrls: ['./user-panel-change-email.component.scss']
})
export class UserPanelChangeEmailComponent implements OnInit {
  private userEmail: string = '';
  isChangeEmailFormVisible: boolean = false
  isValidForm: boolean = false;

  changeEmailForm = new FormGroup({
    email: new FormControl('', [
      Validators.required,
      Validators.email
    ]),
  });


  constructor(public verifyPasswordDialog: MatDialog,
              private confirmationSnackbar: MatSnackBar,
              private userService: UserService) {
  }

  ngOnInit(): void {
    this.changeEmailForm.controls.email.disable()
    this.userService.getCurrentUser$(true).subscribe(u => {
      this.userEmail = u!.email
      this.changeEmailForm.controls.email.setValue(this.userEmail);
    });

    this.changeEmailForm.statusChanges.subscribe(isValid => {
      this.isValidForm = isValid == 'VALID'
    });

  }


  onEmailChange() {
    let endpoint = userEndpoints['changeEmail'];
    let payload = new FormData();
    payload.set('newEmail', this.changeEmailForm.controls.email.value!);

    let dialog = this.verifyPasswordDialog.open(PasswordReenterDialogComponent, {
      data: {endpoint, payload},
      autoFocus: "dialog",
    });

    dialog.afterClosed().subscribe(hasChangedPassword => {
      if (hasChangedPassword) {
        this.confirmationSnackbar.open('Email адресът е променен успешно.', 'ОK', {duration: 3000});
      }

      this.onEmailChangeCancel();
    });
  }

  onEmailChangeCancel() {
    this.isChangeEmailFormVisible = false;
    this.changeEmailForm.reset();
    this.changeEmailForm.controls.email.setValue(this.userEmail)
    this.changeEmailForm.controls.email.disable();
  }

  onEmailEdit() {
    this.isChangeEmailFormVisible = true;
    this.changeEmailForm.controls.email.enable();
    this.changeEmailForm.reset();
  }
}
