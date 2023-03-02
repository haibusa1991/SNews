import {Injectable} from '@angular/core';
import {HttpClient,} from "@angular/common/http";
import {User} from "../../utils/types";
import {Endpoints} from "../../utils/endpoints";
import {Observable} from "rxjs";


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) {
  }

  login(username: string, password: string): Observable<any> {
    let body = {
      'username': username,
      'password': password
    }

    return this.http.post(Endpoints.postEndpoints['login'], body);
  }

  register(username: string, email: string, password: string): Observable<any> {
    let body = {
      'username': username,
      'password': password
    }

    return this.http.post(Endpoints.postEndpoints['login'], body);
  }

  logout(): void {
    this.http.post(Endpoints.postEndpoints['logout'], {});
  }

}
