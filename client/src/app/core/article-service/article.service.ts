import {Injectable} from '@angular/core';
import {articleEndpoints, userEndpoints} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable, switchMap} from "rxjs";
import {error} from "@angular/compiler-cli/src/transformers/util";
import {FormGroup} from "@angular/forms";
import {articleCategories} from "../../utils/snewsConstants";
import {Article, ArticleOverviewData} from "../../utils/types";
import {ArticleOverviewComponent} from "../../mHome/article-overview/article-overview.component";

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  constructor(private http: HttpClient) {
  }

  getToken$() {
    return this.http.get(userEndpoints['csrf'],
      {responseType: 'text', withCredentials: true},)
  }

  postNewArticle$(inputForm: FormGroup, pictureFile: File):Observable<string> {
    let body = new FormData();
    let input = inputForm.controls;
    body.append('heading', input['heading'].value);
    body.append('pictureSource', input['pictureSource'].value);
    body.append('article', input['article'].value);
    body.append('author', input['author'].value);

    let jsonCategories: string[] = [];
    let inputCategories = input['categories'].value
    for (let category of inputCategories) {
      let jsonCat = Object.keys(articleCategories).find(e => articleCategories[e] === category)

      if (!jsonCat) {
        jsonCategories.push(category);
      }

      if (jsonCat) {
        jsonCategories.push(jsonCat)
      }
    }

    body.append('categories', jsonCategories.toString());
    body.append('pictureFile', pictureFile);

    return new Observable<string>(result => {
      this.getToken$().pipe(
        switchMap(() => {
          return this.http.post(articleEndpoints['newArticle'], body, {
            responseType: 'text' as const,
            withCredentials: true
          });
        }),
      ).subscribe(link => result.next(link))
    })
  }

  getArticleCategories$(): Observable<string[]> {
    return new Observable<string[]>(res => {
      this.http.get(articleEndpoints['articleCategories'], {responseType: 'text'}).subscribe(
        {
          next: n => {
            res.next(JSON.parse(n))
          },
          error: e => {
            res.error(e)
          }
        })
    });
  }

  getArticle$(href:string):Observable<Article>{
    return new Observable<Article>(res => {
      this.http.get(`${articleEndpoints['articles']}/${href}`).subscribe(article => res.next(article as Article))
    })
  }

  getHomeArticles$():Observable<ArticleOverviewData[]>{
    return new Observable<ArticleOverviewData[]>(res => {
      this.http.get(articleEndpoints['homePageArticles'],{responseType:'text'})
        .subscribe(article => res.next(JSON.parse(article as string) as ArticleOverviewData[]))
    })
  }

  getArticlesByCategory$(category:string):Observable<ArticleOverviewData[]>{
    return new Observable<ArticleOverviewData[]>(res => {
      this.http.get(`${articleEndpoints['articleByCategory']}/${category}`,{responseType:'text'})
        .subscribe(article => res.next(JSON.parse(article as string) as ArticleOverviewData[]))
    })
  }

  getTodayArticles$():Observable<ArticleOverviewData[]>{
    return this.getArticlesByCategory$('today')
  }
}
