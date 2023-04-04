import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ModeratorPanelComponent} from './moderator-panel/moderator-panel.component';
import {RouterModule, Routes} from "@angular/router";
import {NewArticleComponent} from './new-article/new-article.component';
import {ReactiveFormsModule} from "@angular/forms";
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

const moduleRoutes: Routes = [
  {
    path: "moderation",
    component: ModeratorPanelComponent
  },
  {
    path: "new-article",
    component: NewArticleComponent
  },
  {
    path: "admin-panel",
    component: AdminPanelComponent
  },
  {
    path: "user/:username",
    component: UserDetailsComponent
  }
]


@NgModule({
  declarations: [
    ModeratorPanelComponent,
    NewArticleComponent,
    AdminPanelComponent,
    UsersSearchComponent,
    UserSummaryComponent,
    UserDetailsComponent
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
    MatSnackBarModule
  ]
})
export class SupportModule {
}
