import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {PageNotFoundComponent} from "./mShared/page-not-found/page-not-found.component";

const routes: Routes = [
  {
    path:'',
    pathMatch: 'full',
    redirectTo: 'news'
  },
  {
    path: 'news',
    loadChildren: () => import('./mNews/news.module').then(m => m.NewsModule)
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
    path: '404',
    component: PageNotFoundComponent
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
