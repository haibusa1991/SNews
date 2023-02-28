import {Injectable} from '@angular/core';
import {Observable, Subject} from "rxjs";


export type User = {
  'username': string,
  'isModerator': boolean,
  'isAdmin': boolean
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  // readonly USERDATA: User = {
  //   'username': 'haibusa2005',
  //   isModerator: false,
  //   isAdmin: false
  // };

  // readonly USERDATA: User = {
  //   'username': 'haibusa2005',
  //   isModerator: true,
  //   isAdmin: false
  // };

  readonly USERDATA: User = {
    'username': 'haibusa2005',
    isModerator: true,
    isAdmin: true
  };

  private user: User | null = null;

  constructor() {
  }

  o:Subject<User|null> = new Subject<User | null>()

  hasUser(): boolean {
    return !!this.user;
  }

  login(): void {
    this.user = this.USERDATA;
    this.o.next(this.user);
  }

  logout(): void {
    this.user = null;
    this.o.next(this.user);
  }

  getUsername(): string {
    return this.user ? this.user.username : '<EMPTY>';
  }

  getUser$(): Observable<User | null> {
    return this.o;
  }
}
