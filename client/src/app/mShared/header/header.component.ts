import {Component, OnInit} from '@angular/core';
import {ExtensibleMenuItem} from "../../types/types";
import {NavigationEnd, Router} from "@angular/router";
import {filter} from "rxjs";
import {EventProviderService} from "../../core/event-provider/event-provider.service";
import {menuToggle} from "./animations";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
  animations: [menuToggle]
})
export class HeaderComponent implements OnInit {
  hasUser: boolean = true;

  headerFlags: { [k: string]: boolean } = {
    'search': false,
    'profile': false,
    'menu': false,
    'more': false,
    'laptop-more': false
  }

  endpoints: { [k: string]: string } = {
    'todayNews': 'news/today',
    'analyses': 'news/analyses',
    'politics': 'news/politics',
    'business': 'news/business',
    'sport': 'news/sport',

    'login': 'user/login',
    'register': 'user/register',
    'settings': 'user/settings',
    'moderation': 'user/moderation',
    'administration': 'user/administration',
    'logout': 'user/logout',
  }

  texts: { [k: string]: string } = {
    'todayNews': 'Новините от деня',
    'analyses': 'Анализи',
    'politics': 'Политика',
    'business': 'Бизнес',
    'sport': 'Спорт',
    'login': 'Вход',
    'register': 'Регистрация',
    'settings': 'Настройки на профила',
    'moderation': 'Модераторски панел',
    'administration': 'Администраторски панел',
    'logout': 'Изход',
    'more': 'Още',
  }

  menuItems: ExtensibleMenuItem[] = [
    {href: this.endpoints['todayNews'], name: this.texts['todayNews']},
    {href: this.endpoints['analyses'], name: this.texts['analyses']},
    {href: this.endpoints['politics'], name: this.texts['politics']},
    {href: this.endpoints['business'], name: this.texts['business']},
    {href: this.endpoints['sport'], name: this.texts['sport']},
  ];

  profileMenu: ExtensibleMenuItem[] = [
    {href: this.endpoints['login'], name: this.texts['login']},
    {href: this.endpoints['register'], name: this.texts['register']},
    {href: this.endpoints['settings'], name: this.texts['settings']},
    {href: this.endpoints['moderation'], name: this.texts['moderation']},
    {href: this.endpoints['administration'], name: this.texts['administration']},
    {href: this.endpoints['logout'], name: this.texts['logout']},
  ];

  tabletMenu: ExtensibleMenuItem[] = [
    {href: this.endpoints['todayNews'],  name: this.texts['todayNews']},
    {href: this.endpoints['analyses'], name: this.texts['analyses']},
    {href: this.endpoints['politics'], name: this.texts['politics']},
    {href: this.endpoints['business'], name: this.texts['business']},
    {href: this.endpoints['sport'], name: this.texts['sport']},
    {href: this.endpoints['settings'], name: this.texts['settings']},
    {href: this.endpoints['moderation'], name: this.texts['moderation']},
    {href: this.endpoints['administration'], name: this.texts['administration']},
  ];

  laptopMenu: ExtensibleMenuItem[] = this.profileMenu;

  constructor(private router: Router,
              private eventProvider: EventProviderService) {
  }

  ngOnInit(): void {
    this.router.events.pipe(
      filter(e => e instanceof NavigationEnd),
    ).subscribe(() => {
      this.closeAllPanels();
    });

    this.eventProvider.backgroundClick$().subscribe(() => this.closeAllPanels());
  }


  toggleMoreMenu() {
    this.onMenuInteraction('more');
  }

  toggleLaptopMoreMenu() {
    this.onMenuInteraction('laptop-more');
  }

  toggleSearch() {
    this.onMenuInteraction('search');
  }

  toggleProfile() {
    this.onMenuInteraction('profile');
  }

  toggleMenu() {
    this.onMenuInteraction('menu');
  }

  closeAllPanels() {
    this.onMenuInteraction('');
  }

  onMenuInteraction(toggleFlag: string) {
    let isOpen = this.headerFlags[toggleFlag];

    for (let flag of Object.keys(this.headerFlags)) {
      this.headerFlags[flag] = false;
    }

    this.headerFlags[toggleFlag] = !isOpen;
  }

  onLogin() {
    this.hasUser = true
  }

  onLogout() {
    this.hasUser = false
  }

  navigate(endpoint: string) {
    console.log(`navigating to ${endpoint}. Arrr!`)
  }
}
