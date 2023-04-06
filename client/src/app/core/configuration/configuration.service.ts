import {Injectable} from '@angular/core';
import {catchError, Observable, of, throwError} from "rxjs";
import {ServerConfiguration} from "../../utils/types";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {configurationEndpoints, userEndpoints} from "../../../environments/environment";
import {Authority} from "../../utils/enums";

@Injectable({
  providedIn: 'root'
})
export class ConfigurationService {

  constructor(private http: HttpClient) {
  }

  getState$(): Observable<ServerConfiguration> {
    return new Observable<ServerConfiguration>(state => {
      this.http.get(configurationEndpoints['getState'], {responseType: 'text', withCredentials: true})
        .subscribe(s => {
          state.next(JSON.parse(s) as ServerConfiguration)
        })
    });
  }

  setState$(state: ServerConfiguration): Observable<boolean> {
    let httpPostRequest = this.http.post(configurationEndpoints['setState'],
      state,
      {
        responseType: 'text' as const,
        withCredentials: true
      }
    );

    return new Observable<boolean>(isSuccessful => {
      httpPostRequest.pipe(
        catchError(e => (e as HttpResponse<any>).status == 403 ? httpPostRequest : throwError(() => e))
      ).subscribe({
        next: () => isSuccessful.next(true),
        error: () => isSuccessful.error(false)
      });
    });

  }

  isRegistrationEnabled$():Observable<boolean> {
    return new Observable<boolean> (isEnabled=> {
      this.http.get(configurationEndpoints['canRegister'], {responseType: 'text'})
        .subscribe(val => { isEnabled.next(val == 'true')
        });
    });
  }
}
