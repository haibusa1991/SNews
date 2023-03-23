import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {SharedModule} from "./mShared/shared.module";
import {NewsModule} from "./mNews/news.module";
import {HTTP_INTERCEPTORS, HttpClientModule, HttpClientXsrfModule, HttpXsrfTokenExtractor} from "@angular/common/http";
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatRippleModule} from "@angular/material/core";
import {HttpXsrfInterceptor} from "./core/interceptors/xsrf-interceptor";

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
    HttpClientXsrfModule,
    MatRippleModule,

  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: HttpXsrfInterceptor, multi: true }
  ],
  exports: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
