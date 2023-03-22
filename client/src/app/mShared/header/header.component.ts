import {Component, OnInit} from '@angular/core';
import {NamedLink, User} from "../../utils/types";
import {NavigationEnd, NavigationStart, Router} from "@angular/router";
import {filter, map} from "rxjs";
import {EventProviderService} from "../../core/event-provider/event-provider.service";
import {menuToggle} from "./animations";
import {UserService} from "../../core/user-service/user.service";
import {userEndpoints} from "../../../environments/environment";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
  animations: [menuToggle]
})
export class HeaderComponent implements OnInit {

  currentUser!:User|null;

  headerFlags: { [k: string]: boolean } = {
    'search': false,
    'profile': false,
    'menu': false,
    'more': false,
    'laptop-more': false
  }

  endpoints: { [k: string]: string } = {
    'todayNews': '/news/today',
    'analyses': '/news/analysis',
    'politics': '/news/politics',
    'business': '/news/business',
    'sport': '/news/sport',

    'login': '/user/login',
    'register': '/user/register',
    'settings': '/user/settings',
    'moderation': '/user/moderation',
    'administration': '/support/new-article',
    'logout': '/user/logout'
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
    'search': 'Търсене',
  }

  menuItems: { [k: string]: NamedLink } = {
    'todayNews': {href: this.endpoints['todayNews'], name: this.texts['todayNews']},
    'analyses': {href: this.endpoints['analyses'], name: this.texts['analyses']},
    'politics': {href: this.endpoints['politics'], name: this.texts['politics']},
    'business': {href: this.endpoints['business'], name: this.texts['business']},
    'sport': {href: this.endpoints['sport'], name: this.texts['sport']},
    'login': {href: this.endpoints['login'], name: this.texts['login']},
    'register': {href: this.endpoints['register'], name: this.texts['register']},
    'settings': {href: this.endpoints['settings'], name: this.texts['settings']},
    'moderation': {href: this.endpoints['moderation'], name: this.texts['moderation']},
    'administration': {href: this.endpoints['administration'], name: this.texts['administration']},
    'logout': {href: this.endpoints['logout'], name: this.texts['logout']},
  }

  constructor(private router: Router,
              private eventProvider: EventProviderService,
              private userService: UserService) {
  }

  ngOnInit(): void {
    this.router.events.pipe(
      filter(e => e instanceof NavigationEnd),
    ).subscribe(() => {
      this.closeAllPanels();
    });

    this.eventProvider.backgroundClick$().subscribe(() => this.closeAllPanels());
    this.userService.getUser$().subscribe(user => this.currentUser = user);


    //nasty :(
    this.router.events.pipe(
      filter(e => e instanceof NavigationStart),
      map(e => {
        if ((e as NavigationStart).url == '/' + this.endpoints['logout']) {
          this.userService.logout$().subscribe();
          this.router.navigateByUrl('/');
        }
      })
    ).subscribe();

    this.userService.validateSession();
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

  navigateFromButton(endpoint: string) {
    this.router.navigateByUrl(endpoint)
  }

  getNewsMenu(): NamedLink[] {
    return [
      this.menuItems['todayNews'],
      this.menuItems['analyses'],
      this.menuItems['politics'],
      this.menuItems['business'],
      this.menuItems['sport']
    ];
  }

  getProfileMenu(): NamedLink[] {
    let items: NamedLink[] = [];

    //todo check if enum will be appropriate
    if (this.currentUser) {
      items.push(this.menuItems['settings']);

      if (this.currentUser.roles.find(e=>e=='moderator')) {
        items.push(this.menuItems['moderation']);
      }

      if (this.currentUser.roles.find(e=>e=='administrator')) {
        items.push(this.menuItems['administration']);
      }

      items.push(this.menuItems['logout']);
    }

    if (!this.currentUser) {
      items.push(
        this.menuItems['login'],
        this.menuItems['register']
      );
    }

    return items;
  }

  getTabletMenu(): NamedLink[] {
    let items: NamedLink[] = [];

    items.push(
      this.menuItems['todayNews'],
      this.menuItems['analyses'],
      this.menuItems['politics'],
      this.menuItems['business'],
      this.menuItems['sport']
    );

    //todo refactor. DRY candidate
    if (this.currentUser) {
      items.push(this.menuItems['settings']);

      if (this.currentUser.roles.find(e=>e=='moderator')) {
        items.push(this.menuItems['moderation']);
      }
      if (this.currentUser.roles.find(e=>e=='administrator')) {
        items.push(this.menuItems['administration']);
      }
    }

    return items;
  }

  onLogout(){
    this.userService.logout$().subscribe();
  }
}
