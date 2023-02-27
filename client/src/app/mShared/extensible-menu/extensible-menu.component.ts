import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ExtensibleMenuItem} from "../../types/types";
import {menuToggle} from "../header/animations";

@Component({
  selector: 'app-extensible-menu',
  templateUrl: './extensible-menu.component.html',
  styleUrls: ['./extensible-menu.component.scss'],
  animations:[menuToggle]
})
export class ExtensibleMenuComponent implements OnInit {

  @Input()
  menuItems!: ExtensibleMenuItem[];

  @Input()
  isOpen!:boolean;

  @Output()
  hasInteracted = new EventEmitter<boolean>();

  constructor() {
  }

  ngOnInit(): void {
  }

  onInteraction() {
    this.hasInteracted.emit(true);
  }
}
