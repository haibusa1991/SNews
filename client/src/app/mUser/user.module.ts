import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {UserPanelComponent} from './user-panel/user-panel.component';
import {Router, RouterModule, Routes} from "@angular/router";
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';

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
  }
];

@NgModule({
  declarations: [
    UserPanelComponent,
    LoginComponent,
    RegisterComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(moduleRoutes)
  ]
})
export class UserModule {
}
