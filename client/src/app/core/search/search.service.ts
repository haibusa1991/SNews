import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  constructor() { }

  //todo implement - returns json with article ids
  doSearch(searchPhrase:string) : string {
    return `result for search query "${searchPhrase}" goes here`
  }
}
