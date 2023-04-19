import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HomeComponent} from './home/home.component';
import {ArticleOverviewComponent} from './article-overview/article-overview.component';
import {RouteReuseStrategy, RouterModule, Routes} from "@angular/router";
import {SharedModule} from "../mShared/shared.module";
import {ArticleComponent} from './article/article.component';
import { NewsCategory } from './news-category/news-category.component';
import { SearchResultsComponent } from './search-results/search-results.component';
import {RouterConfiguration} from "../configuration/RouterConfiguration";
import { CommentsComponent } from './comments/comments.component';


const moduleRoutes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    component: HomeComponent
  },
  {
    path: ':category',
    component: NewsCategory
  },
  {
    path: 'article/:articleHref',
    component: ArticleComponent
  },
  {
    path: 'article/:articleHref/comments',
    component: CommentsComponent
  },
  {
    path: 'search-results/:keywords',
    component: SearchResultsComponent
  },
];


@NgModule({
  declarations: [
    HomeComponent,
    ArticleOverviewComponent,
    ArticleComponent,
    NewsCategory,
    SearchResultsComponent,
    CommentsComponent,
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(moduleRoutes),
    SharedModule
  ],
  providers:[
    {
      provide: RouteReuseStrategy,
      useClass: RouterConfiguration
    }
  ]
})
export class NewsModule {
}
