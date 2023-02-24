import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HeaderComponent} from './header/header.component';
import {FooterComponent} from './footer/footer.component';
import {PageNotFoundComponent} from './page-not-found/page-not-found.component';
import {RouterModule} from "@angular/router";
import {IconButtonComponent} from "./icon-button/icon-button.component";
import {MatRippleModule} from "@angular/material/core";
import { TextButtonComponent } from './text-button/text-button.component';


@NgModule({
  declarations: [
    HeaderComponent,
    FooterComponent,
    PageNotFoundComponent,
    IconButtonComponent,
    TextButtonComponent
  ],
  exports: [
    HeaderComponent,
    FooterComponent,
    IconButtonComponent
  ],
    imports: [
        CommonModule,
        RouterModule,
        MatRippleModule,
    ]
})
export class SharedModule {
}
