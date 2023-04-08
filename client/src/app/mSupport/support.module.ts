import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ModeratorPanelComponent} from './moderator-panel/moderator-panel.component';
import {RouterModule, Routes} from "@angular/router";
import {NewArticleComponent} from './new-article/new-article.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {SharedModule} from "../mShared/shared.module";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatSelectModule} from "@angular/material/select";
import {MatChipsModule} from "@angular/material/chips";
import {MatInputModule} from "@angular/material/input";
import { AdminPanelComponent } from './admin-panel/admin-panel.component';
import { UsersSearchComponent } from './users-search/users-search.component';
import { UserSummaryComponent } from './user-summary/user-summary.component';
import { UserDetailsComponent } from './user-details/user-details.component';
import {MatSnackBarModule} from "@angular/material/snack-bar";
import { ServerConfigurationComponent } from './server-configuration/server-configuration.component';
import {MatSlideToggleModule} from "@angular/material/slide-toggle";
import {isModerator} from "../guards/isModerator";
import {isAdministrator} from "../guards/isAdministrator";

const moduleRoutes: Routes = [
  {
    path: "moderation",
    component: ModeratorPanelComponent,
    canActivate: [()=>isModerator()]
  },
  {
    path: "new-article",
    component: NewArticleComponent,
    canActivate: [()=>isAdministrator()]

  },
  {
    path: "admin-panel",
    component: AdminPanelComponent,
    canActivate: [()=>isAdministrator()]
  },
  {
    path: "user/:username",
    component: UserDetailsComponent,
    canActivate: [()=>isAdministrator()]
  }
]


@NgModule({
  declarations: [
    ModeratorPanelComponent,
    NewArticleComponent,
    AdminPanelComponent,
    UsersSearchComponent,
    UserSummaryComponent,
    UserDetailsComponent,
    ServerConfigurationComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(moduleRoutes),
    SharedModule,
    ReactiveFormsModule,
    MatIconModule,
    MatButtonModule,
    MatFormFieldModule,
    MatSelectModule,
    MatChipsModule,
    MatInputModule,
    MatSnackBarModule,
    MatSlideToggleModule,
    FormsModule
  ]
})
export class SupportModule {
}
