import {Injectable} from '@angular/core';
import {AuthService} from "../auth/auth.service";
import {Observable, Subject} from "rxjs";
import {User} from "../../utils/types";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private dummyuser: User = {
    roles: [
      'user',
      'moderator',
      'admin'
    ],
    username: "dummyname"
  };

  private currentUser: User | null = null;

  // private usernameEvent: Subject<string> = new Subject();

  constructor(private authService: AuthService) {
  }

  login(username: string, password: string) {
    this.authService.login(username, password).subscribe(
      next => {
        console.log(next)
      });
  }

  getUser$(): Observable<User|null> {
    return new Observable(e => e.next(this.currentUser));
  }
}

