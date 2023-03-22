import {Component, Input, OnInit} from '@angular/core';
import {rotateArrow} from "./animations";

@Component({
  selector: 'app-text-button',
  templateUrl: './text-button.component.html',
  styleUrls: ['./text-button.component.scss'],
  animations: [rotateArrow]
})
export class TextButtonComponent implements OnInit {
  rippleColor = '#FFFFFF60';

  @Input()
  fontSize = '';
  @Input()
  buttonText!: string;
  @Input()
  isDropdown!: boolean;
  @Input()
  isOpen!: boolean;
  @Input()
  isDisabled: boolean = false;
  @Input()
  href:String='';

  constructor() {
  }

  ngOnInit(): void {
  }


}
