import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {User} from "../../utils/types";
import {HttpClient} from "@angular/common/http";
import {queryEndpoints} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class QueryService {

  constructor(private http:HttpClient) { }

  findUsersByUsername$(query: string):Observable<User[]> {
     return new Observable<User[]>(result => {
       this.http.get(`${queryEndpoints['findUser']}${query}`, {responseType: "text"})
         .subscribe(response => result.next(JSON.parse(response)))
     });
  }
}
