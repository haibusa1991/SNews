import {Component, Inject} from "@angular/core";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-password-reenter-dialog',
  templateUrl: './password-reenter-dialog.component.html',
  styleUrls: ['./password-reenter-dialog.component.scss']
})
export class PasswordReenterDialogComponent {

  result:boolean = true;

  constructor(
    public dialogRef: MatDialogRef<PasswordReenterDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { message: string },
  ) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}

