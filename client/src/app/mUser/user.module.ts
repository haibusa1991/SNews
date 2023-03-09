import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {UserPanelComponent} from './user-panel/user-panel.component';
import {Router, RouterModule, Routes} from "@angular/router";
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {MatInputModule} from "@angular/material/input";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {SharedModule} from "../mShared/shared.module";
import {ReactiveFormsModule} from "@angular/forms";
import {CsrfInterceptor} from "../core/interceptors/csrf-header";
import {HTTP_INTERCEPTORS} from "@angular/common/http";
import {CookieService} from "ngx-cookie-service";
import {ForgottenPasswordComponent} from './forgotten-password/forgotten-password.component';
import {EmailTemplateDeleteMeComponent} from './email-template-delete-me/email-template-delete-me.component';

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
    path: 'email-template',
    component: EmailTemplateDeleteMeComponent
  }
];

@NgModule({
  declarations: [
    UserPanelComponent,
    LoginComponent,
    RegisterComponent,
    ForgottenPasswordComponent,
    EmailTemplateDeleteMeComponent
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
