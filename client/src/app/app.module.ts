import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {SharedModule} from "./mShared/shared.module";
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {HomeModule} from "./mHome/home.module";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {CsrfInterceptor} from "./core/interceptors/csrf-header";

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    SharedModule,
    BrowserAnimationsModule,
    HomeModule,
    HttpClientModule,

  ],
  providers:[
    {
      provide: HTTP_INTERCEPTORS,
      useClass: CsrfInterceptor,
      multi: true,
    },
  ],
  exports: [
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
