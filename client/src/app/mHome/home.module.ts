import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HomeComponent} from './home/home.component';
import {ArticleOverviewComponent} from './article-overview/article-overview.component';
import {RouterModule} from "@angular/router";
import {SharedModule} from "../mShared/shared.module";
import {ArticleComponent} from './article/article.component';


@NgModule({
  declarations: [
    HomeComponent,
    ArticleOverviewComponent,
    ArticleComponent,
  ],
  imports: [
    CommonModule,
    RouterModule,
    SharedModule
  ]
})
export class HomeModule {
}
