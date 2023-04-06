import {Injectable} from '@angular/core';
import {catchError, iif, mergeMap, Observable, of, Subject, switchMap, tap, throwError} from "rxjs";
import {RegisterResponse, User} from "../../utils/types";
import {HttpClient, HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {userEndpoints} from "../../../environments/environment";
import {Authority} from "../../utils/enums";
import * as http from "http";
import {error} from "@angular/compiler-cli/src/transformers/util";


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

  getCurrentUser$(forceRefresh?: boolean): Observable<User | null> {
    if (forceRefresh) {
      return new Observable<User>(result => {
        this.http.get(userEndpoints['getUser'], {responseType: 'text', withCredentials: true})
          .subscribe(currentUser => {
            this.currentUser = JSON.parse(currentUser);
            this.currentUserSubject.next(this.currentUser);
            this.currentUserSubject.next(this.currentUser);
            result.next(this.currentUser ? this.currentUser : undefined);
          });
      });
    }
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
          // this.currentUser = null;
          // this.currentUserSubject.next(this.currentUser);
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

    let registerResponse: RegisterResponse = {
      isUsernameTaken: false,
      isEmailTaken: false,
      isRegistrationClosed: false
    }

    return new Observable<RegisterResponse>(response =>
      httpPostRequest.pipe(
        catchError(() => httpPostRequest))
        .subscribe({
          next: () => {
            response.next(registerResponse);
          },
          error: e => {
            let resp = e as HttpErrorResponse

            if (resp.status == 409 && resp.error == 'Username already registered.') {
              registerResponse.isUsernameTaken = true;
            }

            if (resp.status == 409 && resp.error == 'Email address is already registered.') {
              registerResponse.isEmailTaken = true;
            }

            if (resp.status == 400 && resp.error == 'User registration is not allowed at this time.') {
              registerResponse.isRegistrationClosed = true;
            }

            response.next(registerResponse);
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

  uploadAvatar$(avatar: File): Observable<boolean> {
    let body = new FormData();
    body.append('image', avatar);

    let httpPostRequest = this.http.post(userEndpoints['uploadAvatar'], body,
      {
        responseType: 'text' as const,
        withCredentials: true
      }
    );

    return new Observable<boolean>(isSuccessful => {
      httpPostRequest.pipe(
        catchError(() => httpPostRequest),
      ).subscribe({
        next: () => {
          this.validateSession();
          isSuccessful.next(true)
        },
        error: () => {
          isSuccessful.next(false);
        }
      })
    });
  }

  getCurrentUser(): User | null {
    this.validateSession();
    return this.currentUser;
  }

  removeAvatar$(): Observable<boolean> {
    let httpPostRequest = this.http.post(userEndpoints['removeAvatar'], {},
      {
        responseType: 'text' as const,
        withCredentials: true
      }
    );

    return new Observable<boolean>(isSuccessful => {
      httpPostRequest.pipe(
        catchError(() => httpPostRequest),
      ).subscribe(() => {
        isSuccessful.next(true);
      });
    });
  }

  makePostRequest$(endpoint: string, payload: FormData): Observable<boolean> {
    let httpPostRequest = this.http.post(endpoint, payload,
      {
        responseType: 'text' as const,
        withCredentials: true
      }
    );

    return new Observable(isSuccessful => {
      httpPostRequest.pipe(
        catchError(() => httpPostRequest)
      ).subscribe({
        next: () => isSuccessful.next(true),
        error: () => isSuccessful.next(false)
      });
    });
  }

  updateUserAuthorities$(username: string, action: Authority): Observable<boolean> {
    let httpPostRequest = this.http.post(userEndpoints['updateAuthorities'],
      {
        username: username,
        action: Authority[action]
      },
      {
        responseType: 'text' as const,
        withCredentials: true
      }
    );

    return new Observable<boolean>(isSuccessful => {
      httpPostRequest.pipe(
        catchError(e => (e as HttpResponse<any>).status == 403 ? httpPostRequest : throwError(() => e))
      ).subscribe({
        next: () => {
          isSuccessful.next(true)
        },
        error: () => {
          isSuccessful.next(false)
        }
      })
    })
  }
}

