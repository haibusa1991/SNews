import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {UserPanelComponent} from './user-panel/user-panel.component';
import {Router, RouterModule, Routes} from "@angular/router";

const moduleRoutes: Routes = [
  {
    path: "",
    pathMatch:"full",
    component: UserPanelComponent
  }
]

@NgModule({
  declarations: [
    UserPanelComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(moduleRoutes)
  ]
})
export class UserControlPanelModule {
}
