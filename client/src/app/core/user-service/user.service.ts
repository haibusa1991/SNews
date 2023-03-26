import {Injectable} from '@angular/core';
import {catchError, Observable, Subject, switchMap} from "rxjs";
import {RegisterResponse, User} from "../../utils/types";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {userEndpoints} from "../../../environments/environment";


@Injectable({
  providedIn: 'root'
})
export class UserService {

  private currentUser: User | null = null;
  private currentUserSubject = new Subject<User | null>();
  private urlBeforeLogin: string | null = null;

  constructor(private http: HttpClient) {
  }

  login$(email: string, password: string) {
    let form = new FormData()
    form.append('username', email);
    form.append('password', password);

    let httpPostRequest = this.http.post(userEndpoints['login'], form, {
      responseType: 'text' as const,
      withCredentials: true
    });

    let currentUser = this.http.get(userEndpoints['getUser'], {responseType: 'text', withCredentials: true});

    return new Observable<boolean>(result => {
      httpPostRequest.pipe(
        catchError(() => httpPostRequest),
        switchMap(() => httpPostRequest),
        catchError(() => currentUser),
      ).subscribe({
        next: n => {
          let user: User = JSON.parse(n);
          if (user.username == 'anonymousUser') {
            result.next(false);
            return;
          }
          this.currentUser = user;
          this.currentUserSubject.next(this.currentUser);
          result.next(true);
        }
      })
    });
  }

  getCurrentUser$(): Observable<User | null> {
    return this.currentUserSubject.asObservable();
  }

  getCurrentUsername(): string {
    return this.currentUser?.username ? this.currentUser.username : '';
  }

  validateSession(): void {
    this.http.get(userEndpoints['getUser'], {responseType: 'text', withCredentials: true})
      .subscribe(currentUser => {
        let user: User = JSON.parse(currentUser);
        if (user.username == 'anonymousUser') {
          return;
        }
        this.currentUser = user;
        this.currentUserSubject.next(this.currentUser);
      })
  }

  register$(email: string, username: string, password: string): Observable<RegisterResponse> {
    let httpPostRequest = this.http.post(userEndpoints['register'], {email, username, password}, {
      responseType: 'text',
      withCredentials: true,
    });

    return new Observable<RegisterResponse>(response =>
      httpPostRequest.pipe(
        catchError(() => httpPostRequest))
        .subscribe({
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
    );
  }

  logout$(): Observable<void> {
    let httpPostRequest = this.http.post(userEndpoints['logout'], {}, {responseType: 'text'});

    return new Observable<void>(() => {
      httpPostRequest.pipe(
        catchError(() => httpPostRequest))
        .subscribe(() => {
          this.currentUser = null;
          this.urlBeforeLogin = '/';
          this.currentUserSubject.next(this.currentUser);
        });
    })
  }

  recoverPassword$(emailAddress: string): Observable<void> {
    let httpPostRequest = this.http.post(userEndpoints['forgottenPassword'],
      {
        'email': emailAddress
      },
      {
        responseType: "text",
        withCredentials: true
      });

    return new Observable<void>(() => {
      httpPostRequest.pipe(
        catchError(() => httpPostRequest))
        .subscribe();
    });
  }

  resetPassword$(password: string, token: string): Observable<boolean> {
    let httpPostRequest = this.http.post(userEndpoints['passwordReset'],
      {
        'password': password,
        'recoveryToken': token
      },
      {
        responseType: "text",
        withCredentials: true
      });

    return new Observable(response => {
      httpPostRequest.pipe(
        catchError(() => httpPostRequest)
      ).subscribe({
        next: () => {
          response.next(true)
        },
        error: () => {
          response.next(false)
        }
      });
    });
  }

  setUrlBeforeLogin(url: string) {
    this.urlBeforeLogin = url;
  }

  getUrlBeforeLogin(): string | null {
    return this.urlBeforeLogin;
  }
}

