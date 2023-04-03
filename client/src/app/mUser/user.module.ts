import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {UserPanelComponent} from './user-panel/user-panel.component';
import {RouterModule, Routes} from "@angular/router";
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {MatIconModule} from "@angular/material/icon";
import {SharedModule} from "../mShared/shared.module";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ForgottenPasswordComponent} from './forgotten-password/forgotten-password.component';
import {PasswordResetComponent} from './password-reset/password-reset.component';
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
import {LogoutComponent} from './logout/logout.component';
import {MatDialogModule} from "@angular/material/dialog";
import { ConfirmationDialogComponent } from './confirmation-dialog/confirmation-dialog.component';
import { UserPanelAvatarComponent } from './user-panel-avatar/user-panel-avatar.component';
import { UserPanelChangePasswordComponent } from './user-panel-change-password/user-panel-change-password.component';
import { PasswordReenterDialogComponent } from './password-reenter-dialog/password-reenter-dialog.component';
import {MatSnackBarModule} from "@angular/material/snack-bar";
import { UserPanelChangeEmailComponent } from './user-panel-change-email/user-panel-change-email.component';

const moduleRoutes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    component: UserPanelComponent
  },
  {
    path: 'settings',
    component: UserPanelComponent
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'logout',
    component: LogoutComponent
  },
  {
    path: 'register',
    component: RegisterComponent
  },
  {
    path: 'forgotten-password',
    component: ForgottenPasswordComponent
  },
  {
    path: 'password-reset/:passwordResetToken',
    component: PasswordResetComponent
  }
];

@NgModule({
  declarations: [
    UserPanelComponent,
    LoginComponent,
    RegisterComponent,
    ForgottenPasswordComponent,
    PasswordResetComponent,
    LogoutComponent,
    ConfirmationDialogComponent,
    UserPanelAvatarComponent,
    UserPanelChangePasswordComponent,
    PasswordReenterDialogComponent,
    UserPanelChangeEmailComponent,

  ],
  imports: [
    CommonModule,
    RouterModule.forChild(moduleRoutes),
    MatInputModule,
    MatIconModule,
    MatButtonModule,
    SharedModule,
    ReactiveFormsModule,
    FormsModule,
    MatDialogModule,
    MatSnackBarModule
  ]
})
export class UserModule {
}
