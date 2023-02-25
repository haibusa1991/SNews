import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HeaderComponent} from './header/header.component';
import {FooterComponent} from './footer/footer.component';
import {PageNotFoundComponent} from './page-not-found/page-not-found.component';
import {RouterModule} from "@angular/router";
import {IconButtonComponent} from "./icon-button/icon-button.component";
import {MatRippleModule} from "@angular/material/core";
import {TextButtonComponent} from './text-button/text-button.component';
import {ExtensibleMenuComponent} from './extensible-menu/extensible-menu.component';
import { BackgroundComponent } from './background/background.component';


@NgModule({
  declarations: [
    HeaderComponent,
    FooterComponent,
    PageNotFoundComponent,
    IconButtonComponent,
    TextButtonComponent,
    ExtensibleMenuComponent,
    BackgroundComponent
  ],
    exports: [
        HeaderComponent,
        FooterComponent,
        BackgroundComponent
    ],
  imports: [
    CommonModule,
    RouterModule,
    MatRippleModule,
  ]
})
export class SharedModule {
}
