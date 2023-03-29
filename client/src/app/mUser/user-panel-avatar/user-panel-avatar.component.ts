import {Component, OnInit} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {UserService} from "../../core/user-service/user.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ConfirmationDialogComponent} from "../confirmation-dialog/confirmation-dialog.component";
import {User} from "../../utils/types";
import {userEndpoints} from "../../../environments/environment";
import {Observable, of, switchMap, tap} from "rxjs";

@Component({
  selector: 'app-user-panel-avatar',
  templateUrl: './user-panel-avatar.component.html',
  styleUrls: ['./user-panel-avatar.component.scss']
})
export class UserPanelAvatarComponent implements OnInit {
  protected readonly userEndpoints = userEndpoints;

  hasCustomAvatar: boolean = false;
  isUploadAvatarButtonDisabled: boolean = true;
  isUploadAvatarFormShown: boolean = false;
  hasErrorUploading: boolean = false;


  noFileChosen: string = 'Не е избран файл.'
  imageFilename: string = this.noFileChosen;
  imageFile: File | null = null;
  avatarPreview: string = '';

  currentUser!: User;
  username!: string;

  uploadAvatarForm = new FormGroup({
    imageFile: new FormControl('', Validators.required)
  })

  constructor(public confirmationDialog: MatDialog, private userService: UserService) {
  }

  ngOnInit(): void {
    this.uploadAvatarForm.statusChanges.subscribe(formStatus => this.isUploadAvatarButtonDisabled = formStatus != 'VALID');
    this.userService.getCurrentUser$().subscribe(user => {
      this.currentUser = user!;
      this.hasCustomAvatar = !!this.currentUser.avatarId;
    });

    this.currentUser = this.userService.getCurrentUser()!;
    this.username = this.currentUser.username;

  }

  onAvatarUpload() {
    this.userService.uploadAvatar$(this.imageFile!).subscribe(isSuccessful => {
      if (!isSuccessful) {
        this.hasErrorUploading = true;
        return;
      }
      this.onAvatarChangeCancel();
    });
  }

  onRemoveAvatar() {
    let dialog = this.confirmationDialog.open(ConfirmationDialogComponent, {
      data: {message: 'Сигурни ли сте, че искате да премахнете аватара?'},
      autoFocus: "dialog",
    });

    dialog.afterClosed().pipe(
      switchMap(shouldRemoveAvatar => {
        if (shouldRemoveAvatar) {
          return this.userService.removeAvatar$()
        }
        return of('');
      })
    ).subscribe(() => {
      this.hasCustomAvatar = false;
      this.avatarPreview = '';
      this.userService.validateSession();
    })
  }

  onAvatarChangeCancel() {
    this.isUploadAvatarFormShown = false
    this.uploadAvatarForm.reset();
    this.imageFilename = this.noFileChosen;
    this.hasErrorUploading = false;
    this.avatarPreview = '';
  }

  onFileChange(e: any) {
    this.imageFile = e.target.files[0];
    this.imageFilename = this.imageFile ? this.imageFile.name : this.noFileChosen;

    if (!this.imageFile) {
      this.avatarPreview = '';
      return;
    }

    let reader = new FileReader();
    reader.onload = () => this.avatarPreview = reader.result as string;
    reader.readAsDataURL(this.imageFile);
  }
}
