import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {PageNotFoundComponent} from "./mShared/page-not-found/page-not-found.component";
import {UnderConstructionComponent} from "./mShared/under-construction/under-construction.component";

const routes: Routes = [
  {
    path:'',
    pathMatch: 'full',
    redirectTo: 'news'
  },
  {
    path:'under-construction',
    component: UnderConstructionComponent
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
    path: 'gdpr',
    component: UnderConstructionComponent
  },
  {
    path: 'privacy-policy',
    component: UnderConstructionComponent
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
