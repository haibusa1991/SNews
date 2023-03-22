import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HeaderComponent} from './header/header.component';
import {FooterComponent} from './footer/footer.component';
import {PageNotFoundComponent} from './page-not-found/page-not-found.component';
import {RouterModule} from "@angular/router";
import {IconButtonComponent} from "./icon-button/icon-button.component";
import {TextButtonComponent} from './text-button/text-button.component';
import {ExtensibleMenuComponent} from './extensible-menu/extensible-menu.component';
import {BackgroundComponent} from './background/background.component';
import {SearchBarComponent} from './search-bar/search-bar.component';
import {MatIconModule} from "@angular/material/icon";
import {ReactiveFormsModule} from "@angular/forms";
import {WarningLabelComponent} from './warning-label/warning-label.component';
import {FilenamePipe} from "./pipes/filename.pipe";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
import {MatRippleModule} from "@angular/material/core";
import {DateTimePipe} from "./pipes/date-time.pipe";
import { ComponentSpinnerComponent } from './component-spinner/component-spinner.component';
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";


@NgModule({
  declarations: [
    HeaderComponent,
    FooterComponent,
    PageNotFoundComponent,
    IconButtonComponent,
    TextButtonComponent,
    ExtensibleMenuComponent,
    BackgroundComponent,
    SearchBarComponent,
    WarningLabelComponent,
    FilenamePipe,
    DateTimePipe,
    ComponentSpinnerComponent
  ],
    exports: [
        HeaderComponent,
        FooterComponent,
        BackgroundComponent,
        TextButtonComponent,
        WarningLabelComponent,
        FilenamePipe,
        DateTimePipe,
        ComponentSpinnerComponent
    ],
  imports: [
    CommonModule,
    RouterModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatRippleModule,
    MatProgressSpinnerModule
  ]
})
export class SharedModule {
}
