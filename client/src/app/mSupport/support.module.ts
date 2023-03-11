import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ModeratorPanelComponent } from './moderator-panel/moderator-panel.component';
import {RouterModule, Routes} from "@angular/router";
import { NewArticleComponent } from './new-article/new-article.component';
import {ReactiveFormsModule} from "@angular/forms";
import {MatInputModule} from "@angular/material/input";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {SharedModule} from "../mShared/shared.module";
import {MatSelectModule} from "@angular/material/select";
import {MatChipsModule} from "@angular/material/chips";

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
    ReactiveFormsModule,
    MatInputModule,
    MatIconModule,
    MatButtonModule,
    SharedModule,
    MatSelectModule,
    MatChipsModule,
  ]
})
export class SupportModule { }
