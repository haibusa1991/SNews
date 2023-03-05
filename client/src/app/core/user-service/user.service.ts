import {Injectable} from '@angular/core';
import {Observable, Subject} from "rxjs";
import {RegisterResponse, User} from "../../utils/types";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {Endpoints} from "../../utils/endpoints";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private currentUser: User | null = null;
  private currentUserStatus = new Subject<User | null>();

  constructor(private http: HttpClient) {
  }

  login$(username: string, password: string): Observable<boolean> {
    let body = {username, password};
    return new Observable<boolean>(hasErrors =>
      this.http.post(Endpoints.postEndpoints['login'], body).subscribe(response => {
        let user = response as any as User;
        if (user.username) {
          this.currentUser = user;
          this.currentUserStatus.next(this.currentUser);
          hasErrors.next(false);
        }
        hasErrors.next(true);
      }));
  }

  getUser$(): Observable<User | null> {
    return this.currentUserStatus.asObservable();
  }

  register$(email: string, username: string, password: string): Observable<RegisterResponse> {
    let body = {email, username, password};
    return new Observable<RegisterResponse>(response =>
      this.http.post(Endpoints.postEndpoints['register'], body).subscribe(

      next => {
        response.next({isUsernameTaken: false, isEmailTaken: false});
      },
        error =>{

          let resp = error as HttpErrorResponse

          if (resp.status == 409 && resp.error.message == 'Username already registered.') {
            response.next({isUsernameTaken: true, isEmailTaken: false});
          }

          if (resp.status == 409 && resp.error.message == 'Email address is already registered.') {
            response.next({isUsernameTaken: false, isEmailTaken: true});
          }
        }
      )
    );
  }

  logout() {
    //todo properly implement - should notify server that the session has ended
    this.currentUser = null;
    this.currentUserStatus.next(null);
  }
}

