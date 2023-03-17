import { Injectable } from '@angular/core';
import {Observable, switchMap} from "rxjs";
import {apiEndpoints, articleEndpoints} from "../../../environments/environment";
import {ArticleOverviewData} from "../../utils/types";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  constructor(private http: HttpClient) { }

  doSearch(searchPhrase:string) : Observable<ArticleOverviewData[]> {
      return new Observable<ArticleOverviewData[]>(res => {
        this.http.get(`${apiEndpoints['search']}/${searchPhrase}`,{responseType:'text'})
          .subscribe(article => res.next(JSON.parse(article as string) as ArticleOverviewData[]))
      })
  }
}
