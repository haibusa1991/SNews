import {Component, Inject, OnInit} from "@angular/core";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../../core/user-service/user.service";

@Component({
  selector: 'app-password-reenter-dialog',
  templateUrl: './password-reenter-dialog.component.html',
  styleUrls: ['./password-reenter-dialog.component.scss']
})
export class PasswordReenterDialogComponent implements OnInit {
  isPasswordHidden: boolean = true;
  isConfirmDisabled: boolean = true;
  isLoading: boolean = false;
  hasBadCredentials = false;

  verifyForm: FormGroup = new FormGroup({
    password: new FormControl('', Validators.required),
  });

  constructor(
    private userService: UserService,
    public dialogRef: MatDialogRef<PasswordReenterDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { endpoint: string, payload: FormData },
  ) {
  }

  ngOnInit(): void {
    this.verifyForm.statusChanges.subscribe(status => this.isConfirmDisabled = status != 'VALID')
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onSubmit() {
    this.isLoading = true;
    this.data.payload.set('currentPassword',this.verifyForm.controls['password'].value)

    this.userService.makePostRequest$(this.data.endpoint, this.data.payload).subscribe({
      next: isSuccessful => {
        this.isLoading = false;
        if (isSuccessful) {
          this.dialogRef.close(true);
          return;
        }
        this.hasBadCredentials = true;
      },
    })
  }


}

