import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {PageNotFoundComponent} from "./mShared/page-not-found/page-not-found.component";
import {HomeComponent} from "./mHome/home/home.component";
import {ArticleComponent} from "./mHome/article/article.component";
import {NewsSearchResultsComponent} from "./mHome/news-search-results/news-search-results.component";

const routes: Routes = [
  {
    path: '',
    pathMatch: "full",
    component: HomeComponent
  },
  {
    path: 'news/:category',
    component: NewsSearchResultsComponent
  },
  {
    path: 'article/:articleHref',
    component: ArticleComponent
  },
  {
    path: 'support',
    loadChildren: () => import('./mSupport/support.module').then(m => m.SupportModule)
  },
  {
    path: 'user',
    loadChildren: () => import('./mUser/user.module').then(m => m.UserModule)
  },
  {
    path: '**',
    component: PageNotFoundComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
