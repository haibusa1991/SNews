import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  isSearchOpen: boolean = false;
  isProfileOpen: boolean = false;
  isMenuOpen: boolean = false;

  constructor() {
  }

  ngOnInit(): void {
  }


  //todo refactor the boolean galore
  toggleSearch() {
    this.isMenuOpen = false;
    this.isProfileOpen = false;
    this.isSearchOpen = !this.isSearchOpen;
  }

  toggleProfile() {
    this.isSearchOpen = false;
    this.isMenuOpen = false;
    this.isProfileOpen = !this.isProfileOpen;
  }

  toggleMenu() {
    this.isSearchOpen = false;
    this.isProfileOpen = false;
    this.isMenuOpen = !this.isMenuOpen;
  }

  closeAllPanels() {
    this.isSearchOpen = false;
    this.isMenuOpen = false;
    this.isProfileOpen = false;
  }
}
