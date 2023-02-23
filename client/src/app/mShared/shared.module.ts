import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HeaderComponent} from './header/header.component';
import {FooterComponent} from './footer/footer.component';
import {PageNotFoundComponent} from './page-not-found/page-not-found.component';
import {Router, RouterModule} from "@angular/router";
import {IconButtonComponent} from "./icon-button/icon-button.component";
import {MatRippleModule} from "@angular/material/core";


@NgModule({
  declarations: [
    HeaderComponent,
    FooterComponent,
    PageNotFoundComponent,
    IconButtonComponent
  ],
  exports: [
    HeaderComponent,
    FooterComponent,
    IconButtonComponent
  ],
    imports: [
        CommonModule,
        RouterModule,
      MatRippleModule
    ]
})
export class SharedModule {
}
