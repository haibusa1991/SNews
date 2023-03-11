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

const moduleRoutes: Routes = [
  {
    path: "moderation",
    component: ModeratorPanelComponent
  },
  {
    path: "new-article",
    component: NewArticleComponent
  }
]


@NgModule({
  declarations: [
    ModeratorPanelComponent,
    NewArticleComponent
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
    MatInputModule
  ]
})
export class SupportModule {
}
