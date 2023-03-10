import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {menu, profile, search, xmark} from "./icons";
import {toggleMain, toggleXMark} from "./animations";
import {Icon} from "../../utils/types";

@Component({
  selector: 'app-icon-button',
  templateUrl: './icon-button.component.html',
  styleUrls: ['./icon-button.component.scss'],
  animations: [
    toggleXMark,
    toggleMain
  ]
})
export class IconButtonComponent implements OnInit {
  // rippleColor: string = '#00C53730';
  rippleColor: string = '#FFFFFF60';

  @Input()
  buttonIcon!: string;
  @Input()
  isOpen!: boolean

  icon!: Icon;

  icons: { [key: string]: Icon } = {
    'search': search,
    'xmark': xmark,
    'menu': menu,
    'profile': profile
  }

  constructor() {
  }

  ngOnInit(): void {
    this.icon = this.icons[this.buttonIcon];
  }
}
