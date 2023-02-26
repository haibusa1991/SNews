import {Component, OnInit} from '@angular/core';
import {ExtensibleMenuItem} from "../../types/types";
import {NavigationEnd, Router} from "@angular/router";
import {filter, tap} from "rxjs";
import {EventProviderService} from "../../core/event-provider.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  isSearchOpen: boolean = false;
  isProfileOpen: boolean = false;
  isMenuOpen: boolean = false;
  isMoreOpen: boolean = false;
  hasUser: boolean = false;
  // hasUser: boolean = true;

  menuItems: ExtensibleMenuItem[] = [
    {href: 'news/today', name: 'Новините от деня'},
    {href: 'news/analyses', name: 'Анализи'},
    {href: 'news/politics', name: 'Политика'},
    {href: 'news/business', name: 'Бизнес'},
    {href: 'news/sport', name: 'Спорт'},
  ];

  profileMenu: ExtensibleMenuItem[] = [
    {href: 'user/login', name: 'Вход'},
    {href: 'user/register', name: 'Регистрация'},
    {href: 'user/settings', name: 'Настройки на профила'},
    {href: 'user/moderation', name: 'Модераторски панел'},
    {href: 'user/administration', name: 'Администраторски панел'},
    {href: 'user/logout', name: 'Изход'},
  ];

  constructor(private router: Router,
              private eventProvider:EventProviderService) {
  }

  ngOnInit(): void {
    this.router.events.pipe(
      filter(e => e instanceof NavigationEnd),
    ).subscribe(() => {
      this.closeAllPanels();
    });

    this.eventProvider.$backgroundClick().subscribe(() => this.closeAllPanels());
  }


  //todo refactor the boolean galore

  toggleMoreMenu() {
    this.isMoreOpen = !this.isMoreOpen;
  }

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

  onMenuInteraction() {
    this.closeAllPanels()
  }
}
