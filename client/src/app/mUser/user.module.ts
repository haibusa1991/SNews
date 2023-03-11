import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {UserPanelComponent} from './user-panel/user-panel.component';
import { RouterModule, Routes} from "@angular/router";
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {MatIconModule} from "@angular/material/icon";
import {SharedModule} from "../mShared/shared.module";
import {ReactiveFormsModule} from "@angular/forms";
import {ForgottenPasswordComponent} from './forgotten-password/forgotten-password.component';
import { PasswordResetComponent } from './password-reset/password-reset.component';
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";

const moduleRoutes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    component: UserPanelComponent
  },
  {
    path: 'login',
    component: LoginComponent
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
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(moduleRoutes),
    MatInputModule,
    MatIconModule,
    MatButtonModule,
    SharedModule,
    ReactiveFormsModule
  ]
})
export class UserModule {
}
