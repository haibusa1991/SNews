import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {SharedModule} from "./mShared/shared.module";
import {NewsModule} from "./mNews/news.module";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {CsrfInterceptor} from "./core/interceptors/csrf-header";
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatRippleModule} from "@angular/material/core";
import {RouteReuseStrategy} from "@angular/router";
import {RouterConfiguration} from "./configuration/RouterConfiguration";

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    SharedModule,
    BrowserAnimationsModule,
    NewsModule,
    HttpClientModule,
    MatRippleModule,

  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: CsrfInterceptor,
      multi: true,
    }
  ],
  exports: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
