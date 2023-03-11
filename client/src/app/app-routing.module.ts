import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {PageNotFoundComponent} from "./mShared/page-not-found/page-not-found.component";
import {HomeComponent} from "./mHome/home/home.component";
import {ArticleComponent} from "./mHome/article/article.component";

const routes: Routes = [
  {
    path: '',
    pathMatch: "full",
    component: HomeComponent
  },

  {
    path: 'articles/article-url-goes-here',
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
