import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './home/home.component';
import { ArticleOverviewComponent } from './article-overview/article-overview.component';
import {RouterModule} from "@angular/router";
import {SharedModule} from "../mShared/shared.module";



@NgModule({
  declarations: [
    HomeComponent,
    ArticleOverviewComponent,
  ],
    imports: [
        CommonModule,
        RouterModule,
        SharedModule
    ]
})
export class HomeModule { }
