import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {PageNotFoundComponent} from "./mShared/page-not-found/page-not-found.component";
import {HomeComponent} from "./mHome/home/home.component";

const routes: Routes = [
  {
    path: '',
    pathMatch: "full",
    component: HomeComponent
  },
  {
    path: 'moderation',
    loadChildren: () => import('./mModerator/moderator.module').then(m => m.ModeratorModule)
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
