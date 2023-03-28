import {Component, OnInit} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {UserService} from "../../core/user-service/user.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ConfirmationDialogComponent} from "../confirmation-dialog/confirmation-dialog.component";
import {User} from "../../utils/types";

@Component({
  selector: 'app-user-panel-avatar',
  templateUrl: './user-panel-avatar.component.html',
  styleUrls: ['./user-panel-avatar.component.scss']
})
export class UserPanelAvatarComponent implements OnInit {
  hasCustomAvatar: boolean = false;
  isUploadAvatarButtonDisabled: boolean = true;
  isUploadAvatarFormShown: boolean = true;

  noFileChosen: string = 'Не е избран файл.'
  imageFilename: string = this.noFileChosen;
  private imageFile: File | null = null;

  currentUser!: User;
  username!: string;

  uploadAvatarForm = new FormGroup({
    imageFile: new FormControl('', Validators.required)
  })

  constructor(public confirmationDialog: MatDialog, private userService: UserService) {
  }

  ngOnInit(): void {
    this.uploadAvatarForm.statusChanges.subscribe(formStatus => this.isUploadAvatarButtonDisabled = formStatus != 'VALID');
    this.userService.getCurrentUser$().subscribe(user => this.currentUser != user);

    this.currentUser = this.userService.getCurrentUser()!;
    // this.username = this.userService.getCurrentUsername();
    this.username = this.currentUser.username;

  }

  onAvatarUpload() {
    console.log('submitting avatar!');
    this.userService.uploadAvatar$(this.imageFile!).subscribe(()=>{
      this.onAvatarChangeCancel();
    })
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
    this.isUploadAvatarFormShown = false
    this.uploadAvatarForm.reset();
    this.imageFilename = this.noFileChosen;
  }

  onFileChange(e: any) {
    this.imageFile = e.target.files[0];
    this.imageFilename = this.imageFile ? this.imageFile.name : this.noFileChosen;
  }

}
