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
import { SearchBarComponent } from './search-bar/search-bar.component';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatIconModule} from "@angular/material/icon";
import {ReactiveFormsModule} from "@angular/forms";
import {MatButtonModule} from "@angular/material/button";


@NgModule({
  declarations: [
    HeaderComponent,
    FooterComponent,
    PageNotFoundComponent,
    IconButtonComponent,
    TextButtonComponent,
    ExtensibleMenuComponent,
    BackgroundComponent,
    SearchBarComponent
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
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    ReactiveFormsModule,
    MatButtonModule
  ]
})
export class SharedModule {
}
