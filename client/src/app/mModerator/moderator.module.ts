import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ModeratorPanelComponent } from './moderator-panel/moderator-panel.component';
import {RouterModule, Routes} from "@angular/router";

const moduleRoutes: Routes = [
  {
    path: "",
    pathMatch:"full",
    component: ModeratorPanelComponent
  }
]


@NgModule({
  declarations: [
    ModeratorPanelComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(moduleRoutes)
  ]
})
export class ModeratorModule { }
