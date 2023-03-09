import {Injectable, isDevMode} from '@angular/core';
import {Observable, Subject} from "rxjs";
import {RegisterResponse, User} from "../../utils/types";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {Endpoints} from "../../utils/endpoints";
import {userEndpoints} from "../../../environments/environment";


@Injectable({
  providedIn: 'root'
})
export class UserService {

  private currentUser: User | null = null;
  private currentUserSubject = new Subject<User | null>();

  constructor(private http: HttpClient) {
  }


  getToken$() {
    return this.http.get(userEndpoints['csrf'],
      {responseType: 'text', withCredentials: true},)
  }

  login$(email: string, password: string) {
    let form = new FormData()
    form.append('username', email);
    form.append('password', password);

    //todo try refactoring

    return new Observable<boolean>(result => {
      this.getToken$().subscribe(() => {
        this.http.post(userEndpoints['login'], form, {
          responseType: 'text' as const,
          withCredentials: true,
          observe: 'response'
        }).subscribe({

          next: () => {
            this.getCurrentUser$().subscribe(currentUser => {
              let user: User = JSON.parse(currentUser);
              if (user.username == 'anonymousUser') {
                result.next(false);
                return;
              }
              this.currentUser = user;
              this.currentUserSubject.next(this.currentUser);
              result.next(true);
            });
          },

          error: () => {
            // todo isDevMode
            if (isDevMode()) {
              this.getCurrentUser$().subscribe(currentUser => {
                let user: User = JSON.parse(currentUser);
                if (user.username == 'anonymousUser') {
                  result.next(false);
                  return;
                }
                this.currentUser = user;
                this.currentUserSubject.next(this.currentUser);
                result.next(true);
              });
            }
            result.next(false);
          }
        });
      });
    });
  }

//todo refactor to remove getUser$
  getUser$(): Observable<User | null> {
    return this.currentUserSubject.asObservable();
  }

  getCurrentUser$(): Observable<string> {
    return this.http.get(userEndpoints['getUser'], {responseType: 'text', withCredentials: true});
  }

  validateSession(): void {
    this.getCurrentUser$().subscribe(currentUser => {
      let user: User = JSON.parse(currentUser);
      if (user.username == 'anonymousUser') {
        return;
      }
      this.currentUser = user;
      this.currentUserSubject.next(this.currentUser);
    })
  }

  register$(email: string, username: string, password: string): Observable<RegisterResponse> {
    // let body = new FormData();
    // body.append('email',email);
    // body.append('username',username);
    // body.append('password',password);

    return new Observable<RegisterResponse>(response =>
      this.getToken$().subscribe(() => {
        this.http.post(userEndpoints['register'], {email, username, password}, {
          responseType: 'text',
          withCredentials: true,
        }).subscribe({
          next: () => {
            response.next({isUsernameTaken: false, isEmailTaken: false});
          },
          error: e => {
            let resp = e as HttpErrorResponse

            if (resp.status == 409 && resp.error == 'Username already registered.') {
              response.next({isUsernameTaken: true, isEmailTaken: false});
            }

            if (resp.status == 409 && resp.error == 'Email address is already registered.') {
              response.next({isUsernameTaken: false, isEmailTaken: true});
            }
          }
        })
      })
    );
  }

  logout$(): Observable<void> {
    return new Observable<void>(() => {
      this.getToken$().subscribe({
        next: () => {
          this.http.post(userEndpoints['logout'], {}, {responseType: 'text'}).subscribe();
          this.currentUser = null;
          this.currentUserSubject.next(this.currentUser);
        }
      })
    });
  }

  recoverPassword$(emailAddress: string):Observable<void> {
    return new Observable(()=>{
      this.getToken$().subscribe(()=>{
        this.http.post(userEndpoints['forgottenPassword'],{'email':emailAddress},{responseType:"text",withCredentials:true}).subscribe();
      })
    })


  }
}

